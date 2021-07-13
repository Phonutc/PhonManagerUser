/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * BaseDao.java, 15 thg 5, 2021 PhonPV
 */
package manageuser.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public interface BaseDao {
	/**
	 * 
	 * @return Lấy ra 1 connection.
	 */
	Connection getConn();

	/**
	 * @param conn the conn to set
	 */
	void setConn(Connection conn);

	/**
	 * Mở kết nối tới database.
	 * 
	 * @return trả về 1 kết nối đến database.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	void openConnection() throws ClassNotFoundException, SQLException;

	/**
	 * Đóng kêt nối tới database.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	void closeConnection() throws ClassNotFoundException, SQLException;

	/**
	 * Lấy về tất cả list tên cột trong database
	 * 
	 * @return List<String> danh sách tên các cột
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<String> getListAllColumns() throws ClassNotFoundException, SQLException;

	/**
	 * Thay đổi tính năng autoCommit
	 * 
	 * @param boolean rs
	 * @return boolean false: tắt autocommit true: bật autocommit
	 */
	public void setAutoCommit(boolean rs) throws ClassNotFoundException, SQLException;

	/**
	 * thực hiện commit dữ liệu
	 * 
	 * @throws SQLException
	 */
	void commit() throws SQLException;

	/**
	 * thực hiện rollback lại dữ liệu
	 * 
	 * @throws SQLException
	 */
	void rollBack() throws SQLException;

}
