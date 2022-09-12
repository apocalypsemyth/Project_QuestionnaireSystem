package com.QuestionnaireProject.QuestionnaireSystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Typing;

@Repository
public interface TypingDao extends JpaRepository<Typing, UUID> {
	
	public Typing findByTypingName(String typingName);
	
}
