/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * BaseDao.java, 9 thg 5, 2021 PhonPV
 */
package manageuser.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.BaseDao;
import manageuser.properties.DatabaseProperties;
import manageuser.utils.Constant;

/**
 * @Description Mở kết nối, đóng kết nối đến database
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class BaseDaoImpl implements BaseDao {
	protected Connection conn;

	/*
	 * Hàm khởi tạo không tham số
	 */
	public BaseDaoImpl() {
	}

	/**
	 * @return the conn
	 */
	@Override
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn the conn to set
	 */
	@Override
	public void setConn(Connection conn) {
		this.conn = conn;

	}

	/**
	 * Tạo kết nối tới database
	 * 
	 * @return con - kết nối
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public void openConnection() throws SQLException, ClassNotFoundException {
		String url = Constant.URL_DB;
		String loginName = Constant.USER_NAME;
		String pass = Constant.PASSWORD_LOGIN;
		// Mở khối try catch
		try {
			// Đăng kí jdbc driver
			Class.forName(Constant.DRIVER_DB);
			// Tạo đối tượng connection.
			conn = DriverManager.getConnection(url, loginName, pass);
			// mở khối catch
		} catch (SQLException | ClassNotFoundException e) {
			// ghi log
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném lỗi
			throw e;
		}
		// Đóng chương trình kết thúc function
	}

	/**
	 * Đóng kết nối với database
	 * 
	 * @throws SQLException
	 */
	@Override
	public void closeConnection() throws SQLException {
		// Mới khối try catch.
		try {
			// Kiểm tra có đang tồn tại kết nối không?
			if (conn != null && conn.isClosed()) {
				// Đóng kết nối
				conn.close();
			}
//			mở khối catch
		} catch (SQLException e) {
			// in lỗi
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exeption ra controller xử lí.
			throw e;
			// Đóng catch
		}
		// Đóng chương trình
	}

	/**
	 * Trả về các trường theo cột
	 * 
	 * @return listColumn danh sách các trường theo column
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<String> getListAllColumns() throws ClassNotFoundException, SQLException {
		// Khai báo list để lưu trữ các giá trị
		List<String> listColumn = new ArrayList<String>();
		try {
			// Mở kết nối
			openConnection();
			// Kiểm tra tồn tại kết nối hay không nếu tồn tại
			if (conn != null) {
				// Đọc dữ liệu từ file database với key tương ứng
				String dbName = DatabaseProperties.getValueByKey(Constant.DB_NAME);
				// Khởi tạo câu lệnh query mySQL
				StringBuffer querySQL = new StringBuffer();
				querySQL.append("select distinct column_name from information_schema.columns ");
				querySQL.append("where table_schema = ? ");
				// Sử dụng PrepareStatement để lưu câu truy vấn gửi tới DB
				PreparedStatement stm;
				stm = conn.prepareStatement(querySQL.toString());
				// Set các tham số cho câu lệnh
				stm.setString(1, dbName);
				// THực thi câu lênh truy vấn
				// Tạo đối tượng ResultSet
				ResultSet rs;
				rs = stm.executeQuery();
				// Kiểm tra sự tồn tại trong ResultSet
				while (rs.next()) {
					// Gán giá trị cho các thuộc tính
					listColumn.add(rs.getString(1));
				}

			}
		} catch (ClassNotFoundException | SQLException e) {
			// tiến hành ghi lỗi
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exeption ra để controller xử lí
			throw e;
			// Đóng catch
		} finally {
			// Đóng kết nối tới database
			closeConnection();
		}
		// Trả về danh sách cột
		return listColumn;
	}

	/**
	 * Thay đổi tính năng autoCommit
	 * 
	 * @param boolean rs
	 * @return boolean false: tắt autocommit true: bật autocommit
	 */
	@Override
	public void setAutoCommit(boolean rs) throws ClassNotFoundException, SQLException {
		try {
			// Check sự tồn tại của kết nối
			if (conn != null) {
				conn.setAutoCommit(rs);
			}
		} catch (SQLException e) {
			// tiến hành ghi lỗi
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exeption ra để controller xử lí
			throw e;
		}

	}

	/**
	 * thực hiện commit dữ liệu
	 * 
	 * @throws SQLException
	 */
	@Override
	public void commit() throws SQLException {
		try {
			// Check sự tồn tại của kết nối
			if (conn != null) {
				conn.commit();
			}
		} catch (SQLException e) {
			// tiến hành ghi lỗi
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exeption ra để controller xử lí
			throw e;
		}
	}

	/**
	 * thực hiện rollback lại dữ liệu
	 * 
	 * @throws SQLException
	 */
	@Override
	public void rollBack() throws SQLException {
		try {
			// Check sự tồn tại của kết nối
			if (conn != null) {
				conn.rollback();
			}
		} catch (SQLException e) {
			// tiến hành ghi lỗi
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném Exeption ra để controller xử lí
			throw e;
		}

	}

}
