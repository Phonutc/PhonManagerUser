/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * ConfigProperties.java, 12 thg 5, 2021 PhonPV
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
 * Chứa hàm và khối static xử lí cho việc đọc dữ liệu từ file config_ja.properties
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class ConfigProperties {

	// lưu các cặp <key, value> trong file properties
	private static Map<String, String> mapDB = new HashMap<String, String>();
	static {
		// tạo đối tượng kiểu Properties
		Properties pro = new Properties();
		try {
			pro.load(new InputStreamReader(
					ConfigProperties.class.getClassLoader().getResourceAsStream(Constant.CONFIG_PATH),
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
				mapDB.put(key, value);
			}
		} catch (IOException e) {
			String method = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println("Error ConfigProperties" + "-" + method + "-" + e.getMessage());
		}
	}

	/**
	 * lấy value tương ứng với key trong file properties
	 * 
	 * @param key tên key trong file properties
	 * @return trả về value tương ứng với key
	 */
	public static String getValueByKey(String key) {
		String value = mapDB.get(key);
		return value;
	}
}
