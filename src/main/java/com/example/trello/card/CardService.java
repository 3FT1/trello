package com.example.trello.card;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.card.responsedto.CardPageResponseDto;
import com.example.trello.card.requestDto.CardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.card.requestDto.UpdateCardRequestDto;
import com.example.trello.card.responsedto.UpdateCardResponseDto;
import com.example.trello.cardlist.CardList;
import com.example.trello.cardlist.CardListRepository;
import com.example.trello.common.exception.CardErrorCode;
import com.example.trello.common.exception.CardException;
import com.example.trello.common.exception.WorkspaceMemberErrorCode;
import com.example.trello.common.exception.WorkspaceMemberException;
import com.example.trello.config.auth.UserDetailsImpl;
import com.example.trello.notification.NotificationService;
import com.example.trello.workspace.WorkSpaceRepository;
import com.example.trello.workspace_member.WorkspaceMember;
import com.example.trello.workspace_member.WorkspaceMemberRepository;
import com.example.trello.workspace_member.WorkspaceMemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;


import static com.example.trello.notification.NotificationType.UPDATE_CARD;
import static com.example.trello.util.FileUploadUtil.isAllowedExtension;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardListRepository cardListRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final AmazonS3Client amazonS3Client;
    private final WorkspaceMemberService workspaceMemberService;
    private final NotificationService notificationService;
    private final WorkSpaceRepository workSpaceRepository;

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

        Long workSpaceId = cardList.getBoard().getWorkspace().getId();

        workspaceMemberService.CheckReadRole(userDetails.getUser().getId(), workSpaceId);

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

        Long workSpaceId = card.getCardList().getBoard().getWorkspace().getId();
        workspaceMemberService.CheckReadRole(userDetails.getUser().getId(), workSpaceId);

        card.updateCard(responseDto);

        cardRepository.save(card);



        notificationService.sendSlack(UPDATE_CARD, card.getCardList().getBoard().getWorkspace());

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

        Long workSpaceId = card.getCardList().getBoard().getWorkspace().getId();

        workspaceMemberService.CheckReadRole(userDetails.getUser().getId(), workSpaceId);

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

        Card card = cardRepository.findByIdOrElseThrow(cardId);

        Long workSpaceId = card.getCardList().getBoard().getWorkspace().getId();

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(userDetails.getUser().getId(), workSpaceId)) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }

        workspaceMemberService.CheckReadRole(userDetails.getUser().getId(), workSpaceId);

        // 파일 형식 예외처리
        if (!isAllowedExtension(file.getOriginalFilename())) {
            throw new CardException(CardErrorCode.FORMAT_NOT_SUPPORTED);
        }


        // 파일 크기 예외처리
        long maxSize = 5 * 1024 * 1024;

        if (file.getSize() > maxSize) {
            throw new CardException(CardErrorCode.FILE_SIZE_EXCEEDED);
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

        Long workSpaceId = card.getCardList().getBoard().getWorkspace().getId();

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(userDetails.getUser().getId(), workSpaceId)) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }

        workspaceMemberService.CheckReadRole(userDetails.getUser().getId(), workSpaceId);

        if (!card.getFileName().equals(fileName)) {
            throw new RuntimeException();
        }

        card.deleteFile();
        amazonS3Client.deleteObject(bucketName, fileName);
    }

    @Transactional
    public String getFile(Long cardId, UserDetailsImpl userDetails) {

        Card card = cardRepository.findByIdOrElseThrow(cardId);

        Long workSpaceId = card.getCardList().getBoard().getWorkspace().getId();

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(userDetails.getUser().getId(), workSpaceId)) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }


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
