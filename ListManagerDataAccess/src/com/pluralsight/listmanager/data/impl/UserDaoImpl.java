package com.pluralsight.listmanager.data.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pluralsight.listmanager.data.UserDao;
import com.pluralsight.listmanager.data.util.DbConnector;
import com.pluralsight.listmanager.data.util.JdbcCloses;
import com.pluralsight.listmanager.model.User;

public class UserDaoImpl implements UserDao {

	@Override
	public User getUser(String username) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		User user = null;

		try {
			connection = DbConnector.getConnection();
			stmt = connection.prepareStatement(
					"SELECT U.Id, U.UserName, U.Display_Name FROM App_User U WHERE LOWER(U.UserName) = ?");

			stmt.setString(1, username.toLowerCase());

			rs = stmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getLong("Id"));
				user.setUsername(rs.getString("UserName"));
				user.setDisplayname(rs.getString("Display_Name"));
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcCloses.closeIgnoreError(rs);
			JdbcCloses.closeIgnoreError(stmt);
			JdbcCloses.closeIgnoreError(connection);
		}

		return user;
	}
}
