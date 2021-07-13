/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * MstGroupLogicImpl.java, 12 thg 5, 2021 PhonPV
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstGroupDao;
import manageuser.dao.impl.MstGroupDaoImpl;
import manageuser.entities.MstGroupEntities;
import manageuser.logics.MstGroupLogic;

/**
 * @Description Chứa các method xử lí logic liên quuan tới bảng mst_group.
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class MstGroupLogicImpl implements MstGroupLogic {

	/**
	 * Tạo danh sách chứa các mstgroup
	 * 
	 * @return listGroupEntities danh sách chứa các group
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<MstGroupEntities> getAllMstGroup() throws ClassNotFoundException, SQLException {
		// Khởi tạo 1 đối tượng MstGroupDaoImpl
		MstGroupDao mstGroupDao = new MstGroupDaoImpl();
		// Khởi tạo 1 danh sách chứa các group
		List<MstGroupEntities> listGroupEntities = new ArrayList<MstGroupEntities>();
		// Gán dữ liệu vào listGroupEntities
		listGroupEntities = mstGroupDao.getAllMstGroup();
		// Trả về danh sách chứa các group
		return listGroupEntities;
	}

	/**
	 * Xử lý và lấy về mstGroup ứng với tham số groupId
	 * 
	 * @param int groupId
	 * @return MstGroup mstGroup ứng với tham số groupId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public MstGroupEntities getMstGroupById(int groupId) throws ClassNotFoundException, SQLException {
		// khởi tạo 1 đối tượng MstGroupDAOImpl
		MstGroupDao mstGroupDAO = new MstGroupDaoImpl();
		// lấy mstGroup từ db
		MstGroupEntities mstGroup = mstGroupDAO.getMstGroupById(groupId);
		// trả về 1 listMstGroup
		return mstGroup;
	}

}
