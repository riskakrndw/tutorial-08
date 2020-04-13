package com.apap.tu08.service;

import com.apap.tu08.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt(String password);
	boolean matchPassword(String rawPassword,String encodePassword);
	UserRoleModel getUser(String user);
}