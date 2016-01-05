package com.sensetime.stapi.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

import com.sensetime.stapi.error.STAPIException;

/**
 * STAPI网络请求的所有API接口类
 *
 */
public class STAPI {
	
	private static final String TAG = "STAPI";
	private static final boolean DEBUG = true;
	
	private static final String WEBSITE_CN = "https://v1-api.visioncloudapi.com/";
	 
	static final private int BUFFERSIZE = 1048576;
	static final private int TIMEOUT = 30000;
	
	private String webSite;
	private String apiId, apiSecret;
	private STAPIParameters4Post params;
	private STAPIParameters4Get paramsGet;
	private int httpTimeOut = TIMEOUT;
	
	/**
	 *  客户端初始化函数 （建议在程序入口出调用，或者在调用其他STAPI之前调用）
	 *
	 *  @param apiid     SenseTime 的API ID
	 *  @param apisecret SenseTime 的API SECRET
	 */
	public STAPI(String apiId, String apiSecret) {
		super();
		this.apiId = apiId;
		this.apiSecret = apiSecret;
		this.webSite = WEBSITE_CN;
	}

	public STAPI() {
		super();
	}
	
	// =======================信息获取===============================
	/**
	 *  获得当前账户的使用信息，包括频率限制以及各种数量限制，建议在程序入口调用，一方面测试调用方法是否正确，一方面也可以验证自己的api_id 和 api_secret
	 * @return 当前账户的使用信息
	 * @throws STAPIException
	 */
	public JSONObject infoApi() throws STAPIException{
		return requestGet("info", "api");
	}
	
	/**
	 *  该 API 用于查询异步调用时的 HTTP 请求处理情况，请参考文档使用
	 *  @param taskId    必须，有效期为 24 小时。异步调用时获得的任务 ID
	 *
	 *  @return 异步调用时的 HTTP 请求处理情况
	 *  @throws STAPIException
	 */
	public JSONObject infoTask(String taskId) throws STAPIException{
		STAPIParameters4Get params = new STAPIParameters4Get();
		params.setTaskId(taskId);
		return requestGet("info", "task", params);
	}
	
	/**
	 *  可以获得图片的相关信息
	 *  @param imageId   必须，图片 ID
	 *  @return 
	 */
	public JSONObject infoImage(String imageId) throws STAPIException{
		STAPIParameters4Get params = new STAPIParameters4Get();
		params.setImageId(imageId);
		return requestGet("info", "image", params);
	}
	
	/**
	 * 获得图片的相关信息
	 * @param imageId	必须，图片 ID
	 * @param withFaces	非必须 是否返回人脸所属图片信息，为 true 时返回。默认为false，不返回
	 * @return
	 * @throws STAPIException
	 */
	public JSONObject infoImage(String imageId, Boolean withFaces) throws STAPIException{
		STAPIParameters4Get params = new STAPIParameters4Get();
		params.setImageId(imageId);
		if(withFaces!=null){
			params.setWithFaces(withFaces);
		}
		return requestGet("info", "image", params);
	}
	
	/**
	 * 获取检测过的人脸的相关信息
	 * @param faceId	必须，人脸 ID
	 * @return
	 * @throws STAPIException
	 */
	public JSONObject infoFace(String faceId) throws STAPIException{
		STAPIParameters4Get params = new STAPIParameters4Get();
		params.setFaceId(faceId);
		return requestGet("info", "face", params);
	}
	
	/**
	 * 获取检测过的人脸的相关信息
	 * @param faceId	必须，人脸 ID
	 * @param withFaces	非必须,是否返回人脸所属图片信息，为 true 时返回。默认为 false，不返回
	 * @return	
	 * @throws STAPIException
	 */
	public JSONObject infoFace(String faceId, Boolean withImage) throws STAPIException{
		STAPIParameters4Get params = new STAPIParameters4Get();
		params.setFaceId(faceId);
		if(withImage!=null){
			params.setWithImage(withImage);
		}
		return requestGet("info", "face", params);
	}
	
	/**
	 * 查询该账户目前所拥有的人的信息
	 * @return
	 * @throws STAPIException
	 */
	public JSONObject infoListPersons() throws STAPIException{
		return requestGet("info", "list_persons");
	}
	
	/**
	 * 查询该账户所拥有的组
	 * @return
	 * @throws STAPIException
	 */
	public JSONObject infoListGroups() throws STAPIException{
		return requestGet("info", "list_groups");
	}
	
	/**
	 * 查询该账户所拥有的人脸集合
	 * @return
	 * @throws STAPIException
	 */
	public JSONObject infoListFacesets() throws STAPIException{
		return requestGet("info", "list_facesets");
	}
	
	/**
	 * 获取人的信息及其含有的人脸 ID
	 * @param personId	必须,人的 ID
	 * @return
	 * @throws STAPIException
	 */
	public JSONObject infoPerson(String personId) throws STAPIException{
		STAPIParameters4Get params = new STAPIParameters4Get();
		params.setPersonId(personId);
		return requestGet("info", "person",params);
	}
	
	/**
	 *  查询组的相关信息，包括组的名字、组的自定义数据以及组所拥有的人的信息
	 * @param groupId	必须,组的ID
	 * @return
	 * @throws STAPIException
	 */
	public JSONObject infoGroup(String groupId) throws STAPIException{
		STAPIParameters4Get params = new STAPIParameters4Get();
		params.setGroupId(groupId);
		return requestGet("info", "group",params);
	}
	
	/**
	 * 获取人脸集合的信息及其含有的人脸的 ID
	 * @param facesetId	必须，人脸集合的 ID
	 * @return
	 * @throws STAPIException
	 */
	public JSONObject infoFaceset(String facesetId) throws STAPIException{
		STAPIParameters4Get params = new STAPIParameters4Get();
		params.setFacesetId(facesetId);
		return requestGet("info", "faceset",params);
	}
	
	// =======================人脸检测与分析===============================
	/**
	 *  提供图片，进行人脸检测以及人脸分析
	 *
	 *  @param imageBitmap        必须，格式必须为 JPG（JPEG），BMP，PNG，GIF，TIFF 之一,
	                             宽和高必须大于 8px，小于等于 4000px
	                             文件尺寸小于等于 5 MB
	                             上传本地图片需上传的图片文件
	 *   注意：对于其他可选参数（如isAutoRotate，withLandmarks106和 withAttributes），如果没有需求，请不要开启，这样会减少系统计算时间
	 *  @param withLandmarks106	 非必须，值为 true 时，计算 106 个关键点
	 *  @param withAttributes   非必须，值为 true 时，提取人脸属性
	 *  @param isAutoRotate  非必须，值为true时，对图片进行自动旋转
	 *  @param userData    非必须，用户自定义信息
	 *  @return 
	 *  @throws STAPIException
	 */
	public JSONObject faceDetection(Bitmap imageBitmap,Boolean withLandmarks106,Boolean withAttributes,Boolean isAutoRotate,String userData) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFile(imageBitmap);
		if(withLandmarks106!=null){
			params.setLandmarks106(withLandmarks106);
		}
		if(withAttributes!=null){
			params.setAttributes(withAttributes);
		}
		if(isAutoRotate!=null){
			params.setAutoRotate(isAutoRotate);
		}
		if(!isNull(userData)){
			params.setUserData(userData);
		}
		return requestPost("face", "detection", params);
	}
	
	public JSONObject faceDetection(STAPIParameters4Post params) throws STAPIException{
		return requestPost("face", "detection", params);
	}
	
	public JSONObject faceDetection(Bitmap imageBitmap) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFile(imageBitmap);
		return requestPost("face", "detection", params);
	}
	
	public JSONObject faceDetection(File imageFile) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFile(imageFile);
		return requestPost("face", "detection", params);
	}
	
	public JSONObject faceDetection(byte[] imageBytes) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFile(imageBytes);
		return requestPost("face", "detection", params);
	}
	
	public JSONObject faceDetection(String url) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setUrl(url);
		return requestPost("face", "detection", params);
	}
	
	public JSONObject faceDetection(Bitmap imageBitmap,STAPIParameters4Post params) throws STAPIException{
		if (params == null) params = new STAPIParameters4Post();
		params.setFile(imageBitmap);
		return requestPost("face", "detection", params);
	}
	
	public JSONObject faceDetection(File imageFile,STAPIParameters4Post params) throws STAPIException{
		if (params == null) params = new STAPIParameters4Post();
		params.setFile(imageFile);
		return requestPost("face", "detection", params);
	}
	
	public JSONObject faceDetection(byte[] imageBytes,STAPIParameters4Post params) throws STAPIException{
		if (params == null) params = new STAPIParameters4Post();
		params.setFile(imageBytes);
		return requestPost("face", "detection", params);
	}
	
	public JSONObject faceDetection(String imageUrl,STAPIParameters4Post params) throws STAPIException{
		if (params == null) params = new STAPIParameters4Post();
		params.setUrl(imageUrl);
		return requestPost("face", "detection", params);
	}
	
	/**
	 *  一对一人脸对比的功能
	 *
	 *  @param faceId  人脸 ID
	 *  @param face2Id  人脸 2 的 ID，脸与脸对比返回该字段
	 *
	 *  @return 对比的分数 大于于 0.6 时判断为同一人,
	 */
	public JSONObject faceVerificationByFace(String faceId,String face2Id) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFaceId(faceId);
		params.setFace2Id(face2Id);
		return requestPost("face", "verification", params);
	}
	
	/**
	 *  主要功能是将一个人脸与另外一个人进行对比
	 *
	 *  @param faceId  人脸 ID
	 *  @param personId 人的 ID，脸与人对比返回该字段
	 *
	 *  @return 对比的分数 大于于 0.6 时判断为同一人
	 */
	public JSONObject faceVerificationByPerson(String faceId,String personId) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFaceId(faceId);
		params.setPersonId(personId);
		return requestPost("face", "verification", params);
	}
	
	/**
	 *  在众多人脸中搜索出与目标人脸最为相似的一张或者多张人脸
	 *
	 *  @param faceId    人脸 ID
	 *  @param faceIds 人脸 ID 数组
	 *  @param top     非必须,最多返回 top 个相似人脸，值为 1~10，默认为 3
	 *
	 *  @return 相似的人脸字典
	 */
	public JSONObject faceSearch(String faceId,String[] faceIds,int top) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFaceId(faceId);
		params.setFaceIds(faceIds);
		if(top>=1 && top<=10){
			params.setTop(top);
		}
		return requestPost("face", "search", params);
	}
	
	/**
	 *  在众多人脸中搜索出与目标人脸最为相似的一张或者多张人脸
	 *
	 *  @param faceId    人脸 ID
	 *  @param facesetId 人脸 集合的ID
	 *  @param top     非必须,最多返回 top 个相似人脸，值为 1~10，默认为 3
	 *
	 *  @return 相似的人脸字典
	 */
	public JSONObject faceSearch(String faceId,String facesetId,int top) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFaceId(faceId);
		params.setFacesetId(facesetId);
		if(top>=1 && top<=10){
			params.setTop(top);
		}
		return requestPost("face", "search", params);
	}
	
	public JSONObject faceSearch(STAPIParameters4Post params) throws STAPIException{
		return requestPost("face", "search", params);
	}
	
	public JSONObject faceSearch(String faceId,String[] faceIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFaceId(faceId);
		params.setFaceIds(faceIds);
		return requestPost("face", "search", params);
	}
	
	public JSONObject faceSearch(String faceId,String facesetId) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFaceId(faceId);
		params.setFacesetId(facesetId);
		return requestPost("face", "search", params);
	}
	
	public JSONObject faceSearch(String faceId,String[] faceIds,STAPIParameters4Post params) throws STAPIException{
		if (params == null) params = new STAPIParameters4Post();
		params.setFaceId(faceId);
		params.setFaceIds(faceIds);
		return requestPost("face", "search", params);
	}
	
	public JSONObject faceSearch(String faceId,String facesetId,STAPIParameters4Post params) throws STAPIException{
		if (params == null) params = new STAPIParameters4Post();
		params.setFaceId(faceId);
		params.setFacesetId(facesetId);
		return requestPost("face", "search", params);
	}
	
	public JSONObject faceIdentification(STAPIParameters4Post params) throws STAPIException{
		return requestPost("face", "identification", params);
	}
	
	public JSONObject faceIdentification(String faceId,String groupId) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFaceId(faceId);
		params.setGroupId(groupId);
		return requestPost("face", "identification", params);
	}
	
	/**
	 *  将一个目标人脸与某个组中的所有人进行对比
	 *
	 *  @param faceId  人脸 ID
	 *  @param groupId 组的 ID
	 *  @param top     非必须，最多返回 top 个人，值为 1~5，默认为 2
	 *
	 *  @return 当搜索出的人与目标人脸的置信度大于 0.6 时 candidates 中才会有结果返回。
	 */
	public JSONObject faceIdentification(String faceId,String groupId,Integer top) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFaceId(faceId);
		params.setGroupId(groupId);
		if(top>=1 && top<=5){
			params.setTop(top);
		}
		return requestPost("face", "identification", params);
	}
	
	public JSONObject faceIdentification(String faceId,String groupId,STAPIParameters4Post params) throws STAPIException{
		if (params == null) params = new STAPIParameters4Post();
		params.setFaceId(faceId);
		params.setGroupId(groupId);
		return requestPost("face", "identification", params);
	}
	
	public JSONObject faceTraining(STAPIParameters4Post params) throws STAPIException{
		return requestPost("face", "training", params);
	}
	
	/**
	 *  对人脸、人、人脸集合、组进行训练
	 *  请求参数 face_id、person_id、faceset_id、group_id 至少存在一个，可多选
	 *  @param faceIds    人脸 ID 数组
	 *  @param personIds  人的 ID 数组
	 *  @param facesetIds 人脸集合 ID 数组
	 *  @param groupIds   组的 ID 数组
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject faceTraining(String[] faceIds,String[] personIds,String[] facesetIds,String[] groupIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		if(faceIds!=null && faceIds.length>0){
			params.setFaceId(faceIds);
		}
		if(personIds!=null && personIds.length>0){
			params.setPersonId(personIds);
		}
		if(facesetIds!=null && facesetIds.length>0){
			params.setFacesetId(facesetIds);
		}
		if(groupIds!=null && groupIds.length>0){
			params.setGroupId(groupIds);
		}
		return requestPost("face", "training", params);
	}
	
	public JSONObject faceTrainingByFaceIds(String[] faceIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFaceId(faceIds);
		return requestPost("face", "training", params);
	}
	
	public JSONObject faceTrainingByPersonIds(String[] personIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setPersonId(personIds);
		return requestPost("face", "training", params);
	}
	
	public JSONObject faceTrainingByFacesetIds(String[] facesetIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFacesetId(facesetIds);
		return requestPost("face", "training", params);
	}
	
	public JSONObject faceTrainingByGroupIds(String[] groupIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setGroupId(groupIds);
		return requestPost("face", "training", params);
	}
	
	// =======================人的管理===============================
	public JSONObject personCreate(String name) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setName(name);
		return requestPost("person", "create",params);
	}
	
	/**
	 *  创建一个人。创建人时，可以同时为其添加人脸信息，也可以加上自定义信息
	 *
	 *  @param name   必须，人名
	 *  @param faceIds 非必须，人脸的ID数组。注意：一个 face_id 只能添加到一个 person 中
	 *  @param userdata 非必须，用户自定义信息
	 *
	 *  @return JSON类型的Person对象
	 */
	public JSONObject personCreate(String name,String[] faceIds,String userData) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setName(name);
		if(faceIds!=null && faceIds.length>0){
			params.setFaceId(faceIds);
		}
		if(!isNull(userData)){
			params.setUserData(userData);
		}
		return requestPost("person", "create",params);
	}
	
	public JSONObject personCreate(String name,STAPIParameters4Post params) throws STAPIException{
		if (params == null) params = new STAPIParameters4Post();
		params.setName(name);
		return requestPost("person", "create",params);
	}
	
	/**
	 *  是删除一个人,删除人后，该人所拥有的人脸 ID 若未过期，则人脸 ID 不会失效。
	 *
	 *  @param personId ，要删除的人的 ID
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject personDelete(String personId) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setPersonId(personId);
		return requestPost("person", "delete",params);
	}
	
	/**
	 *  人创建成功后，调用本 API，可以为该人添加人脸信息。
	 *
	 *  @param personId ，人的 ID
	 *
	 *  @param faceIds ，人脸的 ID 数组
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject personAddFace(String personId,String[] faceIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setPersonId(personId);
		params.setFaceId(faceIds);
		return requestPost("person", "add_face",params);
	}
	
	/**
	 *  作用是移除人脸，使其不再属于这个人
	 *
	 *  @param personId ，要移除的人的 ID
	 *
	 *  @param faceIds ，人脸的 ID 数组
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject personRemoveFace(String personId,String[] faceIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setPersonId(personId);
		params.setFaceId(faceIds);
		return requestPost("person", "remove_face",params);
	}
	
	/**
	 *  用于修改 person 的 user_data 和 name 信息
	 *
	 *  @param personId   人的ID
	 *  @param name     人名
	 *  @param userData 用户自定义数据
	 *   请求参数 name、userData 两者中至少存在一个。
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject personChange(String personId,String name,String userData) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setPersonId(personId);
		if(!isNull(name)){
			params.setName(name);
		}
		if(!isNull(userData)){
			params.setUserData(userData);
		}
		return requestPost("person", "change",params);
	}
	
	// =======================组的管理===============================
	/**
	 *  用于创建一个组。创建组的同时可以向该组中添加人。
	 *
	 *  @param name     组名
	 *  @param personIds 非必须
	 *  @param userdata 非必须用户自定义信息
	 *
	 *  @return JSON类型的Group对象
	 */
	public JSONObject groupCreate(String name,String[] personIds,String userData) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setName(name);
		if(personIds!=null && personIds.length > 0){
			params.setPersonId(personIds);
		}
		if(!isNull(userData)){
			params.setUserData(userData);
		}
		return requestPost("group", "create",params);
	}
	
	/**
	 *  删除一个组
	 *
	 *  @param groupid STGroup对象中的strGroupID
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject groupDelete(String groupId) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setGroupId(groupId);
		return requestPost("group", "delete",params);
	}
	
	/**
	 *  组创建成功后，调用本 API，可以为组添加人(一个人可以属于多个组)。
	 *
	 *  @param groupId STGroup对象中的strGroupID
	 *  @param personIds （如果其中有一个或多个 person_id 无效时，那么其他的 person_id 也会添加失败）
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject groupAddPerson(String groupId,String[] personIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setGroupId(groupId);
		params.setPersonId(personIds);
		return requestPost("group", "add_person",params);
	}
	
	/**
	 *  本 API 的作用是移除组中的人。
	 *
	 *  @param groupid 
	 *  @param personIds 
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject groupRemovePerson(String groupId,String[] personIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setGroupId(groupId);
		params.setPersonId(personIds);
		return requestPost("group", "remove_person",params);
	}
	
	/**
	 *  用于修改 group 的 user_data 和 name 信息
	 *
	 *  @param groupId 
	 *  @param name     组名
	 *  @param userdata 用户自定义数据
	 *  请求参数 name、user_data 两者中至少存在一个
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject groupChange(String groupId,String name,String userData) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setGroupId(groupId);
		if(!isNull(name)){
			params.setName(name);
		}
		if(!isNull(userData)){
			params.setUserData(userData);
		}
		return requestPost("group", "change",params);
	}
	
	// =======================人脸集合的管理===============================
	/**
	 *  是创建一个人脸集合
	 *
	 *  @param name     人脸集合名
	 *  @param faceIds   非必须，人脸的 ID
	 *  @param userdata 非必须，用户自定义信息
	 *
	 *  @return JSON类型的faceset对象
	 */
	public JSONObject facesetCreate(String name,String[] faceIds,String userData) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setName(name);
		if(faceIds!=null && faceIds.length > 0){
			params.setFaceId(faceIds);
		}
		if(!isNull(userData)){
			params.setUserData(userData);
		}
		return requestPost("faceset", "create",params);
	}
	
	/**
	 *  是删除一个人脸集合
	 *
	 *  @param facesetId 要删除的人脸集合的 ID
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject facesetDelete(String facesetId) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFacesetId(facesetId);
		return requestPost("faceset", "delete",params);
	}
	
	/**
	 *  可以向人脸集合中添加人脸
	 *
	 *  @param facesetId 人脸集合的ID
	 *  @param faceIds    人脸的 ID数组
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject facesetAddFace(String facesetId,String[] faceIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFacesetId(facesetId);
		params.setFaceId(faceIds);
		return requestPost("faceset", "add_face",params);
	}
	
	/**
	 *  移除人脸集合所含有的人脸，使其不再属于该人脸集合
	 *
	 *  @param facesetids 人脸集合的ID
	 *  @param faceids    人脸的 ID 数组
	 *
	 *  @return status OK成功，否则失败
	 */
	public JSONObject facesetRemoveFace(String facesetId,String[] faceIds) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFacesetId(facesetId);
		params.setFaceId(faceIds);
		return requestPost("faceset", "remove_face",params);
	}
	
	/**
	 *  用于修改人脸集合的 user_data 和 name 信息。
	 *
	 *  @param facesetId 人脸集合的ID
	 *  @param name      人脸集合名
	 *  @param userData  用户自定义数据
	 *  请求参数name 与 user_data 二者中至少存在一个
	 *  @return status OK成功，否则失败
	 */
	public JSONObject facesetChange(String facesetId,String name,String userData) throws STAPIException{
		STAPIParameters4Post params = new STAPIParameters4Post();
		params.setFacesetId(facesetId);
		if(!isNull(name)){
			params.setName(name);
		}
		if(!isNull(userData)){
			params.setUserData(userData);
		}
		return requestPost("faceset", "change",params);
	}
	
	public void setHttpTimeOut(int timeOut) {
		this.httpTimeOut = timeOut;
	}
	
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}
	
	public JSONObject request(String control, String action) throws STAPIException {
		return requestPost(control, action, getParams());
	}
	
	public STAPIParameters4Post getParams() {
		if (params == null) params = new STAPIParameters4Post();
		return params;
	}
	
	public void setParams(STAPIParameters4Post params) {
		this.params = params;
	}
	
	private JSONObject requestPost(String control, String action, STAPIParameters4Post params) throws STAPIException {
		URL url;
		HttpsURLConnection urlConn = null;
		int responseCode = -1;
//		STAPIException lfapiException = null;
		try {
			if(DEBUG){
	            Log.d(TAG, "post url:"+webSite+control+"/"+action);
	        }
			url = new URL(webSite+control+"/"+action);
			urlConn = (HttpsURLConnection) url.openConnection();
	        urlConn.setRequestMethod("POST");
	        urlConn.setConnectTimeout(httpTimeOut);
	        urlConn.setReadTimeout(httpTimeOut);
	        urlConn.setDoOutput(true);
	        
	        urlConn.setRequestProperty("connection", "keep-alive");
	        urlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + params.boundaryString());
			
	        MultipartEntity reqEntity = params.getMultiPart();
            
            reqEntity.addPart("api_id", new StringBody(apiId));
            reqEntity.addPart("api_secret", new StringBody(apiSecret));
            reqEntity.writeTo(urlConn.getOutputStream());
            
            String resultString = null;
            InputStream inputStream = null;
            responseCode = urlConn.getResponseCode();
            if(DEBUG){
            	Log.d(TAG, "request getResponseCode:"+responseCode);
            }
            if (HttpsURLConnection.HTTP_OK == responseCode)
            	inputStream = urlConn.getInputStream();
            else {
            	inputStream = urlConn.getErrorStream();
            }
            resultString = readString(inputStream);
            if(DEBUG){
            	Log.d(TAG, "request resultString:"+resultString);
            }
            JSONObject result = new JSONObject(resultString);
//		    if(result.has(JsonStatus.STATUS_KEY)){
//		    	String status = result.get(JsonStatus.STATUS_KEY).toString();
//	            if(!JsonStatus.STATUS_OK.equalsIgnoreCase(status)){
//	            	String reason = null;
//	            	if(result.has(JsonStatus.REASON_KEY)){
//	            		reason = result.get(JsonStatus.REASON_KEY).toString();
//	            	}
//	            	lfapiException = new STAPIException(
//	            			responseCode,status,reason);
//	            	throw lfapiException;
//	            }
//	        }else{
//	        	lfapiException = new STAPIException(responseCode,JsonStatus.NO_RESPONSE,null);
//	            throw lfapiException;
//	        }
		    if(inputStream!=null){
		    	inputStream.close();
		    }
            return result;
		} catch (Exception e) {
			throw new STAPIException(responseCode,e.toString());
		} finally {
			if (urlConn != null)
				urlConn.disconnect();
		}
	}
	
	public JSONObject requestGet(String control, String action) throws STAPIException {
		return requestGet(control, action, getParamsGet());
	}
	
	public STAPIParameters4Get getParamsGet() {
		if (paramsGet == null) paramsGet = new STAPIParameters4Get();
		return paramsGet;
	}
	
	private JSONObject requestGet(String control, String action, STAPIParameters4Get params) throws STAPIException {
		URL url;
		HttpsURLConnection urlConn = null;
		int responseCode = -1;
		try {
			if(DEBUG){
	            Log.d(TAG, "get url:"+webSite+control+"/"+action+"?api_id="+apiId+"&api_secret="+apiSecret+params.getParamsMap()+"&date="+System.currentTimeMillis());
	        }
			url = new URL(webSite+control+"/"+action+"?api_id="+apiId+"&api_secret="+apiSecret+params.getParamsMap()+"&date="+System.currentTimeMillis());
			urlConn = (HttpsURLConnection) url.openConnection();
	        urlConn.setRequestMethod("GET");
	        urlConn.setConnectTimeout(httpTimeOut);
	        urlConn.setReadTimeout(httpTimeOut);
	        urlConn.setRequestProperty("connection", "keep-alive");
            String resultString = null;
            InputStream inputStream = null;
            responseCode = urlConn.getResponseCode();
            if(DEBUG){
            	Log.d(TAG, "request getResponseCode:"+responseCode);
            }
            if (HttpsURLConnection.HTTP_OK == responseCode)
            	inputStream = urlConn.getInputStream();
            else {
            	inputStream = urlConn.getErrorStream();
            }
            resultString = readString(inputStream);
            if(DEBUG){
            	Log.d(TAG, "request resultString:"+resultString);
            }
            JSONObject result = new JSONObject(resultString);
//		    if(result.has(JsonStatus.STATUS_KEY)){
//		    	String status = result.get(JsonStatus.STATUS_KEY).toString();
//	            if(!JsonStatus.STATUS_OK.equalsIgnoreCase(status)){
//	            	String reason = null;
//	            	if(result.has(JsonStatus.REASON_KEY)){
//	            		reason = result.get(JsonStatus.REASON_KEY).toString();
//	            	}
//	            	lfapiException = new STAPIException(
//	            			responseCode,status,reason);
//	            	throw lfapiException;
//	            }
//	        }else{
//	        	lfapiException = new STAPIException(responseCode,JsonStatus.NO_RESPONSE,null);
//	            throw lfapiException;
//	        }
		    if(inputStream!=null){
		    	inputStream.close();
		    }
            return result;
		} catch (Exception e) {
			throw new STAPIException(responseCode,e.toString());
		} finally {
			if (urlConn != null)
				urlConn.disconnect();
		}
	}
	
	private JSONObject requestGetMap(String control, String action, Map<String,Object> paramsMap) throws STAPIException {
		URL url;
		HttpsURLConnection urlConn = null;
		int responseCode = -1;
		try {
			StringBuffer params = new StringBuffer();
			if(paramsMap!=null){
				String flag = "0";
				for(Map.Entry entry:paramsMap.entrySet()){
					if("true".equals(entry.getValue().toString())){
						flag = "1";
					}else if("false".equals(entry.getValue().toString())){
						flag = "0";
					}else{
						flag = entry.getValue().toString();
					}
					params.append("&"+entry.getKey()+"="+flag);
				}
			}
			url = new URL(webSite+control+"/"+action+"?api_id="+apiId+"&api_secret="+apiSecret+params.toString());
			urlConn = (HttpsURLConnection) url.openConnection();
	        urlConn.setRequestMethod("GET");
	        urlConn.setConnectTimeout(httpTimeOut);
	        urlConn.setReadTimeout(httpTimeOut);
	        urlConn.setRequestProperty("connection", "keep-alive");
	        String resultString = null;
            InputStream inputStream = null;
            responseCode = urlConn.getResponseCode();
            if(DEBUG){
            	Log.d(TAG, "request getResponseCode:"+responseCode);
            }
            if (HttpsURLConnection.HTTP_OK == responseCode)
            	inputStream = urlConn.getInputStream();
            else {
            	inputStream = urlConn.getErrorStream();
            }
            resultString = readString(inputStream);
            if(DEBUG){
            	Log.d(TAG, "request resultString:"+resultString);
            }
            JSONObject result = new JSONObject(resultString);
//		    if(result.has(JsonStatus.STATUS_KEY)){
//		    	String status = result.get(JsonStatus.STATUS_KEY).toString();
//	            if(!JsonStatus.STATUS_OK.equalsIgnoreCase(status)){
//	            	String reason = null;
//	            	if(result.has(JsonStatus.REASON_KEY)){
//	            		reason = result.get(JsonStatus.REASON_KEY).toString();
//	            	}
//	            	lfapiException = new STAPIException(
//	            			responseCode,status,reason);
//	            	throw lfapiException;
//	            }
//	        }else{
//	        	lfapiException = new STAPIException(responseCode,JsonStatus.NO_RESPONSE,null);
//	            throw lfapiException;
//	        }
		    if(inputStream!=null){
		    	inputStream.close();
		    }
            return result;
		} catch (Exception e) {
			throw new STAPIException(responseCode,e.toString());
		} finally {
			if (urlConn != null)
				urlConn.disconnect();
		}
	}
	
	private static String readString(InputStream is) {
		if(null == is){
			return null;
		}
		StringBuffer rst = new StringBuffer();
		byte[] buffer = new byte[BUFFERSIZE];
		int len = 0;
		try {
			while ((len = is.read(buffer)) > 0)
				for (int i = 0; i < len; ++i)
					rst.append((char)buffer[i]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst.toString();
	}
	
	private boolean isNull(String s){
		return (null == s || "" == s);
	}
	
}