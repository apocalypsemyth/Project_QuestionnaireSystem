package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.util.UUID;
import java.util.stream.Collectors;
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
	public boolean hasCategory(
			String categoryName,
			List<Category> categoryList
			) throws Exception {
		try {
			List<Category> filteredCategoryList = 
					categoryList
					.stream()
					.filter(item -> item.getCategoryName().equals(categoryName))
					.collect(Collectors.toList());
			if (filteredCategoryList == null || filteredCategoryList.isEmpty()) 
				return false;
			else if (filteredCategoryList.size() > 1) 
				throw new Exception("There should be only one target category");
			else if (filteredCategoryList.size() == 1) 
				return true;
			else 
				return false;
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
