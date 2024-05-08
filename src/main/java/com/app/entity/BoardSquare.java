package com.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="board_squares")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSquare {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="state", length = 1, columnDefinition = "INT default 0")
	private int state = 0;
	
	@ManyToOne
	@JoinColumn(name = "board_id")
	@JsonBackReference
	private Board board;

}
