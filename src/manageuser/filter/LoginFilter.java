/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * LoginFilter.java, 19 th 6, 2021 PhonPV
 */
package manageuser.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * @Description
 * Class chứa hàm xử lí check login cho chương trình
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class LoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Khai báo thuộc tính request của client và respone của server
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// Lấy 1 session và gán giá trị mặc định cho nó là false.
		// Vì require là checkLogin thành công thì mới tạo session
		HttpSession session = req.getSession(false);
		String contextPath = req.getContextPath();
		try {
			String url = req.getServletPath();
			String jspPath = Constant.JSP_FOLDER_PATTERN;
			String cssPath = Constant.CSS_FOLDER_PATTERN;
			String jsPath = Constant.JS_FOLDER_PATTERN;
			String imgPath = Constant.IMG_FOLDER_PATTERN;
			if (session == null) {
				if (Constant.LOGIN_PAGE.equals(url)) {
					chain.doFilter(req, resp);
				} else {
					resp.sendRedirect(contextPath + Constant.LOGIN_PAGE);
				}
			} else {
				// Kiểm tra đã đăng nhập hay chưa? cho các màn hình khác
				if (Constant.ERROR_PAGE.equals(url) || url.startsWith(cssPath) || url.startsWith(jsPath) || url.startsWith(imgPath)) {
					// Nếu gọi error thì cho qua
					chain.doFilter(req, resp);
				} else if (url.equals(Constant.LOGIN_PAGE) || url.equals(Constant.ROOT_PATH)) {
					// Nếu gọi đến url Login hoặc mặc định "/"
					// Kiểm tra đăng nhập
					if (Common.checkLogin(session)) {
						// Nếu đã đăng nhập, redirect về trang listUser
						resp.sendRedirect(contextPath + Constant.LIST_USER_PAGE);
					} else {
						// Nếu chưa đăng nhập
						// Cho phép vượt qua
						chain.doFilter(req, resp);
					}
				} else if (url.startsWith(jspPath)) {
					// Nếu chưa login mà nhập các path tới các views jsp
					resp.sendRedirect(contextPath + Constant.LOGIN_PAGE);
				} else if (Common.checkLogin(session)) {
					chain.doFilter(req, resp);
				} else {
					resp.sendRedirect(contextPath + Constant.LOGIN_PAGE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			resp.sendRedirect(req.getContextPath() + Constant.ERROR_PAGE);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
