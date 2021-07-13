/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * TblDetailUserJapanDaoImpl.java, 6 thg 6, 2021 PhonPV
 */
package manageuser.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import manageuser.dao.TblDetailUserJapanDao;
import manageuser.entities.TblDetailUserJapanEntities;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * @Description
 * Class chứa các hàm thao tác với bảng tbl_detail_user_japan
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

	/**
	 * Hàm insert TblDetailUserJapan
	 * 
	 * @param TblDetailUserJapan tblDetailUser
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public void insertDetailUserJapan(TblDetailUserJapanEntities tblDetailUser)
			throws SQLException, ClassNotFoundException {
		try {
			// Tạo câu lệnh SQL
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("INSERT INTO tbl_detail_user_japan ");
			sqlQuery.append("(user_id, code_level, start_date, end_date, total) ");
			sqlQuery.append(" VALUES ( ?, ?, ?, ?, ?);");
			PreparedStatement stm = conn.prepareStatement(sqlQuery.toString());
			int i = 1;
			// Set các thuộc tính vào Sql
			stm.setInt(i++, tblDetailUser.getUserId());
			stm.setString(i++, tblDetailUser.getCodeLevel());
			stm.setDate(i++, (Date) tblDetailUser.getStartDate());
			stm.setDate(i++, (Date) tblDetailUser.getEndDate());
			stm.setInt(i++, tblDetailUser.getTotal());
			// Thực thi câu lệnh insert
			stm.executeUpdate();
			// Đong PreparedStatement
			stm.close();
		} catch (Exception e) {
			// Lẩy ra tên của method
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log ra console
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exeption về cho controller xử lí
			throw e;
		}

	}

	/**
	 * Lấy DetailJapan từ database ra.
	 * 
	 * @param userId id của user tồn tại trong Db được edit
	 * @return detailJapan
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public TblDetailUserJapanEntities getDetailJapanById(int userId) throws ClassNotFoundException, SQLException {
		// Tạo ra đối tượng detailJapan
		TblDetailUserJapanEntities detailJapan = new TblDetailUserJapanEntities();
		try {
			// Mở 1 kết nối tới DB
			openConnection();
			// Kiểm tra tồn tại kết nối
			if (conn != null) {
				// Tạo câu lệnh truy vấn dữ liệu từ db
				// Tạo đối tượng stringbuilder
				StringBuilder querySQL = new StringBuilder();
				querySQL.append("select code_level, start_date, end_date, total ");
				querySQL.append("from tbl_detail_user_japan ");
				querySQL.append("where user_id = ?; ");
				PreparedStatement stm = conn.prepareStatement(querySQL.toString());
				ResultSet rs;
				// set tham số vào câu SQL
				int i = 0;
				stm.setInt(++i, userId);
				// Thực thi câu lệnh
				rs = stm.executeQuery();
				while (rs.next()) {
					int k = 0;
					detailJapan.setCodeLevel(rs.getString(++k));
					detailJapan.setStartDate(Common.convertStringToDate(rs.getString(++k)));
					detailJapan.setEndDate(Common.convertStringToDate(rs.getString(++k)));
					detailJapan.setTotal(Common.convertStringToInt(rs.getString(++k), Constant.DEFAULT_USER_ID));
				}
				// Đóng ResultSet
				rs.close();
				// Đóng PreparedStatement
				stm.close();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// Lẩy ra tên của method
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log ra console
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exeption về cho controller xử lí
			throw e;
		} finally {
			// Đóng kết nối với db
			closeConnection();
		}
		// Trả về đối tượng detailJapan
		return detailJapan;
	}

	/**
	 * Hàm delete DetailUserJapan
	 * 
	 * @param TblDetailUserJapan tblDetailUser
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public boolean deleteDetailJapan(int userId) throws SQLException, ClassNotFoundException {
		// Tạo biến check đã xóa thành công hay không?
		boolean checkDelete = false;
		try {
			// Tạo câu lệnh SQL
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM tbl_detail_user_japan ");
			sql.append("WHERE user_id = ? ");
			PreparedStatement pre = conn.prepareStatement(sql.toString());
			// set tham số vào câu lệnh
			int i = 0;
			pre.setInt(++i, userId);
			// Thực thi câu lệnh
			pre.execute();
			checkDelete = true;
			// Đóng PreparedStatement
			pre.close();
		} catch (Exception e) {
			// Lẩy ra tên của method
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log ra console
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exeption về cho controller xử lí
			throw e;
		}
		// Trả về giá trị check
		return checkDelete;
	}

	/**
	 * Hàm update DetailUserJapan
	 * 
	 * @param TblDetailUserJapan tblDetailUser
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public void updateDetailJapan(TblDetailUserJapanEntities tblDetailUser)
			throws ClassNotFoundException, SQLException {
		try {
			// Tạo câu lệnh SQL
			StringBuilder querySQL = new StringBuilder();
			querySQL.append("update tbl_detail_user_japan ");
			querySQL.append("set code_level = ?, start_date = ?, end_date = ?, total = ? ");
			querySQL.append("where user_id = ?; ");
			PreparedStatement stm = conn.prepareStatement(querySQL.toString());
			// Set các tham số vào câu SQL
			int i = 0;
			stm.setString(++i, tblDetailUser.getCodeLevel());
			stm.setDate(++i, (Date) tblDetailUser.getStartDate());
			stm.setDate(++i, (Date) tblDetailUser.getEndDate());
			stm.setInt(++i, tblDetailUser.getTotal());
			stm.setInt(++i, tblDetailUser.getUserId());
			// Thực thi câu lệnh
			stm.execute();
			// Đóng PreparedStatement
			stm.close();
		} catch (Exception e) {
			// Lẩy ra tên của method
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log ra console
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exeption về cho controller xử lí
			throw e;
		}

	}

}
