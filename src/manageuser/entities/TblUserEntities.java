/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * TblUserEntities.java, 9 thg 5, 2021 PhonPV
 */
package manageuser.entities;

import java.sql.Date;

/**
 * @Description
 * Đối tượng TblUser
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class TblUserEntities {
//	Khai báo thuộc tính id
	private int id;
//	Khai báo thuộc tính email
	private String email;
//	Khai báo thuộc tính fullName
	private String fullName;
//	Khai báo thuộc tính groupId
	private int groupId;
//	Khai báo thuộc tính groupName
	private String kanaName;
//  Khai báo thuộc tính loginName
	private String loginName;
//	Khai báo thuộc tính Password
	private String password;
//	Khai báo thuộc tính số điện thoại user
	private String tel;
//	Khai báo thuộc tính ngày sinh
	private Date birthday;
//	Khai báo thuộc tính rule
	private int rule;
//	Khai báo thuộc tính salt
	private String salt;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the kanaName
	 */
	public String getKanaName() {
		return kanaName;
	}

	/**
	 * @param kanaName the kanaName to set
	 */
	public void setKanaName(String kanaName) {
		this.kanaName = kanaName;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the rule
	 */
	public int getRule() {
		return rule;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(int rule) {
		this.rule = rule;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

}
