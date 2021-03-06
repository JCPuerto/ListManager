package com.pluralsight.listmanager.web.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.pluralsight.listmanager.model.User;
import com.pluralsight.listmanager.service.UserService;
import com.pluralsight.listmanager.service.impl.UserServiceImpl;

@Path("/user")
public class UserResource {
	private final UserService userService;

	public UserResource() {
		userService = new UserServiceImpl();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Long getUserId(@QueryParam("username") String username){
		User user = userService.authenticateUser(username);
		
		return user == null ? null : user.getId();
	}
}
