package com.ctrip.gs.common.mq;

import java.io.Serializable;
import java.util.HashMap;

public class DeltaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int operatorType;
	private int targetType;
	private String targetId;
	private String version;
	private HashMap<String, String> data;
	public int getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(int operatorType) {
		this.operatorType = operatorType;
	}
	public int getTargetType() {
		return targetType;
	}
	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public HashMap<String, String> getData() {
		return data;
	}
	public void setData(HashMap<String, String> data) {
		this.data = data;
	}
}
