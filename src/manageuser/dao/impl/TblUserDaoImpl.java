/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * TblUserDaoImpl.java, 9 thg 5, 2021 PhonPV
 */
package manageuser.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import manageuser.dao.TblUserDao;
import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;
import manageuser.utils.Constant;

/**
 * @Description Thao tác với cơ sở dữ liệu
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {

	/**
	 * Khởi tạo đối tượng để lấy phương thức <br>
	 * Mở kết nối <br>
	 * Đóng kết nối
	 */
	public TblUserDaoImpl() {
	}

	/**
	 * Lấy dữ liệu tblUser từ database thông qua loginName
	 * 
	 * @param loginName Tên đăng nhập
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @return đối tượng result chứa giá trị của user có trong database
	 */
	@Override
	public TblUserEntities getTblUserByLoginName(String loginName) throws ClassNotFoundException, SQLException {
//		Khởi tạo 1 thực thể TblUserEntities có tên là result.
		TblUserEntities tblUser = new TblUserEntities();
		try {
//			Tạo đối tượng connection
			openConnection();
//			Kiểm tra kết nối có tồn tại hay không?
			if (conn != null) {
//			Tạo câu lệnh truy vấn
				String querySQL = "select u.password, u.salt from tbl_user u where u.rule = ? and u.login_name = ?;";

//				Sử dụng PrepareStatement để lưu câu truy vấn gửi tới DB
				PreparedStatement stm;
				stm = conn.prepareStatement(querySQL);
//				Lưu giá trị tham số vào câu lệnh truy vấn
				int i = 1;
				stm.setInt(i++, Constant.RULE_ADMIN);
				stm.setString(i++, loginName);
//				Thực thi câu lệnh truy vấn
				ResultSet rs = stm.executeQuery();
//				Kiểm tra nếu có kết quả trong ResultSet
				if (rs.next()) {
					int i1 = 0;
//					Gán giá trị cho các thuộc tính.
					tblUser.setLoginName(loginName);
					tblUser.setPassword(rs.getString(++i1));
					tblUser.setSalt(rs.getString(++i1));
//				Đóng if kiểm tra kết quả trong ResultSet
				}
				stm.close();
				rs.close();
//				Đóng if đầu kiểm tra kết nối
			}
//			Đóng try mở cathc
		} catch (SQLException | ClassNotFoundException e) {
			// Ghi lỗi hệ thống
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném lỗi
			throw e;
//			Đóng catch mở finally
		} finally {
//			Đóng kết nối tới database
			closeConnection();
//			Đóng finally
		}
//		Trả về đối tượng blUserEntities có chứa giá trị là result.
		return tblUser;
//		Đóng chương trình.
	}

	/**
	 * Lấy tổng số user
	 * 
	 * @param groupId  Mã nhóm của user
	 * @param fullName Tên đầy đủ của user
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @return totalUser tổng số User.
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException {
		// Khai báo thuộc tính tổng số user
		int totalUser = 0;
		try {
			openConnection();
			if (conn != null) {
				// Tạo câu lệnh query mysql
				StringBuilder querySQL = new StringBuilder();
				querySQL.append("select count(*) ");
				querySQL.append("from tbl_user u ");
				querySQL.append("inner join mst_group g ");
				querySQL.append("on g.group_id = u.group_id ");
				querySQL.append("where u.rule = ? ");
				// Check sự tồn tại của group
				if (groupId != 0) {
					// Thêm câu lệnh điều kiện
					querySQL.append("and u.group_id = ? ");
				}
				if (!fullName.equals(Constant.EMPTY_STRING)) {
					// Thêm câu lệnh điều kiện
					querySQL.append("and binary u.full_name like ? ; ");
				}
				// Tạo 1 đối tượng PrepareStatement thao tác với database
				PreparedStatement stm;
				// Thao tác với database
				stm = conn.prepareStatement(querySQL.toString());
				int i = 1;
				// Lưu giá trị tham số vào câu lệnh truy vấn
				stm.setInt(i++, Constant.RULE_USER);
				if (groupId != 0) {
					stm.setInt(i++, groupId);
				}
				if (!fullName.equals(Constant.EMPTY_STRING)) {
					stm.setString(i++, "%" + fullName + "%");
				}
				// Thực thi câu lệnh truy vấn
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					// Gán giá trị cho totalUser
					totalUser = rs.getInt(1);
				}
				stm.close();
				rs.close();
			}

		} catch (ClassNotFoundException | SQLException e) {
			// Thực hiện tác vụ ghi log
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném exeption ra cho controller xử lí.
			throw e;
		} finally {
			// Đóng kết nối tới database
			closeConnection();
		}
		// Trả về số lượng User
		return totalUser;
	}

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
	 * @return listUser Danh sách user
	 */
	@Override
	public List<UserInforEntities> getListUser(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException {
		// Khởi tạo 1 list chứa các user.
		List<UserInforEntities> listUser = new ArrayList<UserInforEntities>();
		try {
			// Mở kết nối tới database
			openConnection();
			if (conn != null) {
				// Tạo câu lệnh query làm việc với database
				StringBuilder querySQL = new StringBuilder();
				querySQL.append(
						"select u.user_id, u.full_name, u.birthday, g.group_name, u.email, u.tel, mj.name_level, du.end_date, du.total ");
				querySQL.append("from tbl_user u ");
				querySQL.append("inner join mst_group g ");
				querySQL.append("on g.group_id = u.group_id ");
				querySQL.append("left join tbl_detail_user_japan du ");
				querySQL.append("on du.user_id = u.user_id ");
				querySQL.append("left join mst_japan mj ");
				querySQL.append("on mj.code_level = du.code_level ");
				querySQL.append("where u.rule = ? ");
				// Kiểm tra sự tồn tại của groupId
				if (groupId != 0) {
					// nếu != 0 thì thêm câu lệnh dưới
					querySQL.append("and g.group_id = ? ");
				}
				// Thêm câu lệnh tìm kiếm theo fullName
				querySQL.append("and binary u.full_name like ? ");
				// Trường hợp kiểm tra giá trị truyền vào của sortType
				switch (sortType) {
				// TH sort theo full_name
				case Constant.SORT_FULLNAME:
					querySQL.append("order by u.full_name ").append(sortByFullName);
					querySQL.append(", mj.name_level ").append(sortByCodeLevel);
					querySQL.append(", du.end_date ").append(sortByEndDate);
					break;
				// TH sort theo code_level
				case Constant.SORT_CODELEVEL:
					querySQL.append("order by mj.name_level ").append(sortByCodeLevel);
					querySQL.append(", u.full_name ").append(sortByFullName);
					querySQL.append(", du.end_date ").append(sortByEndDate);
					break;
				// TH sort theo end_date
				case Constant.SORT_ENDDATE:
					querySQL.append("order by du.end_date ").append(sortByEndDate);
					querySQL.append(", u.full_name ").append(sortByFullName);
					querySQL.append(", mj.name_level ").append(sortByCodeLevel);
					break;
				default:
					querySQL.append("order by u.full_name asc ");
					querySQL.append(", mj.name_level asc");
					querySQL.append(", du.end_date desc ");
					break;
				}

				querySQL.append("\n limit ? ");
				querySQL.append("\n offset ? ; ");
				// Tạo đối tượng PreparedStatement thao tác với database
				PreparedStatement stm = conn.prepareStatement(querySQL.toString());
				// set giá trị cho tham số
				int i = 1;
				stm.setInt(i++, Constant.RULE_USER);
				// Check giá trị của groupId
				if (groupId != 0) {
					stm.setInt(i++, groupId);
				}
				stm.setString(i++, "%" + fullName + "%");
				stm.setInt(i++, limit);
				stm.setInt(i++, offset);
				// Thực thi câu lệnh truy vấn
				// Tạo đối tượng ResultSet
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					// Tạo đối tượng userInforEntities
					UserInforEntities userInfor = new UserInforEntities();
					// Khái báo biến index
					int i1 = 1;
					// Sét các giá trị tương ứng vào đối tương userInforEntities
					userInfor.setUserId(rs.getInt(i1++));
					userInfor.setFullName(rs.getString(i1++));
					userInfor.setBirthday(rs.getString(i1++));
					userInfor.setGroupName(rs.getString(i1++));
					userInfor.setEmail(rs.getString(i1++));
					userInfor.setTel(rs.getString(i1++));
					userInfor.setNameLevel(rs.getString(i1++));
					userInfor.setEndDate(rs.getString(i1++));
					userInfor.setTotal(rs.getString(i1++));
					// Thêm đôi tượng vào danh sách các userInfor
					listUser.add(userInfor);
				}
				stm.close();
				rs.close();
			}
			// Bắt exeption.
		} catch (SQLException | ClassNotFoundException e) {
			// Tạo thuộc tính method
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném ra Exeption về controller xử lí
			throw e;
		} finally {
			// Đóng kết nối với database.
			closeConnection();
		}
		// Trả về 1 list userInfor
		return listUser;
	}

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
	@Override
	public TblUserEntities getUserByLoginName(int userId, String loginName)
			throws ClassNotFoundException, SQLException {
		TblUserEntities tblUser = new TblUserEntities();
		try {
			openConnection();
			if (conn != null) {
				// Tạo câu lệnh SQL
				StringBuilder sql = new StringBuilder();
				sql.append("select user.login_name ");
				sql.append("from tbl_user user ");
				sql.append("where user.login_name = ? ");
				if (userId != 0) {
					sql.append("and user.user_id = ?; ");
				}
				PreparedStatement stm;
				ResultSet rs;
				// Thao tác với DB
				stm = conn.prepareStatement(sql.toString());
				int i = 1;
				// set tham số truyền vào cho câu SQL
				stm.setString(i++, loginName);
				// Thực thi câu lệnh
				rs = stm.executeQuery();
				while (rs.next()) {
					// set các giá trị vào đối tượng
					tblUser.setLoginName(rs.getString(1));
				}
				stm.close();
				rs.close();
			}

		} catch (SQLException | ClassNotFoundException e) {
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			throw e;
		} finally {
			closeConnection();
		}
		// Trả về 1 user
		return tblUser;
	}

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
	@Override
	public TblUserEntities getUserByEmail(int userId, String email) throws ClassNotFoundException, SQLException {
		TblUserEntities tblUser = new TblUserEntities();
		try {
			openConnection();
			if (conn != null) {
				// Tạo câu lệnh SQL
				StringBuilder sql = new StringBuilder();
				sql.append("select user.email ");
				sql.append("from tbl_user user ");
				sql.append("where user.email = ? ");
				if (userId != 0) {
					sql.append("and user.user_id != ? ;");
				}
				PreparedStatement stm;
				ResultSet rs;
				// Thao tác với DB
				stm = conn.prepareStatement(sql.toString());
				int i = 1;
				// set tham số truyền vào cho câu SQL
				stm.setString(i++, email);
				if (userId != 0) {
					stm.setInt(i++, userId);
				}
				// Thực thi câu lệnh
				rs = stm.executeQuery();
				while (rs.next()) {
					// set các giá trị vào đối tượng
					tblUser.setEmail(rs.getString(1));
				}
				stm.close();
				rs.close();
			}

		} catch (SQLException | ClassNotFoundException e) {
			// Lẩy ra tên của method
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log ra console
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exeption về cho controller xử lí
			throw e;
		} finally {
			// Đóng kết nối tới db
			closeConnection();
		}
		// Trả về 1 user
		return tblUser;

	}

	/**
	 * Thêm dữ liệu vào tblUser
	 * 
	 * @param TblUserEntities tblUser đối tượng được t
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @return int idUser id của của user
	 */
	@Override
	public int insertUser(TblUserEntities tblUser) throws ClassNotFoundException, SQLException {
		// Khai báo thuộc tính userId
		int userId = 0;
		try {
			StringBuilder sqlQuery = new StringBuilder();
			// Tạo câu lệnh insert
			sqlQuery.append("insert into tbl_user ");
			sqlQuery.append(
					"(group_id, login_name, password, full_name, full_name_kana, email, tel, birthday, rule, salt) ");
			sqlQuery.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			PreparedStatement stm;
			// Statement.RETURN_GENERATED_KEYS Hằng số chỉ ra rằng các khóa đã tạo phải luôn
			// sẵn sàng để truy xuất.
			stm = conn.prepareStatement(sqlQuery.toString(), Statement.RETURN_GENERATED_KEYS);
			ResultSet rs;
			int i = 0;
			// Set các tham số truyền vào sql
			stm.setInt(++i, tblUser.getGroupId());
			stm.setString(++i, tblUser.getLoginName());
			stm.setString(++i, tblUser.getPassword());
			stm.setString(++i, tblUser.getFullName());
			stm.setString(++i, tblUser.getKanaName());
			stm.setString(++i, tblUser.getEmail());
			stm.setString(++i, tblUser.getTel());
			stm.setDate(++i, (Date) tblUser.getBirthday());
			stm.setInt(++i, tblUser.getRule());
			stm.setString(++i, tblUser.getSalt());
			stm.executeUpdate();
			// Lấy về Key
			rs = stm.getGeneratedKeys();
			if (rs.next()) {
				// Lấy ra userID của đối tượng
				userId = rs.getInt(1);
			}
			stm.close();
			rs.close();
		} catch (SQLException e) {
			// Lẩy ra tên của method
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log ra console
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exeption về cho controller xử lí
			throw e;
		}
		// Trả về userID của tbluser
		return userId;
	}

	/**
	 * Lấy user từ DB ra bằng ID tương ứng
	 * 
	 * @param userId
	 * @return userInfor lấy ra từ DB
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public boolean checkExistedId(int userId) throws ClassNotFoundException, SQLException {
		boolean checkExistUser = false;
		try {
			openConnection();
			if (conn != null) {
				// Tạo câu lệnh SQL check tồn tại id
				// Trả về 0 là không tồn tại
				// Trả về 1 là có tồn tại
				StringBuilder sql = new StringBuilder();
				sql.append("select u.user_id, g.group_name ");
				sql.append("from tbl_user u ");
				sql.append("inner join mst_group g ");
				sql.append("on u.group_id = g.group_id ");
				sql.append("where u.user_id = ? and u.rule = ?; ");

				PreparedStatement stm = conn.prepareStatement(sql.toString());
				// set tham số vào câu SQL
				int i = 0;
				stm.setInt(++i, userId);
				stm.setInt(++i, Constant.RULE_USER);
				// thực thi câu lệnh
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					// Lấy ra id của tbluser
					int id = rs.getInt(1);
					String groupName = rs.getString(2);
					if (id > 0 && groupName != null) {
						checkExistUser = true;
					}
				}
				// Đóng ResultSet
				rs.close();
				// Đóng PreparedStatement
				stm.close();
			}
		} catch (Exception e) {
			checkExistUser = false;
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			throw e;
		} finally {
			// Đóng kết nối với DB
			closeConnection();
		}
		// Trả về giá trị boolean
		return checkExistUser;
	}

	/**
	 * Hàm thao tác với DB để lấy ra userInfor với userId tồn tại tương ứng
	 * 
	 * @param userId id của user
	 * @return userInfor thông tin của user
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 */
	@Override
	public UserInforEntities getUserInforById(int userId) throws ClassNotFoundException, SQLException {
		UserInforEntities userInfor = new UserInforEntities();
		try {
			openConnection();
			if (conn != null) {
				// Tạo câu lệnh SQL
				StringBuilder sqlQuery = new StringBuilder();
				sqlQuery.append(
						"select u.user_id, u.login_name, g.group_id, g.group_name, u.full_name, u.full_name_kana, u. birthday, ");
				sqlQuery.append("u.email, u.tel, mj.code_level, mj.name_level, du.start_date, du.end_date, du.total ");
				sqlQuery.append("from tbl_user u ");
				sqlQuery.append("inner join mst_group g ");
				sqlQuery.append("on g.group_id = u.group_id ");
				sqlQuery.append("left join tbl_detail_user_japan du ");
				sqlQuery.append("on du.user_id = u.user_id ");
				sqlQuery.append("left join mst_japan mj ");
				sqlQuery.append("on mj.code_level = du.code_level ");
				sqlQuery.append("where u.user_id = ? ");
				sqlQuery.append("and u.rule = ?; ");

				PreparedStatement stm = conn.prepareStatement(sqlQuery.toString());
				// set các tham số vào câu SQL
				int i = 0;
				stm.setInt(++i, userId);
				stm.setInt(++i, Constant.RULE_USER);
				// Thực thi câu lệnh
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					// Khởi tạo giá trị index để set giá trị
					int i1 = 0;
					// Set giá trị cho userInfor cho từng trường tương ứng trên màn hình ADM005
					userInfor.setUserId(rs.getInt(++i1));
					userInfor.setLoginName(rs.getString(++i1));
					userInfor.setGroupId(rs.getInt(++i1));
					userInfor.setGroupName(rs.getString(++i1));
					userInfor.setFullName(rs.getString(++i1));
					userInfor.setFullNameKana(rs.getString(++i1));
					userInfor.setBirthday(rs.getString(++i1));
					userInfor.setEmail(rs.getString(++i1));
					userInfor.setTel(rs.getString(++i1));
					userInfor.setCodeLevel(rs.getString(++i1));
					userInfor.setNameLevel(rs.getString(++i1));
					userInfor.setStartDate(rs.getString(++i1));
					userInfor.setEndDate(rs.getString(++i1));
					userInfor.setTotal(rs.getString(++i1));
				}
				// Đóng PreparedStatement
				stm.close();
				// Đóng ResultSet
				rs.close();
			}
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exception ra controller xử lí
			throw e;
		} finally {
			// Đóng kết nối tới database
			closeConnection();
		}
		// Trả về đối tượng userInfor
		return userInfor;
	}

	/**
	 * Cập nhật dữ liệu vào tblUser
	 * 
	 * @param TblUserEntities tblUser
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return true update thành công <br>
	 *         false update không thành công
	 */
	@Override
	public boolean updateTblUser(TblUserEntities tblUser) throws ClassNotFoundException, SQLException {
		boolean checkUpdate = false;
		try {
			// Tạo câu lệnh SQL
			StringBuilder sql = new StringBuilder();
			sql.append("update tbl_user ");
			sql.append("set group_id = ?, full_name = ?, full_name_kana = ?, email = ?, tel = ?, birthday = ? ");
			sql.append("where user_id = ? and rule = ?; ");
			PreparedStatement stm = conn.prepareStatement(sql.toString());
			// set tham số vào câu lệnh
			int i = 0;
			stm.setInt(++i, tblUser.getGroupId());
			stm.setString(++i, tblUser.getFullName());
			stm.setString(++i, tblUser.getKanaName());
			stm.setString(++i, tblUser.getEmail());
			stm.setString(++i, tblUser.getTel());
			stm.setDate(++i, (Date) tblUser.getBirthday());
			stm.setInt(++i, tblUser.getId());
			stm.setInt(++i, Constant.RULE_USER);
			// Thực thi câu lệnh
			stm.execute();
			// update thành công
			checkUpdate = true;
			// Đóng PreparedStatement
			stm.close();
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exception ra controller xử lí
			throw e;
		}
		// Trả về giá trị cho logic xử lí
		return checkUpdate;
	}

	/**
	 * Xóa dữ liệu tblUser trong db
	 * 
	 * @param int userId id của user
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 */
	@Override
	public void deleteTblUser(int userId) throws ClassNotFoundException, SQLException {
		try {
			// Tạo câu lệnh SQL
			StringBuilder sql = new StringBuilder();
			sql.append("delete from tbl_user ");
			sql.append("where user_id = ?; ");
			PreparedStatement stm = conn.prepareStatement(sql.toString());
			// set tham số vào câu lệnh
			int i = 0;
			stm.setInt(++i, userId);
			// Thực thi câu lênh
			stm.execute();
			// Đóng PreparedStatement
			stm.close();
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exception ra controller xử lí
			throw e;
		}

	}

	/**
	 * Lấy ra rule của user có id tương ứng trong db
	 * 
	 * @param userId id user cần lấy
	 * @return rule của user
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi truy cập cơ sở dữ liệu
	 */
	@Override
	public int getRuleById(int userId) throws SQLException, ClassNotFoundException {
		// Khởi tạo giá trị mặc định rule ban đầu trường hợp ko có record
		int rule = -1;
		try {
			// Mở 1 kết nối tới DB
			openConnection();
			if (conn != null) {
				// Tạo câu lệnh truy vấn tới db
				StringBuilder sqlQuery = new StringBuilder();
				sqlQuery.append("select user.rule ");
				sqlQuery.append("from tbl_user user ");
				sqlQuery.append("where user.user_id = ?; ");
				PreparedStatement stm = conn.prepareStatement(sqlQuery.toString());
				// set tham số vào câu SQL
				int i = 0;
				stm.setInt(++i, userId);
				// thực thi câu lệnh
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					// gán giá trị cho rule
					rule = rs.getInt(1);
				}
				// Đóng PreparedStatement
				stm.close();
				// Đóng ResultSet
				rs.close();
			}
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exception ra controller xử lí
			throw e;
		} finally {
			// Đóng kết nối tới db
			closeConnection();
		}
		// return về đối tượng tblUser
		return rule;
	}

}
