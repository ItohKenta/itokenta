package ito_kenta.beans;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String subject;
	private String text;
	private String category;
	private int branchId;
	private int positionId;
	private Date insertDate;
	private int userId;

	// 自動連番id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// 件名subject
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	// 本文text
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	// カテゴリcategory
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	// 支店ID branchId
	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	// 役職ID positionId
	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	// 登録日時 insertDate
	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	// 登録者ID userId
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}