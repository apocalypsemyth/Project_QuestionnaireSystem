package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.util.List;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Category;

public interface CategoryService {
	
	/**
	 * 檢查資料庫是否擁有目標種類名稱的問題種類。
	 * @param String 種類名稱
	 * @param List 種類列表
	 * @return boolean 是否擁有目標種類名稱的問題種類
	 * @throws 檢查資料庫是否擁有目標種類名稱的問題種類時，發生錯誤。
	 */
	public boolean hasCategory(
			String categoryName, 
			List<Category> categoryList
			) throws Exception;
	
	/**
	 * 取得擁有目標種類Id的問題種類。
	 * @param String 種類Id
	 * @return Object 擁有目標種類Id的問題種類
	 * @throws 取得擁有目標種類Id的問題種類時，發生錯誤。
	 */
	public Category getCategory(String categoryIdStr) throws Exception;
	
	/**
	 * 取得問題種類列表。
	 * @return List 問題種類列表
	 * @throws 取得問題種類列表時，發生錯誤。
	 */
	public List<Category> getCategoryList() throws Exception;
	
	/**
	 * 創建擁有種類名稱的問題種類。
	 * @param String 種類名稱
	 * @throws 創建擁有種類名稱的問題種類時，發生錯誤。
	 */
	public void createCategory(String categoryName) throws Exception;
	
}
