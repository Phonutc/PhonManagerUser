/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * CharsetEndcodingFilter.java, June 20, 2021 PhonPV
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

import manageuser.utils.Constant;

/**
 * @Description
 * Class chứa hàm xử lí hiện thị tiếng Việt
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class CharsetEndcodingFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public CharsetEndcodingFilter() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// khởi tạo đối tượng httpServletResponse và httpServletRequest để ép kiểu dữ
		// liệu
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// nếu charsetEndcoding khác utf-8 thì set charsetEncoding là utf-8
		req.setCharacterEncoding(Constant.CHARSET_ENDCODING);
		resp.setCharacterEncoding(Constant.CHARSET_ENDCODING);
		// vượt qua filter
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
