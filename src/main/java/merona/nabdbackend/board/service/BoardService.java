package merona.nabdbackend.board.service;

import lombok.RequiredArgsConstructor;
import merona.nabdbackend.board.dto.BoardResponseDto;
import merona.nabdbackend.board.dto.BoardSaveRequestDto;
import merona.nabdbackend.board.dto.BoardUpdateRequestDto;
import merona.nabdbackend.board.entity.Address;
import merona.nabdbackend.board.entity.Board;
import merona.nabdbackend.board.enums.State;
import merona.nabdbackend.board.repository.BoardRepository;
import merona.nabdbackend.user.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시물 작성
    public Long save(BoardSaveRequestDto boardSaveRequestDto, Member member) {
        boardSaveRequestDto.setMember(member);
        Board board = boardSaveRequestDto.boardFormDto();
        boardRepository.save(board);
        return board.getId();
    }

    @Transactional(readOnly = true)
    // 게시물 전체 조회
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    // 게시물 id로 조회
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional(readOnly = true)
    // 게시물 id로 조회
    public BoardResponseDto findBoardById(Long id) {
        Board entity = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다. id=" + id));
        return new BoardResponseDto(entity);
    }

    // 게시글 삭제
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    // 게시글 수정
    public Long updateBoard(Long id, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다. id=" + id));
        board.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContents(),boardUpdateRequestDto.getCost(),
                new Address(boardUpdateRequestDto.getZipcode(), boardUpdateRequestDto.getStreetAddress(), boardUpdateRequestDto.getDetailAddress()));
        return id;
    }

    // 게시글 상태 수정
    public void updateState(Long id, State state) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다. id=" + id));
        board.updateState(state);
    }
}
