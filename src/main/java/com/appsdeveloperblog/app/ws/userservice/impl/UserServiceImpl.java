package com.appsdeveloperblog.app.ws.userservice.impl;

import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import com.appsdeveloperblog.app.ws.userservice.UserService;

@Service
public class UserServiceImpl implements UserService{

	Map<String, UserRest> users; //stores data: key=user ID; value = UserRest object: has user details
	Utils utils;
	
	public UserServiceImpl() {}
	
	@Autowired
	public UserServiceImpl(Utils utils)
	{
		this.utils =utils;
	}
	
	@Override
	public UserRest createUser(UserDetailsRequestModel userDetails) {
		
		UserRest returnValue = new UserRest();
		returnValue.setEmail(userDetails.getEmail());
		returnValue.setFirstName(userDetails.getFirstName());
		returnValue.setLastName(userDetails.getLastName());
		
		String userId = utils.generateUserId();
		returnValue.setUserId(userId);
		
		if(users == null) users = new HashMap<>();
		users.put(userId, returnValue);
		
		return returnValue;
		
	}
	
	@Override
	public List<UserRest> getAllUsers(){
		if (users == null)
			return new ArrayList<>();
		return new ArrayList<>(users.values());
	}
	
	@Override
	public UserRest getUserById(String userId) {
	    if (users != null && users.containsKey(userId)) {
	        return users.get(userId);
	    } else {
	        return null; 
	    }
	}
	
	@Override
    public void deleteUser(String userId) {
        users.remove(userId);
    }

    @Override
    public UserRest updateUser(String userId, UpdateUserDetailsRequestModel userDetails) {
        UserRest existingUser = users.get(userId);
        if (existingUser != null) {
        	existingUser.setEmail(userDetails.getEmail());
            existingUser.setFirstName(userDetails.getFirstName());
            existingUser.setLastName(userDetails.getLastName());
            users.put(userId, existingUser);
        }
        return existingUser;
    }

}
