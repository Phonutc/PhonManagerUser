package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.properties.MessageErrorProperties;
import manageuser.utils.Constant;

/**
 * 
 * @Description Thực hiện hiển thị thông báo lỗi
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class ErrorController extends HttpServlet {

	/**
	 * Thuộc tính định danh của Serial class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm xử lý khi thực hiện hiện thị lỗi
	 * 
	 * @param HttpServletRequest  request lấy dữ liệu người dùng từ client
	 * @param HttpServletResponse response server trả về.
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			// Lấy type từ request về
			String type = request.getParameter(Constant.TYPE);
			// Khai báo thuộc tính lỗi lúc đầu = rỗng.
			String error = Constant.EMPTY_STRING;
			// Kiểm tra với mã lỗi user không tồn tại
			if (Constant.NOT_EXIST_USER.equals(type)) {
				error = MessageErrorProperties.getValueByKey(Constant.ER0013);
			} else if (Constant.NOT_DELETE_ADMIN.equals(type)) {
				// Với mã lỗi không xóa được user là Admin
				error = MessageErrorProperties.getValueByKey(Constant.ER020);
			} else if (Constant.DELETE_NOTEXIST_USER.equals(type)) {
				// Với mã lỗi không xóa được user không tồn tại
				error = MessageErrorProperties.getValueByKey(Constant.ER014);
			} else {
				// Mã lỗii thao tác với Database
				error = MessageErrorProperties.getValueByKey(Constant.ER015);
			}
			request.setAttribute(Constant.ERROR, error);
			// Điều hướng sang trang lỗi
			RequestDispatcher rd = request.getRequestDispatcher(Constant.SYSTEM_ERROR);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println(this.getClass().getName() + "-" + methodName + "-" + e.getMessage());
		}
	}

}
