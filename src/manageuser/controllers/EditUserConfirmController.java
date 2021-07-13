/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * EditUserConfirmController.java, 15 thg 6, 2021 PhonPV
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.UserInforEntities;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblDetailUserJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblDetailUserJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * @Description Xử lí điểu hướng cho chức năng hiện thị dữ liệu edit user
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class EditUserConfirmController extends HttpServlet {

	/**
	 * Thuộc tính định danh cho Serial class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm xử lý chức năng hiện thị user edit ADM004
	 * 
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			// Lấy ID của session từ session về đánh dấu đã qua màn hình ADM003
			Object validate = session.getAttribute(Constant.VALIDATE);
			if (validate != null) {
				UserInforEntities userInfor = new UserInforEntities();
				MstGroupLogic mstGroup = new MstGroupLogicImpl();
				MstJapanLogic mstJapan = new MstJapanLogicImpl();
				// Xóa key khỏi session
				session.removeAttribute(Constant.VALIDATE);
				// Lấy Idsession từ request về
				String idSession = request.getParameter(Constant.ID_SESSION);
				// Lấy đối tượng userInfor trên session về
				userInfor = (UserInforEntities) session.getAttribute(Constant.USERINFOR + idSession);
				// set giá trị GroupName vào cho mục selecBox ở màn hình
				userInfor.setGroupName(mstGroup.getMstGroupById(userInfor.getGroupId()).getGroupName());
				// Lấy ra giá trị tên trình độ tiếng nhật.
				userInfor.setNameLevel(mstJapan.getMstJapanByCodeLevel(userInfor.getCodeLevel()).getNameLevel());
				// Set key lên view để phục vụ cho việc back lại màn hình ADM003
				request.setAttribute(Constant.ID_SESSION, idSession);
				// Set đối tượng userInfor lên request để hiện thị ra màn hình ADM004
				request.setAttribute(Constant.USERINFOR, userInfor);
				// Điều hướng về trang ADM004 để hiện thị
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM004_PATH);
				rd.forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + Constant.ADD_USER_INPUT);
			}
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Điều hướng về trang lỗi hệ thống nếu xảy ra lỗi hệ thống
			RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.SYSTEM_ERROR);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * Hàm xử lý chức năng submit update user ở màn ADM004
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
			// Lấy userId từ trên request về
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), Constant.DEFAULT_USER_ID);
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			TblDetailUserJapanLogic japanLogic = new TblDetailUserJapanLogicImpl();
			if (tblUserLogic.checkExistedId(userId)) {
				HttpSession session = request.getSession();
				// Lấy ID từ request về
				String iDSession = request.getParameter(Constant.ID_SESSION);
				// Lấy đối tượng userInfor theo id từ session về
				UserInforEntities userInfor = (UserInforEntities) session.getAttribute(Constant.USERINFOR + iDSession);
				// Xóa attibute mà mình vừa get về vì mình không dùng nữa
				session.removeAttribute(Constant.USERINFOR + iDSession);
				// Khai báo path để điều hướng tới các controller xử li hiện thị thông báo
				String path = Constant.EMPTY_STRING;
				// Check Email của user có tồn tại trong DB hay chưa?
				boolean existEmail = tblUserLogic.checkExistedEmail(userId, userInfor.getEmail());
				// Nếu email chưa tồn tại thì update các giá trị vào 2 bảng tbl và
				// detailJapan
				if (!existEmail) {
					// Thực hiện check detailJapan có tồn tại hay không
					boolean checkDetail = japanLogic.checkExistedDetailUserById(userInfor.getUserId());
					// trả về 1 biến boolean
					// Sau đó thực hiện câu lệnh update user
					boolean update = tblUserLogic.updateUser(userInfor, checkDetail);
					// Kiểm tra xem có update thành công hay không?
					if (update) {
						// nếu thành công
						// path định hướng tới successController để hiện thị thông báo lên màn ADM006
						path = request.getContextPath() + Constant.SUCCESS_PAGE + "?" + Constant.TYPE + "="
								+ Constant.UPDATE_SUCCESS;
					} else {
						// Nếu insert ko thành công thì set cấu thông báo lỗi Er015 rồi điều hướng
						// system Erro
						path = request.getContextPath() + Constant.ERROR_PAGE;
					}
				} else {
					// Nếu Loginame và email đã tồn tại
					path = request.getContextPath() + Constant.ERROR_PAGE;
				}
				response.sendRedirect(path);
			}
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Điều hướng về trang lỗi hệ thống nếu xảy ra lỗi hệ thống
			RequestDispatcher rd = request.getRequestDispatcher(Constant.SYSTEM_ERROR);
			rd.forward(request, response);
		}
	}

}
