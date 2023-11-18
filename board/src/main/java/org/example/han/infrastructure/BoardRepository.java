package org.example.han.infrastructure;

import org.example.han.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByIdBetween(int startIndex, int endIndex);

}
