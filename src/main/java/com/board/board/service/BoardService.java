package com.board.board.service;

import com.board.board.entity.Board;
import com.board.board.repository.BoardRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.util.UUID;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시물 작성
    public void write(Board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    // 게시물 리스트
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    // 상세 페이지
    public Board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }

    // 게시물 삭제
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }

    // 게시물 검색
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

}
