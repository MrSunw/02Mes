package com.mes.pojo;

public class Depart {

	private Integer id;
	private Integer departCode;
	private String departName;
	private String departDesc;
	private Integer departNum;
	private String departRemake;
	public Depart(Integer id, Integer departCode, String departName, String departDesc, Integer departNum,
			String departRemake) {
		super();
		this.id = id;
		this.departCode = departCode;
		this.departName = departName;
		this.departDesc = departDesc;
		this.departNum = departNum;
		this.departRemake = departRemake;
	}
	public Depart() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDepartCode() {
		return departCode;
	}
	public void setDepartCode(Integer departCode) {
		this.departCode = departCode;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getDepartDesc() {
		return departDesc;
	}
	public void setDepartDesc(String departDesc) {
		this.departDesc = departDesc;
	}
	public Integer getDepartNum() {
		return departNum;
	}
	public void setDepartNum(Integer departNum) {
		this.departNum = departNum;
	}
	public String getDepartRemake() {
		return departRemake;
	}
	public void setDepartRemake(String departRemake) {
		this.departRemake = departRemake;
	}
	@Override
	public String toString() {
		return "Depart [id=" + id + ", departCode=" + departCode + ", departName=" + departName + ", departDesc="
				+ departDesc + ", departNum=" + departNum + ", departRemake=" + departRemake + "]";
	}
	
}
