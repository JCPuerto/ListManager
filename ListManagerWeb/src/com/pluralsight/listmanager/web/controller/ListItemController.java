package com.pluralsight.listmanager.web.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.pluralsight.listmanager.model.ListItem;
import com.pluralsight.listmanager.service.ListItemService;
import com.pluralsight.listmanager.service.impl.ListItemServiceImpl;
import com.pluralsight.listmanager.web.model.ListItemBean;
import com.pluralsight.listmanager.web.model.UserBean;

@ManagedBean
@RequestScoped
public class ListItemController {

	private final ListItemService listItemService;

	@ManagedProperty("#{userBean}")
	private UserBean userBean;

	@ManagedProperty("#{listItemBean}")
	private ListItemBean listItemBean;

	public ListItemController() {
		listItemService = new ListItemServiceImpl();
	}

	public String save() {
		if(listItemBean.getId() == null || listItemBean.getId() == 0){
			listItemService.addListItem(userBean.getUserId(), listItemBean.getValue());	
		}
		else{
			listItemService.updateListItem(userBean.getUserId(), listItemBean.getId(), listItemBean.getValue());
		}
		
		listItemBean.setId(null);
		listItemBean.setValue(null);
		
		return null;
	}

	public String edit(ListItem listItem) {		
		listItemBean.setId(listItem.getId());
		listItemBean.setValue(listItem.getValue());
		
		return null;
	}

	public String delete(ListItem listItem) {		
		listItemService.deleteListItem(userBean.getUserId(), listItem.getId());
		return null;
	}

	public void setUserBean(UserBean userBean) {		
		this.userBean = userBean;
	}

	public void setListItemBean(ListItemBean listItemBean) {		
		this.listItemBean = listItemBean;
	}
}
