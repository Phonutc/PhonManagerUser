/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * TblUserDao.java, 15 thg 5, 2021 PhonPV
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;

/**
 * @Description Thao tác với database
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public interface TblUserDao extends BaseDao {

	/**
	 * Lấy dữ liệu tblUser từ database thông qua loginName
	 * 
	 * @param loginName Tên đăng nhập
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @return đối tượng result chứa giá trị của user có trong database
	 */
	public TblUserEntities getTblUserByLoginName(String loginName) throws ClassNotFoundException, SQLException;

	/**
	 * Lấy tất cả các đối tượng userInfor từ database theo điều kiện tìm kiếm.
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
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @return listUser Danh sách user
	 */
	List<UserInforEntities> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException;

	/**
	 * Lấy tổng số user
	 * 
	 * @param groupId  Mã nhóm của user
	 * @param fullName Tên đầy đủ của user
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @return totalUser tổng số User.
	 */
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException;

	/**
	 * lấy 1 user có loginName tương ứng
	 * 
	 * @param userId    id của user
	 * @param loginName tên đăng nhập của user
	 * @return tblUser user có loginName tương ứng
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @return tblUser đối tượng tblUser
	 */
	public TblUserEntities getUserByLoginName(int userId, String loginName) throws ClassNotFoundException, SQLException;

	/**
	 * Lấy 1 user có email tương ứng
	 * 
	 * @param int    userId id của user
	 * @param String email email của user
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @return tblUser user có email tương ứng
	 * @return tblUser đối tượng tblUser
	 */
	public TblUserEntities getUserByEmail(int userId, String email) throws ClassNotFoundException, SQLException;

	/**
	 * Thêm dữ liệu vào tblUser
	 * 
	 * @param TblUserEntities tblUser đối tượng được t
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @return int idUser id của của user
	 */
	public int insertUser(TblUserEntities tblUser) throws ClassNotFoundException, SQLException;

	/**
	 * Lấy user từ DB ra bằng ID tương ứng
	 * 
	 * @param userId id của user
	 * @return userInfor thông tin user lấy ra từ DB
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @return true tồn tại user có id tương ứng <br>
	 *         false không tồn tại user có id tương ứng
	 */
	public boolean checkExistedId(int userId) throws ClassNotFoundException, SQLException;

	/**
	 * Hàm thao tác với DB để lấy ra userInfor với userId tồn tại tương ứng
	 * 
	 * @param userId id của user
	 * @return userInfor thông tin của user
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 */
	public UserInforEntities getUserInforById(int userId) throws ClassNotFoundException, SQLException;

	/**
	 * Cập nhật dữ liệu vào tblUser
	 * 
	 * @param TblUserEntities tblUser
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return true update thành công <br>
	 *         false update không thành công
	 */
	public boolean updateTblUser(TblUserEntities tblUser) throws ClassNotFoundException, SQLException;

	/**
	 * Xóa dữ liệu tblUser trong db
	 * 
	 * @param int userId id của user
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 */
	public void deleteTblUser(int userId) throws ClassNotFoundException, SQLException;

	/**
	 * Lấy ra rule của user có id tương ứng trong db
	 * 
	 * @param userId id user cần lấy
	 * @return rule của user
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 */
	public int getRuleById(int userId) throws SQLException, ClassNotFoundException;

}
