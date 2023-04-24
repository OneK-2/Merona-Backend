package merona.nabdbackend.board.service;

import lombok.RequiredArgsConstructor;
import merona.nabdbackend.board.dto.BoardSaveRequestDto;
import merona.nabdbackend.board.entity.Board;
import merona.nabdbackend.board.repository.BoardRepository;
import merona.nabdbackend.user.entity.User;
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
    public Long save(BoardSaveRequestDto boardSaveRequestDto, User user){
        boardSaveRequestDto.setUser(user);
        Board board = boardSaveRequestDto.boardFormDto();
        boardRepository.save(board);
        return board.getId();
    }

    @Transactional(readOnly = true)
    // 게시물 전체 조회
    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    // 게시물 id로 조회
    public Optional<Board> findById(Long id){
        return boardRepository.findById(id);
    }

}
