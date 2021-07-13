/**
 * Copyright(C) 2021 Luvina Software Joint Stock Company
 * TblDetailUserJapanEntities.java, 6 thg 6, 2021 PhonPV
 */
package manageuser.entities;

import java.util.Date;

/**
 * @Description
 * Đối tượng TblDetailUserJapan
 *
 * @Author Phạm Văn Phồn
 * @Version 1.0
 */
public class TblDetailUserJapanEntities {
	private int detailId;
	private int userId;
	private int total;
	private String codeLevel;
	private Date startDate, endDate;

	/**
	 * @return the detailId
	 */
	public int getDetailId() {
		return detailId;
	}

	/**
	 * @param detailId the detailId to set
	 */
	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}

	/**
	 * @param codeLevel the codeLevel to set
	 */
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
