package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.BoardSquare;

public interface BoardSquareRepository extends JpaRepository<BoardSquare, Long> {
	
}
