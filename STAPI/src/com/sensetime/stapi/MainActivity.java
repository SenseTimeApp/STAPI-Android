package com.sensetime.stapi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sensetime.stapi.error.STAPIException;
import com.sensetime.stapi.http.STAPI;
import com.sensetime.stapi_key.MyApiKey;

/**
 * 实例DEMO
 * @author Crystal
 *
 */
public class MainActivity extends Activity {

	private String mResultString;
	private ImageView mImageView;
	private TextView mTextView;
	private TextView mCastTime;
	private Bitmap mBitmap;
	private long mStartTime;
	
//	使用时候请替换为自己的apiId和apiSecret
	private STAPI mSTAPI = new STAPI(MyApiKey.MyApiID, MyApiKey.MyApiSecret);
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0x01:
				mCastTime.setText("cast time:"+(System.currentTimeMillis() - mStartTime)+"ms");
				mTextView.setText(mResultString);
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mImageView = (ImageView) findViewById(R.id.iv);
		mTextView = (TextView) findViewById(R.id.tv);
		mCastTime = (TextView) findViewById(R.id.tv_time);
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jack);
		mImageView.setImageBitmap(mBitmap);
	}
	
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.detect:
			mTextView.setText("");
			mCastTime.setText("");
			mStartTime = System.currentTimeMillis();
			new Thread(){
				public void run() {
					try {
						mResultString = mSTAPI.faceDetection(mBitmap).toString();
					} catch (STAPIException e) {
						mResultString = e.toString();
					}finally{
						mHandler.sendEmptyMessage(0x01);
					}
				};
			}.start();
			break;
		default:
			break;
		}
	}

}
