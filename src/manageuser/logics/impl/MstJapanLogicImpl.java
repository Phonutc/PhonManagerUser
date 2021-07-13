/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * MstJapanLogicImpl.java, 29 thg 5, 2021 PhonPV
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstJapanDao;
import manageuser.dao.impl.MstJapanDaoImpl;
import manageuser.entities.MstJapanEntities;
import manageuser.logics.MstJapanLogic;

/**
 * @Description Xử lý logic liên quan đến mstJapan
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class MstJapanLogicImpl implements MstJapanLogic {

	/**
	 * Lấy tất cả dữ liệu trong bảng mst_japan của DB
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return List<> listMstJapan danh sách trình độ tiếng Nhật
	 */
	@Override
	public List<MstJapanEntities> getAllMstJapan() throws ClassNotFoundException, SQLException {
		try {
			List<MstJapanEntities> listMstJapan = new ArrayList<MstJapanEntities>();
			MstJapanDao mstJapanDao = new MstJapanDaoImpl();
			listMstJapan = mstJapanDao.getAllMstJapan();
			return listMstJapan;
		} catch (ClassNotFoundException | SQLException e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném exeption ra cho controller xử lí
			throw e;
		}
	}

	/**
	 * Xử lý và lấy về mstJapan ứng với tham số codeLevel
	 * 
	 * @param String codeLevel
	 * @return MstGroup mstJapan ứng với tham số codeLevel
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public MstJapanEntities getMstJapanByCodeLevel(String codeLevel) throws ClassNotFoundException, SQLException {
		// Khởi tạo đối tượng mstJapanDao thao tác với db
		MstJapanDao mstJapanDao = new MstJapanDaoImpl();
		// Lấy ra dối tượng mstJapan theo codeLevel
		MstJapanEntities mstJapan = mstJapanDao.getMstJapanByCodeLevel(codeLevel);
		// Trả về đối tượng mstJapan
		return mstJapan;
	}

}
