/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * TblDetailUserJapanLogicImpl.java, 15 thg 6, 2021 PhonPV
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.text.ParseException;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.entities.TblDetailUserJapanEntities;
import manageuser.entities.UserInforEntities;
import manageuser.logics.TblDetailUserJapanLogic;
import manageuser.utils.Common;

/**
 * @Description Chứa các hàm xử lí logic tbl_detail_japan
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class TblDetailUserJapanLogicImpl implements TblDetailUserJapanLogic {

	/**
	 * lấy chi tiết trình độ tiếng Nhật từ userInfor
	 * 
	 * @param userInfo userinfo được dùng để lấy detail
	 * @return đối tượng userJapan
	 */
	@Override
	public TblDetailUserJapanEntities getDetailUserjapanFromUserInfor(UserInforEntities userInfor)
			throws ParseException {
		// Tạo ra 1 đối tượng TblDetailUserJapanEntities
		TblDetailUserJapanEntities userJapan = new TblDetailUserJapanEntities();
		// Set cac thuoc tinh cho doi tuowng
		userJapan.setUserId(userInfor.getUserId());
		userJapan.setCodeLevel(userInfor.getCodeLevel());
		userJapan.setStartDate(Common.convertStringToDate(userInfor.getStartDate()));
		userJapan.setEndDate(Common.convertStringToDate(userInfor.getEndDate()));
		userJapan.setTotal(Common.convertStringToInt(userInfor.getTotal(), 0));
		return userJapan;
	}

	/**
	 * Kiểm tra trình độ tiếng Nhật có tồn tại hay không
	 * 
	 * @param userInfo userinfor được dùng để lấy detail
	 * @return đối tượng userJapan
	 */
	@Override
	public boolean checkExistedDetailUserById(int userId) throws ClassNotFoundException, SQLException, ParseException {
		// Tạo ra thuộc tính boolean để check cho việc lấy ra thành công hay không? <br>
		boolean checkExisted = false;
		// Tạo ra đối tượng userJapanDao để lấy đối tượng userJapan từ DB
		TblDetailUserJapanDao userJapanDao = new TblDetailUserJapanDaoImpl();
		// Lấy ra userJapan từ Database
		TblDetailUserJapanEntities userJapan = userJapanDao.getDetailJapanById(userId);
		if (userJapan.getCodeLevel() != null) {
			// Nếu tồn tại thì gán checkExisted = true
			checkExisted = true;
		}
		// Trả về giá trị checkExisted
		return checkExisted;
	}

}
