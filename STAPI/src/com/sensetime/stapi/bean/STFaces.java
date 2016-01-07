package com.sensetime.stapi.bean;

import java.util.List;

public class STFaces {

	private String face_id;
	private RectEntity rect;
	private int eye_dist;
	private AttributesEntity attributes;
	private EmotionsEntity emotions;
	private List<List<Double>> landmarks21;
	private List<List<Double>> landmarks106;

	public void setFace_id(String face_id) {
		this.face_id = face_id;
	}

	public void setRect(RectEntity rect) {
		this.rect = rect;
	}

	public void setEye_dist(int eye_dist) {
		this.eye_dist = eye_dist;
	}

	public void setAttributes(AttributesEntity attributes) {
		this.attributes = attributes;
	}

	public void setEmotions(EmotionsEntity emotions) {
		this.emotions = emotions;
	}

	public void setLandmarks21(List<List<Double>> landmarks21) {
		this.landmarks21 = landmarks21;
	}
	
	public void setLandmarks106(List<List<Double>> landmarks106) {
		this.landmarks106 = landmarks106;
	}

	public String getFace_id() {
		return face_id;
	}

	public RectEntity getRect() {
		return rect;
	}

	public int getEye_dist() {
		return eye_dist;
	}

	public AttributesEntity getAttributes() {
		return attributes;
	}

	public EmotionsEntity getEmotions() {
		return emotions;
	}

	public List<List<Double>> getLandmarks21() {
		return landmarks21;
	}
	
	public List<List<Double>> getLandmarks106() {
		return landmarks106;
	}

	public static class RectEntity {
		private int left;
		private int top;
		private int right;
		private int bottom;

		public void setLeft(int left) {
			this.left = left;
		}

		public void setTop(int top) {
			this.top = top;
		}

		public void setRight(int right) {
			this.right = right;
		}

		public void setBottom(int bottom) {
			this.bottom = bottom;
		}

		public int getLeft() {
			return left;
		}

		public int getTop() {
			return top;
		}

		public int getRight() {
			return right;
		}

		public int getBottom() {
			return bottom;
		}

		@Override
		public String toString() {
			return "RectEntity [left=" + left + ", top=" + top + ", right="
					+ right + ", bottom=" + bottom + "]";
		}

	}

	public static class AttributesEntity {
		private int age;
		private int gender;
		private int attractive;
		private int eyeglass;
		private int sunglass;
		private int smile;

		public void setAge(int age) {
			this.age = age;
		}

		public void setGender(int gender) {
			this.gender = gender;
		}

		public void setAttractive(int attractive) {
			this.attractive = attractive;
		}

		public void setEyeglass(int eyeglass) {
			this.eyeglass = eyeglass;
		}

		public void setSunglass(int sunglass) {
			this.sunglass = sunglass;
		}

		public void setSmile(int smile) {
			this.smile = smile;
		}

		public int getAge() {
			return age;
		}

		public int getGender() {
			return gender;
		}

		public int getAttractive() {
			return attractive;
		}

		public int getEyeglass() {
			return eyeglass;
		}

		public int getSunglass() {
			return sunglass;
		}

		public int getSmile() {
			return smile;
		}

		@Override
		public String toString() {
			return "AttributesEntity [age=" + age + ", gender=" + gender
					+ ", attractive=" + attractive + ", eyeglass=" + eyeglass
					+ ", sunglass=" + sunglass + ", smile=" + smile + "]";
		}

	}

	public static class EmotionsEntity {
		private int angry;
		private int calm;
		private int disgust;
		private int happy;
		private int sad;
		private int surprised;

		public void setAngry(int angry) {
			this.angry = angry;
		}

		public void setCalm(int calm) {
			this.calm = calm;
		}

		public void setDisgust(int disgust) {
			this.disgust = disgust;
		}

		public void setHappy(int happy) {
			this.happy = happy;
		}

		public void setSad(int sad) {
			this.sad = sad;
		}

		public void setSurprised(int surprised) {
			this.surprised = surprised;
		}

		public int getAngry() {
			return angry;
		}

		public int getCalm() {
			return calm;
		}

		public int getDisgust() {
			return disgust;
		}

		public int getHappy() {
			return happy;
		}

		public int getSad() {
			return sad;
		}

		public int getSurprised() {
			return surprised;
		}

		@Override
		public String toString() {
			return "EmotionsEntity [angry=" + angry + ", calm=" + calm
					+ ", disgust=" + disgust + ", happy=" + happy + ", sad="
					+ sad + ", surprised=" + surprised + "]";
		}
		
	}

	@Override
	public String toString() {
		return "FacesEntity [face_id=" + face_id + ", rect=" + rect
				+ ", eye_dist=" + eye_dist + ", attributes=" + attributes
				+ ", emotions=" + emotions + ", landmarks21=" + landmarks21
				+ ", landmarks106=" + landmarks106 +"]";
	}

}
