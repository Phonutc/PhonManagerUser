/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * MstGroupEntities.java, 12 thg 5, 2021 PhonPV
 */
package manageuser.entities;

/**
 * @Description
 * Tạo đối tượng MstGroup
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class MstGroupEntities {
	private int groupId;
	private String groupName;

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
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
