/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * MstJapanDao.java, 29 thg 5, 2021 PhonPV
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstJapanEntities;

/**
 * @Description
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public interface MstJapanDao {

	/**
	 * Lấy tất cả dữ liệu trong bảng mst_japan của DB
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return List<> listMstJapan danh sách trình độ tiếng Nhật
	 */
	List<MstJapanEntities> getAllMstJapan() throws ClassNotFoundException, SQLException;

	/**
	 * Lấy về mstJapan ứng với tham số codeLevel
	 * 
	 * @param String codeLevel
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return mstJapan có codeLevel tương ứng
	 */
	MstJapanEntities getMstJapanByCodeLevel(String codeLevel) throws ClassNotFoundException, SQLException;
}
