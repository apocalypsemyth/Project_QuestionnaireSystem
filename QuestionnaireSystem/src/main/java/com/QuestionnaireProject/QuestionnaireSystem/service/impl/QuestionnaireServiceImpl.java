package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.model.DataListForPager;
import com.QuestionnaireProject.QuestionnaireSystem.repository.QuestionnaireDao;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionnaireService;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
	
	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Override
	public Questionnaire getQuestionnaire(String questionnaireIdStr) throws Exception {
		try {
			UUID questionnaireId = UUID.fromString(questionnaireIdStr);
			Optional<Questionnaire> questionnaireOp = 
					questionnaireDao.findById(questionnaireId);
			if (!questionnaireOp.isPresent()) return null;
			
			return questionnaireOp.get();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public boolean hasQuestionnaire() throws Exception {
		try {
			List<Questionnaire> questionnaireList = questionnaireDao.findAll();
			if (questionnaireList == null || questionnaireList.isEmpty()) return false;
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public int getQuestionnaireTotal() throws Exception {
		try {
			boolean hasQuestionnaire = hasQuestionnaire();
			if (!hasQuestionnaire) return 0;
			return questionnaireDao.findAll().size();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public boolean checkDateOfQuestionnaireList(
			List<Questionnaire> questionnaireList
			) throws Exception {
		try {
			boolean hasNotStartOrOverEndDate = false;
            if (questionnaireList == null || questionnaireList.isEmpty()) 
            	return hasNotStartOrOverEndDate;
            
            LocalDateTime nowDate = DateTimeUtil.Func.getLocalDateTime(false);
            for (var questionnaire : questionnaireList)
            {
            	LocalDateTime startDate = 
						DateTimeUtil
						.Convert
						.convertDateToLocalDateTime(questionnaire.getStartDate());
				LocalDateTime endDate = 
						questionnaire.getEndDate() == null 
						? null 
						: DateTimeUtil.Convert.convertDateToLocalDateTime(questionnaire.getEndDate());
            	boolean isEnable = questionnaire.getIsEnable();
				
                if (startDate.equals(nowDate) && !isEnable)
                {
                    hasNotStartOrOverEndDate = true;
                    return hasNotStartOrOverEndDate;
                }
                else if (endDate != null && endDate.isBefore(nowDate) && isEnable)
                {
                    hasNotStartOrOverEndDate = true;
                    return hasNotStartOrOverEndDate;
                }
            }

            return hasNotStartOrOverEndDate;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void setIsEnableOfQuestionnaireList(
			List<Questionnaire> questionnaireList
			) throws Exception {
		try {
			List<Questionnaire> toSetIsEnableOfQuestionnaireList = new ArrayList<>();
			LocalDateTime nowDate = DateTimeUtil.Func.getLocalDateTime(false);
			questionnaireList.forEach(questionnaire -> {
				LocalDateTime startDate = 
						DateTimeUtil
						.Convert
						.convertDateToLocalDateTime(questionnaire.getStartDate());
				LocalDateTime endDate = 
						questionnaire.getEndDate() == null 
						? null 
						: DateTimeUtil.Convert.convertDateToLocalDateTime(questionnaire.getEndDate());
				
				if (startDate.isEqual(nowDate))
                {
                    questionnaire.setIsEnable(true);
                    toSetIsEnableOfQuestionnaireList.add(questionnaire);
                }
				if (endDate != null && endDate.isBefore(nowDate))
                {
                    questionnaire.setIsEnable(false);
                    toSetIsEnableOfQuestionnaireList.add(questionnaire);
                }
			});
			
			questionnaireDao.saveAllAndFlush(toSetIsEnableOfQuestionnaireList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<Questionnaire> getQuestionnaireList() throws Exception {
		try {
			List<Questionnaire> questionnaireList = questionnaireDao.findAll();
			if (questionnaireList == null || questionnaireList.isEmpty()) return null;
			return questionnaireList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public DataListForPager getQuestionnaireList(
			String keyword, 
			String startDateStr, 
			String endDateStr,
			int pageSize, 
			int pageIndex
			) throws Exception {
		Pageable pageReq = 
				PageRequest.of(
						pageIndex, 
						pageSize,
						Sort.by("startDate").descending()
						.and(Sort.by("updateDate").descending())
						);
		DataListForPager questionnaireListObj = new DataListForPager();
        try {
        	if (StringUtils.hasText(keyword))
            {	
        		questionnaireListObj.setTotalRows(
        				questionnaireDao.findByCaptionContaining(keyword, null).size()
        				);
        		questionnaireListObj.setQuestionnaireList(
        				questionnaireDao.findByCaptionContaining(keyword, pageReq)
        				);
                return questionnaireListObj;
            }
            else if (StringUtils.hasText(startDateStr)
                && StringUtils.hasText(endDateStr))
            {
                Date startDate = 
                		DateTimeUtil
        				.Convert
        				.convertStringToDate(startDateStr);
                Date endDate = 
                		DateTimeUtil
        				.Convert
        				.convertStringToDate(endDateStr);
        		Date endDatePlus1 = new Date(endDate.getTime() + 86400000);
        		
        		questionnaireListObj.setTotalRows(
        				questionnaireDao
        				.filterByStartAndEndDate(
        						startDate,
        						endDatePlus1,
        						null
        						).size()
        				);
        		questionnaireListObj.setQuestionnaireList(
        				questionnaireDao
        				.filterByStartAndEndDate(
        						startDate,
        						endDatePlus1,
        						pageReq
        						)
        				);
        		return questionnaireListObj;
            }
            else {
            	questionnaireListObj.setTotalRows(
        				questionnaireDao.findAll().size()
        				);
            	questionnaireListObj.setQuestionnaireList(
        				questionnaireDao.findAll(pageReq).getContent()
        				);
            	return questionnaireListObj;
            }
        } catch (Exception e) {
        	throw new Exception(e);
        }
	}
	
}
