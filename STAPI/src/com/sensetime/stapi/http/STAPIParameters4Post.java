package com.sensetime.stapi.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import android.graphics.Bitmap;

/**
 * POST请求是参数设置类
 *
 */
public class STAPIParameters4Post {
	
	private MultipartEntity multiPart = null;
	private final static int boundaryLength = 32;
	private final static String boundaryAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";
	private String mBoundary;
	
	public MultipartEntity getMultiPart() {
		return multiPart;
	}
	
	public STAPIParameters4Post() {
		super();
		mBoundary = getBoundary();
		multiPart = new MultipartEntity(HttpMultipartMode.STRICT, mBoundary, Charset.forName("UTF-8"));
	}
	
	public String boundaryString() {
		return mBoundary;
	}
	
	public STAPIParameters4Post setTaskId(String taskId) {
		addString("task_id", taskId);
		return this;
	}
	
	public STAPIParameters4Post setImageId(String imageId) {
		addString("image_id", imageId);
		return this;
	}
	
	public STAPIParameters4Post setWithFaces(boolean withFaces) {
		addString("with_faces", booleanToString(withFaces));
		return this;
	}
	
	/**
	 * detection
	 * @return
	 */
	public STAPIParameters4Post setLandmarks106(boolean withLandmarks106) {
		addString("landmarks106", booleanToString(withLandmarks106));
		return this;
	}
	
	public STAPIParameters4Post setAttributes(boolean withAttributes) {
		addString("attributes", booleanToString(withAttributes));
		return this;
	}
	
	public STAPIParameters4Post setAutoRotate(boolean isAutoRotate) {
		addString("auto_rotate", booleanToString(isAutoRotate));
		return this;
	}
	
	public STAPIParameters4Post setUserData(String userData) {
		addString("user_data", userData);
		return this;
	}
	
	/**
	 * verification
	 */
	public STAPIParameters4Post setFaceId(String faceId) {
		addString("face_id", faceId);
		return this;
	}
	
	public STAPIParameters4Post setFace2Id(String face2Id) {
		addString("face2_id", face2Id);
		return this;
	}
	
	public STAPIParameters4Post setPersonId(String personId) {
		addString("person_id", personId);
		return this;
	}
	
	/**
	 * search
	 */
	public STAPIParameters4Post setFaceIds(String[] faceIds) {
		addString("face_ids", toStringList(faceIds));
		return this;
	}
	
	public STAPIParameters4Post setFacesetId(String facesetId) {
		addString("faceset_id", facesetId);
		return this;
	}
	
	public STAPIParameters4Post setTop(Integer top) {
		addString("top", ""+top);
		return this;
	}
	
	/**
	 * identification
	 */
	public STAPIParameters4Post setGroupId(String groupId) {
		addString("group_id", groupId);
		return this;
	}
	
	public STAPIParameters4Post setUrl(String url){
		addString("url", url);
		return this;
	}
	
	public STAPIParameters4Post setFile(Bitmap faceBitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();    
		faceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);    
		setFile(baos.toByteArray());
		return this;
	}
	
	public STAPIParameters4Post setFile(File file) {
		multiPart.addPart("file", new FileBody(file));
		return this;
	}
	
	public STAPIParameters4Post setFile(byte[] data) {
		multiPart.addPart("file", new ByteArrayBody(data, "NoName"));
		return this;
	}
	
	public STAPIParameters4Post setFacesetId(String[] facesetId) {
		setFacesetId(toStringList(facesetId));
		return this;
	}
	
	public STAPIParameters4Post setName(String name) {
		addString("name", name);
		return this;
	}
	
	public STAPIParameters4Post setFaceId(String[] faceIds) {
		return setFaceId(toStringList(faceIds));
	}
	
	public STAPIParameters4Post setPersonId(String[] personIds) {
		return setPersonId(toStringList(personIds));
	}
	
	public STAPIParameters4Post setGroupId(String[] groupIds) {
		return setGroupId(toStringList(groupIds));
	}
	
	public STAPIParameters4Post setGroupId(ArrayList<String> groupIds) {
		return  setGroupId(toStringList(groupIds));
	}
	
	private void addString(String id, String str) {
		try {
			multiPart.addPart(id, new StringBody(str, Charset.forName("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private String toStringList(String[] sa) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sa.length; ++i) {
			if (i != 0) sb.append(',');
			sb.append(sa[i]);
		}
		return sb.toString();
	}
	
	private String toStringList(ArrayList<String> sa) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sa.size(); ++i) {
			if (i != 0) sb.append(',');
			sb.append(sa.get(i));
		}
		return sb.toString();
	}
	
	private String getBoundary() {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < boundaryLength; ++i)
			sb.append(boundaryAlphabet.charAt(random.nextInt(boundaryAlphabet.length())));
		return sb.toString();
	}
	
	private String booleanToString(boolean flag){
		if(flag){
			return "1";
		}else{
			return "0";
		}
	}
}
