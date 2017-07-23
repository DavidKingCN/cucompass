package com.slf.crud.model.demo;

public class User {
    private Integer userId;

    private String userName;

    private String userPwd;

    private String userSex;
    
    private Integer userAge;

    private Integer userGroupFk;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    
    
    public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }

    public Integer getUserGroupFk() {
        return userGroupFk;
    }

    public void setUserGroupFk(Integer userGroupFk) {
        this.userGroupFk = userGroupFk;
    }
}