package com.appsdeveloperblog.app.ws.ui.controllers;

import java.util.List;


import java.util.Map;


import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import com.appsdeveloperblog.app.ws.userservice.UserService;


@RestController //this is a Controller class: handles HTTP request generates responses
@RequestMapping("/users")  // http://localhost:8080/users : base URL for all end points
public class UserController {

	Map<String, UserRest> users;
	
	@Autowired //injecting the UserService into the Controller
	UserService userService; //dependent on a service called UserService which handles logic
	
	@GetMapping //Retrieves a list of users: uses pagination(page limit, sort)
	public ResponseEntity<List<UserRest>> getUsers(@RequestParam(value="page", defaultValue="1") int page, 
			@RequestParam(value="limit", defaultValue="50") int limit,
			@RequestParam(value="sort", defaultValue = "desc", required = false) String sort)
	{
		List<UserRest> allUsers = userService.getAllUsers();
		if (allUsers.isEmpty()) {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
		else {
			return new ResponseEntity<>(allUsers, HttpStatus.OK);
		}
		}
		
	
	@GetMapping(path="/{userId}", //show user ID
			produces =  { 
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE
					} )
	public ResponseEntity<UserRest> getUser(@PathVariable String userId)
	{ UserRest user = userService.getUserById(userId);
		if(user != null)
		{
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping( //create API
			consumes =  { 
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE
			}, 
			produces =  { 
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE
					}  )
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails)
	{

		UserRest returnValue = userService.createUser(userDetails);
		return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
	}
	
	
	@PutMapping(path="/{userId}", consumes =  { //update user
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE
			}, 
			produces =  { 
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE
					}  )
	public ResponseEntity<UserRest> updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails)
	{
		 UserRest updatedUser = userService.updateUser(userId, userDetails);
		 
		 if (updatedUser  != null) {
			 return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		    } else {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
	}
	
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id)
	{
		userService.deleteUser(id);
		
		return ResponseEntity.noContent().build();
	}
}
