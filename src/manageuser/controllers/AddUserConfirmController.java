/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * AddUserConfirmController.java, Jun 1, 2021 PhonPV
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
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;

/**
 * @Description
 * Class chưa các hàm xử lí việc add user
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class AddUserConfirmController extends HttpServlet {

	/**
	 * Thuộc tính định danh của Serial class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm xử lí hiện thị user lên form ADM004
	 * 
	 * @param HttpServletRequest  req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			// Check tồn tại của login
			// Lấy ID của session từ session về đánh dấu đã qua màn hình ADM003
			Object validate = session.getAttribute(Constant.VALIDATE);
			// Check IDSession có thì có nghĩa là đi qua ADM003
			if (validate != null) {
				// Tạo ra các đối tượng để set các giá trị vào rối gửi lên request
				// Đối tượng mstGroup để lấy groupName tương ứng theo groupID
				MstGroupLogic mstGroup = new MstGroupLogicImpl();
				// Đối tượng mstJapan để lấy ra nameLevel tương ứng với codeLevel
				MstJapanLogic mstJapan = new MstJapanLogicImpl();
				// Đối tượng userInfor để lấy hết tất cả các thuộc tính tương ứng với các mục
				// hiện thị trên màn hình ADM004
				UserInforEntities userInfor = new UserInforEntities();
				// Xóa key khỏi session
				session.removeAttribute(Constant.VALIDATE);
				// Lấy Idsession từ request về
				String idSession = req.getParameter(Constant.ID_SESSION);
				// Lấy đối tượng userInfor trên session về
				userInfor = (UserInforEntities) session.getAttribute(Constant.USERINFOR + idSession);
				// set giá trị GroupName vào cho mục selecBox ở màn hình
				userInfor.setGroupName(mstGroup.getMstGroupById(userInfor.getGroupId()).getGroupName());
				// Lấy ra giá trị tên trình độ tiếng nhật.
				userInfor.setNameLevel(mstJapan.getMstJapanByCodeLevel(userInfor.getCodeLevel()).getNameLevel());
				// Set key lên view để phục vụ cho việc back lại màn hình ADM003
				req.setAttribute(Constant.ID_SESSION, idSession);
				// Set đối tượng userInfor lên request để hiện thị ra màn hình ADM004
				req.setAttribute(Constant.USERINFOR, userInfor);
				// Điều hướng về trang ADM004 để hiện thị
				RequestDispatcher rd = req.getRequestDispatcher(Constant.ADM004_PATH);
				rd.forward(req, resp);
			} else {
				// Điều hướng về màn ADM003 Defautl( Trường hợp nhập link addUserConfirm tại màn
				// ADM002)
				resp.sendRedirect(req.getContextPath() + Constant.ADD_USER_INPUT);
			}

		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Điều hướng về trang lỗi hệ thống nếu xảy ra lỗi hệ thống
			RequestDispatcher dispatcher = req.getRequestDispatcher(Constant.SYSTEM_ERROR);
			dispatcher.forward(req, resp);
		}
	}

	/**
	 * Hàm xử lí submit thêm, sửa tại form ADM004
	 * 
	 * @param HttpServletRequest  req
	 * @param HttpServletResponse resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			// Check sự tồn tại của login hay chưa?
			// Lấy ID từ request về
			String iDSession = req.getParameter(Constant.ID_SESSION);
			// Lấy đối tượng userInfor theo id từ session về
			UserInforEntities userInfor = (UserInforEntities) session.getAttribute(Constant.USERINFOR + iDSession);
			session.removeAttribute(Constant.USERINFOR);
			// Khai báo 1 đối tượng tblUserLogic để sử dụng các phần login
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Khai báo path để điều hướng tới các controller xử li hiện thị thông báo
			String path = Constant.EMPTY_STRING;
			// Check LoginName và Email của user có tồn tại trong DB hay chưa?
			boolean existLoginName = tblUserLogic.checkExistedLoginName(Constant.DEFAULT_USER_ID,
					userInfor.getLoginName());
			boolean existEmail = tblUserLogic.checkExistedEmail(Constant.DEFAULT_USER_ID, userInfor.getEmail());
			// Nếu login và email chưa tồn tại thì insert các giá trị vào 2 bảng tbl và
			// detailJapan
			if (!existLoginName && !existEmail) {
				boolean result = tblUserLogic.creatUser(userInfor);
				// Nếu insert thành công
				if (result) {
					// path định hướng tới successController để hiện thị thông báo lên màn ADM006
					path = req.getContextPath() + Constant.SUCCESS_PAGE + "?" + Constant.TYPE + "="
							+ Constant.INSERT_SUCCESS;
				} else {
					// Nếu insert ko thành công thì set cấu thông báo lỗi Er015 rồi điều hướng
					// system Erro
					path = req.getContextPath() + Constant.ERROR_PAGE;
				}
			} else {
				// Nếu Loginame và email đã tồn tại
				path = req.getContextPath() + Constant.ERROR_PAGE;
			}
			resp.sendRedirect(path);
		} catch (Exception e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Điều hướng về trang lỗi hệ thống nếu xảy ra lỗi hệ thống
			RequestDispatcher rd = req.getRequestDispatcher(Constant.SYSTEM_ERROR);
			rd.forward(req, resp);
		}
	}
}
