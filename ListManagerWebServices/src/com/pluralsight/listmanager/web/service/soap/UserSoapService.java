package com.pluralsight.listmanager.web.service.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.pluralsight.listmanager.model.ListItem;
import com.pluralsight.listmanager.model.User;
import com.pluralsight.listmanager.service.UserService;
import com.pluralsight.listmanager.service.impl.UserServiceImpl;

@WebService(serviceName = "UserSoapService")
// The default serviceName would be "UserSoapServiceService" if not set 
public class UserSoapService {
	private final UserService userService;
	
	public UserSoapService() {
		userService = new UserServiceImpl();
	}
	
	@WebMethod
	public Long getUserId(@WebParam(name = "username") String username){
		User user = userService.authenticateUser(username);
		
		return user == null ? null : user.getId();
	}
	
	@WebMethod
	public List<ListItem> getUserListItems(@WebParam(name = "userId") Long userId){
		return userService.getListItems(userId);
	}
}
