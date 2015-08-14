package com.pluralsight.listmanager.data.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pluralsight.listmanager.data.ListItemDao;
import com.pluralsight.listmanager.data.util.DbConnector;
import com.pluralsight.listmanager.data.util.JdbcCloses;
import com.pluralsight.listmanager.model.ListItem;

public class ListItemDaoImpl implements ListItemDao {

	@Override
	public ListItem getListItemById(Long listItemId) {

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		ListItem listItem = null;

		try {
			connection = DbConnector.getConnection();
			stmt = connection.prepareStatement("SELECT LI.User_Id, LI.Value FROM List_Item LI WHERE LI.Id = ?");

			stmt.setLong(1, listItemId);

			rs = stmt.executeQuery();

			if (rs.next()) {

				listItem = new ListItem();
				listItem.setId(listItemId);
				listItem.setUserId(rs.getLong("User_Id"));
				listItem.setValue(rs.getString("Value"));
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

		return listItem;
	}

	@Override
	public List<ListItem> getListItemsByUserId(Long userId) {

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<ListItem> listItems = new ArrayList<ListItem>();

		try {
			connection = DbConnector.getConnection();
			stmt = connection.prepareStatement("SELECT LI.Id, LI.Value FROM List_Item LI WHERE LI.User_Id = ?");

			stmt.setLong(1, userId);

			rs = stmt.executeQuery();

			while (rs.next()) {

				ListItem listItem = new ListItem();
				listItem.setId(rs.getLong("Id"));
				listItem.setUserId(userId);
				listItem.setValue(rs.getString("Value"));
				listItems.add(listItem);
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

		return listItems;
	}

	@Override
	public ListItem saveListItem(ListItem listItem) {

		if (listItem.getId() == null) {
			insertListItem(listItem);
		} else {
			updateListItem(listItem);
		}

		return listItem;
	}

	private void insertListItem(ListItem listItem) {

		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = DbConnector.getConnection();
			stmt = connection.prepareStatement("INSERT INTO List_Item (User_Id, Value) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stmt.setLong(1, listItem.getUserId());
			stmt.setString(2, listItem.getValue());

			long listItemId = stmt.executeUpdate(); // ???

			assert(Long) listItemId != null;

			listItem.setId(listItemId);

			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcCloses.closeIgnoreError(stmt);
			JdbcCloses.closeIgnoreError(connection);
		}
	}

	private void updateListItem(ListItem listItem) {

		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = DbConnector.getConnection();
			stmt = connection.prepareStatement("UPDATE List_Item SET Value = ? WHERE Id = ?");

			stmt.setString(1, listItem.getValue());
			stmt.setLong(2, listItem.getId());

			stmt.executeUpdate();

			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcCloses.closeIgnoreError(stmt);
			JdbcCloses.closeIgnoreError(connection);
		}

	}

	@Override
	public void deleteListItem(Long listItemId) {

		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = DbConnector.getConnection();
			stmt = connection.prepareStatement("DELETE FROM List_Item WHERE Id = ?");

			stmt.setLong(1, listItemId);

			stmt.executeUpdate();

			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcCloses.closeIgnoreError(stmt);
			JdbcCloses.closeIgnoreError(connection);
		}

	}
}
