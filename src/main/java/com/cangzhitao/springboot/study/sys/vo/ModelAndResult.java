package com.cangzhitao.springboot.study.sys.vo;

import java.io.Serializable;

public class ModelAndResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4233486507141321029L;
	
	private boolean success = true;
	
	private Serializable data;
	
	private String message;
	
	public ModelAndResult() {
	}
	
	public ModelAndResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public ModelAndResult(Object data) {
		this.data = (Serializable) data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Serializable getData() {
		return data;
	}

	public void setData(Serializable data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
