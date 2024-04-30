package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

//	@Query("select b from Board b where fk_user_id = ?1")
//	List<Board> getBoardsByUserId(Long id);
}
