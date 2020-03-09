package com.mage.service;

import com.mage.vo.User;

public interface UserService {
	void register(User user);//注册
	boolean checkName(String uName);//检查用户名是否唯一
	User login(User user);
	void update(User user);//用户信息修改保存方法
}
