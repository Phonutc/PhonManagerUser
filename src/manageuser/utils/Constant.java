/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * Constant.java, 9 thg 5, 2021 PhonPV
 */
package manageuser.utils;

import java.time.MonthDay;
import java.time.Year;

import manageuser.properties.DatabaseProperties;

/**
 * @Description Khai báo các thuộc tính hằng cho chương trình
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class Constant {
	
	// Mục database
	public static final String DB_NAME = "dbName";
	public static final String DRIVER_DB = "com.mysql.jdbc.Driver";
	public static final String URL_DB = DatabaseProperties.getValueByKey("urlMySQL");
	public static final String USER_NAME = DatabaseProperties.getValueByKey("user");
	public static final String PASSWORD_LOGIN = DatabaseProperties.getValueByKey("pass");
	
//	Giá trị rule ứng với Admin
	public static final int RULE_ADMIN = 0;
//	Giá trị rule ứng với User
	public static final int RULE_USER = 1;

	// đường dẫn tới file error.properties
	public static final String PROPERTIES_ERROR = "manageuser/properties/files/message_error_ja.properties";
	// Đường dẫn tới file message.propertise
	public static final String PROPERTIES_MESSAGE = "manageuser/properties/files/message_ja.properties";
	// Đường dẫn tới file database.properties
	public static final String PROPETIES_DATABASE_PATH = "manageuser/properties/files/database_ja.properties";
	// Đường dẫn tời file config.properties
	public static final String CONFIG_PATH = "manageuser/properties/files/config_ja.properties";

	// Đường dẫn tới các file jsp các màn hình.
	public static final String ADM001_PATH = "./Views/jsp/ADM001.jsp";
	public static final String ADM002_PATH = "./Views/jsp/ADM002.jsp";
	public static final String ADM003_PATH = "./Views/jsp/ADM003.jsp";
	public static final String ADM004_PATH = "./Views/jsp/ADM004.jsp";
	public static final String ADM005_PATH = "./Views/jsp/ADM005.jsp";
	public static final String ADM006_PATH = "./Views/jsp/ADM006.jsp";
	public static final String SYSTEM_ERROR = "./Views/jsp/System_Error.jsp";

	// set giá trị defaul màn ADM002
	public static final int DEFAUT_CURRENT_PAGE = 1;
	public static final int DEFAULT_OFFSET = 0;
	public static final int DEFAULT_GROUP_ID = 0;
	public static final String DEFAUT_FULLNAME = "";

	// Kiểu lỗi của chương trình
	public static final String TYPE = "type";
	// Mã lỗi theo từng hạng mục
	// Lỗi không nhập LoginName
	public static final String ER001_LOGIN_NAME = "ER001_LOGIN_NAME";
	// Lỗi nhập sai tên LoginName hoặc Password không chính xác
	public static final String ER016_LOGIN_NAME = "ER016_LOGIN_NAME";
	// Định dạng loginName
	public static final String ER019_LOGIN_NAME = "ER019_LOGIN_NAME";
	public static final String ER007_LOGIN_NAME = "ER007_LOGIN_NAME";
	public static final String ER003_LOGIN_NAME = "ER003_LOGIN_NAME";

	// Hạng mục groupName
	public static final String ER002_GROUP_NAME = "ER002_GROUP_NAME";
	public static final String ER004_GROUP_NAME = "ER004_GROUP_NAME";

	// Hạng mục fullName
	public static final String ER001_FULL_NAME = "ER001_FULL_NAME";
	public static final String ER006_FULL_NAME = "ER006_FULL_NAME";

	// Hạng mục fullNameKana
	public static final String ER009_FULL_NAME_KANA = "ER009_FULL_NAME_KANA";
	public static final String ER006_FULL_NAME_KANA = "ER006_FULL_NAME_KANA";

	// Hạng mục ngày sinh birthday
	public static final String ER011_BIRTHDAY = "ER011_BIRTHDAY";

	// Hạng mục email
	public static final String ER001_EMAIL = "ER001_EMAIL";
	public static final String ER006_EMAIL = "ER006_EMAIL";
	public static final String ER005_EMAIL = "ER005_EMAIL";
	public static final String ER003_EMAIL = "ER003_EMAIL";

	// Hạng mục số điện thoại
	public static final String ER001_TEL = "ER001_TEL";
	public static final String ER006_TEL = "ER006_TEL";
	public static final String ER005_TEL = "ER005_TEL";

	// Hạng mục password
	public static final String ER001_PASSWORD = "ER001_PASSWORD";
	public static final String ER008_PASSWORD = "ER008_PASSWORD";
	public static final String ER007_PASSWORD = "ER007_PASSWORD";

	// Hạng mục confirm password
	public static final String ER017_PASSWORD_CONFIRM = "ER017_PASSWORD_CONFIRM";

	// Hạng mục codeLevel
	public static final String ER004_LEVEL = "ER004_LEVEL";
	// Hạng mục startDate
	public static final String ER011_START_DATE = "ER011_START_DATE";

	// Hạng mục endDate
	public static final String ER011_END_DATE = "ER011_END_DATE";
	public static final String ER012_END_DATE = "ER012_END_DATE";

	// Hạng mục điểm total
	public static final String ER001_TOTAL = "ER001_TOTAL";
	public static final String ER018_TOTAL = "ER018_TOTAL";
	public static final String ER006_TOTAL = "ER006_TOTAL";

	// Tổng hợp các mã lỗi chung.
	public static final String ER0013 = "ER013";
	public static final String ER014 = "ER014";
	public static final String ER015 = "ER015";
	public static final String ER020 = "ER020";

	// Toonge hợp các mã lỗi theo từng hạng mục
	// LoginName

	// Tổng hợp các mã thông báo
	public static final String MSG001 = "MSG001";
	public static final String MSG002 = "MSG002";
	public static final String MSG003 = "MSG003";
	public static final String MSG004 = "MSG004";
	public static final String MSG005 = "MSG005";

	// Hạng mục check độ dài các kí tự min max length
	// Max and Min length
	public static final int MAX_LENGTH = 255;
	public static final int MAX_LOGIN_NAME = 15;
	public static final int MIN_LOGIN_NAME = 4;
	public static final int MAX_EMAIL = 100;
	public static final int MAX_TEL = 14;
	public static final int MAX_PASSWORD = 15;
	public static final int MIN_PASSWORD = 5;
	public static final int MAX_TOTAL = 11;

	// Đối tượng userInfor
	public static final String USERINFOR = "userInfor";
	// Chuỗi rỗng
	public static final String EMPTY_STRING = "";
	// Chuỗi endCoding
	public static final String CHARSET_ENDCODING = "UTF-8";
	// ID của session
	public static final String ID_SESSION = "idSession";
	// Cờ ID của session
	public static final String VALIDATE = "validate";
	// Tham số hiện thông báo
	public static final String MESSAGE = "message";

	// Các thuộc tính của user
	public static final String USER_ID = "userId";
	public static final String LOGIN_NAME = "loginName";
	public static final String GROUP_ID = "groupId";
	public static final String GROUP_NAME = "groupName";
	public static final String FULL_NAME = "fullName";
	public static final String FULL_NAME_KANA = "fullNameKana";
	public static final String YEAR_BIRTHDAY = "yearBirthday";
	public static final String MONTH_BIRTHDAY = "monthBirthday";
	public static final String DAY_BIRTHDAY = "dayBirthday";
	public static final String EMAIL = "email";
	public static final String TEL = "tel";
	public static final String PASSWORD = "password";
	public static final String PASSWORD_CONFIRM = "passwordConfirm";
	public static final int START_YEAR = 1900;
	public static final String CODE_LEVEL = "codeLevel";
	public static final String NAME_LEVEL = "nameLevel";
	public static final String YEAR_STARTDATE = "yearStartDate";
	public static final String MONTH_STARTDATE = "monthStartDate";
	public static final String DAY_STARTDATE = "dayStartDate";
	public static final String YEAR_ENDDATE = "yearEndDate";
	public static final String MONTH_ENDDATE = "monthEndDate";
	public static final String DAY_ENDDATE = "dayEndDate";
	public static final String TOTAL = "total";

	// Các tham số dùng cho ADM002
	public static final String SORT_TYPE = "sortType";
	public static final String SORT_VALUE = "sortValue";
	public static final String SORT_FULLNAME = "full_name";
	public static final String SORT_CODELEVEL = "code_level";
	public static final String SORT_ENDDATE = "end_date";
	public static final String SORT_ASC = "ASC";
	public static final String SORT_DESC = "DESC";
	public static final String SORT_BY_FULL_NAME = "sortByFullName";
	public static final String SORT_BY_CODE_LEVEL = "sortByCodeLevel";
	public static final String SORT_BY_END_DATE = "sortByEndDate";
	public static final String DEFAULT = "default";
	public static final String LIMIT_RECORD = "limit_record";
	public static final String LIMIT_PAGE = "limit_page";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String NEXT = "NEXT";
	public static final String PRE = "PRE";

	// Các tham số dùng cho ADM003
	public static final String YEAR = "year";
	public static final String MONTH = "month";
	public static final String DAY = "day";

	// List dùng trong chương trình
	public static final String LIST_USER = "listUser";
	public static final String LIST_ERROR = "listError";
	public static final String LIST_MSTGROUP = "listGroup";
	public static final String LIST_PAGING = "listPaging";
	public static final String LIST_JAPAN = "listJapan";
	public static final String LIST_YEAR_BIRTHDAY = "listYearBirthday";
	public static final String LIST_MONTH_BIRTHDAY = "listMonthBirthDay";
	public static final String LIST_DAY_BIRTHDAY = "listDayBirthDay";
	public static final String LIST_YEAR_STARTDATE = "listYearStartDate";
	public static final String LIST_MONTH_STARTDATE = "listMonthStartDate";
	public static final String LIST_DAY_STARTDATE = "listDayStartDate";
	public static final String LIST_YEAR_ENDDATE = "listYearEndDate";
	public static final String LIST_MONTH_ENDDATE = "listMonthEndDate";
	public static final String LIST_DAY_ENDDATE = "listDayEndDate";

	// Link Controller
	public static final String LOGIN_PAGE = "/login.do";
	public static final String LOGOUT_PAGE = "/logout.do";
	public static final String LIST_USER_PAGE = "/listUser.do";
	public static final String ERROR_PAGE = "/error.do";
	public static final String ADD_USER_CONFIRM_DO = "/addUserConfirm.do";
	public static final String SUCCESS_PAGE = "/success.do";
	public static final String ADD_USER_INPUT = "/addUserInput.do";
	public static final String EDIT_USER_INPUT = "/editUserInput.do";
	public static final String EDIT_USER_CONFIRM = "/editUserConfirm.do";
	public static final Object ROOT_PATH = "/";
	public static final String JSP_FOLDER_PATTERN = "/Views/jsp/";
	public static final String CSS_FOLDER_PATTERN = "/Views/css/";
	public static final String IMG_FOLDER_PATTERN = "/Views/images/";
	public static final String JS_FOLDER_PATTERN = "/Views/js/";

	// Mã thông báo insert thành công
	public static final String INSERT_SUCCESS = "insertSuccess";
	// Mã thông báo update thành công
	public static final String UPDATE_SUCCESS = "updateSuccess";
	// Mã thông báo delete thành công
	public static final Object DELETE_SUCCESS = "deleteSuccess";
	// mã loại thông báo user không tồn tại
	public static final String NOT_EXIST_USER = "notExistUser";
	public static final String DELETE_NOTEXIST_USER = "deleteNotExitUser";
	public static final String NOT_DELETE_ADMIN = "notDeleteAdmin";

	// thông báo error
	public static final String ERROR = "error";

	// action thực hiện của chương trình
	public static final String ACTION = "action";
	// action search
	public static final String SEARCH = "search";
	// action sort
	public static final String SORT = "sort";
	// Action paging
	public static final String PAGING = "paging";
	// Action back
	public static final String BACK = "back";

	// Các action của màn hình ADM003
	public static final String CONFIRM = "confirm";

	// Giá trị default của các trường trên màn hình ADM003
	public static final String DEFAULT_BIRTHDAY_03 = Year.now().getValue() + "-" + MonthDay.now().getMonthValue() + "-"
			+ MonthDay.now().getDayOfMonth();
	public static final String DEFAULT_STARTDATE_03 = Year.now().getValue() + "-" + MonthDay.now().getMonthValue() + "-"
			+ MonthDay.now().getDayOfMonth();
	public static final String DEFAULT_ENDDATE_03 = ((Year.now().getValue()) + 1) + "-" + MonthDay.now().getMonthValue()
			+ "-" + MonthDay.now().getDayOfMonth();
	public static final String DEFAULT_LOGIN_NAME_03 = "";
	public static final int DEFAULT_GROUP_ID_03 = 0;
	public static final String DEFAULT_GROUP_NAME_03 = "";
	public static final String DEFAULT_FULL_NAME_03 = "";
	public static final String DEFAULT_FULL_NAME_KANA_03 = "";
	public static final String DEFAULT_EMAIL_03 = "";
	public static final String DEFAULT_TEL_03 = "";
	public static final String DEFAULT_PASSWWORD_03 = "";
	public static final String DEFAULT_PASSWWORD_COMFIRM_03 = "";
	public static final String DEFAULT_CODE_LEVEL_03 = "";
	public static final String DEFAULT_TOTAL_03 = "";

	// Default màn ADM002
	public static final int DEFAULT_USER_ID = 0;
	public static final int DEFAULT_TOTAL = 0;

	// Regex dùng cho các hạng mục cần format
	public static final String FORMAT_LOGIN_NAME = "^[a-z|A-Z|\\_]\\w+";
	public static final String FORMAT_NUMBER = "\\D*";
	public static final String FORMAT_FULL_NAME_KANA = "[ア-ンｧ-ﾝﾞﾟ]*";
	public static final String FORMAT_EMAIL = "^[\\w\\.+]*[\\w\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	public static final String FORMAT_TEL = "\\d{1,}[-]\\d{1,}[-]\\d{1,}+$";

}
