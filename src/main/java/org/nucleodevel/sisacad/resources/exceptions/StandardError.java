package org.nucleodevel.sisacad.resources.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable {

	private static final long serialVersionUID = -6780518627972700538L;

	private Integer status;
	private String msg;

	public StandardError() {

	}

	public StandardError(Integer status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
