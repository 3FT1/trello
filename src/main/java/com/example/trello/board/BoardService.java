package com.example.trello.board;

import com.example.trello.board.dto.BoardDetailResponseDto;
import com.example.trello.board.dto.BoardResponseDto;
import com.example.trello.card.Card;
import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.card.dto.GetCardResponseDto;
import com.example.trello.cardlist.CardList;
import com.example.trello.cardlist.CardListRepository;
import com.example.trello.cardlist.dto.GetCardListResponseDto;
import com.example.trello.workspace.Workspace;
import com.example.trello.workspace_member.WorkspaceMember;
import com.example.trello.workspace_member.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.trello.workspace_member.WorkspaceMemberRole.READ_ONLY;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final CardListRepository cardListRepository;
    private final CardRepository cardRepository;

    @Transactional
    public BoardResponseDto createBoard(Long workspaceId, String title, String color, String image, Long loginUserId) {
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(loginUserId, workspaceId);

        if (findWorkspaceMember.getRole() == READ_ONLY) {
            throw new RuntimeException("읽기 전용 역할은 보드를 생성할 수 없습니다.");
        }

        Workspace workspace = findWorkspaceMember.getWorkspace();

        Board board = Board.builder()
                .title(title)
                .color(color)
                .image(image)
                .workspace(workspace)
                .build();

        boardRepository.save(board);

        return BoardResponseDto.toDto(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> viewAllBoard(Long workspaceId, Long loginUserId) {
        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(loginUserId, workspaceId)) {
            throw new RuntimeException("해당 워크스페이스의 멤버가 아닙니다.");
        }

        List<Board> findBoardList = boardRepository.findAllByWorkspaceId(workspaceId);

        return findBoardList
                .stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BoardDetailResponseDto viewBoard(Long boardId, Long loginUserId) {
        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(loginUserId, findBoard.getWorkspace().getId())) {
            throw new RuntimeException("해당 워크스페이스의 멤버가 아닙니다.");
        }

        List<CardList> findCardLists = cardListRepository.findByBoard(findBoard);
        List<GetCardListResponseDto> getCardListResponseDtoList = new ArrayList<>();

        for (CardList cardList : findCardLists) {
            List<Card> findCards = cardRepository.findByCardList(cardList);
            List<GetCardResponseDto> getCardResponseDtoList = findCards.stream().map(GetCardResponseDto::toDto).toList();
            GetCardListResponseDto getCardListResponseDto = GetCardListResponseDto.toDto(cardList, getCardResponseDtoList);
            getCardListResponseDtoList.add(getCardListResponseDto);
        }

        return BoardDetailResponseDto.toDto(findBoard, getCardListResponseDtoList);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, String title, String color, String image, Long loginUserId) {
        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(loginUserId, findBoard.getWorkspace().getId());

        if (findWorkspaceMember.getRole() == READ_ONLY) {
            throw new RuntimeException("읽기 전용 역할은 보드를 수정할 수 없습니다.");
        }

        findBoard.updateBoard(title, color, image);

        return BoardResponseDto.toDto(findBoard);
    }

    @Transactional
    public void deleteBoard(Long boardId, Long loginUserId) {
        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(loginUserId, findBoard.getWorkspace().getId());

        if (findWorkspaceMember.getRole() == READ_ONLY) {
            throw new RuntimeException("읽기 전용 역할은 보드를 삭제할 수 없습니다.");
        }

        boardRepository.delete(findBoard);
    }
}
