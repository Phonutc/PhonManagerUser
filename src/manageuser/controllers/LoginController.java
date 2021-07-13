/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * LoginController.java, 9 thg 5, 2021 PhonPV
 */
package manageuser.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * @Description Class xử lí controller cho phần Login
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class LoginController extends HttpServlet {

	/**
	 * Thuộc tính định danh của Serial class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {

	}

	/**
	 * Hiển thị dữ liệu từ màn hình ADM001
	 * 
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy RequestDispatcher tới màn hình ADM001.
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM001_PATH);
			// Forward tới màn hình ADM001
			rd.forward(request, response);
			// Mở catch để xử lí lỗi
		} catch (Exception e) {
			// Lấy ra tên của method
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// GHi log ra hệ thống
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Chuyển hướng sang trang system_error
			response.sendRedirect(request.getContextPath() + Constant.ERROR_PAGE);
		}
	}

	/**
	 * Hàm xử lý khi submit form login
	 * 
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// lấy giá trị LoginName từ request
			String loginName = request.getParameter(Constant.LOGIN_NAME);
			// lấy giá trị Password từ request
			String password = request.getParameter(Constant.PASSWORD);
			// Khởi tạo đối tượng để validate thông tin đăng nhập
			ValidateUser validateUser = new ValidateUser();
			// Khởi tạo list chứa các message nếu có lỗi đăng nhập
			List<String> listError = validateUser.validateLogin(loginName, password);
			if (listError.size() != 0) {
				// Truyền message vào req
				request.setAttribute(Constant.LIST_ERROR, listError);
				// Lưu LoginName lại để giữ lại cho trang tiếp theo.
				request.setAttribute(Constant.LOGIN_NAME, loginName);
				// Lấy RequestDispatcher tới màn hình ADM001.
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM001_PATH);
				// Forward tới ADM001.jsp để hiện thị lỗi.
				rd.forward(request, response);
			} else {
				// Nếu đăng nhập thành công
				// Lấy ra session của request
				HttpSession httpSession = request.getSession();
				// Cập nhật thông tin cần thiết vào session.
				httpSession.setAttribute(Constant.LOGIN_NAME, loginName);
				httpSession.setMaxInactiveInterval(300);
				// Điều hướng tới màn lisUser.
				response.sendRedirect(request.getContextPath() + Constant.LIST_USER_PAGE);

			}
		} catch (Exception e) {
			// Lấy ra tên method hiện hành
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + methodName + "-" + e.getMessage());
			// Chuyển hướng sang trang system_error
			response.sendRedirect(request.getContextPath() + Constant.ERROR_PAGE);
		}
	}

}
