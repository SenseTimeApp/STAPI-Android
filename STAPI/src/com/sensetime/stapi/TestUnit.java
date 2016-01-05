package com.sensetime.stapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;
import android.util.Log;

import com.sensetime.stapi.error.STAPIException;
import com.sensetime.stapi.http.STAPI;
import com.sensetime.stapi.http.STAPIParameters4Post;

/**
 * 单元测试
 *
 */
public class TestUnit extends AndroidTestCase {

	#error // 使用时候请填写自己的apiId和apiSecret
	private static final String API_KEY = "";
	private static final String API_SECRET = "";
	private STAPI mSTAPI = new STAPI(API_KEY, API_SECRET);
	
	public void test01InfoApi()  {  
		try {
			for (int i = 0; i < 1; i++) {
				JSONObject jsonObject = mSTAPI.infoApi();
				assertEquals(jsonObject.get("status").toString(), "OK");
			}
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
    }  
	
	public void test18InfoImage()  {  
		try {
			Bitmap bitmapJack = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jack);
			JSONObject jsonObjectJack = mSTAPI.faceDetection(bitmapJack);
			assertEquals(jsonObjectJack.get("status").toString(), "OK");
			String imageId = jsonObjectJack.getString("image_id");
			
			JSONObject jsonObject = mSTAPI.infoImage(imageId, true);
			assertEquals(jsonObject.get("status").toString(), "OK");
			assertNotNull(jsonObject.get("image_id"));
			assertEquals(bitmapJack.getWidth(),jsonObject.getInt("width"));
			assertEquals(bitmapJack.getHeight(),jsonObject.getInt("height"));
			assertNotNull(jsonObjectJack.get("faces"));
			JSONArray arrayJack = (JSONArray)jsonObjectJack.get("faces");
			assertTrue(1==arrayJack.length());
			JSONObject faceObjectJack = (JSONObject)arrayJack.get(0);
			JSONArray arrayLandmarks21 = (JSONArray)faceObjectJack.get("landmarks21");
			assertTrue(21==arrayLandmarks21.length());
			
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
    }
	
	public void test19InfoFace()  {  
		try {
			Bitmap bitmapJack = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jack);
			JSONObject jsonObjectJack = mSTAPI.faceDetection(bitmapJack);
			assertEquals(jsonObjectJack.get("status").toString(), "OK");
			String imageId = jsonObjectJack.getString("image_id");
			JSONArray arrayJack = (JSONArray)jsonObjectJack.get("faces");
			JSONObject faceObjectJack = (JSONObject)arrayJack.get(0);
			String faceId = faceObjectJack.getString("face_id");
			
			JSONObject jsonObject = mSTAPI.infoFace(faceId, true);
			assertEquals(jsonObject.get("status").toString(), "OK");
			assertNotNull(jsonObject.get("face_id"));
			assertEquals(faceId, jsonObject.get("face_id"));
			JSONArray arrayLandmarks21 = (JSONArray)jsonObject.get("landmarks21");
			assertTrue(21==arrayLandmarks21.length());
			assertEquals(imageId, jsonObject.getJSONObject("image").getString("image_id"));
			
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
    }
	
	public void test02FaceDetection_Bitmap()  {  
		try {
			JSONObject jsonObjectJack = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jack));
			assertEquals(jsonObjectJack.get("status").toString(), "OK");
			assertNotNull(jsonObjectJack.get("image_id"));
			assertNotNull(jsonObjectJack.get("faces"));
			JSONArray arrayJack = (JSONArray)jsonObjectJack.get("faces");
			assertTrue(1==arrayJack.length());
			JSONObject faceObjectJack = (JSONObject)arrayJack.get(0);
			JSONArray arrayLandmarks21 = (JSONArray)faceObjectJack.get("landmarks21");
			for (int i = 0; i < arrayLandmarks21.length(); i++) {
				JSONArray XY = arrayLandmarks21.getJSONArray(i);
				Log.d("tag", "X->"+XY.getDouble(0)+";Y->"+XY.getDouble(1));
			}
			assertTrue(21==arrayLandmarks21.length());
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
    }
	
	public void test03FaceDetection_Url()  {  
		try {
			STAPIParameters4Post params= new STAPIParameters4Post();
			params.setLandmarks106(true);
			params.setAttributes(true);
			params.setUserData("testFaceDetection_Url user data");
			JSONObject jsonObject = mSTAPI.faceDetection("http://p4.so.qhimg.com/t013cd0a26cd18948ae.jpg",params);
//			JSONObject jsonObject = mSTAPI.faceDetection("http://img1.3lian.com/2015/w21/2/d/61.jpg",params);
			assertEquals(jsonObject.get("status").toString(), "OK");
			assertNotNull(jsonObject.get("image_id"));
			assertNotNull(jsonObject.get("faces"));
			JSONArray array = (JSONArray)jsonObject.get("faces");
			assertTrue(1==array.length());
			JSONObject faceObject = (JSONObject)array.get(0);
			JSONArray arrayLandmarks21 = (JSONArray)faceObject.get("landmarks21");
			assertTrue(21==arrayLandmarks21.length());
			JSONArray arraLandmarks106 = (JSONArray)faceObject.get("landmarks106");
			assertTrue(106==arraLandmarks106.length());
			assertTrue(faceObject.has("attributes"));
			if(faceObject.has("attributes")){
				JSONObject attributes = (JSONObject)faceObject.get("attributes");
				assertTrue(attributes.getInt("gender")<50);
			}else{
				assertTrue(false);
			}
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
    }
	
	public void test04FaceVerification_face2Id()  {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			JSONObject jsonObjectJay2 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay2));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			assertEquals(jsonObjectJay2.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONArray arrayJay2 = (JSONArray)jsonObjectJay2.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			JSONObject faceObjectJay2 = (JSONObject)arrayJay2.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			String jay2FaceId = faceObjectJay2.getString("face_id");
			
//			JSONObject jsonObject = mSTAPI.faceVerificationByFace(jay2FaceId, jackFaceId);
			JSONObject jsonObject = mSTAPI.faceVerificationByFace(jay2FaceId, jay1FaceId);
			assertEquals(jsonObject.get("status").toString(), "OK");
			assertEquals(jay2FaceId, jsonObject.get("face_id").toString());
//			assertEquals(jackFaceId, jsonObject.get("face2_id").toString());
			assertEquals(jay1FaceId, jsonObject.get("face2_id").toString());
			assertTrue(jsonObject.getBoolean("same_person"));
			assertTrue(Float.parseFloat(jsonObject.get("confidence").toString()) > 0.6);
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
    }
	
	public void test20InfoListPersons()  {  
		try {
			JSONObject jsonObject = mSTAPI.infoListPersons();
			assertEquals(jsonObject.get("status").toString(), "OK");
			JSONArray arrayPersons = jsonObject.getJSONArray("persons");
			if(arrayPersons!=null && arrayPersons.length()>0){
				for (int i = 0; i < arrayPersons.length(); i++) {
					JSONObject person = arrayPersons.getJSONObject(i);
					JSONObject jsonObjectPerson = mSTAPI.personDelete(person.getString("person_id"));
					assertEquals(jsonObjectPerson.getString("status"), "OK");
					if(i % 5 == 0){
						Thread.sleep(2000);
					}
				}
			}
			JSONObject jsonObject2 = mSTAPI.infoListPersons();
			assertEquals(jsonObject2.get("status").toString(), "OK");
			arrayPersons = jsonObject2.getJSONArray("persons");
			assertNotNull(arrayPersons);
			assertTrue(arrayPersons.length() == 0);
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			assertTrue(false);
			e.printStackTrace();
		}
    } 
	
	public void test21PersonAddFaceThenRemoveFace()  {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			
			JSONObject jsonObject = mSTAPI.personCreate("PJay",null,"test user data");
			assertEquals(jsonObject.get("status").toString(), "OK");
			assertNotNull(jsonObject.get("person_id"));
			assertTrue(!jsonObject.has("face_count"));
			String personId = jsonObject.getString("person_id");
			
			JSONObject jsonObject2 = mSTAPI.personAddFace(personId, new String[]{jay1FaceId});
			assertEquals(jsonObject2.get("status").toString(), "OK");
			assertEquals(1, jsonObject2.getInt("added_count"));
			
			JSONObject jsonObject3 = mSTAPI.personRemoveFace(personId, new String[]{jay1FaceId});
			assertEquals(jsonObject3.get("status").toString(), "OK");
			assertEquals(1, jsonObject3.getInt("removed_count"));
			
			JSONObject jsonObject4 = mSTAPI.personDelete(personId);
			assertEquals(jsonObject4.get("status").toString(), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			assertTrue(false);
			e.printStackTrace();
		}
    } 
	
	public void test22PersonChangeAndInfoPerson()  {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			
			JSONObject jsonObject = mSTAPI.personCreate("PJay",new String[]{jay1FaceId},"test user data");
			assertEquals(jsonObject.get("status").toString(), "OK");
			String personId = jsonObject.getString("person_id");
			
			JSONObject jsonObject1 = mSTAPI.personChange(personId, "change PJay", "change test user data");
			assertEquals(jsonObject1.get("status").toString(), "OK");
			
			JSONObject jsonObject2 = mSTAPI.infoPerson(personId);
			assertEquals(jsonObject2.get("status").toString(), "OK");
			assertEquals(personId,jsonObject2.getString("person_id"));
			assertEquals("change PJay",jsonObject2.getString("name"));
			assertEquals("change test user data",jsonObject2.getString("user_data"));
			JSONArray arrayFaceIds = jsonObject2.getJSONArray("face_ids");
			assertEquals(arrayFaceIds.getString(0), jay1FaceId);
			
			JSONObject jsonObject3 = mSTAPI.personDelete(personId);
			assertEquals(jsonObject3.get("status").toString(), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			assertTrue(false);
			e.printStackTrace();
		}
    } 
	
	public void test23FacesetAddFaceThenRemoveFace()  {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			
			JSONObject jsonObject = mSTAPI.facesetCreate("facesetName",null,"test user data");
			assertEquals(jsonObject.get("status").toString(), "OK");
			assertNotNull(jsonObject.get("faceset_id"));
			assertTrue(!jsonObject.has("face_count"));
			String facesetId = jsonObject.getString("faceset_id");
			Log.d("tag", "faceset_id："+facesetId);
			JSONObject jsonObject2 = mSTAPI.facesetAddFace(facesetId, new String[]{jay1FaceId});
			Log.d("tag", "faceset_id："+facesetId);
			assertEquals(jsonObject2.get("status").toString(), "OK");
			assertEquals(1, jsonObject2.getInt("added_count"));
			
			JSONObject jsonObject3 = mSTAPI.facesetRemoveFace(facesetId, new String[]{jay1FaceId});
			assertEquals(jsonObject3.get("status").toString(), "OK");
			assertEquals(1, jsonObject3.getInt("removed_count"));
			
			JSONObject jsonObject4 = mSTAPI.facesetDelete(facesetId);
			assertEquals(jsonObject4.get("status").toString(), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			assertTrue(false);
			e.printStackTrace();
		}
    } 
	
	public void test24FacesetChangeAndInfoFaceset()  {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			
			JSONObject jsonObject = mSTAPI.facesetCreate("facesetName",new String[]{jay1FaceId},"test user data");
			assertEquals(jsonObject.get("status").toString(), "OK");
			String facesetId = jsonObject.getString("faceset_id");
			
			JSONObject jsonObject1 = mSTAPI.facesetChange(facesetId, "change facesetName", "change test user data");
			assertEquals(jsonObject1.get("status").toString(), "OK");
			
			JSONObject jsonObject2 = mSTAPI.infoFaceset(facesetId);
			assertEquals(jsonObject2.get("status").toString(), "OK");
			assertEquals(facesetId,jsonObject2.getString("faceset_id"));
			assertEquals("change facesetName",jsonObject2.getString("name"));
			assertEquals("change test user data",jsonObject2.getString("user_data"));
			JSONArray arrayFaceIds = jsonObject2.getJSONArray("face_ids");
			assertEquals(arrayFaceIds.getString(0), jay1FaceId);
			
			JSONObject jsonObject3 = mSTAPI.facesetDelete(facesetId);
			assertEquals(jsonObject3.get("status").toString(), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			assertTrue(false);
			e.printStackTrace();
		}
    } 
	
	public void test25GroupAddPersonThenRemovePerson()  {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			
			JSONObject jsonObject = mSTAPI.personCreate("PJay",new String[]{jay1FaceId},"test user data");
			assertEquals(jsonObject.get("status").toString(), "OK");
			String personId = jsonObject.getString("person_id");
			
			JSONObject jsonObject1 = mSTAPI.groupCreate("groupName",null,"test user data");
			assertEquals(jsonObject1.get("status").toString(), "OK");
			assertNotNull(jsonObject1.get("group_id"));
			assertTrue(!jsonObject1.has("person_count"));
			String groupId = jsonObject1.getString("group_id");
			
			JSONObject jsonObject2 = mSTAPI.groupAddPerson(groupId, new String[]{personId});
			assertEquals(jsonObject2.get("status").toString(), "OK");
			assertEquals(1, jsonObject2.getInt("added_count"));
			
			JSONObject jsonObject3 = mSTAPI.groupRemovePerson(groupId, new String[]{personId});
			assertEquals(jsonObject3.get("status").toString(), "OK");
			assertEquals(1, jsonObject3.getInt("removed_count"));
			
			JSONObject jsonObject4 = mSTAPI.groupDelete(groupId);
			assertEquals(jsonObject4.get("status").toString(), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			assertTrue(false);
			e.printStackTrace();
		}
    } 
	
	public void test05PersonCreateThenDelete()  {  
		try {
//			每跑一次就会把对应的faceId转移
//			一个 face_id 只能添加到一个 person 中。如果将一个 face_id 重复添加给另一个人时， 那么此 face_id 将属于最新添加的这个人，且系统不会返回错误信息。
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			
			JSONObject jsonObject = mSTAPI.personCreate("PJay",new String[]{jay1FaceId},"test user data");
			assertEquals(jsonObject.get("status").toString(), "OK");
			assertNotNull(jsonObject.get("person_id"));
			assertEquals(jsonObject.getInt("face_count"),1); 
			
			JSONObject jsonObject2 = mSTAPI.personDelete(jsonObject.getString("person_id"));
			assertEquals(jsonObject2.get("status").toString(), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			assertTrue(false);
			e.printStackTrace();
		}
    } 
	
	public void test06FaceVerification_personId()  {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			JSONObject jsonObjectJay2 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay2));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			assertEquals(jsonObjectJay2.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONArray arrayJay2 = (JSONArray)jsonObjectJay2.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			JSONObject faceObjectJay2 = (JSONObject)arrayJay2.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			String jay2FaceId = faceObjectJay2.getString("face_id");
			
			JSONObject jsonObject2 = mSTAPI.personCreate("PJay",new String[]{jay1FaceId},"test user data");
			assertEquals(jsonObject2.get("status").toString(), "OK");
			JSONObject jsonObject = mSTAPI.faceVerificationByPerson(jay2FaceId, jsonObject2.getString("person_id"));
			assertEquals(jsonObject.get("status").toString(), "OK");
			assertEquals(jay2FaceId, jsonObject.get("face_id").toString());
			assertEquals(jsonObject2.getString("person_id"), jsonObject.get("person_id").toString());
			assertTrue((Boolean) jsonObject.get("same_person"));
			assertTrue(Float.parseFloat(jsonObject.get("confidence").toString()) > 0.6);
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
    }
	
	public void test07FaceSearch_faceIds() {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			JSONObject jsonObjectJay2 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay2));
			JSONObject jsonObjectJack = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jack));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			assertEquals(jsonObjectJay2.get("status").toString(), "OK");
			assertEquals(jsonObjectJack.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONArray arrayJay2 = (JSONArray)jsonObjectJay2.get("faces");
			JSONArray arrayJack = (JSONArray)jsonObjectJack.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			JSONObject faceObjectJay2 = (JSONObject)arrayJay2.get(0);
			JSONObject faceObjectJack = (JSONObject)arrayJack.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			String jay2FaceId = faceObjectJay2.getString("face_id");
			String jackFaceId = faceObjectJack.getString("face_id");
			
			JSONObject jsonObject = mSTAPI.faceSearch(jay1FaceId, new String[]{jay2FaceId,jackFaceId},1);
			assertEquals(jsonObject.getString("status"), "OK");
			assertEquals(jay1FaceId,jsonObject.getString("face_id"));
			JSONArray arrayCandidates = (JSONArray)jsonObject.get("candidates");
			assertTrue(arrayCandidates.length() == 1);
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
		}	catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		} 
	}
	
	public void test08InfoListFacesets() {  
		try {
			JSONObject jsonObject = mSTAPI.infoListFacesets();
			assertEquals(jsonObject.get("status").toString(), "OK");
			JSONArray arrayFacesets = jsonObject.getJSONArray("facesets");
			if(arrayFacesets!=null && arrayFacesets.length()>0){
				for (int i = 0; i < arrayFacesets.length(); i++) {
					JSONObject faceset = arrayFacesets.getJSONObject(i);
					JSONObject jsonObjectFaceset = mSTAPI.facesetDelete(faceset.getString("faceset_id"));
					assertEquals(jsonObjectFaceset.getString("status"), "OK");
				}
			}
			JSONObject jsonObject2 = mSTAPI.infoListFacesets();
			assertEquals(jsonObject2.get("status").toString(), "OK");
			arrayFacesets = jsonObject2.getJSONArray("facesets");
			assertNotNull(arrayFacesets);
			assertTrue(arrayFacesets.length() == 0);
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
		} catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		} 
	}
	
	public void test09FacesetCreateThenDelete(){
		try {
			JSONObject jsonObjectJay2 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay2));
			JSONObject jsonObjectJack = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jack));
			assertEquals(jsonObjectJay2.get("status").toString(), "OK");
			assertEquals(jsonObjectJack.get("status").toString(), "OK");
			JSONArray arrayJay2 = (JSONArray)jsonObjectJay2.get("faces");
			JSONArray arrayJack = (JSONArray)jsonObjectJack.get("faces");
			JSONObject faceObjectJay2 = (JSONObject)arrayJay2.get(0);
			JSONObject faceObjectJack = (JSONObject)arrayJack.get(0);
			String jay2FaceId = faceObjectJay2.getString("face_id");
			String jackFaceId = faceObjectJack.getString("face_id");
			
			JSONObject jsonObject = mSTAPI.facesetCreate("facesetName", new String[]{jay2FaceId,jackFaceId}, "facesetCreate user data");
			assertEquals(jsonObject.get("status").toString(), "OK");
			assertTrue(jsonObject.has("faceset_id"));
			assertNotNull(jsonObject.get("faceset_id"));
			assertTrue(jsonObject.has("name"));
			assertEquals("facesetName",jsonObject.getString("name"));
			assertEquals(2,jsonObject.getInt("face_count"));
			String facesetId = jsonObject.getString("faceset_id");
			JSONObject jsonObject2 = mSTAPI.facesetDelete(facesetId);
			assertEquals(jsonObject2.getString("status"), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
			assertTrue(false);
		} catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void test10FaceSearch_facesetId() {  
		try {
			Thread.sleep(1000);
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			JSONObject jsonObjectJay2 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay2));
			JSONObject jsonObjectJack = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jack));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			assertEquals(jsonObjectJay2.get("status").toString(), "OK");
			assertEquals(jsonObjectJack.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONArray arrayJay2 = (JSONArray)jsonObjectJay2.get("faces");
			JSONArray arrayJack = (JSONArray)jsonObjectJack.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			JSONObject faceObjectJay2 = (JSONObject)arrayJay2.get(0);
			JSONObject faceObjectJack = (JSONObject)arrayJack.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			String jay2FaceId = faceObjectJay2.getString("face_id");
			String jackFaceId = faceObjectJack.getString("face_id");
			
			JSONObject jsonObject2 = mSTAPI.facesetCreate("facesetName", new String[]{jay2FaceId,jackFaceId}, "facesetCreate user data");
			assertEquals(jsonObject2.get("status").toString(), "OK");
			String facesetId = jsonObject2.getString("faceset_id");
			JSONObject jsonObject = mSTAPI.faceSearch(jay1FaceId, facesetId,1);
			assertEquals(jsonObject.getString("status"), "OK");
			assertEquals(jay1FaceId,jsonObject.getString("face_id"));
			JSONArray arrayCandidates = (JSONArray)jsonObject.get("candidates");
			assertTrue(arrayCandidates.length() == 1);
			assertEquals(jay2FaceId,arrayCandidates.getJSONObject(0).getString("face_id"));
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
		}	catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (InterruptedException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test11InfoListGroups() {  
		try {
			JSONObject jsonObject = mSTAPI.infoListGroups();
			assertEquals(jsonObject.get("status").toString(), "OK");
			JSONArray arrayGroups = jsonObject.getJSONArray("groups");
			if(arrayGroups!=null && arrayGroups.length()>0){
				for (int i = 0; i < arrayGroups.length(); i++) {
					JSONObject group = arrayGroups.getJSONObject(i);
					JSONObject jsonObject2 = mSTAPI.groupDelete(group.getString("group_id"));
					assertEquals(jsonObject2.getString("status"), "OK");
				}
			}
			JSONObject jsonObject2 = mSTAPI.infoListGroups();
			assertEquals(jsonObject2.get("status").toString(), "OK");
			arrayGroups = jsonObject2.getJSONArray("groups");
			assertNotNull(arrayGroups);
			assertTrue(arrayGroups.length() == 0);
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
		}	catch (JSONException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test12GroupCreateThenDelete() {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			
			JSONObject jsonObject2 = mSTAPI.personCreate("PJay",new String[]{jay1FaceId},"test user data");
			assertEquals(jsonObject2.get("status").toString(), "OK");
			JSONObject jsonObject = mSTAPI.groupCreate("groupName", new String[]{jsonObject2.getString("person_id")}, "testGroupCreate user data");
			assertEquals(jsonObject.get("status").toString(), "OK");
			assertTrue(jsonObject.has("group_id"));
			assertNotNull(jsonObject.get("group_id"));
			assertTrue(jsonObject.has("name"));
			assertEquals("groupName",jsonObject.getString("name"));
			assertEquals(1,jsonObject.getInt("person_count"));
			String groupId = jsonObject.getString("group_id");
			JSONObject jsonObject3 = mSTAPI.groupDelete(groupId);
			assertEquals(jsonObject3.getString("status"), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
		}	catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void test13FaceIdentification() {  
		try {
			Thread.sleep(1000);
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			
			JSONObject jsonObject2 = mSTAPI.personCreate("PJay",new String[]{jay1FaceId},"test user data");
			assertEquals(jsonObject2.get("status").toString(), "OK");
			JSONObject jsonObject3 = mSTAPI.groupCreate("groupName", new String[]{jsonObject2.getString("person_id")}, "testGroupCreate user data");
			assertEquals(jsonObject3.get("status").toString(), "OK");
			String groupId = jsonObject3.getString("group_id");
			JSONObject jsonObject = mSTAPI.faceIdentification(jay1FaceId, groupId, 1);
			assertEquals(jsonObject.getString("status"), "OK");
			assertEquals(jay1FaceId,jsonObject.getString("face_id"));
			assertEquals(groupId,jsonObject.getString("group_id"));
			JSONArray arrayCandidates = (JSONArray)jsonObject.get("candidates");
			assertTrue(arrayCandidates.length() == 1);
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
		}	catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (InterruptedException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void test14FaceTraining_faceIds() {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			JSONObject jsonObjectJay2 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay2));
			JSONObject jsonObjectJack = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jack));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			assertEquals(jsonObjectJay2.get("status").toString(), "OK");
			assertEquals(jsonObjectJack.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONArray arrayJay2 = (JSONArray)jsonObjectJay2.get("faces");
			JSONArray arrayJack = (JSONArray)jsonObjectJack.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			JSONObject faceObjectJay2 = (JSONObject)arrayJay2.get(0);
			JSONObject faceObjectJack = (JSONObject)arrayJack.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			String jay2FaceId = faceObjectJay2.getString("face_id");
			String jackFaceId = faceObjectJack.getString("face_id");
			
			JSONObject jsonObject = mSTAPI.faceTrainingByFaceIds(new String[]{jay1FaceId,jay2FaceId,jackFaceId});
			assertEquals(jsonObject.getString("status"), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
		}	catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void test15FaceTraining_personIds() {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			
			JSONObject jsonObject2 = mSTAPI.personCreate("PJay",new String[]{jay1FaceId},"test user data");
			assertEquals(jsonObject2.get("status").toString(), "OK");
			JSONObject jsonObject = mSTAPI.faceTrainingByPersonIds(new String[]{jsonObject2.getString("person_id")});
			assertEquals(jsonObject.getString("status"), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
		}	catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void test16FaceTraining_facesetIds() {  
		try {
			JSONObject jsonObjectJay2 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay2));
			JSONObject jsonObjectJack = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jack));
			assertEquals(jsonObjectJay2.get("status").toString(), "OK");
			assertEquals(jsonObjectJack.get("status").toString(), "OK");
			JSONArray arrayJay2 = (JSONArray)jsonObjectJay2.get("faces");
			JSONArray arrayJack = (JSONArray)jsonObjectJack.get("faces");
			JSONObject faceObjectJay2 = (JSONObject)arrayJay2.get(0);
			JSONObject faceObjectJack = (JSONObject)arrayJack.get(0);
			String jay2FaceId = faceObjectJay2.getString("face_id");
			String jackFaceId = faceObjectJack.getString("face_id");
			
			JSONObject jsonObject2 = mSTAPI.facesetCreate("facesetName", new String[]{jay2FaceId,jackFaceId}, "facesetCreate user data");
			assertEquals(jsonObject2.get("status").toString(), "OK");
			String facesetId = jsonObject2.getString("faceset_id");
			JSONObject jsonObject = mSTAPI.faceTrainingByFacesetIds(new String[]{facesetId});
			assertEquals(jsonObject.getString("status"), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
		}	catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void test17FaceTraining_groupIds() {  
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			String jay1FaceId = faceObjectJay1.getString("face_id");
			
			JSONObject jsonObject2 = mSTAPI.personCreate("PJay",new String[]{jay1FaceId},"test user data");
			assertEquals(jsonObject2.get("status").toString(), "OK");
			JSONObject jsonObject3 = mSTAPI.groupCreate("groupName", new String[]{jsonObject2.getString("person_id")}, "testGroupCreate user data");
			assertEquals(jsonObject3.get("status").toString(), "OK");
			String groupId = jsonObject3.getString("group_id");
			JSONObject jsonObject = mSTAPI.faceTrainingByGroupIds(new String[]{groupId});
			assertEquals(jsonObject.getString("status"), "OK");
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
		}	catch (JSONException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/**
	 * 暂时不能测试
	 */
	/*public void testFaceGrouping()  {  
		try {
			mSTAPI.faceGrouping(new String[]{"259689deacca460fb8034ec1574b37da"});
		} catch (STAPIException e) {
			Log.d("tag", e.toString());
		}
	}*/
	
	private String[] getFaceIds(){
		String jay1FaceId = null;
		String jay2FaceId = null;
		String jackFaceId = null;
		try {
			JSONObject jsonObjectJay1 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay1));
			JSONObject jsonObjectJay2 = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jay2));
			JSONObject jsonObjectJack = mSTAPI.faceDetection(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jack));
			assertEquals(jsonObjectJay1.get("status").toString(), "OK");
			assertEquals(jsonObjectJay2.get("status").toString(), "OK");
			assertEquals(jsonObjectJack.get("status").toString(), "OK");
			JSONArray arrayJay1 = (JSONArray)jsonObjectJay1.get("faces");
			JSONArray arrayJay2 = (JSONArray)jsonObjectJay2.get("faces");
			JSONArray arrayJack = (JSONArray)jsonObjectJack.get("faces");
			JSONObject faceObjectJay1 = (JSONObject)arrayJay1.get(0);
			JSONObject faceObjectJay2 = (JSONObject)arrayJay2.get(0);
			JSONObject faceObjectJack = (JSONObject)arrayJack.get(0);
			jay1FaceId = faceObjectJay1.getString("face_id");
			jay2FaceId = faceObjectJay2.getString("face_id");
			jackFaceId = faceObjectJack.getString("face_id");
		} catch (STAPIException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (JSONException e) {
			assertTrue(false);
			e.printStackTrace();
		}
		return new String[]{jay1FaceId,jay2FaceId,jackFaceId};
	}
}
