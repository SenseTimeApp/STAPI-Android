package com.sensetime.stapi.bean;

import java.util.List;

public class STImages {

	private String image_id;
	private int exif_orientation;
	private String rotate;
	private int width;
	private int height;
	private List<STFaces> faces;
	
	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}

	public int getExif_orientation() {
		return exif_orientation;
	}

	public void setExif_orientation(int exif_orientation) {
		this.exif_orientation = exif_orientation;
	}

	public String getRotate() {
		return rotate;
	}

	public void setRotate(String rotate) {
		this.rotate = rotate;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<STFaces> getFaces() {
		return faces;
	}

	public void setFaces(List<STFaces> faces) {
		this.faces = faces;
	}

	@Override
	public String toString() {
		return "Images [image_id=" + image_id + ", exif_orientation="
				+ exif_orientation + ", rotate=" + rotate + ", width=" + width
				+ ", height=" + height + ", faces=" + faces + "]";
	}
}
