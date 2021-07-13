/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * TblDetailUserJapanLogic.java, 15 thg 6, 2021 PhonPV
 */
package manageuser.logics;

import java.sql.SQLException;
import java.text.ParseException;

import manageuser.entities.TblDetailUserJapanEntities;
import manageuser.entities.UserInforEntities;


/**
 * @Description Chứa các hàm xử lí logic với bảng tbl_detail_japan
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public interface TblDetailUserJapanLogic {
	
	/**
	 * lấy chi tiết trình độ tiếng Nhật từ userInfor
	 * 
	 * @param userInfo userinfo được dùng để lấy detail
	 * @return đối tượng userJapan
	 */
	TblDetailUserJapanEntities getDetailUserjapanFromUserInfor(UserInforEntities userInfor) throws ParseException;
	
	/**
	 * Kiểm tra trình độ tiếng Nhật có tồn tại hay không
	 * 
	 * @param userInfo userinfor được dùng để lấy detail
	 * @return đối tượng userJapan
	 */
	public boolean checkExistedDetailUserById(int userId) throws ClassNotFoundException, SQLException, ParseException;
}
