package com.freakypulse.writenote;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {

	TextView name_slot2,email_slot2,mobNo_slot2;
	UserLocalStore userLocalStore;
	User user;
	Button logout,backup,sync;
	ProgressDialog progressDialog;
	ImageView goBack,syncIt;
	Data data;
	int count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppBaseTheme1);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		userLocalStore =  new UserLocalStore(this);
		name_slot2 = (TextView)findViewById(R.id.name_slot2);
		email_slot2 = (TextView)findViewById(R.id.email_slot2);
		mobNo_slot2 = (TextView)findViewById(R.id.mobNo_slot2);
	//	backup = (Button)findViewById(R.id.backup);

		user = userLocalStore.getLoggedInUser();
		name_slot2.setText(user.name);
		email_slot2.setText(user.email);
		mobNo_slot2.setText(user.mobNo);
		logout = (Button) findViewById(R.id.logout);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setTitle("Backing up");
		progressDialog.setMessage("Please wait...");

		//syncIt = (ImageView) findViewById(R.id.syncIt);
		goBack = (ImageView) findViewById(R.id.goBack);
		
		count=0;
		
		goBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});/**
		syncIt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<String> a1 = new ArrayList<String>(MainActivity.arrayList);
				ArrayList<String> a2 = new ArrayList<String>(MainActivity.arrayList_2);
				int len = a1.size();
				int i=0;
				Toast.makeText(getBaseContext(),"Sync has been started,it will be done in a while",
							 Toast.LENGTH_LONG).show();
				long seconds1 = System.currentTimeMillis()/1000;
				JSONArray array = new JSONArray();
				JSONObject object = new JSONObject();
				try {
					object.put("email", user.email);
					object.put("password",user.password);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				array.put(object);
				
				while(i<len){
					int id = MainActivity.keyList.get(i);
					String title = MainActivity.arrayList.get(i);
					String content = MainActivity.arrayList_2.get(i);
					long time = MainActivity.timeList.get(i);
					//data=null;
					//SyncTask1(id,title,content,time,user);
					//Toast.makeText(getBaseContext(),"time being sent is "+time,
						//	 Toast.LENGTH_LONG).show();
					//backup(user,a1.get(i),a2.get(i));
					JSONObject newObject = new JSONObject();
					try {
						newObject.put("id", id+"");
						newObject.put("title", title);
						newObject.put("content", content);
						newObject.put("time", time+"");
						array.put(newObject);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
				}
				
//				SyncTask(array);
				
				
				
			}
		});
**/
		logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userLocalStore.setUserLoggedIn(false);
				userLocalStore.clearUserData();
				MainActivity.userName.setText("Sign in");
				//startActivity(new Intent(ProfileActivity.this,MainActivity.class));
				finish();
			}
		});
	}

	
	public void delete_backup(User user){
		ServerRequests serverRequests = new ServerRequests(this);
		serverRequests.DeleteUserDataInBackground(user, new GetUserCallBack() {
			@Override
			public void done(User returnedUser) {
				
			}
		});
	}
	
}
