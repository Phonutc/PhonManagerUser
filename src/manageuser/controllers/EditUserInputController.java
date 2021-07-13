/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * EditUserInputController.java, Jun 11, 2021 PhonPV
 */
package manageuser.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manageuser.entities.UserInforEntities;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * @Description Chứa các hàm xử lí edit user
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class EditUserInputController extends HttpServlet {
	
	/**
	 * Thuộc tính định danh cho Serial class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Thực hiên các thao tác hiện thị trên màn hình ADM003 edit.
	 * 
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
		try {
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), Constant.DEFAULT_USER_ID);
			if (tblUserLogic.checkExistedId(userId)) {
				// Sét các giá trị vào cho các selecBox.
				Common.setDataLogic(request);
				// Khai báo 1 đối tượng userInfor để đẩy giá trị mặc định vào
				UserInforEntities userInfor = getDefaultValues(request);
				request.setAttribute(Constant.USERINFOR, userInfor);
				// Điều hướng sang trang ADM003
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM003_PATH);
				rd.forward(request, respone);
			}
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Chuyển hướng sang trang system_error
			respone.sendRedirect(request.getContextPath() + Constant.ERROR_PAGE);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse respone)
			throws ServletException, IOException {
		ValidateUser validateUser = new ValidateUser();
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), Constant.DEFAULT_USER_ID);
		try {
			if (userId > 0 && tblUserLogic.checkExistedId(userId)) {
				UserInforEntities userInfor = getDefaultValues(request);
				List<String> listError = validateUser.validateUserInfor(userInfor);
				if (listError.size() == 0) {
					HttpSession session = request.getSession();
					// Lấy id của session gán cho mỗi userInfor để phục vụ cho việc mở 1 lúc nhiều
					// tab
					String idSession = Common.getSalt();
					// tạo key đánh dấu màn hình ADM003
					// tránh trường hợp nhập link sang màn hình ADM004
					// Set các giá trị lên session
					// Đẩy id đánh dấu ADM003 lên session
					session.setAttribute(Constant.VALIDATE, idSession);
					session.setAttribute(Constant.USERINFOR + idSession, userInfor);
					respone.sendRedirect(
							request.getContextPath() + Constant.EDIT_USER_CONFIRM + "?idSession=" + idSession);

				} else {
					Common.setDataLogic(request);
					request.setAttribute(Constant.LIST_ERROR, listError);
					request.setAttribute(Constant.USER_ID, userId);
					request.setAttribute(Constant.USERINFOR, userInfor);
					// Điều hướng ở lại trang ADM003
					RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM003_PATH);
					rd.forward(request, respone);
				}
			} else {
				// Nếu user không tồn tại thì hiện thị lỗi Không tồn tại user bên màn
				// SysTemError
				String path = request.getContextPath() + Constant.ERROR_PAGE + "?type=" + Constant.NOT_EXIST_USER;
				respone.sendRedirect(path);
			}
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Chuyển hướng sang trang system_error
			respone.sendRedirect(request.getContextPath() + Constant.ERROR_PAGE);
		}
	}

	/**
	 * Lấy giá trị cho các trường trên màn hình ADM003 tương ứng với các thuộc tính
	 * của userInfor trong trường hợp
	 * 
	 * @param request yêu cầu của client
	 * @return userInfor thông tin đối tượng của user
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private UserInforEntities getDefaultValues(HttpServletRequest request)
			throws ClassNotFoundException, SQLException, UnsupportedEncodingException {
		UserInforEntities userInfor = new UserInforEntities();
		int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), Constant.DEFAULT_USER_ID);
		String action = request.getParameter(Constant.ACTION);
		if (action == null) {
			action = Constant.DEFAULT;
		}
		switch (action) {
		case Constant.CONFIRM:
			// Lấy các giá trị của userInfor từ request về để set vào userInfor
			// Thuộc tính loginName lấy từ request về
			userInfor.setUserId(userId);
			String loginName = request.getParameter(Constant.LOGIN_NAME);
			// Set giá trị vào userInfor
			userInfor.setLoginName(loginName);
			int groupId = Common.convertStringToInt(request.getParameter(Constant.GROUP_ID), Constant.DEFAULT_GROUP_ID);
			userInfor.setGroupId(groupId);
			String groupName = request.getParameter(Constant.GROUP_NAME);
			userInfor.setGroupName(groupName);
			String fullName = request.getParameter(Constant.FULL_NAME);
			userInfor.setFullName(fullName);
			String fullNameKana = request.getParameter(Constant.FULL_NAME_KANA);
			userInfor.setFullNameKana(fullNameKana);
			String birthday = Common.formatDate(request.getParameter(Constant.YEAR_BIRTHDAY),
					request.getParameter(Constant.MONTH_BIRTHDAY), request.getParameter(Constant.DAY_BIRTHDAY));
			userInfor.setBirthday(birthday);
			String email = request.getParameter(Constant.EMAIL);
			userInfor.setEmail(email);
			String tel = request.getParameter(Constant.TEL);
			userInfor.setTel(tel);
			String codeLevel = request.getParameter(Constant.CODE_LEVEL);
			userInfor.setCodeLevel(codeLevel);
			// Kiểm tra check xem trường trình độ tiếng nhật đã được chọn hay chưa
			// Nếu có thì set các trường bên dưới nó
			if (!Constant.DEFAULT_CODE_LEVEL_03.equals(codeLevel)) {
				String nameLevel = request.getParameter(Constant.NAME_LEVEL);
				userInfor.setNameLevel(nameLevel);
				String startDate = Common.formatDate(request.getParameter(Constant.YEAR_STARTDATE),
						request.getParameter(Constant.MONTH_STARTDATE), request.getParameter(Constant.DAY_STARTDATE));
				userInfor.setStartDate(startDate);
				String endDate = Common.formatDate(request.getParameter(Constant.YEAR_ENDDATE),
						request.getParameter(Constant.MONTH_ENDDATE), request.getParameter(Constant.DAY_ENDDATE));
				userInfor.setEndDate(endDate);
				String total = request.getParameter(Constant.TOTAL);
				userInfor.setTotal(total);
			} else {
				String startDate = Common.formatDate(request.getParameter(Constant.YEAR_STARTDATE),
						request.getParameter(Constant.MONTH_STARTDATE), request.getParameter(Constant.DAY_STARTDATE));
				userInfor.setStartDate(startDate);
				String endDate = Common.formatDate(request.getParameter(Constant.YEAR_ENDDATE),
						request.getParameter(Constant.MONTH_ENDDATE), request.getParameter(Constant.DAY_ENDDATE));
				userInfor.setEndDate(endDate);
			}
			break;
		case Constant.BACK:
			HttpSession session = request.getSession();
			// Lấy các thuộc tính từ sesion về
			String idSession = request.getParameter(Constant.ID_SESSION);
			// Lấy userInfor theo id của từng user
			userInfor = (UserInforEntities) session.getAttribute(Constant.USERINFOR + idSession);
			// Kiểm tra xem user đó có trình độ tiếng Nhật hay không
			if (!Constant.DEFAULT_CODE_LEVEL_03.equals(request.getParameter(Constant.CODE_LEVEL))) {
				// Nếu có thì set các giá trị date trong bảng LevelJapan về trạng thaí Default
				userInfor.setStartDate(Constant.DEFAULT_STARTDATE_03);
				userInfor.setEndDate(Constant.DEFAULT_ENDDATE_03);
			}
			break;
		case Constant.DEFAULT:
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			userInfor = tblUserLogic.getUserInforById(userId);
			// Trường hợp user không có trình độ tiếng nhật
			if (userInfor.getCodeLevel() == null) {
				// Nếu có thì set các giá trị date trong bảng LevelJapan về trạng thaí Default
				userInfor.setStartDate(Constant.DEFAULT_STARTDATE_03);
				userInfor.setEndDate(Constant.DEFAULT_ENDDATE_03);
			}
			break;
		}
		// Trả về đối tượng userInfor
		return userInfor;

	}
}
