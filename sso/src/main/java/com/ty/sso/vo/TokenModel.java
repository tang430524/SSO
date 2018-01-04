package com.ty.sso.vo;

public class TokenModel {
private String token;
private User user;
@Override
public String toString() {
	return "TokenModel [token=" + token + ", user=" + user + "]";
}
public TokenModel(String token, User user) {
	super();
	this.token = token;
	this.user = user;
}
public TokenModel() {
	super();
	// TODO Auto-generated constructor stub
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
} 

}
