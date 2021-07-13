/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * SuccessController.java, 7 thg 6, 2021 PhonPV
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.properties.MessageProperties;
import manageuser.utils.Constant;

/**
 * @Description
 * Chứa hàm xử lí hiện thị thông báo trên màn ADM006
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class SuccessController extends HttpServlet {

	/**
	 * Thuộc tính định danh của Serial class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Thực hiên các thao tác hiện thị thông báo trên màn hình ADM006
	 * 
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Lấy từ request về hành động trên màn hình
			String type = req.getParameter(Constant.TYPE);
			String msg = Constant.EMPTY_STRING;
			// Khi type là insert user vô db
			if (type.equals(Constant.INSERT_SUCCESS)) {
				// Đọc câu thông báo từ file Câu thông báo
				msg = MessageProperties.getValueByKey(Constant.MSG001);
			}
			// Khi type là update user vô db
			if (type.equals(Constant.UPDATE_SUCCESS)) {
				msg = MessageProperties.getValueByKey(Constant.MSG002);
			}
			// Khi type là delete user khỏi db
			if (type.equals(Constant.DELETE_SUCCESS)) {
				msg = MessageProperties.getValueByKey(Constant.MSG003);
			}
			// Gửi thông báo lên request hiện thị ra màn hình
			req.setAttribute(Constant.MESSAGE, msg);
			// điều hướng sang view ADM006
			RequestDispatcher rd = req.getRequestDispatcher(Constant.ADM006_PATH);
			rd.forward(req, resp);
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Chuyển hướng sang trang system_error
			resp.sendRedirect(req.getContextPath() + Constant.ERROR_PAGE);
		}
	}

}
