/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * DeleteUserController.java, 16 thg 6, 2021 PhonPV
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.logics.TblDetailUserJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblDetailUserJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * @Description
 * Chứa hàm xứ lí xóa user từ db
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class DeleteUserController extends HttpServlet {
	/**
	 * Thuộc tính định danh cho Serial class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm xử lý chức năng delete user
	 * 
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy userId từ request
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), Constant.DEFAULT_USER_ID);
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			TblDetailUserJapanLogic tblDetailLogic = new TblDetailUserJapanLogicImpl();
			int rule = tblUserLogic.getRuleById(userId);
			// check kiểm tra tồn tại của user
			if (rule == Constant.RULE_USER) {
				// Không có lỗi có nghĩa là user tồn tại
				// check sự tồn tại của userDetailJapan
				boolean checkDetailJapan = tblDetailLogic.checkExistedDetailUserById(userId);
				// Xóa user
				boolean checkDeleteUser = tblUserLogic.deleteUser(userId, checkDetailJapan);
				String url = Constant.EMPTY_STRING;
				if (checkDeleteUser) {
					// Nếu xóa thành công
					url = request.getContextPath() + Constant.SUCCESS_PAGE + "?" + Constant.TYPE + "="
							+ Constant.DELETE_SUCCESS;
				} else {
					// Lỗi thao thác với database khi xóa
					url = request.getContextPath() + Constant.ERROR_PAGE + "?" + Constant.TYPE + "="
							+ Constant.DELETE_NOTEXIST_USER;
				}
				// Điều hướng để hiện thị các view cho các th tương ứng
				response.sendRedirect(url);
			} else if (rule == Constant.RULE_ADMIN) {
				// Nếu có lỗi thì hiện thị lỗi đó ra màn Error với lỗi tương ứng
				response.sendRedirect(request.getContextPath() + Constant.ERROR_PAGE + "?" + Constant.TYPE + "="
						+ Constant.NOT_DELETE_ADMIN);
			}
			if (rule == -1) {
				// Nếu user không tồn tại thì hiện thị câu thông báo Ero013 ra systemerror
				response.sendRedirect(request.getContextPath() + Constant.ERROR_PAGE + "?" + Constant.TYPE + "="
						+ Constant.NOT_EXIST_USER);
			}
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Chuyển hướng sang trang system_error
			response.sendRedirect(request.getContextPath() + Constant.ERROR_PAGE);
		}
	}

}
