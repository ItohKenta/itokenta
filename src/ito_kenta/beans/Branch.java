package ito_kenta.beans;

import java.io.Serializable;

public class Branch implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;

	// 自動連番id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// 支店名name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}