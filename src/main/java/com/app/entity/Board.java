package com.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity(name="boards")
@Data
public class Board {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="board_id")
	private Long id;
	
	@Column(name="name",length=100)
	private String name;

	@OneToMany(mappedBy="board",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<BoardSquare> boardState = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="is_finished")
	private boolean isFinished = false;
}