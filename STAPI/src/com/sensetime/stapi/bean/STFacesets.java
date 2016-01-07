package com.sensetime.stapi.bean;

import java.util.List;

public class STFacesets {

	private String faceset_id;
	private String name;
	private String user_data;
	private List<String> face_ids;
	
	public String getFaceset_id() {
		return faceset_id;
	}
	public void setFaceset_id(String faceset_id) {
		this.faceset_id = faceset_id;
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
		return "Facesets [faceset_id=" + faceset_id + ", name=" + name
				+ ", user_data=" + user_data + ", face_ids=" + face_ids + "]";
	}
	
}
