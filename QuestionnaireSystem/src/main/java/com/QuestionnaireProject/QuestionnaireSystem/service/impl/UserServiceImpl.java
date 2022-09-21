package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QuestionnaireProject.QuestionnaireSystem.constant.SessionConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.model.DataListForPager;
import com.QuestionnaireProject.QuestionnaireSystem.repository.UserDao;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserService;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostUserReq;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public User getUser(
			HttpSession session 
			) throws Exception {
		try {
			User user = (User) session.getAttribute(SessionConstant.Name.USER);
			if (user == null) return null;
			return user;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public User getUser(
			String userIdStr,
			List<User> userList
			) throws Exception {
		try {
			UUID userId = UUID.fromString(userIdStr);
			Optional<User> userOp =
					userList
					.stream()
					.filter(item -> item.getUserId().equals(userId))
					.findFirst();
			if (!userOp.isPresent()) return null;
			return userOp.get();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserList(
			HttpSession session
			) throws Exception {
		try {
			List<User> userList = 
					(List<User>) session.getAttribute(SessionConstant.Name.USER_LIST);
			if (userList == null || userList.isEmpty()) return null;
			
			userList.sort(Comparator.comparing(User::getAnswerDate).reversed());
			return userList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<User> getUserList(
			String questionnaireIdStr
			) throws Exception {
		try {
			UUID questionnaireId = UUID.fromString(questionnaireIdStr);
			List<User> userList = userDao.findByQuestionnaireId(questionnaireId);
			if (userList == null || userList.isEmpty()) return null;
			return userList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public DataListForPager getUserListForPager(
			int pageSize,
			int pageIndex,
			List<User> userList
			) throws Exception {
		DataListForPager userListObj = new DataListForPager();
		try {
			int totalData = userList.size();
			int pageNum = 
					totalData < pageSize 
					? 0
					: totalData % pageSize == 0 
					? totalData / pageSize - 1
					: totalData / pageSize;
			if (pageIndex < 0) pageIndex = 0;
			else if (pageIndex > pageNum) pageIndex = pageNum;
			else if (pageIndex == Integer.MAX_VALUE) pageIndex = pageNum;
			
			int fromParam = pageIndex * pageSize;
			int toParam = 
					pageIndex * pageSize + pageSize > totalData 
					? totalData 
					: pageIndex * pageSize + pageSize;
			List<User> filteredUserList = userList.subList(fromParam, toParam);
			filteredUserList.sort(Comparator.comparing(User::getAnswerDate).reversed());
			
			userListObj.setPageIndex(pageIndex);
			userListObj.setTotalRows(totalData);
			userListObj.setUserList(filteredUserList);
			return userListObj;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void setUser(
			HttpSession session, 
			User user
			) throws Exception {
		try {
			session.setAttribute(SessionConstant.Name.USER, user);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void setUserList(
			HttpSession session, 
			List<User> userList
			) throws Exception {
		try {
			session.setAttribute(SessionConstant.Name.USER_LIST, userList);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void createUser(
			HttpSession session, 
			PostUserReq postUserReq
			) throws Exception {
		try {
			setUser(session, new User(postUserReq));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void updateUser(
			HttpSession session, 
			PostUserReq postUserReq
			) throws Exception {
		try {
			User toUpdateUser = getUser(session);
			if (toUpdateUser == null) throw new Exception("To update user is null");
			
			toUpdateUser.setUserName(postUserReq.getUserName());
			toUpdateUser.setPhone(postUserReq.getPhone());
			toUpdateUser.setEmail(postUserReq.getEmail());
			toUpdateUser.setAge(postUserReq.getAge());
			toUpdateUser.setAnswerDate(DateTimeUtil.Func.getDate());
			
			setUser(session, toUpdateUser);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public void deleteUser(
			HttpSession session
			) throws Exception {
		try {
			session.removeAttribute(SessionConstant.Name.USER);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public void deleteUserList(
			HttpSession session
			) throws Exception{
		try {
			session.removeAttribute(SessionConstant.Name.USER_LIST);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
