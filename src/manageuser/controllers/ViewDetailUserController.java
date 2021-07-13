/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * ViewDetailUserController.java, 9 thg 6, 2021 PhonPV
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.entities.UserInforEntities;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.properties.MessageProperties;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * @Description Xử lí hiện thị user ra màn hình ADM005
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class ViewDetailUserController extends HttpServlet {

	/**
	 * Thuộc tính định danh của Serial class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm xử lí hiện thị user lên form ADM005
	 * 
	 * @param HttpServletRequest  req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Lấy userId từ request về
			int userId = Common.convertStringToInt(req.getParameter(Constant.USER_ID), Constant.DEFAULT_USER_ID);
			// Check sự tồn tại của user với userId lấy từ url request về
			if (userId > 0 && tblUserLogic.checkExistedId(userId)) {
				UserInforEntities userInfor = tblUserLogic.getUserInforById(userId);
				// Set đối tượng userInfor lên request hiện thị ra màn hình
				req.setAttribute(Constant.MESSAGE, MessageProperties.getValueByKey(Constant.MSG004));
				req.setAttribute(Constant.USERINFOR, userInfor);
				// Điều hướng sang trang ADM005 hiện thị
				RequestDispatcher rd = req.getRequestDispatcher(Constant.ADM005_PATH);
				rd.forward(req, resp);
			} else {
				// Nếu user không tồn tại thì hiện thị lỗi Không tồn tại user bên màn
				// SysTemError
				String path = req.getContextPath() + Constant.ERROR_PAGE + "?" + Constant.TYPE + "="
						+ Constant.NOT_EXIST_USER;
				resp.sendRedirect(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Điều hướng về trang lỗi hệ thống nếu xảy ra lỗi hệ thống
			resp.sendRedirect(req.getContextPath() + Constant.ERROR_PAGE);
		}
	}

}
