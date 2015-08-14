package com.pluralsight.listmanager.service.impl;

import java.util.List;

import com.pluralsight.listmanager.data.ListItemDao;
import com.pluralsight.listmanager.data.UserDao;
import com.pluralsight.listmanager.data.impl.ListItemDaoImpl;
import com.pluralsight.listmanager.data.impl.UserDaoImpl;
import com.pluralsight.listmanager.model.ListItem;
import com.pluralsight.listmanager.model.User;
import com.pluralsight.listmanager.service.UserService;

public class UserServiceImpl implements UserService {
	private final UserDao userDao;
	private final ListItemDao listItemDao;

	public UserServiceImpl() {
		this.userDao = new UserDaoImpl();
		this.listItemDao = new ListItemDaoImpl();
	}

	@Override
	public User authenticateUser(String username) {

		User user = null;

		if (username != null && !"".equals(username.trim())) {
			user = userDao.getUser(username);
		}

		return user;
	}

	@Override
	public List<ListItem> getListItems(Long userId) {
		return listItemDao.getListItemsByUserId(userId);
	}
}