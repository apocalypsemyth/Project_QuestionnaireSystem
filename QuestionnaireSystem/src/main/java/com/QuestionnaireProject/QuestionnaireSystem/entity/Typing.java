package com.QuestionnaireProject.QuestionnaireSystem.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "typing")
public class Typing {
	
	@Id
	@Column(name = "typing_id")
	@Type(type = "uuid-char")
	private UUID typingId;
	
	@Column(name = "typing_name")
	private String typingName;
	
	public Typing() {
	}
	
	public Typing(String typingName) {
		this.typingId = UUID.randomUUID();
		this.typingName = typingName;
	}

	public UUID getTypingId() {
		return typingId;
	}

	public void setTypingId(UUID typingId) {
		this.typingId = typingId;
	}

	public String getTypingName() {
		return typingName;
	}

	public void setTypingName(String typingName) {
		this.typingName = typingName;
	}
}
