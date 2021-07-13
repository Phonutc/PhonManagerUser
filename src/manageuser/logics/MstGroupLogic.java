/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * MstGroupLogic.java, 15 thg 5, 2021 PhonPV
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstGroupEntities;

/**
 * @Description Xử lý logic liên quan đến group
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public interface MstGroupLogic {

	/**
	 * Xử lý và lấy tất cả dữ liệu trong bảng mst_group của db
	 * 
	 * @throws SQLException           lỗi câu lệnh SQL
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @return ArrayList<MstGroup> listMstGroup
	 */
	List<MstGroupEntities> getAllMstGroup() throws ClassNotFoundException, SQLException;

	/**
	 * Xử lý và lấy về mstGroup ứng với tham số groupId
	 * 
	 * @param int groupId
	 * @return MstGroup mstGroup ứng với tham số groupId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MstGroupEntities getMstGroupById(int groupId) throws ClassNotFoundException, SQLException;
}
