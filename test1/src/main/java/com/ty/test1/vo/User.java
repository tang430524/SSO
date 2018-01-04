package com.ty.test1.vo;

public class User {
private String uname;
private String upassword;
@Override
public String toString() {
	return "User [uname=" + uname + ", upassword=" + upassword + "]";
}
public String getUname() {
	return uname;
}
public void setUname(String uname) {
	this.uname = uname;
}
public String getUpassword() {
	return upassword;
}
public void setUpassword(String upassword) {
	this.upassword = upassword;
}
}
