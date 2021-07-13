/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * ListUserController.java, 9 thg 5, 2021 PhonPV
 */
package manageuser.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manageuser.entities.MstGroupEntities;
import manageuser.entities.UserInforEntities;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.properties.ConfigProperties;
import manageuser.properties.MessageProperties;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * @Description Class xử lí cho màn hình ADM002
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class ListUserController extends HttpServlet {

	/**
	 * Thuộc tính định danh của Serial class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ListUserController() {

	}

	/**
	 * Thực hiên các thao tác hiện thị trên màn hình ADM002.
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
			// Khởi tạo 1 session
			HttpSession session = request.getSession();
			// Tạo ra 1 đối tượng mstgroup
			MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
			// Tạo ra đối tượng tblUserLogic
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Tạo ra danh sách chứa tất cả các mstgroup
			List<MstGroupEntities> listGroup = mstGroupLogic.getAllMstGroup();
			// Tạo danh sách chứa các userInfor
			List<UserInforEntities> listUser = new ArrayList<UserInforEntities>();
			// Tạo 1 danh sách chứa paging
			List<Integer> listPaging = new ArrayList<Integer>();

			// Khai báo các giá trị default.
			int offset = Constant.DEFAULT_OFFSET;
			// Lấy ra giá trị default để lấy giá trị cho hàm getListUser
			int limitPage = Integer.parseInt(ConfigProperties.getValueByKey(Constant.LIMIT_PAGE));
			int groupId = Constant.DEFAULT_GROUP_ID;
			String fullName = Constant.DEFAUT_FULLNAME;
			String sortType = Constant.SORT_FULLNAME;
			String sortValue = Constant.EMPTY_STRING;
			String sortByFullName = Constant.SORT_ASC;
			String sortByCodeLevel = Constant.SORT_ASC;
			String sortByEndDate = Constant.SORT_DESC;
			int currentPage = Constant.DEFAUT_CURRENT_PAGE;
			// Lấy action thực hiện
			String action = request.getParameter(Constant.ACTION);
			// Kiểm tra sự tồn tại của action
			if (action == null) {
				// Trạng thái action null thì ban đầu sẽ default
				action = Constant.DEFAULT;
			}
			// Xét các trường hợp action cụ thể
			switch (action) {
			// TH search.
			case Constant.SEARCH:
				// Lấy giá trị fullName từ request
				fullName = request.getParameter(Constant.FULL_NAME);
				// Kiểm tra sự tồn tại của giá trị
				if (fullName == null) {
					// Gán giá trị default cho fullName
					fullName = Constant.DEFAUT_FULLNAME;
				}
				if (request.getParameter(Constant.GROUP_ID) != null) {

					// Lấy dữ liệu group từ selectBox
					groupId = Common.convertStringToInt(request.getParameter(Constant.GROUP_ID),
							Constant.DEFAULT_GROUP_ID);
				}
				break;
			// TH Sort
			case Constant.SORT:
				// Trường hợp sort thì vẫn giữ nguyên điều kiện search
				// Lấy giá trị fullName từ request
				fullName = request.getParameter(Constant.FULL_NAME);
				// Kiểm tra sự tồn tại của giá trị
				if (fullName == null) {
					// Gán giá trị default cho fullName
					fullName = Constant.DEFAUT_FULLNAME;
				}
				if (request.getParameter(Constant.GROUP_ID) != null) {

					// Lấy dữ liệu group từ selectBox
					groupId = Common.convertStringToInt(request.getParameter(Constant.GROUP_ID),
							Constant.DEFAULT_GROUP_ID);
				}

				// Nếu trường hợp action là sort
				if (Constant.SORT.equals(action)) {
					// Lấy giá trị từ request các kiểu sort.(theo fullName, theo codelevel và theo
					// enddate)
					sortType = request.getParameter(Constant.SORT_TYPE);
					sortValue = request.getParameter(Constant.SORT_VALUE);
					// Kiểm tra sortType
					// Với sort theo fullName
					if (Constant.SORT_FULLNAME.equals(sortType)) {
						sortByFullName = sortValue;
					}
					// Với trường hợp sort theo trình độ tiếng Nhật
					if (Constant.SORT_CODELEVEL.equals(sortType)) {
						sortByCodeLevel = sortValue;
					}
					// Trường hợp sort theo ngày kết thúc Enddate
					if (Constant.SORT_ENDDATE.equals(sortType)) {
						sortByEndDate = sortValue;
					}
				}
				break;
			// TH paging
			case Constant.PAGING:
				// Giữ nguyên các giá trị tìm kiếm
				// Lấy giá trị fullName từ request
				fullName = request.getParameter(Constant.FULL_NAME);
				// Kiểm tra sự tồn tại của giá trị
				if (fullName == null) {
					// Gán giá trị default cho fullName
					fullName = Constant.DEFAUT_FULLNAME;
				}
				if (request.getParameter(Constant.GROUP_ID) != null) {

					// Lấy dữ liệu group từ selectBox
					groupId = Common.convertStringToInt(request.getParameter(Constant.GROUP_ID),
							Constant.DEFAULT_GROUP_ID);
				}

				// Nếu trường hợp action là paging thì giữ nguyên các điều kiện sort
				if (Constant.PAGING.equals(action)) {
					// Lấy giá trị từ request các kiểu sort.(theo fullName, theo codelevel và theo
					// enddate)
					sortType = request.getParameter(Constant.SORT_TYPE);
					sortValue = request.getParameter(Constant.SORT_VALUE);
					// Kiểm tra sortType
					// Với sort theo fullName
					if (Constant.SORT_FULLNAME.equals(sortType)) {
						sortByFullName = sortValue;
					}
					// Với trường hợp sort theo trình độ tiếng Nhật
					if (Constant.SORT_CODELEVEL.equals(sortType)) {
						sortByCodeLevel = sortValue;
					}
					// Trường hợp sort theo ngày kết thúc Enddate
					if (Constant.SORT_ENDDATE.equals(sortType)) {
						sortByEndDate = sortValue;
					}
				}
				// Thực hiện phần paging. Convert giá trị paging trên request sang Int
				currentPage = Common.convertStringToInt(request.getParameter(Constant.CURRENT_PAGE),
						Constant.DEFAUT_CURRENT_PAGE);
				break;
			// Trường hợp action là back
			case Constant.BACK:
				// lấy giá trị các giá trị từ session
				fullName = (String) session.getAttribute(Constant.FULL_NAME);
				groupId = (int) session.getAttribute(Constant.GROUP_ID);
				currentPage = (int) session.getAttribute(Constant.CURRENT_PAGE);
				sortType = (String) session.getAttribute(Constant.SORT_TYPE);
				sortByFullName = (String) session.getAttribute(Constant.SORT_BY_FULL_NAME);
				sortByCodeLevel = (String) session.getAttribute(Constant.SORT_BY_CODE_LEVEL);
				sortByEndDate = (String) session.getAttribute(Constant.SORT_BY_END_DATE);
				break;
			// Trường hợp default
			case Constant.DEFAULT:
				break;
			}
			// Lấy tổng số user tối đa hiện thị trong 1 page
			int limit = Common.getLimit();
			// lấy tổng các user theo điều kiện group or fullname
			int totalRecord = tblUserLogic.getTotalUser(groupId, fullName);
			if (totalRecord != 0) {
				// lấy tổng số Page cần có
				int totalPage = Common.getTotalPage(totalRecord, limit);
				// Set lại trang hiện tại nếu nó lớn hơn tổng số trang có
				// Trường hợp sửa trên link URL
				// 
				if (currentPage > totalPage) {
					currentPage = totalPage;
				}
				// Lấy số offset cho currentPage
				// Vị trí bắt đầu lấy bản ghi
				offset = Common.getOffset(limit, currentPage);
				// Set giá trị vào listUser
				listUser = tblUserLogic.getListUser(offset, limit, groupId, fullName, sortType, sortByFullName,
						sortByCodeLevel, sortByEndDate);
				// Set giá trị và listPaging
				listPaging = Common.getListPaging(totalRecord, limit, currentPage);
				if (listPaging != null) {
					// Xử lí hiện thị <<
					if (currentPage > limitPage) {
						// Set giá trị vào btn <<
						request.setAttribute(Constant.PRE, (listPaging.get(0) - limitPage));
					}
					// Xử lí hiện thị btn >>
					if (listPaging.get(listPaging.size() - 1) < totalPage) {
						request.setAttribute(Constant.NEXT, listPaging.get(0) + limitPage);
					}
				}

			} else {
				// Hiện thị messError vào bảng
				String messageError = MessageProperties.getValueByKey(Constant.MSG005);
				request.setAttribute(Constant.LIST_ERROR, messageError);
			}
			// Set thuộc tính của listPaging lên request.
			request.setAttribute(Constant.LIST_PAGING, listPaging);
			// Set giá trị sort theo trường cần sort lên request.
			request.setAttribute(Constant.SORT_VALUE, sortValue);
			// Set giá trị cho listUser.
			request.setAttribute(Constant.LIST_USER, listUser);
			// Set giá trị cho listGroup.
			request.setAttribute(Constant.LIST_MSTGROUP, listGroup);

			// gửi các điều kiện lên session để giữ lại phục vụ cho trường hợp back lại hoặc
			// giữ lai
			// điều kiện trước đó
			session.setAttribute(Constant.FULL_NAME, fullName);
			session.setAttribute(Constant.GROUP_ID, groupId);
			session.setAttribute(Constant.CURRENT_PAGE, currentPage);
			session.setAttribute(Constant.SORT_TYPE, sortType);
			session.setAttribute(Constant.SORT_BY_FULL_NAME, sortByFullName);
			session.setAttribute(Constant.SORT_BY_CODE_LEVEL, sortByCodeLevel);
			session.setAttribute(Constant.SORT_BY_END_DATE, sortByEndDate);

			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM002_PATH);
			rd.forward(request, response);
			// Mở catch để ném ra exeption.
		} catch (Exception e) {
			e.printStackTrace();
			// Lấy giá trị thuộc tính tên method hiện hành
			String methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println(this.getClass().getName() + "-" + methodName + "-" + e.getMessage());
			// Chuyển hướng sang trang system_error
			response.sendRedirect(request.getContextPath() + Constant.ERROR_PAGE);

		}
	}

	/**
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
