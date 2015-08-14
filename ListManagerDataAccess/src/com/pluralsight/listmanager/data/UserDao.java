package com.pluralsight.listmanager.data;

import com.pluralsight.listmanager.model.User;

public interface UserDao {

	User getUser(String username);

}
