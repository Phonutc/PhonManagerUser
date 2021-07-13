/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * MstGroupDao.java, 15 thg 5, 2021 PhonPV
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstGroupEntities;

/**
 * @Description Thao tác với Database
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public interface MstGroupDao {

	/**
	 * Lấy tất cả dữ liệu trong bảng mst_group từ DB
	 * 
	 * @return ArrayList<MstGroup> listMstGroup
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<MstGroupEntities> getAllMstGroup() throws ClassNotFoundException, SQLException;

	/**
	 * Lấy về mstGroup ứng với tham số groupId
	 * 
	 * @param int groupId
	 * @return MstGroup mstGroup ứng với tham số groupId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return mstGroup với groupId tương ứng
	 */
	public MstGroupEntities getMstGroupById(int groupId) throws ClassNotFoundException, SQLException;
}
