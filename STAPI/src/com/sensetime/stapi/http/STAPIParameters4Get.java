package com.sensetime.stapi.http;

import java.util.HashMap;
import java.util.Map;

/**
 * GET请求是参数设置类
 *
 */
public class STAPIParameters4Get {

	private StringBuffer mParams = new StringBuffer();
	private Map<String,Object> mParamsMap = new HashMap<String,Object>();
	
	public String getParamsMap(){
		for(Map.Entry entry:mParamsMap.entrySet()){
			mParams.append("&"+entry.getKey()+"="+entry.getValue());
		}
		return mParams.toString();
	}
	
	public void setTaskId(String taskId){
		addAttribute("task_id", taskId);
	}
	
	public void setFaceId(String faceId){
		addAttribute("face_id", faceId);
	}
	
	public void setImageId(String imageId){
		addAttribute("image_id", imageId);
	}
	
	public void setPersonId(String personId){
		addAttribute("person_id", personId);
	}
	
	public void setGroupId(String groupId){
		addAttribute("group_id", groupId);
	}
	
	public void setFacesetId(String facesetId){
		addAttribute("faceset_id", facesetId);
	}
	
	public void setWithFaces(boolean withFaces){
		addAttribute("with_faces", booleanToString(withFaces));
	}
	
	public void setWithImage(boolean withImage){
		addAttribute("with_image", booleanToString(withImage));
	}
	
	private void addAttribute(String key, String value) {
		mParamsMap.put(key, value);
	}
	
	private String booleanToString(boolean flag){
		if(flag){
			return "1";
		}else{
			return "0";
		}
	}
}
