package com.mage.service;

import com.mage.vo.User;

public interface UserService {
	void register(User user);//ע��
	boolean checkName(String uName);//����û����Ƿ�Ψһ
	User login(User user);
	void update(User user);//�û���Ϣ�޸ı��淽��
}
