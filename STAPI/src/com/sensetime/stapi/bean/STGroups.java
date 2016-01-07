package com.sensetime.stapi.bean;

import java.util.List;

public class STGroups {

	private String group_id;
	private String name;
	private String user_data;
	private List<STPersons> persons;
	
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser_data() {
		return user_data;
	}
	public void setUser_data(String user_data) {
		this.user_data = user_data;
	}
	public List<STPersons> getPersons() {
		return persons;
	}
	public void setPersons(List<STPersons> persons) {
		this.persons = persons;
	}
	@Override
	public String toString() {
		return "Groups [group_id=" + group_id + ", name=" + name
				+ ", user_data=" + user_data + ", persons=" + persons + "]";
	}
	
}
