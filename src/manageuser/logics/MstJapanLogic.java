/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * MstJapanLogic.java, 29 thg 5, 2021 PhonPV
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstJapanEntities;

/**
 * @Description Xử lý logic liên quan đến mstJapan
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public interface MstJapanLogic {

	/**
	 * Xử lý và lấy tất cả dữ liệu trong bảng mst_group của db
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return ArrayList<MstGroup> listMstGroup
	 */
	List<MstJapanEntities> getAllMstJapan() throws ClassNotFoundException, SQLException;

	/**
	 * Xử lý và lấy về mstJapan ứng với tham số codeLevel
	 * 
	 * @param String codeLevel
	 * @return MstGroup mstJapan ứng với tham số codeLevel
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	MstJapanEntities getMstJapanByCodeLevel(String codeLevel) throws ClassNotFoundException, SQLException;

}
