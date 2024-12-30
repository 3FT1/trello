package com.example.trello.card;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.card.cardrepository.CardRepositoryCustomImpl;
import com.example.trello.card.responsedto.CardPageResponseDto;
import com.example.trello.card.requestDto.CardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.card.requestDto.UpdateCardRequestDto;
import com.example.trello.card.responsedto.UpdateCardResponseDto;
import com.example.trello.cardlist.CardList;
import com.example.trello.cardlist.CardListRepository;
import com.example.trello.common.exception.WorkspaceMemberErrorCode;
import com.example.trello.common.exception.WorkspaceMemberException;
import com.example.trello.config.auth.UserDetailsImpl;
import com.example.trello.workspace_member.WorkspaceMember;
import com.example.trello.workspace_member.WorkspaceMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



import static com.example.trello.workspace_member.WorkspaceMemberRole.WORKSPACE;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardListRepository cardListRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * 카드 CRUD
     */

    // 카드 생성
    @Transactional
    public CardResponseDto createdCardService(CardRequestDto requestDto, UserDetailsImpl userDetails) {

        CardList cardList = cardListRepository.findByIdOrElseThrow(requestDto.getCardListId());

        Long workspaceId = cardList.getBoard().getWorkspace().getId();

        WorkspaceMember workspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(userDetails.getUser().getId(), workspaceId);

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(workspaceMember.getId(), workspaceId)) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }

        if (workspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("카드를 생성할 권한이 없습니다.");
        }

        Card card = Card.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .workspaceMember(workspaceMember)
                .startAt(requestDto.getStartAt())
                .endAt(requestDto.getEndAt())
                .cardList(cardList)
                .build();
        cardRepository.save(card);

        return CardResponseDto.toDto(card);
    }

    //카드 업데이트
    @Transactional
    public CardResponseDto updateCardService(Long cardId, UpdateCardRequestDto requestDto, UserDetailsImpl userDetails) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        WorkspaceMember workspaceMember = findWorkSpaceMember(userDetails, cardId);

        CardList cardList = cardRepository.findByIdOrElseThrow(requestDto.getCardListId()).getCardList();

        UpdateCardResponseDto responseDto = new UpdateCardResponseDto(cardList, requestDto.getTitle(), requestDto.getDescription(), requestDto.getStartAt(), requestDto.getEndAt());

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(workspaceMember.getId(), card.getCardList().getBoard().getWorkspace().getId())) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }

        if (workspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("카드를 수정할 권한이 없습니다.");
        }

        card.updateCard(responseDto);

        cardRepository.save(card);

        return CardResponseDto.toDto(card);
    }

    // 카드 삭제
    @Transactional
    public void deleteCardService(Long cardId, UserDetailsImpl userDetails) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        WorkspaceMember workspaceMember = findWorkSpaceMember(userDetails, cardId);

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(workspaceMember.getId(), card.getCardList().getBoard().getWorkspace().getId())) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }

        if (workspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("카드를 삭제할 권한이 없습니다.");
        }

        cardRepository.delete(card);
    }

    // 카드 단건 조회
    @Transactional
    public CardResponseDto findCardById(Long cardId) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);
        return CardResponseDto.toDto(card);
    }

    // 카드 다건 조회(조건 O)
    @Transactional
    public CardPageResponseDto searchCards(int page , Long cardListId, LocalDate startAt, LocalDate endAt, Long boardId) {

        PageRequest pageRequest = PageRequest.of(page,10, Sort.by(Sort.Direction.DESC, "id"));

        CardPageResponseDto cards = cardRepository.searchCard(pageRequest, cardListId, startAt, endAt, boardId);

        return cards;
    }



    /**
     * 파일 업로드
     */


    // 파일 업로드
    @Transactional
    public String uploadFile(Long cardId, MultipartFile file, UserDetailsImpl userDetails)  {

        WorkspaceMember workspaceMember = findWorkSpaceMember(userDetails, cardId);

        Card card = cardRepository.findByIdOrElseThrow(cardId);

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(workspaceMember.getId(), card.getCardList().getBoard().getWorkspace().getId())) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }


        if (workspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("파일을 업로드할 권한이 없습니다.");
        }

        // 파일 형식 예외처리
        List<String> contentTypeList = new ArrayList<>();
        contentTypeList.add("jpg");
        contentTypeList.add("png");
        contentTypeList.add("pdf");
        contentTypeList.add("csv");

        if (StringUtils.hasText(file.getContentType())) {
                if (!contentTypeList.contains(file.getName().substring(file.getName().lastIndexOf('.') + 1).toLowerCase())) {
                    throw new RuntimeException("지원하지 않는 파일 형식입니다");
                }
        }


        // 파일 크기 예외처리
        long maxSize = 5 * 1024 * 1024;

        if (file.getSize() > maxSize) {
            throw new RuntimeException("파일의 크기가 5MB를 넘었습니다 파일의 크기를 줄여주세요");
        }

        String fileName = file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        card.uploadFile(file.getOriginalFilename());
        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    // 파일 삭제
    @Transactional
    public void deleteFile(Long cardId, String fileName, UserDetailsImpl userDetails) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        WorkspaceMember workspaceMember = findWorkSpaceMember(userDetails, cardId);

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(workspaceMember.getId(), card.getCardList().getBoard().getWorkspace().getId())) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }

        if (workspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("파일을 삭제할 권한이 없습니다.");
        }

//        if (card.getFileName() == null) {
//            throw new RuntimeException();
//        }

        card.deleteFile();
        amazonS3Client.deleteObject(bucketName, fileName);
    }

    @Transactional
    public String getFile(Long cardId, UserDetailsImpl userDetails) {

        Card card = cardRepository.findByIdOrElseThrow(cardId);

        try {
            return amazonS3Client.getUrl(bucketName, card.getFileName()).toString();
        } catch (Exception e) {
            throw new RuntimeException("File not found: " + card.getFileName(), e);
        }
    }


    /**
     * 편의성 메소드
     */

    // WorkspaceMember 찾아주는 메소드
    @Transactional
    public WorkspaceMember findWorkSpaceMember(UserDetailsImpl userDetails, Long cardId) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        Long workspaceId = card.getCardList().getBoard().getWorkspace().getId();

        return workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(userDetails.getUser().getId(), workspaceId);
    }

}
