package com.appsdeveloperblog.app.ws.userservice;

import java.util.List;

import com.appsdeveloperblog.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;

import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;

public interface UserService {
	UserRest createUser(UserDetailsRequestModel userDetails);// create user
	
	 List<UserRest> getAllUsers();
	 
	 UserRest getUserById(String userId);
	 
	 UserRest updateUser(String userId, UpdateUserDetailsRequestModel userDetails);
	    
	    void deleteUser(String userId);
}
