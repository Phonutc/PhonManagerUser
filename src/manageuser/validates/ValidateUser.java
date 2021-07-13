/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * ValidateUser.java, 9 thg 5, 2021 PhonPV
 */
package manageuser.validates;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import manageuser.entities.MstGroupEntities;
import manageuser.entities.MstJapanEntities;
import manageuser.entities.UserInforEntities;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.properties.MessageErrorProperties;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * @Description <br>
 *              Chứa phương thức validate hệ thống của chương trình.
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class ValidateUser {
	// Khởi tạo danh sách chứa các lỗi trong chương trình
	static List<String> listError = new ArrayList<String>();

	/**
	 * Kiểm tra người dùng đã nhập loginName và Password hay chưa?
	 * 
	 * @param loginName Tên đăng nhập
	 * @param password  Mật khẩu
	 * @return listError danh sách lỗi của chương trình
	 * @throws Exception
	 */
	public List<String> validateLogin(String loginName, String password) throws Exception {
		try {
			// Reset listError về trạng thái ban đầu.
			listError.clear();
			// Khởi tạo 1 đội tượng TblUserLogicImpl có tên tblUserLogicImpl
			TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
			// Kiểm tra Nếu người dùng chưa điền tên đăng nhập
			if (Common.checkIsEmpty(loginName)) {
				// Thêm lỗi ER001_LOGINNAME vào danh sách lỗi
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER001_LOGIN_NAME));
			}
			// Kiểm tra nếu người dùng chưa điền mật khẩu.
			if (Common.checkIsEmpty(password)) {
				// Thêm lỗi ER001_PASSWORD vào danh sách lỗi.
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER001_PASSWORD));
			}
			// Kiểm tra danh sách chữa lỗi mà trống.
			if (listError.size() == 0) {
				// Kiểm tra sự tồn tại của loginName và pass
				if (!tblUserLogicImpl.checkExistLoginName(loginName, password)) {
					// Thêm lỗi ER016_LOGIN vào danh sách lỗi
					listError.add(MessageErrorProperties.getValueByKey(Constant.ER016_LOGIN_NAME));
				}
			}
			// Trả về danh sách các lỗi.
			return listError;
		} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
			// Ghi lỗi hệ thống
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println(this.getClass().getName() + "-" + method + "-" + e.getMessage());
			// Ném lỗi
			throw e;
		}
	}

	/**
	 * Validate đối tượng user cho thêm mới và cho việc update
	 * 
	 * @param userInfor đối tượng user được validate
	 * @return listError danh sách các lỗi khi validate thông tin user
	 * @throws Exception
	 */
	public List<String> validateUserInfor(UserInforEntities userInfor) throws Exception {
		try {
			// Reset làm trống list phục vụ cho việc validate
			listError.clear();
			if (userInfor.getUserId() == Constant.DEFAULT_USER_ID) {
				// Kiểm tra trường hợp thêm mới
				validateLoginName(userInfor);
			}
			// Kiểm tra Kiểm tra hạng mục group
			validateGroup(userInfor);
			// Kiểm tra hạng mục fullName
			validateFullName(userInfor);
			// Kiểm tra hạng mục fullNameKana
			validateFullNameKana(userInfor);
			// Kiểm tra hạng mục birthday
			validateBirthday(userInfor);
			validateEmail(userInfor);
			// Kiểm tra hạng mục tel
			validateTel(userInfor);
			if (userInfor.getUserId() == Constant.DEFAULT_USER_ID) {
				// Trường hợp thêm mới mới validate
				// Kiểm tra hạng mục password
				validatePassword(userInfor);
				// Kiểm tra hạng mục confirmPassword
				validatePasswordConfirm(userInfor);
			}

			String codeLevel = userInfor.getCodeLevel();
			// Kiểm tra hạng mục codelevel
			if (!Constant.DEFAULT_CODE_LEVEL_03.equals(codeLevel)) {
				// Kiểm tra trình độ tiếng Nhật
				validateNameLevel(userInfor);
				// Kiểm tra hạng mục startDate
				validateStartDate(userInfor);
				// Kiểm tra hạng mục endDate
				validateEndDate(userInfor);
				// Kiểm tra hạng mục total
				validateTotal(userInfor);
			}
			return listError;
		} catch (Exception e) {
			System.err.println("Error" + "-" + this.getClass().getName() + "-" + e.getMessage());
			throw e;
		}
	}

	/**
	 * validate LoginName
	 * 
	 * @param userInfo user cần validate
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi câu lệnh sql
	 */
	public void validateLoginName(UserInforEntities userInfor) throws ClassNotFoundException, SQLException {
		TblUserLogic tblUserLogic = new TblUserLogicImpl();

		try {
			// Lấy giá trị loginName từ userInfor
			String loginName = userInfor.getLoginName();
			// Nếu trống có nghĩa là không nhập
			if (Common.checkIsEmpty(loginName)) {
				// Check k nhập
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER001_LOGIN_NAME));
				// Nếu đã nhập thì check format
			} else if (!Common.checkFormatLoginName(loginName)) {
				// Check đúng định dạng
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER019_LOGIN_NAME));
				// nếu format đã đúng thì check length
			} else if (!Common.checkMaxMinLength(loginName, Constant.MIN_LOGIN_NAME, Constant.MAX_LOGIN_NAME)) {
				// Check độ dài
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER007_LOGIN_NAME));
				// Nếu độ dài đúng quy định rồi checl sự tồn tại
			} else if (tblUserLogic.checkExistedLoginName(userInfor.getUserId(), loginName)) {
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER003_LOGIN_NAME));
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Error" + "-" + this.getClass().getName() + "-" + e.getMessage());
			throw e;
		}
	}

	/**
	 * validate Group
	 * 
	 * @param userInfo user cần validate
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi câu lệnh sql
	 */
	public void validateGroup(UserInforEntities userInfor) throws ClassNotFoundException, SQLException {
		// Tạo đối tượng mstGroupLogic
		MstGroupLogic mstGrouplogic = new MstGroupLogicImpl();
		try {
			// Lấy giá trị id của group về
			int groupId = userInfor.getGroupId();
			// Nếu id không tồn tại
			if (groupId == 0) {
				// nếu groupId = 0(ứng với ko chọn) thì add thông báo lỗi
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER002_GROUP_NAME));
			} else {
				// kiểm tra tồn tại trong db
				// Lấy đối tương mstGroup trong db
				MstGroupEntities mstGroup = mstGrouplogic.getMstGroupById(groupId);
				String groupName = mstGroup.getGroupName();
				// Kiểm tra nếu null
				if (groupName == null) {
					// nếu không tồn tại thì add thông báo lỗi
					listError.add(MessageErrorProperties.getValueByKey(Constant.ER004_GROUP_NAME));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Error" + "-" + this.getClass().getName() + "-" + e.getMessage());
			throw e;
		}
	}

	/**
	 * Validate trường fullName
	 * 
	 * @param userInfor user cần validate thuộc tính fullName
	 */
	public void validateFullName(UserInforEntities userInfor) {
		String fullName = userInfor.getFullName();
		// Kiểm tra nếu rỗng
		if (Common.checkIsEmpty(fullName)) {
			// Check không nhập fullName
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER001_FULL_NAME));
		} else if (!Common.checkMaxLength(fullName, Constant.MAX_LENGTH)) {
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER006_FULL_NAME));
		}
	}

	/**
	 * Validate trường fullNameKana của userInfor
	 * 
	 * @param userInfor user cần được validate
	 */
	public void validateFullNameKana(UserInforEntities userInfor) {
		String fullNameKana = userInfor.getFullNameKana();
		// Check nhập kí tự kana
		if (!Common.checkNameKana(fullNameKana)) {
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER009_FULL_NAME_KANA));
			// Nếu đã nhập thì check length max của kí tự (255)
		} else if (!Common.checkMaxLength(fullNameKana, Constant.MAX_LENGTH)) {
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER006_FULL_NAME_KANA));
		}
	}

	/**
	 * validateBirthday
	 * 
	 * @param userInfo user cần validate
	 */
	public void validateBirthday(UserInforEntities userInfor) {
		String birthday = userInfor.getBirthday();
		// Check định dạng ngày sinh nhật của user
		if (!Common.checkDate(birthday)) {
			// Thêm lỗi vào listError để hiện thị ra màn hình
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER011_BIRTHDAY));
		}
	}

	/**
	 * validateEmail
	 * 
	 * @param userInfo user cần validate
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi câu lệnh sql
	 */
	public void validateEmail(UserInforEntities userInfor) throws ClassNotFoundException, SQLException {
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		try {
			String email = userInfor.getEmail();
			if (Common.checkIsEmpty(email)) {
				// Check không nhập
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER001_EMAIL));
			} else if (!Common.checkMaxLength(email, Constant.MAX_EMAIL)) {
				// Check max ký tự
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER006_EMAIL));
			} else if (!Common.checkFormatEmail(email)) {
				// Check định dạng email
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER005_EMAIL));
			} else if (tblUserLogic.checkExistedEmail(userInfor.getUserId(), email)) {
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER003_EMAIL));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// Ghi lỗi ra console
			System.err.println("Error" + this.getClass().getName() + e.getMessage());
			// Ném Exeption ra controller xử lí
			throw e;
		}

	}

	public void validateTel(UserInforEntities userInfor) {
		String tel = userInfor.getTel();
		// Check bắt buộc nhập
		if (Common.checkIsEmpty(tel)) {
			// Nếu không nhập thì add lỗi vào listError để in ra màn hình
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER001_TEL));
			// Check maxLength của điện thoại
		} else if (!Common.checkMaxLength(tel, Constant.MAX_TEL)) {
			// Nếu không thỏa mãn thì add lỗi maxlength của Tell vào listError để in ra màn
			// hình
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER006_TEL));
			// Check format cho số điện thoại
		} else if (!Common.checkFomatTel(tel)) {
			// Nếu không thỏa mãn format theo requir yêu cầu
			// add lỗi vào listError để in ra màn hình
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER005_TEL));
		}
	}

	/**
	 * Validate trường password
	 * 
	 * @param userInfor user được check
	 * @throws UnsupportedEncodingException
	 */
	public void validatePassword(UserInforEntities userInfor) throws UnsupportedEncodingException {
		String password = userInfor.getPassword();
		// Check nhập password
		if (Common.checkIsEmpty(password)) {
			// Nếu không nhập thì add lỗi vào listError để in ra màn hình
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER001_PASSWORD));
			// Check kí tự 1 byte
		} else if (!Common.checkOneByte(password)) {
			// Khongo thỏa mãn thì add lỗi
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER008_PASSWORD));
			// Check min max length kí tự
		} else if (!Common.checkMaxMinLength(password, Constant.MIN_PASSWORD, Constant.MAX_PASSWORD)) {
			// Add lỗi vào listErro
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER007_PASSWORD));
		}
	}

	/**
	 * Validate trường confirm password
	 * 
	 * @param userInfor user được validate
	 */
	public void validatePasswordConfirm(UserInforEntities userInfor) {
		String confirmPassword = userInfor.getPasswordConfirm();
		String password = userInfor.getPassword();
		// Check mật khẩu xác nhận giống với mật khậu nhập trên không
		if (!confirmPassword.equals(password)) {
			// Nếu nhập không đúng thì add lỗi vào listError
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER017_PASSWORD_CONFIRM));
		}
	}

	/**
	 * validateLevel
	 * 
	 * @param userInfo user cần validate
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException           lỗi câu lệnh sql
	 */
	public void validateNameLevel(UserInforEntities userInfor) throws ClassNotFoundException, SQLException {
		MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
		try {
			MstJapanEntities mstJappan = mstJapanLogic.getMstJapanByCodeLevel(userInfor.getCodeLevel());
			if (mstJappan.getCodeLevel() == null) {
				// Check tồn tại
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER004_LEVEL));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// Ghi log ra console
			System.err.println("Error" + this.getClass().getName() + "-" + e.getMessage());
			// Ném Exeption ra controller
			throw e;
		}
	}

	/**
	 * validateStartDate
	 * 
	 * @param userInfo user cần validate
	 */
	public void validateStartDate(UserInforEntities userInfor) {
		String startDate = userInfor.getStartDate();
		if (!Common.checkDate(startDate)) {
			// Check định dạng date
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER011_START_DATE));
		}
	}

	/**
	 * validateEndDate Throw check parse sang kiểu Date
	 * 
	 * @param userInfo user cần validate
	 * @throws ParseException
	 */
	public void validateEndDate(UserInforEntities userInfor) throws ParseException {
		String endDate = userInfor.getEndDate();
		if (!Common.checkDate(endDate)) {
			// check định dạng date
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER011_END_DATE));
		} else if (!Common.checkEndDate(userInfor.getStartDate(), endDate)) {
			// Check ngày hết hạn < ngày đăng ký
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER012_END_DATE));
		}
	}

	/**
	 * validateTotal
	 * 
	 * @param userInfo user cần validate
	 * @throws UnsupportedEncodingException lỗi không hỗ trợ endcoding
	 */
	public void validateTotal(UserInforEntities userInfor) throws UnsupportedEncodingException {
		String total = userInfor.getTotal();

		if (Common.checkIsEmpty(total)) {
			// check không nhập
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER001_TOTAL));
		} else if (!Common.checkHalfSize(total)) {
			// Check số haftSize
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER018_TOTAL));
		} else if (!Common.checkMaxLength(total, 9)) {
			// Check max ký tự
			listError.add(MessageErrorProperties.getValueByKey(Constant.ER006_TOTAL));
		}
	}
}
