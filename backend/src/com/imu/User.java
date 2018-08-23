package com.imu;

import java.sql.Date;

public class User {
	private String username;
	private Integer userid;
	private Integer age;
	private Date birth;
	public User() {}
	public User(String name,Integer userid,Integer age,Date birth) {
		this.setAge(age);
		this.setBirth(birth);
		this.setUserid(userid);
		this.setUsername(name);;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String toString() {
	        return "Student [userid=" + userid + ",username=" + username + ",age=" + age + ",birth=" + birth + "]";  
	}
}
