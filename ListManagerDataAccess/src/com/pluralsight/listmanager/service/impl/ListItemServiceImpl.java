package com.pluralsight.listmanager.service.impl;

import com.pluralsight.listmanager.data.ListItemDao;
import com.pluralsight.listmanager.data.impl.ListItemDaoImpl;
import com.pluralsight.listmanager.model.ListItem;
import com.pluralsight.listmanager.service.ListItemService;

public class ListItemServiceImpl implements ListItemService {

	private final ListItemDao listItemDao;

	public ListItemServiceImpl() {
		this.listItemDao = new ListItemDaoImpl();
	}

	@Override
	public ListItem addListItem(Long userId, String value) {

		ListItem listItem = null;

		if (userId != null && value != null && !"".equals(value)) {

			listItem = new ListItem();
			listItem.setUserId(userId);
			listItem.setValue(value);
			listItem = listItemDao.saveListItem(listItem);
		}

		return listItem;
	}

	@Override
	public ListItem updateListItem(Long userId, Long listItemId, String newValue) {

		ListItem listItem = listItemDao.getListItemById(listItemId);

		if ((listItem == null) || !userId.equals(listItem.getUserId())) {
			listItem = null;
		} else {
			listItem.setValue(newValue);
			listItem = listItemDao.saveListItem(listItem);
		}

		return listItem;
	}

	@Override
	public void deleteListItem(Long userId, Long listItemId) {

		ListItem listItem = listItemDao.getListItemById(listItemId);

		if ((listItem != null) || userId.equals(listItem.getUserId())) {
			listItemDao.deleteListItem(listItemId);
		}

	}

}
