package com.example.trello.board;

import com.example.trello.board.dto.*;
import com.example.trello.card.Card;
import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.card.dto.GetCardResponseDto;
import com.example.trello.cardlist.CardList;
import com.example.trello.cardlist.CardListRepository;
import com.example.trello.cardlist.dto.GetCardListResponseDto;
import com.example.trello.common.exception.*;
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
    public BoardResponseDto createBoard(BoardRequestDto dto, Long loginUserId) {
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(loginUserId, dto.getWorkspaceId());

        if (findWorkspaceMember.getRole() == READ_ONLY) {
            throw new BoardException(BoardErrorCode.READ_ONLY_CANT_NOT_HANDLE_BOARD);
        }

        Workspace workspace = findWorkspaceMember.getWorkspace();

        Board board = Board.builder()
                .title(dto.getTitle())
                .color(dto.getColor())
                .image(dto.getImage())
                .workspace(workspace)
                .build();

        boardRepository.save(board);

        return BoardResponseDto.toDto(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> viewAllBoard(viewAllBoardRequestDto dto, Long loginUserId) {
        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(loginUserId, dto.getWorkspaceId())) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }

        List<Board> findBoardList = boardRepository.findAllByWorkspaceId(dto.getWorkspaceId());

        return findBoardList
                .stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BoardDetailResponseDto viewBoard(Long boardId, Long loginUserId) {
        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(loginUserId, findBoard.getWorkspace().getId())) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
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
    public BoardResponseDto updateBoard(Long boardId, UpdateBoardRequestDto dto, Long loginUserId) {
        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(loginUserId, findBoard.getWorkspace().getId());

        if (findWorkspaceMember.getRole() == READ_ONLY) {
            throw new BoardException(BoardErrorCode.READ_ONLY_CANT_NOT_HANDLE_BOARD);
        }

        findBoard.updateBoard(dto.getTitle(), dto.getColor(), dto.getImage());

        return BoardResponseDto.toDto(findBoard);
    }

    @Transactional
    public void deleteBoard(Long boardId, Long loginUserId) {
        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(loginUserId, findBoard.getWorkspace().getId());

        if (findWorkspaceMember.getRole() == READ_ONLY) {
            throw new BoardException(BoardErrorCode.READ_ONLY_CANT_NOT_HANDLE_BOARD);
        }

        boardRepository.delete(findBoard);
    }
}
