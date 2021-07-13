/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * MessageProperties.java, 9 thg 5, 2021 PhonPV
 */
package manageuser.properties;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import manageuser.utils.Constant;

/**
 * @Description
 * Chứa hàm đọc dữ liệu các câu thông báo từ file message_ja.properties
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class MessageProperties {

	// lưu các cặp <key, value> trong file properties
	private static Map<String, String> map = new HashMap<String, String>();
	static {
		// tạo đối tượng kiểu Properties
		Properties pro = new Properties();
		try {
			pro.load(new InputStreamReader(
					MessageErrorProperties.class.getClassLoader().getResourceAsStream(Constant.PROPERTIES_MESSAGE),
					Constant.CHARSET_ENDCODING));
			// lưu các giá trị key trong file properties
			Enumeration<?> emumeration = pro.propertyNames();
			// true nếu vẫn còn phần tử, false nếu tất cả phần tử đã được lấy ra
			while (emumeration.hasMoreElements()) {
				// key = key tiếp theo
				String key = (String) emumeration.nextElement();
				// lấy value tương ứng với key
				String value = pro.getProperty(key);
				// thêm vào list
				map.put(key, value);
				// Đóng while
			}
			// Đóng try mở catch xử lí lỗi
		} catch (IOException e) {
			// ghi lỗi
			e.printStackTrace();
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println("Error: MessageProperties" + "-" + method + "-" + e.getMessage());
		}

	}

	/**
	 * Lấy value tương ứng với key trong file properties
	 * 
	 * @param key tên key trong file properties
	 * @return trả về value tương ứng với key
	 */
	public static String getValueByKey(String key) {
		String value = map.get(key);
		return value;
	}
}
