package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.util.UUID;
import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Category;
import com.QuestionnaireProject.QuestionnaireSystem.repository.CategoryDao;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public boolean hasCategory(String categoryName) throws Exception {
		try {
			Category category = categoryDao.findByCategoryName(categoryName);
			if (category == null) return false;
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Category getCategory(String categoryIdStr) throws Exception {
		try {
			UUID categoryId = UUID.fromString(categoryIdStr);
			Optional<Category> categoryOp = categoryDao.findById(categoryId);
			if (!categoryOp.isPresent()) return null;
			return categoryOp.get();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<Category> getCategoryList() throws Exception {
		try {
			List<Category> categoryList = categoryDao.findAll();
			if (categoryList == null || categoryList.isEmpty()) return null;
			return categoryList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void createCategory(String categoryName) throws Exception {
		try {
			Category newCategory = new Category(categoryName);
			categoryDao.saveAndFlush(newCategory);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
