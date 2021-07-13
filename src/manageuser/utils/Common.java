/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * Common.java, 9 thg 5, 2021 PhonPV
 */
package manageuser.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import manageuser.dao.TblUserDao;
import manageuser.dao.impl.TblUserDaoImpl;
import manageuser.entities.MstGroupEntities;
import manageuser.entities.MstJapanEntities;
import manageuser.entities.TblDetailUserJapanEntities;
import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.properties.ConfigProperties;

/**
 * @Description Chứa các phương thức logic chung của chương trình.
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class Common {

	/**
	 * Hàm xử lí mã hóa password
	 * 
	 * @param password mật khẩu người dùng nhập vào thỏa mãn
	 * @param salt
	 * @return Chuỗi mã hóa người dùng nhập vào
	 */
	public static String encryptPassword(String password, String salt) throws NoSuchAlgorithmException {
		String resuslt = password + salt;
		try {
			// Sử dụng thuật toán SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// Chuyễn mã byte sang hex.
			byte[] messageDigest = md.digest((resuslt).getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				sb.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Chuyển sang string
			resuslt = sb.toString();
		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
			// ghi log
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println("Error: Common" + "-" + method + "-" + e.getMessage());
			// Ném lỗi
			throw e;
		}
		// Trả về 1 String
		return resuslt;
	}

	/**
	 * Kiểm tra mật khẩu người dùng nhập vào + salt có trùng với mật khẩu trong
	 * database hay không
	 * 
	 * @param str      mật khẩu người dùng nhập sau khi mã hóa
	 * @param password mật khẩu trong databasr
	 * @return true nếu giống nhau, false nếu không giống nhau
	 */
	public static boolean compareString(String str, String password) {
		// Kiểm tra điều kiện so sánh
		if (str.equals(password)) {
			// Nếu bằng nhau trả về true
			return true;
		}
		// Khác nhau trả về false
		return false;
		// Đóng chương trình
	}

	/**
	 * Kiểm tra sự tồn tại của login admin
	 * 
	 * @param httpSession phiên làm việc
	 * @return result true nếu đăng nhập <br>
	 *         false nếu chưa đăng nhập
	 * @throws NoSuchAlgorithmException
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean checkLogin(HttpSession httpSession) throws NoSuchAlgorithmException, SQLException, Exception {
		// Tạo 1 đối tượng tblUser
		TblUserEntities tblUser = new TblUserEntities();
		// Tạo một
		TblUserDao tblUserDao = new TblUserDaoImpl();
		boolean result = false;
		try {
			// Lấy đối tượng loginName từ session về ( tránh trường hợp không lấy được về
			// loginName null)
			Object loginName = httpSession.getAttribute(Constant.LOGIN_NAME);
			// Check loginName lấy về
			if (loginName != null) {
				// Lấy 1 tblUser theo loginName
				tblUser = tblUserDao.getTblUserByLoginName((String) loginName);
				// Kiểm tra sự tồn tại của user Admin
				if (tblUser != null) {
					// Nếu không tồn tại return false
					result = true;
				} else {
					result = false;
				}
			} else {
				result = false;
			}
			return result;
		} catch (SQLException | ClassNotFoundException e) {
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println("Error: Common" + "-" + method + "-" + e.getMessage());
			throw e;
		}

	}

	/**
	 * Thay thế các toán tử WildCard
	 * 
	 * @param String str giá trị truyền vào
	 * @return chuỗi giá trị sau khi thay thế các toán tử WildCard
	 */
	public static String replaceWildcard(String str) {
		// Thay thế các toán tử WildCard
		str = str.replace("\\", "\\\\");
		str = str.replace("%", "\\%");
		str = str.replace("_", "\\_");
		// chuỗi giá trị sau khi thay thế các toán tử WildCard
		return str;
	}

	/**
	 * Thay thế các toán tử kí tự đặc biệt
	 * 
	 * @param str giá trị chuỗi truyền vào
	 * @return str chuỗi giá trị sau khi thay thế các toán tử Wildcard
	 */
	public static String encodeHtml(String str) {
		// Chuyển đổi kí tự &
		str = str.replaceAll("&", "&amp;");
		// Chuyển đổi kí tự \
		str = str.replaceAll("\"", "&qout;");
		// Chuyển đổi kí tự '
		str = str.replaceAll("'", "&#x27;");
		// Chuyển đổi kí tự /
		str = str.replaceAll("/", "&#x2F;");
		// Chuyển đổi kí tự <
		str = str.replaceAll("<", "&lt;");
		// Chuyển đổi kí tự >
		str = str.replaceAll(">", "&gt;");
		// Trả về kết quả
		return str;
		// Kết thúc hàm
	}

	/**
	 * Chuyển dữ liệu từ String sang Int
	 * 
	 * @param str       Giá trị ban đầu lấy về
	 * @param defaultId giá trị sau khi ép kiểu
	 * @return idFisrt groupId
	 */
	public static int convertStringToInt(String str, int defaultId) {
		// Gán về giá trị mặc định
		int idFisrt = 0;
		try {
			// chuyển từ string sang int
			idFisrt = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			idFisrt = defaultId;
		}
		// trả về idFisrt
		return idFisrt;
	}

	/**
	 * Lấy số lượng hiển thị bản ghi trên 1 trang
	 * 
	 * @return limit số lượng bản ghi max trên 1 trang.
	 */
	public static int getLimit() {
		// Đọc số lượng bản ghi trên 1 trang từ file confif_ja.properties.
		int limit = Integer.parseInt(ConfigProperties.getValueByKey(Constant.LIMIT_RECORD));
		return limit;
	}

	/**
	 * Lấy giá trị offset
	 * 
	 * @param limit       số lượng cần hiển thị trên 1 trang
	 * @param currentPage trang hiện tại
	 * @return offset giá trị offset.
	 */
	public static int getOffset(int limit, int currentPage) {
		// số offset = (trang hiện tại - 1)* giới hạn user trong 1 trang
		int offset = (currentPage - 1) * limit;
		return offset;
	}

	/**
	 * Tạo danh sách paging
	 * 
	 * @param totalRecord tổng số bản ghi.
	 * @param limit       số lượng bản ghi trên 1 page
	 * @param currentPage trang hiện tại
	 * @return listPaging Danh sách các trang cần hiển thị ở chuỗi paging theo trang
	 *         hiện tại
	 */
	public static List<Integer> getListPaging(int totalRecord, int limit, int currentPage) {
		// Tạo ra 1 danh sách chứa các page
		List<Integer> listPaging = new ArrayList<Integer>();
		// Lấy tổng số page đang có
		int totalPage = getTotalPage(totalRecord, limit);
		// Lấy giới hạn hiện thị số record trên 1 page
		int limitPage = Integer.parseInt(ConfigProperties.getValueByKey(Constant.LIMIT_PAGE));
		// Lấy nhóm các paging hiện tại. Giá trị chia được là tròn lên( Dùng hàm
		// Math.ceil)
		int currentGroup = ((int) Math.ceil((double) currentPage / limitPage)) - 1;
		// Lấy giá trị min trong page hiện tại
		int minPageGroup = (currentGroup) * limitPage + 1;
		// Lấy giá trị max trong paging hiện tại
		int maxPageGroup = minPageGroup + limitPage - 1;
		// Check điều kiện nếu số page max lớn hơn tổng số page hiện có
		if (maxPageGroup > totalPage) {
			// Giá trị max ko thể vượt quá được tổng số page
			maxPageGroup = totalPage;
		}
		// Check điều kiện tổng số bản ghi trên 1 trang không vượt quá mức limit
		if (totalRecord <= limit) {
			if (totalRecord == 0) {
				// THì listPaging == null
				listPaging = null;
			} else {
				// THì chỉ có đúng 1 trang để chứa.
				listPaging.add(1);
			}
		} else {
			// Mở vòng for duyệt từ giá trị min tới giá trị măx
			for (int i = minPageGroup; i <= maxPageGroup; i++) {
				// add vào danh dách.
				listPaging.add(i);
			}
		}
		// trả về danh sách
		return listPaging;
	}

	/**
	 * Tính số lượng page
	 * 
	 * @param totalRecord tổng số bản ghi
	 * @param limit       số lượng bản ghi trên 1 page
	 * @return totalPage số lượng page
	 */
	public static int getTotalPage(int totalRecord, int limit) {
		// số lượng page = (tổng số user/ giới hạn user hiển thị 1 trang) làm
		// tròn lên
		int totalPage = (int) Math.ceil(((double) totalRecord) / limit);
		return totalPage;
	}

	/**
	 * Lấy ra danh sách các năm từ năm đầu tiên đến năm hiện tại
	 * 
	 * @param startYear năm bắt đầu lấy
	 * @param nowYear   năm hiện tại
	 * @return listYear danh sách các năm
	 */
	public static List<Integer> getListYears(int startYear, int nowYear) {
		// Tạo ra danh sách chứa các năm
		List<Integer> listYear = new ArrayList<>();
		// Duyệt mạng từ năm bắt đầu đến năm kết thúc
		for (int i = startYear; i <= nowYear; i++) {
			// Thêm các năm vào danh sách
			listYear.add(i);
		}
		// Trả về danh sách chứa các năm
		return listYear;
	}

	/**
	 * Tạo danh sách các tháng trong năm
	 * 
	 * @return listDay danh sách các tháng trong năm
	 */
	public static List<Integer> getListMonths() {
		// Tạo danh sách chứa các tháng trong năm
		List<Integer> listMonth = new ArrayList<>();
		// Năm có 12 tháng duyệt vòng for
		for (int i = 1; i <= 12; i++) {
			// Thêm các tháng vào danh sách
			listMonth.add(i);
		}
		// Trả về danh sách các tháng
		return listMonth;
	}

	/**
	 * Danh sách các ngày trong tháng
	 * 
	 * @return listDay danh sách chứa các ngày trong tháng
	 */
	public static List<Integer> getListDays() {
		// Tạo ra danh sách động chứa các ngày trong tháng
		List<Integer> listDay = new ArrayList<>();
		// 1 tháng có tối da 31 ngày
		// duyệt vòng for để add vào danh sách
		for (int i = 1; i <= 31; i++) {
			// Thêm các ngày vào danh sách
			listDay.add(i);
		}
		// Trả về danh sách các ngày
		return listDay;
	}

	/**
	 * format lại date theo đúng định dạng yyyy/MM/dd
	 * 
	 * @param year  năm hiện thị
	 * @param month tháng hiện thị
	 * @param day   ngày hiện thị
	 * @return String Ngày tháng năm theo định dạng
	 */
	public static String formatDate(String year, String month, String day) {
		String format = "0";
		// Nếu tháng có 1 chữ sô thì thêm số 0 ở trước
		if (month.length() == 1) {
			month = format.concat(month);
		}
		// Nếu ngày có 1 chữ sô thì thêm số 0 ở trước
		if (day.length() == 1) {
			day = format.concat(day);
		}
		// Tạo chuỗi date theo định dạng YYYY-MM-DD
		String date = year + "-" + month + "-" + day;
		// Trả về 1 thuộc tính date
		return date;
	}

	/**
	 * Check format các kí tự của loginName
	 * 
	 * @param loginName chuỗi được check format
	 * @return true nếu đúng với format false nếu sai với format
	 */
	public static boolean checkFormatLoginName(String loginName) {
		boolean re = false;
		if ((loginName.matches(Constant.FORMAT_LOGIN_NAME))) {
			re = true;
		}
		return re;
	}

	/**
	 * Check độ dài kí tự
	 * 
	 * @param str    chuỗi được check độ dài
	 * @param minStr độ dại min của kí tự
	 * @param maxStr độ dài max kí tự
	 * @return re true nếu length nằm trong khoảng cho phép false nếu nằm ngoài
	 *         khoảng cho phép
	 */
	public static boolean checkMaxMinLength(String str, int minStr, int maxStr) {
		boolean re = false;
		if (minStr <= str.length() && str.length() <= maxStr) {
			re = true;
		}
		return re;
	}

	/**
	 * Check độ dài tối đa của chuỗi
	 * 
	 * @param str       chuỗi được check length
	 * @param maxLength độ dài tối da của kí tự
	 * @return
	 */
	public static boolean checkMaxLength(String str, int maxLength) {
		boolean re = false;
		if (str.length() <= maxLength) {
			re = true;
		}
		return re;
	}

	/**
	 * Hàm check kí tự kana
	 * 
	 * @param fullNameKana kí tự kana
	 * @return re true nếu đúng false nếu không hợp lệ
	 */
	public static boolean checkNameKana(String fullNameKana) {
		boolean re = false;
		if (fullNameKana.matches(Constant.FORMAT_FULL_NAME_KANA)) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}

	/**
	 * @param date
	 * @return result true nếu thỏa mãn giá trị<br>
	 *         false nếu sai
	 */
	public static boolean checkDate(String date) {
		boolean result = true;

		// Lấy các giá trị năm tháng ngày tương ứng là các phần tử của mảng selectBox
		// Phần tử thứ 1 là giá trị của năm
		int year = splitElementOfArrayDate(date)[0];
		// Tháng tương đương với phần tử thứ 2 của mảng
		int month = splitElementOfArrayDate(date)[1];
		// ngày tương ứng phần tử thứ 3 của mảng
		int day = splitElementOfArrayDate(date)[2];
		// Các tháng đủ ngày trong năm
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			if (day > 31) {
				return false;
			}
		}
		// Các tháng có thiếu số ngày < 31 ngày
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day > 30) {
				result = false;
			}
		}
		// Nếu các năm nhuần
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			if (month == 2) {
				if (day > 29) {
					result = false;
				}
			}
		} else {
			if (month == 2) {
				if (day > 28) {
					result = false;
				}
			}
		}
		// Trả về 1
		return result;
	}

	/**
	 * Cắt chuỗi ngày tháng năm từ kiểu String sang mảng string
	 * 
	 * @param String str
	 * @return dateArr mảng chứa ngày tháng năm
	 */
	private static int[] splitElementOfArrayDate(String str) {
		// Tạo 1 mảng chứa 3 phần tử của date
		int dateArr[] = new int[3];
		// Tạo mạng bằng cách split với nhau bằng dấu "-"
		String strArr[] = str.split("-");
		// Mở for để duyệt
		for (int i = 0; i < dateArr.length; i++) {
			// Ép kiểu các giá trị trong mảng từ Strings sang int
			dateArr[i] = Integer.parseInt(strArr[i]);
		}
		return dateArr;
	}

	/**
	 * Hàm check format email
	 * 
	 * @param email của user
	 * @return true nếu format đúng <br>
	 *         false nếu format sai
	 */
	public static boolean checkFormatEmail(String email) {
		boolean re = false;
		if (email.matches(Constant.FORMAT_EMAIL)) {
			re = true;
		}
		return re;
	}

	/**
	 * Check định dạng của telephone
	 * 
	 * @param String tel
	 * @return boolean true: đúng format false: sai format
	 */
	public static boolean checkFomatTel(String tel) {
		boolean result = false;
		if (tel.matches(Constant.FORMAT_TEL)) {
			result = true;
		}
		return result;
	}

	/**
	 * Check ký tự 1 byte, đưa về định dạng utf-8
	 * 
	 * @param str kí tự được check validate
	 * @throws UnsupportedEncodingException
	 * @return boolean true: là ký tự 1 byte false: k là ký tự 1 byte
	 */
	public static boolean checkOneByte(String str) throws UnsupportedEncodingException {
		boolean result = false;
		try {
			// Tạo ra mảng byte
			byte arr[] = str.getBytes(Constant.CHARSET_ENDCODING);
			if (str.length() == arr.length) {
				result = true;
			}
		} catch (UnsupportedEncodingException e) {
			// Lấy ra tên của method
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi Log ra màn hình cosole
			System.out.println("Error: Common" + "-" + method + "-" + e.getMessage());
			throw e;
		}
		return result;
	}

	/**
	 * Hàm kiểm tra endDate > startDate
	 * 
	 * @param startDate ngày bắt đầu
	 * @param endDate   ngày kết thúc
	 * @return result nếu thỏa mãn yêu cầu endDate > startDate
	 */
	public static boolean checkEndDate(String startDate, String endDate) {
		boolean result = true;
		// Chuyển các string sang dạng date
		Date start = convertStringToDate(startDate);
		Date end = convertStringToDate(endDate);
		// so sánh 2 date này với nhau
		if (start.after(end)) {
			result = false;
		}
		return result;
	}

	/**
	 * Hàm chuyển đổi giá trị ngày từ String sang kiểu date
	 * 
	 * @param str giá trị chuỗi ngày dạng string
	 * @return sqlDate ở dạng date
	 */
	public static java.sql.Date convertStringToDate(String str) {
		java.sql.Date sqlDate = null;
		try {
			if (!Constant.EMPTY_STRING.equals(str)) {
				// chuyển từ string sang Date
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
				sqlDate = new java.sql.Date(date.getTime());
			}

		} catch (ParseException e) {
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println("Error : Common" + "-" + method + "-" + e.getMessage());
		}
		return sqlDate;
	}

	/**
	 * Check số haftsize
	 * 
	 * @param String total
	 * @return boolean result = true: là số haftsize <br>
	 *         = false: k là số haftsize
	 */
	public static boolean checkHalfSize(String total) {
		boolean result = false;
		try {
			Integer.parseInt(total);
			byte[] arr = total.getBytes(Constant.CHARSET_ENDCODING);
			if (total.length() == arr.length) {
				result = true;
			}
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println("Error: Common" + "-" + method + "-" + e.getMessage());
			result = false;
		}
		return result;
	}

	/**
	 * Lấy ID cho session để đánh dấu dạng milisecond
	 * 
	 * @return iDSession là id của một session
	 */
	public static String getSalt() {
		// Lấy giá trị ID của session là dạng millisecond
		String iDSession = Long.toString(System.currentTimeMillis());
		return iDSession;
	}

	/**
	 * Lấy đối tượng tblUser từ đối tượng userInfor
	 * 
	 * @param userInfor đối tượng user cần lấy được tách từ userInfor
	 * @return đối tượng tbluser
	 * @throws NoSuchAlgorithmException lỗi k tìm thấy thuật toán khi mã hóa
	 *                                  password
	 * @throws Exception
	 */
	public static TblUserEntities getTblUserFromUserInfor(UserInforEntities userInfor)
			throws NoSuchAlgorithmException, Exception {
		TblUserEntities tblUser = new TblUserEntities();
		try {
			tblUser.setId(userInfor.getUserId());
			tblUser.setLoginName(userInfor.getLoginName());
			tblUser.setFullName(userInfor.getFullName());
			tblUser.setKanaName(userInfor.getFullNameKana());
			tblUser.setGroupId(userInfor.getGroupId());
			tblUser.setBirthday(Common.convertStringToDate(userInfor.getBirthday()));
			tblUser.setEmail(userInfor.getEmail());
			tblUser.setTel(userInfor.getTel());
			tblUser.setRule(Constant.RULE_USER);
			tblUser.setSalt(Common.getSalt());
			tblUser.setPassword(Common.encryptPassword(userInfor.getPassword(), Common.getSalt()));
		} catch (Exception e) {
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println("Error: Common" + "-" + method + "-" + e.getMessage());
		}
		return tblUser;
	}

	/**
	 * lấy chi tiết trình độ tiếng Nhật từ userInfor
	 * 
	 * @param userInfor được dùng để lấy tblDetailUserJapan
	 * @return đối tượng tblDetailUserJapan
	 */
	public static TblDetailUserJapanEntities getTblDetailUserJapanFromUserInfor(UserInforEntities userInfor)
			throws Exception {
		TblDetailUserJapanEntities tblDetailUserJapan = new TblDetailUserJapanEntities();
		try {

			tblDetailUserJapan.setUserId(userInfor.getUserId());
			tblDetailUserJapan.setCodeLevel(userInfor.getCodeLevel());
			tblDetailUserJapan.setStartDate(Common.convertStringToDate(userInfor.getStartDate()));
			tblDetailUserJapan.setEndDate(Common.convertStringToDate(userInfor.getEndDate()));
			tblDetailUserJapan.setTotal(Common.convertStringToInt(userInfor.getTotal(), Constant.DEFAULT_TOTAL));
		} catch (Exception e) {
			// Lấy ra tên method
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Thực hiện ghi log
			System.out.println("Error: Common" + "-" + method + "-" + e.getMessage());
		}
		// Trả về 1 đối tượng tblDetailUserJapan
		return tblDetailUserJapan;

	}

	/**
	 * Nạp giá trị cho các trường selecBox trên màn hình ADM003
	 * 
	 * @param req
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void setDataLogic(HttpServletRequest req) throws ClassNotFoundException, SQLException {
		// Khai báo 1 đối tượng MstGroup
		MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
		// Khai báo 1 đối tượng MstJapan
		MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
		// Tạo danh sách chứa các đối tượng mstgroup
		List<MstGroupEntities> listGroup = new ArrayList<MstGroupEntities>();
		// Tạo danh sách chứa các mstJapan
		List<MstJapanEntities> listJapan = new ArrayList<MstJapanEntities>();
		// Lấy ra các list cho selectBox thời gian
		// Danh sách các năm lấy từ năm 1990
		List<Integer> listYear = Common.getListYears(Constant.START_YEAR, (Year.now().getValue()));
		// Danh sách tháng của năm = 12
		List<Integer> listMonth = Common.getListMonths();
		// List của ngày theo tháng của năm
		List<Integer> listDay = Common.getListDays();

		try {
			// Lấy ra danh sách các nhóm
			listGroup = mstGroupLogic.getAllMstGroup();
			// Lấy ra danh sách trình độ tiếng Nhật
			listJapan = mstJapanLogic.getAllMstJapan();
			// Set các giá trị lên màn hình ADM003
			req.setAttribute(Constant.LIST_JAPAN, listJapan);
			req.setAttribute(Constant.LIST_MSTGROUP, listGroup);

			// Set giá trị của selectBox birthDay
			req.setAttribute(Constant.LIST_YEAR_BIRTHDAY, listYear);
			req.setAttribute(Constant.LIST_MONTH_BIRTHDAY, listMonth);
			req.setAttribute(Constant.LIST_DAY_BIRTHDAY, listDay);
			// Lấy giá trị từ req lên các thuộc tính startDate
			req.setAttribute(Constant.LIST_YEAR_STARTDATE, listYear);
			req.setAttribute(Constant.LIST_MONTH_STARTDATE, listMonth);
			req.setAttribute(Constant.LIST_DAY_STARTDATE, listDay);
			// Lấy giá trị từ req lên các thuộc tính EndDate
			req.setAttribute(Constant.LIST_YEAR_ENDDATE,
					Common.getListYears(Constant.START_YEAR, (Year.now().getValue()) + 1));
			req.setAttribute(Constant.LIST_MONTH_ENDDATE, listMonth);
			req.setAttribute(Constant.LIST_DAY_ENDDATE, listDay);

		} catch (ClassNotFoundException | SQLException e) {
			// Lấy ra tên của phương thức
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// Ghi log
			System.out.println("Error: Common" + "-" + method + "-" + e.getMessage());
			// Ném exeption ra cho controller xử lí
			throw e;
		}

	}

	/**
	 * Hàm check null và rỗng của chuỗi
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIsEmpty(String str) {
		boolean rs = false;
		if (str == null || Constant.EMPTY_STRING.equals(str)) {
			rs = true;
		} else {
			rs = false;
		}
		return rs;
	}

}
