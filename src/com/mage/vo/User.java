package com.mage.vo;

import java.util.Date;

public class User {
	private int id;//id
	private String trueName;//真实名称
	private String userName;//昵称
	private String password;//密码
	private String sex;//性别
	private Date birthday;//出生日志
	private String dentityCode;//身份证号
	private String email;//邮件
	private String mobile;//手机
	private String address;//地址
	private int status=1;//状态 1普通用户 2管理员账户
	public User() {
	}
	public User(String userName, String password, String sex, Date birthday, String dentityCode,
	String email,
	String mobile, String address) {
	super();
	this.userName = userName;
	this.password = password;
	this.sex = sex;
	this.birthday = birthday;
	this.dentityCode = dentityCode;
	this.email = email;
	this.mobile = mobile;
	this.address = address;
	}
	public User(String uname, String pwd) {
	this.userName = uname;
	this.password = pwd;
	}
	public int getId() {
	return id;
	}
	public void setId(int id) {
	this.id = id;
	}
	public String getTrueName() {
	return trueName;
	}
	public void setTrueName(String trueName) {
	this.trueName = trueName;
	}
	public String getUserName() {
	return userName;
	}
	public void setUserName(String userName) {
	this.userName = userName;
	}
	public String getPassword() {
	
	return password;
	}
	public void setPassword(String password) {
	this.password = password;
	}
	public String getSex() {
	return sex;
	}
	public void setSex(String sex) {
	this.sex = sex;
	}
	public Date getBirthday() {
	return birthday;
	}
	public void setBirthday(Date birthday) {
	this.birthday = birthday;
	}
	public String getDentityCode() {
	return dentityCode;
	}
	public void setDentityCode(String dentityCode) {
	this.dentityCode = dentityCode;
	}
	public String getEmail() {
	return email;
	}
	public void setEmail(String email) {
	this.email = email;
	}
	public String getMobile() {
	return mobile;
	}
	public void setMobile(String mobile) {
	this.mobile = mobile;
	}
	public String getAddress() {
	return address;
	}
	public void setAddress(String address) {
	this.address = address;
	}
	public int getStatus() {
	return status;
	}
	public void setStatus(int status) {
	this.status = status;
	}	

}
