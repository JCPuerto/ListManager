package com.pluralsight.listmanager.service;

import java.util.List;

import com.pluralsight.listmanager.model.ListItem;
import com.pluralsight.listmanager.model.User;

public interface UserService {

	User authenticateUser(String username);

	List<ListItem> getListItems(Long userId);

}
