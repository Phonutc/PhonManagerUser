/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * MstJapanDaoImpl.java, 29 thg 5, 2021 PhonPV
 */
package manageuser.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstJapanDao;
import manageuser.entities.MstJapanEntities;

/**
 * @Description
 * Chứa các hàm thao tác với bảng mst_japan từ db
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {

	/**
	 * Lấy tất cả dữ liệu trong bảng mst_japan của DB
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return List<> listMstJapan danh sách trình độ tiếng Nhật
	 */
	@Override
	public List<MstJapanEntities> getAllMstJapan() throws ClassNotFoundException, SQLException {
		// tạo ra 1 danh sách chứa các đối mstJapan
		List<MstJapanEntities> listMstJapan = new ArrayList<MstJapanEntities>();
		try {
			openConnection();
			if (conn != null) {
				// Khởi tạo câu lệnh SQL
				StringBuffer sqlQuery = new StringBuffer();
				sqlQuery.append("select code_level, name_level from mst_japan ");
				sqlQuery.append("order by code_level asc;");
				// Khai báo 1 đối tượng preparaStatement
				PreparedStatement stm;
				stm = conn.prepareStatement(sqlQuery.toString());
				// Khai báo 1 đói trượng ResultSet để thực thi câu lệnh truy vấn
				ResultSet rs;
				rs = stm.executeQuery();
				while (rs.next()) {
					// Tạo 1 đối tượng mstJapan
					MstJapanEntities mstJapan = new MstJapanEntities();
					// Set các giá trị tương ứng vào đối tượng
					mstJapan.setCodeLevel(rs.getString(1));
					mstJapan.setNameLevel(rs.getString(2));
					// Thêm đối tượng vào danh sách chứa các mstJapan
					listMstJapan.add(mstJapan);

				}
				stm.close();
				rs.close();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi Log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném ra exeption cho controller xử lí
			throw e;
		} finally {
			// Đóng kết nối tới database
			closeConnection();
		}
		// Tră về danh sách MstJanpan
		return listMstJapan;
	}

	/**
	 * Lấy tất cả các trường trong bảng mst_japan của DB
	 * 
	 * @param String codeLevel
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return mstJapan đối tượng chưa các thuộc tính tương ứng
	 */
	@Override
	public MstJapanEntities getMstJapanByCodeLevel(String codeLevel) throws ClassNotFoundException, SQLException {
		MstJapanEntities mstJapan = new MstJapanEntities();
		try {
			openConnection();
			if (conn != null) {
				// Khởi tạo câu lệnh SQL
				StringBuilder sql = new StringBuilder();
				sql.append("select code_level, name_level from mst_japan ");
				sql.append("where code_level = ?;");
				PreparedStatement stm;
				ResultSet rs;
				stm = conn.prepareStatement(sql.toString());
				// set các tham số cho câu lệnh SQL
				int i = 1;
				stm.setString(i++, codeLevel);
				// Thực thi câu lệnh
				rs = stm.executeQuery();
				while (rs.next()) {
					mstJapan.setCodeLevel(rs.getString(1));
					mstJapan.setNameLevel(rs.getString(2));
				}
				stm.close();
				rs.close();
			}
		} catch (SQLException | ClassNotFoundException e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi Log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném ra exeption cho controller xử lí
			throw e;
		} finally {
			// Đóng kết nối
			closeConnection();
		}
		// Trả về đối tượng mstjapan
		return mstJapan;
	}

}
