/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * AddUserInputController.java, 29 thg 5, 2021 PhonPV
 */
package manageuser.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.UserInforEntities;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * @Description Thực hướng cho các chức năng thêm mới của màn hình ADM003
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class AddUserInputController extends HttpServlet {

	/**
	 * Thuộc tính định danh cho Serial class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Thực hiện hiển thị các giá trị lên textbox, selectbox màn hình ADM003
	 * 
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
		try {
			// Kiểm tra đã login thành công hay chưa?
			// Sét các giá trị vào cho các selecBox.
			Common.setDataLogic(request);
			// Khai báo 1 đối tượng userInfor để đẩy giá trị mặc định vào
			UserInforEntities userInfor = getDefaultValues(request);
			request.setAttribute(Constant.USERINFOR, userInfor);

			// Điều hướng sang trang ADM003
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM003_PATH);
			rd.forward(request, respone);
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			respone.sendRedirect(request.getContextPath() + Constant.ERROR_PAGE);
		}
	}

	/**
	 * Hàm xử lí các trường trên form ADM003
	 * 
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			ValidateUser validateUser = new ValidateUser();
			// Khai báo 1 đối tượng userInfor để đẩy giá trị mặc định vào
			UserInforEntities userInfor = getDefaultValues(req);
			List<String> listError = validateUser.validateUserInfor(userInfor);
			// Trường hợp có lỗi
			if (listError.size() != 0) {
				// set các giá trị trong selectBox.
				Common.setDataLogic(req);
				// Set đối tượng userInfor lên request
				req.setAttribute(Constant.USERINFOR, userInfor);
				// Set list lỗi validate lên request để hiện thị lên màn hình ADM003
				req.setAttribute(Constant.LIST_ERROR, listError);
				// điều hướng ở lại view ADM003
				RequestDispatcher rd = req.getRequestDispatcher(Constant.ADM003_PATH);
				rd.forward(req, resp);
				// Trường hợp không có lỗi
			} else {
				// Lấy id của session gán cho mỗi userInfor để phục vụ cho việc mở 1 lúc nhiều
				// tab
				String idSession = Common.getSalt();
				// tạo key đánh dấu màn hình ADM003
				// tránh trường hợp nhập link sang màn hình ADM004
				// set key lên request
				req.setAttribute(Constant.ID_SESSION, idSession);
				// Set các giá trị lên session
				session.setAttribute(Constant.VALIDATE, idSession);
				session.setAttribute(Constant.USERINFOR + idSession, userInfor);
				// chuyển hướng về trang ADM004
				resp.sendRedirect(req.getContextPath() + Constant.ADD_USER_CONFIRM_DO + "?idSession=" + idSession);
			}
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

	/**
	 * Lấy giá trị cho các trường trên màn hình ADM003 tương ứng với các thuộc tính
	 * của userInfor trong trường hợp
	 * 
	 * @return userInfor Đối tương chứa thông tin của màn hình ADM003
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private UserInforEntities getDefaultValues(HttpServletRequest req) {
		try {
			// Tạo ra đối tượng userInfor
			UserInforEntities userInfor = new UserInforEntities();
			// Lấy về action trên request về
			String action = req.getParameter(Constant.ACTION);
			// Kiểm tra sự tồn tại của action
			if (action == null) {
				// Nếu không có action thì mặc định giá trị theo requier
				action = Constant.DEFAULT;
			}
			switch (action) {
			case Constant.CONFIRM:
				// Lấy các giá trị của userInfor từ request về để set vào userInfor
				// Thuộc tính loginName lấy từ request về
				String loginName = req.getParameter(Constant.LOGIN_NAME);
				// Set giá trị vào userInfor
				userInfor.setLoginName(loginName);
				int groupId = Common.convertStringToInt(req.getParameter(Constant.GROUP_ID), Constant.DEFAULT_GROUP_ID);
				userInfor.setGroupId(groupId);
				String groupName = req.getParameter(Constant.GROUP_NAME);
				userInfor.setGroupName(groupName);
				String fullName = req.getParameter(Constant.FULL_NAME);
				userInfor.setFullName(fullName);
				String fullNameKana = req.getParameter(Constant.FULL_NAME_KANA);
				userInfor.setFullNameKana(fullNameKana);
				String birthday = Common.formatDate(req.getParameter(Constant.YEAR_BIRTHDAY),
						req.getParameter(Constant.MONTH_BIRTHDAY), req.getParameter(Constant.DAY_BIRTHDAY));
				userInfor.setBirthday(birthday);
				String email = req.getParameter(Constant.EMAIL);
				userInfor.setEmail(email);
				String tel = req.getParameter(Constant.TEL);
				userInfor.setTel(tel);
				String password = req.getParameter(Constant.PASSWORD);
				userInfor.setPassword(password);
				String passwordConfirm = req.getParameter(Constant.PASSWORD_CONFIRM);
				userInfor.setPasswordConfirm(passwordConfirm);
				String codeLevel = req.getParameter(Constant.CODE_LEVEL);
				userInfor.setCodeLevel(codeLevel);
				// Kiểm tra check xem trường trình độ tiếng nhật đã được chọn hay chưa
				// Nếu có thì set các trường bên dưới nó
				if (!Constant.DEFAULT_CODE_LEVEL_03.equals(codeLevel)) {
					String nameLevel = req.getParameter(Constant.NAME_LEVEL);
					userInfor.setNameLevel(nameLevel);
					String startDate = Common.formatDate(req.getParameter(Constant.YEAR_STARTDATE),
							req.getParameter(Constant.MONTH_STARTDATE), req.getParameter(Constant.DAY_STARTDATE));
					userInfor.setStartDate(startDate);
					String endDate = Common.formatDate(req.getParameter(Constant.YEAR_ENDDATE),
							req.getParameter(Constant.MONTH_ENDDATE), req.getParameter(Constant.DAY_ENDDATE));
					userInfor.setEndDate(endDate);
					String total = req.getParameter(Constant.TOTAL);
					userInfor.setTotal(total);
				} else {
					String startDate = Common.formatDate(req.getParameter(Constant.YEAR_STARTDATE),
							req.getParameter(Constant.MONTH_STARTDATE), req.getParameter(Constant.DAY_STARTDATE));
					userInfor.setStartDate(startDate);
					String endDate = Common.formatDate(req.getParameter(Constant.YEAR_ENDDATE),
							req.getParameter(Constant.MONTH_ENDDATE), req.getParameter(Constant.DAY_ENDDATE));
					userInfor.setEndDate(endDate);
				}
				break;
			// Trường hợp back về từ màn ADM004 về ADM003
			// Các giá trị trước đó nhập ở ADM003 được giữ nguyên
			case Constant.BACK:
				// Lấy session về
				HttpSession session = req.getSession();
				// Lấy ID của user về
				String idSession = req.getParameter(Constant.ID_SESSION);
				// Lấy userInfor từ session theo ID về
				userInfor = (UserInforEntities) session.getAttribute(Constant.USERINFOR + idSession);
				// Kiểm tra xem user đó có trình độ tiếng Nhật hay không
				if (!Constant.DEFAULT_CODE_LEVEL_03.equals(userInfor.getCodeLevel())) {
					// Nếu có thì set các giá trị date trong bảng LevelJapan về trạng thaí Default
					userInfor.setStartDate(Constant.DEFAULT_STARTDATE_03);
					userInfor.setEndDate(Constant.DEFAULT_ENDDATE_03);
				}
				// Xóa userInfor trước đó trên session
				session.removeAttribute(Constant.USERINFOR + idSession);
				break;
			case Constant.DEFAULT:
				// Set các thuộc tính của đối tượng userInfor là Default
				userInfor.setLoginName(Constant.DEFAULT_LOGIN_NAME_03);
				userInfor.setGroupId(Constant.DEFAULT_GROUP_ID_03);
				userInfor.setGroupName(Constant.DEFAULT_GROUP_NAME_03);
				userInfor.setFullName(Constant.DEFAULT_FULL_NAME_03);
				userInfor.setFullNameKana(Constant.DEFAULT_FULL_NAME_KANA_03);
				userInfor.setBirthday(Constant.DEFAULT_BIRTHDAY_03);
				userInfor.setEmail(Constant.DEFAULT_EMAIL_03);
				userInfor.setTel(Constant.DEFAULT_TEL_03);
				userInfor.setPassword(Constant.DEFAULT_PASSWWORD_03);
				userInfor.setPasswordConfirm(Constant.DEFAULT_PASSWWORD_COMFIRM_03);
				userInfor.setCodeLevel(Constant.DEFAULT_CODE_LEVEL_03);
				userInfor.setStartDate(Constant.DEFAULT_STARTDATE_03);
				userInfor.setEndDate(Constant.DEFAULT_ENDDATE_03);
				userInfor.setTotal(Constant.DEFAULT_TOTAL_03);
				break;
			}
			// Trả về 1 đối tượng userInfor
			return userInfor;
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			throw e;
		}
	}

}
