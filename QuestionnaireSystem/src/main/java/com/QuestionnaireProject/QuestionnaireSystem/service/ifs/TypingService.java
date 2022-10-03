package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.util.List;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Typing;

public interface TypingService {
	
	/**
	 * 檢查資料庫是否擁有目標類別名稱的問題類別。
	 * @param String 類別名稱
	 * @param List 類別列表
	 * @return boolean 是否擁有目標類別名稱的問題類別
	 * @throws 檢查資料庫是否擁有目標類別名稱的問題類別時，發生錯誤。
	 */
	public boolean hasTyping(
			String typingName, 
			List<Typing> typingList
			) throws Exception;
	
	/**
	 * 取得問題類別列表。
	 * @return List 問題類別列表
	 * @throws 取得問題類別列表時，發生錯誤。
	 */
	public List<Typing> getTypingList() throws Exception;
	
	/**
	 * 創建擁有目標類別名稱的問題類別。
	 * @param String 類別名稱
	 * @throws 創建擁有目標類別名稱的問題類別時，發生錯誤。
	 */
	public void createTyping(String typingName) throws Exception;
	
}
