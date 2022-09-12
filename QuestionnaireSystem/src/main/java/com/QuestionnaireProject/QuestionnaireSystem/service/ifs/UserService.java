package com.QuestionnaireProject.QuestionnaireSystem.service.ifs;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.model.DataListForPager;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostUserReq;

public interface UserService {
	
	/**
	 * 取得使用者於Session。
	 * @param HttpSession session
	 * @return Object 使用者於Session
	 * @throws 取得使用者於Session時，發生錯誤。
	 */
	public User getUser(
			HttpSession session
			) throws Exception;
	
	/**
	 * 取得使用者，藉由使用者Id和使用者列表。
	 * @param String 使用者Id
	 * @param List 使用者列表
	 * @return Object 取得使用者，藉由使用者Id和使用者列表
	 * @throws 取得使用者，藉由使用者Id和使用者列表時，發生錯誤。
	 */
	public User getUser(
			String userIdStr,
			List<User> userList
			) throws Exception;
	
	/**
	 * 取得使用者列表於Session。
	 * @param HttpSession session
	 * @return List 使用者列表於Session
	 * @throws 取得使用者列表於Session時，發生錯誤。
	 */
	public List<User> getUserList(
			HttpSession session
			) throws Exception;
	
	/**
	 * 取得使用者列表，藉由問卷Id。
	 * @param String 問卷Id
	 * @return List 取得使用者列表，藉由問卷Id
	 * @throws 取得使用者列表，藉由問卷Id時，發生錯誤。
	 */
	public List<User> getUserList(
			String questionnaireIdStr
			) throws Exception;
	
	/**
	 * 取得符合參數條件的為了Pager之資料列表(使用者列表和資料總數)。
	 * @param int 每頁大小
	 * @param int 頁數
	 * @param List 使用者列表
	 * @return Object 符合參數條件的為了Pager之資料列表
	 * @throws 取得符合參數條件的為了Pager之資料列表時，發生錯誤。
	 */
	public DataListForPager getUserListForPager(
			int pageSize,
			int pageIndex,
			List<User> userList
			) throws Exception;
	
	/**
	 * 設置使用者於Session。
	 * @param HttpSession session
	 * @param Object 使用者
	 * @throws 設置使用者於Session時，發生錯誤。
	 */
	public void setUser(
			HttpSession session, 
			User user
			) throws Exception;
	
	/**
	 * 設置使用者列表於Session。
	 * @param HttpSession session
	 * @param List 使用者列表
	 * @throws 設置使用者列表於Session時，發生錯誤。
	 */
	public void setUserList(
			HttpSession session, 
			List<User> userList
			) throws Exception;
	
	/**
	 * 創建使用者於Session，藉由使用者請求Body。
	 * @param HttpSession session
	 * @param Object 使用者請求Body
	 * @throws 創建使用者於Session時，發生錯誤。
	 */
	public void createUser(
			HttpSession session, 
			PostUserReq postUserReq
			) throws Exception;
	
	/**
	 * 更新使用者於Session，藉由使用者請求Body。
	 * @param HttpSession session
	 * @param Object 使用者請求Body
	 * @throws 更新使用者於Session時，發生錯誤。
	 */
	public void updateUser(
			HttpSession session, 
			PostUserReq postUserReq
			) throws Exception;
	
	/**
	 * 刪除全部使用者於Session。
	 * @param HttpSession session
	 * @throws 刪除使用者於Session時，發生錯誤。
	 */
	public void deleteUser(
			HttpSession session
			) throws Exception;
	
	/**
	 * 刪除全部使用者列表於Session。
	 * @param HttpSession session
	 * @throws 刪除全部使用者列表於Session時，發生錯誤。
	 */
	public void deleteUserList(
			HttpSession session
			) throws Exception;
	
}
