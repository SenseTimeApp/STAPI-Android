package com.sensetime.stapi.bean;

import java.util.List;

public class STPersons {

	private String person_id;
	private String name;
	private String user_data;
	private List<String> face_ids;
	
	public String getPerson_id() {
		return person_id;
	}
	public void setPerson_id(String person_id) {
		this.person_id = person_id;
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
	public List<String> getFace_ids() {
		return face_ids;
	}
	public void setFace_ids(List<String> face_ids) {
		this.face_ids = face_ids;
	}
	@Override
	public String toString() {
		return "Persons [person_id=" + person_id + ", name=" + name
				+ ", user_data=" + user_data + ", face_ids=" + face_ids + "]";
	}
}
