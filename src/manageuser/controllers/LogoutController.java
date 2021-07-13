/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * LogoutController.java, 9 thg 5, 2021 PhonPV
 */
package manageuser.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description Xử lí cho tác vụ logout màn hình.
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class LogoutController extends HttpServlet {

	/**
	 * Thuộc tính định danh của Serial class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Hàm xử lý khi logout đăng nhập
	 * 
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession httpSession = request.getSession();
		// Xóa session
		httpSession.invalidate();
		String path = request.getContextPath();
		response.sendRedirect(path);
	}

}
