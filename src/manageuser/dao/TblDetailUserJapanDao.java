/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * TblDetailUserJapanDao.java, 6 thg 6, 2021 PhonPV
 */
package manageuser.dao;

import java.sql.SQLException;

import manageuser.entities.TblDetailUserJapanEntities;

/**
 * @Description
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public interface TblDetailUserJapanDao extends BaseDao {

	/**
	 * Hàm insert TblDetailUserJapan
	 * 
	 * @param TblDetailUserJapan tblDetailUser
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void insertDetailUserJapan(TblDetailUserJapanEntities tblDetailUser)
			throws SQLException, ClassNotFoundException;

	/**
	 * Lấy DetailJapan từ database ra.
	 * 
	 * @param userId id của user tồn tại trong Db được edit
	 * @return detailJapan
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public TblDetailUserJapanEntities getDetailJapanById(int userId) throws SQLException, ClassNotFoundException;

	/**
	 * Hàm delete DetailUserJapan
	 * 
	 * @param TblDetailUserJapan tblDetailUser
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean deleteDetailJapan(int userId) throws SQLException, ClassNotFoundException;

	/**
	 * Hàm update DetailUserJapan
	 * 
	 * @param TblDetailUserJapan tblDetailUser
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void updateDetailJapan(TblDetailUserJapanEntities tblDetailUser) throws ClassNotFoundException, SQLException;

}
