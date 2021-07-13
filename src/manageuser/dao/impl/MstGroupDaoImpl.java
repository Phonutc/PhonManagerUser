/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * MstGroupDaoImpl.java, 12 thg 5, 2021 PhonPV
 */
package manageuser.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstGroupDao;
import manageuser.entities.MstGroupEntities;

/**
 * @Description Lấy dữ liệu từ bảng mst_group trong database
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {

	/**
	 * Lấy dữ liệu từ mảng mstgroup
	 * 
	 * @return listMstGroup danh sách mstgroup
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@SuppressWarnings("finally")
	@Override
	public List<MstGroupEntities> getAllMstGroup() throws ClassNotFoundException, SQLException {
		// Tạo 1 danh sách chứa các đối tượng mstGroup.
		List<MstGroupEntities> listGroup = new ArrayList<MstGroupEntities>();
		try {
			openConnection();
			if (conn != null) {
				// Khởi tạo 1 cấu lệnh query SQL
				StringBuffer querySQL = new StringBuffer();
				querySQL.append("select * from mst_group ");
				querySQL.append("order by group_id ");
				// Sử dụng PrepareStatement để lưu câu truy vấn gửi tới DB
				PreparedStatement stm;
				stm = conn.prepareStatement(querySQL.toString());
				// Tạo đối tượng ResultSet để thực thi câu lệnh truy vấn
				ResultSet rs;
				// Thực thi câu lệnh query
				rs = stm.executeQuery();
				while (rs.next()) {
					// Tạo 1 đối tượng mstGroup
					MstGroupEntities mstGroup = new MstGroupEntities();
					// Gán giá trị cho các thuộc tính của đối tương mstGroup
					mstGroup.setGroupId(rs.getInt(1));
					mstGroup.setGroupName(rs.getString(2));
					// Thêm đối tượng mstGroup vào danh sách các đối tượng.
					listGroup.add(mstGroup);
				}
				stm.close();
				rs.close();
			}
		} catch (SQLException | ClassNotFoundException e) {
			// Lấy ra tên của method hiện tại
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi Log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném lỗi về controller xử lí
			throw e;
		} finally {
			// Đóng kết nối tới database
			closeConnection();
			// Trả về danh sách MstGroup.
			return listGroup;
		}
	}

	/**
	 * Lấy về mstGroup ứng với tham số groupId
	 * 
	 * @param int groupId
	 * @return MstGroup mstGroup ứng với tham số groupId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return mstGroup với groupId tương ứng
	 */
	@Override
	public MstGroupEntities getMstGroupById(int groupId) throws ClassNotFoundException, SQLException {
		MstGroupEntities mstGroup = new MstGroupEntities();
		try {
			openConnection();
			if (conn != null) {
				// Khởi tạo câu lệnh SQL
				StringBuilder sql = new StringBuilder();
				sql.append("select * from mst_group ");
				sql.append("where group_id = ?;");
				PreparedStatement stm;
				ResultSet rs;
				stm = conn.prepareStatement(sql.toString());
				// set tham số vào câu lệnh SQL
				int i = 1;
				stm.setInt(i++, groupId);
				// Thực thi câu lệnh
				rs = stm.executeQuery();
				while (rs.next()) {
					mstGroup.setGroupId(rs.getInt(1));
					mstGroup.setGroupName(rs.getString(2));
				}
				stm.close();
				rs.close();
			}

		} catch (SQLException | ClassNotFoundException e) {
			// Lấy ra tên của method hiện tại
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi Log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném lỗi về controller xử lí
			throw e;
		} finally {
			// Đóng kết nối đến database
			closeConnection();
		}
		// Trả về 1 đối tượng mstGroup chứa các thuộc tính vừa lấy về
		return mstGroup;

	}
}
