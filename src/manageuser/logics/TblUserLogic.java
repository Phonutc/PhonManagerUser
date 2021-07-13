/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * TblUserLogic.java, 15 thg 5, 2021 PhonPV
 */
package manageuser.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import manageuser.dao.TblUserDao;
import manageuser.dao.impl.TblUserDaoImpl;
import manageuser.entities.UserInforEntities;

/**
 * @Description Xử lý logic liên quan đến thông tin của user
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public interface TblUserLogic {
	TblUserDao tblUserDao = new TblUserDaoImpl();

	/**
	 * Check sự tồn tại của user nhập vào trong bảng TblUser
	 * 
	 * @param loginName Tên đăng nhập
	 * @param password  Mật khẩu
	 * @return true nếu tồn tại loginName trong database <br>
	 *         false nếu không tồn tại LoginName trong database
	 * @throws ClassNotFoundException, SQLException, NoSuchAlgorithmException
	 */
	boolean checkExistLoginName(String loginName, String password)
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

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
	public List<UserInforEntities> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException;

	/**
	 * Lấy ra tổng số user có trong database.
	 * 
	 * @param groupId  id của user
	 * @param fullName tên đầy đủ của user
	 * @return totalUser tổng số user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getTotalUser(int groupId, String fullName) throws ClassNotFoundException, SQLException;

	/**
	 * hàm check tồn tại user có loginName trong db
	 * 
	 * @param String loginName tên đăng nhập
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return boolean true: tồn tại false: không tồn tại
	 */
	public boolean checkExistedLoginName(int userId, String loginName) throws ClassNotFoundException, SQLException;

	/**
	 * hàm check tồn tại user có email trong db
	 * 
	 * @param String email
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return boolean true: tồn tại false: không tồn tại
	 */
	public boolean checkExistedEmail(int userId, String email) throws ClassNotFoundException, SQLException;

	/**
	 * Check để thưc hiện tạo mới user
	 * 
	 * @param UserInforEntities userInfo
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return boolean true: tạo thanh công false: tạo thất bại
	 * @throws ClassNotFoundException, SQLException
	 */
	public boolean creatUser(UserInforEntities userInfor) throws ClassNotFoundException, SQLException;

	/**
	 * Check tồn tại của user có Id tương ứng truyền vào ở trong database
	 * 
	 * @param userId id truyền vào để check tồn tại
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return true tồn tại <br>
	 *         false không tồn tại
	 */
	public boolean checkExistedId(int userId) throws ClassNotFoundException, SQLException;

	/**
	 * Lấy thông tin user từ database với id tương ứng
	 * 
	 * @param userId id của user
	 * @return userInfor thông tin của user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public UserInforEntities getUserInforById(int userId) throws ClassNotFoundException, SQLException;

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
	public boolean updateUser(UserInforEntities userInfor, boolean checkDetail)
			throws ClassNotFoundException, SQLException;

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
	public boolean deleteUser(int userId, boolean checkExistDetail) throws ClassNotFoundException, SQLException;

	/**
	 * Lấy ra rule của user có id tương ứng trong db
	 * 
	 * @param int userId id của user
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return int rule của user
	 */
	public int getRuleById(int userId) throws ClassNotFoundException, SQLException;

}
