/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * TblUserLogicImpl.java, 9 thg 5, 2021 PhonPV
 */
package manageuser.logics.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.dao.TblUserDao;
import manageuser.dao.impl.BaseDaoImpl;
import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.dao.impl.TblUserDaoImpl;
import manageuser.entities.TblDetailUserJapanEntities;
import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;
import manageuser.logics.TblUserLogic;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * @Description Chứa các phương thức xử lí logic của chương chình
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class TblUserLogicImpl implements TblUserLogic {

	/**
	 * Check sự tồn tại của user nhập vào trong bảng TblUser
	 * 
	 * @param loginName Tên đăng nhập
	 * @param password  Mật khẩu
	 * @return true nếu tồn tại loginName trong database <br>
	 *         false nếu không tồn tại LoginName trong database
	 * @throws ClassNotFoundException, SQLException, NoSuchAlgorithmException
	 */
	@Override
	public boolean checkExistLoginName(String loginName, String password)
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		// Thay thế các kí tự đặc biệt của loginName
		loginName = Common.encodeHtml(loginName);
		try {
			// Lấy tblUserEntities theo LoginName từ database
			TblUserEntities tblUserEntities = tblUserDao.getTblUserByLoginName(loginName);
			// Kiểm tra nếu đối tượng không được tìm thấy tblUserEntities tương ứng
			if (tblUserEntities == null) {
				// Trả về kết quả kiểm tra
				return false;
				// Đóng if
			}
			// lấy ra salt
			String salt = tblUserEntities.getSalt();
			// Lấy ra mật khẩu trong database
			String passwordDB = tblUserEntities.getPassword();
			// Tạo 1 password mã hóa người dùng nhập vào
			String passwordUser = Common.encryptPassword(password, salt);
			// Trả về kết quả so sánh mật khẩu người dùng nhập và mật khẩu có trong database
			return Common.compareString(passwordUser, passwordDB);
			// Đóng try mở catch để bắt và xử lí exeption thu được
		} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
			// Lấy tên của method hiện tại.
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log.
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném lỗi
			throw e;
		}
	}

	/**
	 * Lấy danh sách user
	 * 
	 * @param offset          vị trí data cần lấy nào
	 * @param limit           số lượng data lấy
	 * @param groupId         mã nhóm tìm kiếm
	 * @param fullName        Tên tìm kiếm
	 * @param sortType        Nhận biết xem cột nào được ưu tiên sắp xếp (full_name
	 *                        or end_date or code_level) <br>
	 * @param sortByFullName  Giá trị sắp xếp của cột Tên(ASC or DESC)
	 * @param sortByCodeLevel Giá trị sắp xếp của cột Trình độ tiếng nhật (ASC or
	 *                        DESC) <br>
	 * @param sortByEndDate   Giá trị sắp xếp của cột Ngày kết hạn(ASC or DESC) <br>
	 * @return listUser Danh sách user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<UserInforEntities> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException {
		// Thay thế các toán tử WildCard của fullName.
		fullName = Common.replaceWildcard(fullName);
		try {
			// Tạo ra 1 đối tượng BaseDao
			BaseDaoImpl baseDao = new BaseDaoImpl();
			// Tạo danh sách các list column được sort
			List<String> listAllColumn = baseDao.getListAllColumns();
			// Kiểm tra sự tồn tại của sortType
			if (!listAllColumn.contains(sortType)) {
				// Nếu không thì gán về rỗng trở về giá trị default
				sortType = Constant.EMPTY_STRING;
			}
			// Lấy ra list danh sách user.
			List<UserInforEntities> listUserInfor = tblUserDao.getListUser(offset, limit, groupId, fullName, sortType,
					sortByFullName, sortByCodeLevel, sortByEndDate);
			return listUserInfor;
		} catch (ClassNotFoundException | SQLException e) {
			// Lấy tên của method curend
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi lỗi hệ thống
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném lỗi
			throw e;
		}

	}

	/**
	 * Lấy ra tổng số user có trong database.
	 * 
	 * @param groupId  id của user
	 * @param fullName tên đầy đủ của user
	 * @return totalUser tổng số user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public int getTotalUser(int groupId, String fullName) throws ClassNotFoundException, SQLException {
		// Thay thế các toán tử WildCard của fullName.
		fullName = Common.replaceWildcard(fullName);
		int totalUser = tblUserDao.getTotalUsers(groupId, fullName);
		return totalUser;
	}

	/**
	 * hàm check tồn tại user có loginName trong db
	 * 
	 * @param String loginName tên đăng nhập
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return boolean true: tồn tại false: không tồn tại
	 */
	@Override
	public boolean checkExistedLoginName(int userId, String loginName) throws ClassNotFoundException, SQLException {
		boolean rs = true;
		TblUserEntities tblUser = tblUserDao.getUserByLoginName(userId, loginName);
		if (tblUser.getLoginName() == null) {
			rs = false;
		}
		return rs;
	}

	/**
	 * hàm check tồn tại user có email trong db
	 * 
	 * @param String email
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return boolean true: tồn tại false: không tồn tại
	 */
	@Override
	public boolean checkExistedEmail(int userId, String email) throws ClassNotFoundException, SQLException {
		boolean result = false;
		TblUserEntities tblUser = tblUserDao.getUserByEmail(userId, email);
		if (tblUser.getEmail() == null) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	/**
	 * Check để thưc hiện tạo mới user
	 * 
	 * @param UserInforEntities userInfo
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return boolean true: tạo thanh công false: tạo thất bại
	 * @throws ClassNotFoundException, SQLException
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean creatUser(UserInforEntities userInfor) throws ClassNotFoundException, SQLException {
		boolean rs = false;
		TblUserDao tblUserDao = new TblUserDaoImpl();
		try {
			// Tạo 1 Transaction
			// Mở kết nối db
			tblUserDao.openConnection();
			// Set lại auto comit về false
			tblUserDao.setAutoCommit(false);

			// Lấy các giá trị đã set của tblUser từ userInfor
			TblUserEntities tblUser = Common.getTblUserFromUserInfor(userInfor);
			// Lấy ra Id của user sau khi đã insert
			int iDUser = tblUserDao.insertUser(tblUser);
			if (iDUser != 0 && !Constant.EMPTY_STRING.equals(userInfor.getCodeLevel())) {
				// Thực hiện insert tblDetailJapan
				userInfor.setUserId(iDUser);
				TblDetailUserJapanDao detailJapan = new TblDetailUserJapanDaoImpl();
				// Lấy connection từ tầng dao về
				Connection conn = tblUserDao.getConn();
				// Set connection cho detail
				detailJapan.setConn(conn);
				// Lấy thông tin đã set cho tblDetailJapan từ userInfor
				TblDetailUserJapanEntities tblUserJapan = Common.getTblDetailUserJapanFromUserInfor(userInfor);
				// Insert vào bảng tbl_detail_user_japan
				detailJapan.insertDetailUserJapan(tblUserJapan);
			}
			// Commit transaction
			tblUserDao.commit();
			rs = true;
		} catch (ClassNotFoundException | SQLException e) {
			rs = false;
			tblUserDao.rollBack();
		} finally {
			// Đóng kết nối nếu insert thành công!
			tblUserDao.closeConnection();
			// Trả về giá trị rs bằng true
			return rs;
		}
	}

	/**
	 * Check tồn tại của user có Id ở trong database
	 * 
	 * @param userId id truyền vào để check tồn tại
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return rs = true tồn tại <br>
	 *         rs = false không tồn tại
	 */
	@Override
	public boolean checkExistedId(int userId) throws ClassNotFoundException, SQLException {
		boolean rs = false;
		// Lấy ra 1 đối tượng TblUser từ db theo Id tương ứng truyền vào
		boolean bl = tblUserDao.checkExistedId(userId);
		// Kiểm tra giá trị Id có tồn tại hay không
		if (bl) {
			// Nếu tồn tại id thì gán rs = true.
			rs = true;
		}
		// Trả về rs để check tồn tại
		return rs;

	}

	/**
	 * Lấy thông tin user từ database với id tương ứng
	 * 
	 * @param userId id của user
	 * @return userInfor thông tin của user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public UserInforEntities getUserInforById(int userId) throws ClassNotFoundException, SQLException {
		TblUserDao tblUserDao = new TblUserDaoImpl();
		UserInforEntities userInfor = tblUserDao.getUserInforById(userId);
		return userInfor;
	}

	/**
	 * check để thưc hiện sửa user
	 * 
	 * @param UserInforEntities checkExistDetail
	 * @param boolean           userInfo
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return boolean true: tạo thanh công false: tạo thất bại
	 * @throws ClassNotFoundException, SQLException
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean updateUser(UserInforEntities userInfor, boolean checkDetail)
			throws ClassNotFoundException, SQLException {
		boolean checkUpdate = false;
		TblUserDao tblUserDao = new TblUserDaoImpl();
		try {
			// Tạo 1 kết nối tới DB
			tblUserDao.openConnection();
			// Set lại autoCommit để tạo 1 transaction
			tblUserDao.setAutoCommit(false);
			// Sét giá trị vào tblUer từ userInfor
			TblUserEntities tblUser = Common.getTblUserFromUserInfor(userInfor);
			// Update tblUser
			boolean checkUpdateTblUser = tblUserDao.updateTblUser(tblUser);
			// Kiểm tra thực hiện việc update
			if (checkUpdateTblUser) {
				// Nếu update thành công
				// Tạo đối tượng userDetailJapanDao
				TblDetailUserJapanDao userDetailJapanDao = new TblDetailUserJapanDaoImpl();
				// Lấy ra giá đối tượng detaiJapan từ userInfor
				TblDetailUserJapanEntities detailJapn = Common.getTblDetailUserJapanFromUserInfor(userInfor);
				// Lấy connect vừa mở
				Connection conn = tblUserDao.getConn();
				// Set connect cho userDetailJapanDao
				userDetailJapanDao.setConn(conn);
				// Thực hiện check các trường hợp của trường detailJanp
				// Trường hợp insert: là chọn 1 giá trị codelevel và đối tượng detail chưa tồn
				// tại trong db <br>
				if (!Constant.DEFAULT_CODE_LEVEL_03.equals(detailJapn.getCodeLevel()) && !checkDetail) {
					userDetailJapanDao.insertDetailUserJapan(detailJapn);
				}
				// Trường hợp delete: Khi người dùng không chọn gì và đối tượng detail đã tồn
				// tại trong db
				if (Constant.DEFAULT_CODE_LEVEL_03.equals(detailJapn.getCodeLevel()) && checkDetail) {
					userDetailJapanDao.deleteDetailJapan(userInfor.getUserId());
				}
				// Trường hợp update: là đã tồn tại và người dùng chọn 1 giá trị codeLevel khác
				// với giá trị ban đầu
				if (!Constant.DEFAULT_CODE_LEVEL_03.equals(detailJapn.getCodeLevel()) && checkDetail) {
					userDetailJapanDao.updateDetailJapan(detailJapn);
				}
				// Khi thực hiện xong tất cả các trường hợp thao tác với các bảng thành công
				// <br>
				// thì thực hiện commit
				tblUserDao.commit();
				// Trả về giá trị checkUpdate thành công
				checkUpdate = true;
			} else {
				// Nếu update không thành công thì tra về false
				checkUpdate = false;
			}
		} catch (Exception e) {
			// Nếu xảy ra lỗi
			checkUpdate = false;
			// rollBack() về
			tblUserDao.rollBack();
		} finally {
			// Đóng kết nối lại với db
			tblUserDao.closeConnection();
			// trả về giá chị checkUpdate
			return checkUpdate;
		}
	}

	/**
	 * check để thưc hiện xóa user
	 * 
	 * @param int     userId
	 * @param boolean checkExistDetail
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return boolean true: tạo thanh công false: tạo thất bại
	 * @throws ClassNotFoundException, SQLException
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean deleteUser(int userId, boolean checkExistDetail) throws ClassNotFoundException, SQLException {
		boolean checkDelete = false;
		TblUserDao tblUserDao = new TblUserDaoImpl();
		try {
			tblUserDao.openConnection();
			// Set commit về false để thực hiện transaction
			tblUserDao.setAutoCommit(false);
			// Kiểm tra tồn tại của detailJapan
			if (checkExistDetail) {
				// Nếu tồn tại
				// Tạo đối tượng userDetailJapanDao
				TblDetailUserJapanDao userDetailJapanDao = new TblDetailUserJapanDaoImpl();
				// Lấy connect vừa mở
				Connection conn = tblUserDao.getConn();
				// Set connect cho userDetailJapanDao
				userDetailJapanDao.setConn(conn);
				// Thực hiện việc xóa detailJapan theo id của user
				boolean deleteDetail = userDetailJapanDao.deleteDetailJapan(userId);
				if (deleteDetail) {
					// Nếu xóa thành công
					// Thì xóa tblUser
					tblUserDao.deleteTblUser(userId);
				}
			} else {
				// Nếu không tồn tại detailJapan thì thực hiện xóa luôn tblUser<br>
				tblUserDao.deleteTblUser(userId);
			}
			// Commit transaction
			tblUserDao.commit();
			// Gán giá trị trả về = true
			checkDelete = true;
		} catch (ClassNotFoundException | SQLException e) {
			// Nếu xảy ra lỗi trong quá trình xóa
			// rollback()
			tblUserDao.rollBack();
			// Gán giá trị checkDelete = false
			checkDelete = false;
		} finally {
			// Đóng kết nối với DB lại
			tblUserDao.closeConnection();
			// Trả về giá trị checkDelete về logic xử lí.
			return checkDelete;
		}
	}

	/**
	 * Lấy ra rule của user có id tương ứng trong db
	 * 
	 * @param int userId id của user
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return int rule của user
	 */
	@Override
	public int getRuleById(int userId) throws ClassNotFoundException, SQLException {
		TblUserDao tblUserDao = new TblUserDaoImpl();
		// Lấy rule từ DB ra thông qua tầng Dao trả về cho controller xử lí
		int rule = tblUserDao.getRuleById(userId);
		// Trả về giá trị của rule
		return rule;
	}
}
