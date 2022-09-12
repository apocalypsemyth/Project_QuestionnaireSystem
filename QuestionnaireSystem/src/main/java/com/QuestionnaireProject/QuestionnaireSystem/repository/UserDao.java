package com.QuestionnaireProject.QuestionnaireSystem.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.QuestionnaireProject.QuestionnaireSystem.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, UUID> {
	
//	public User findByQuestionnaireIdIs(UUID questionnaireId);
//
//	public User findByQuestionnaireIdAndUserId(UUID questionnaireId, UUID userId);
	
	public List<User> findByQuestionnaireId(UUID questionnaireId);

}
