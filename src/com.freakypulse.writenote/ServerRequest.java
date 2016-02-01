package com.freakypulse.writenote;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ServerRequests {

	ProgressDialog progressDialog;
	public static final int CONN_TIME = 1000 * 15;
	public static final String SERVER_ADDRESS = "http://pratikbsp.comoj.com/";

	ServerRequests(Context context) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		progressDialog.setTitle("Processing");
		progressDialog.setMessage("Please wait...");
	}

	public void storeUserDataInBackground(User user, GetUserCallBack callBack) {
		progressDialog.show();
		new StoreUserDataAsyncTask(user, callBack).execute();
	}

	public void DeleteUserDataInBackground(User user, GetUserCallBack callBack) {
		// progressDialog.show();
		new DeleteUserDataAsyncTask(user, callBack).execute();
	}

	public void fetchUserDataInBackground(User user, GetUserCallBack callBack) {
		progressDialog.show();
		new FetchUserDataAsyncTask(user, callBack).execute();
	}


	public void SyncUserDataInBackground4(User user, GetDataCallBack callBack) {
		// progressDialog.show();
		new SyncUserDataAsyncTask4(user, callBack).execute();
	}

	public void SyncUserDataInBackground1(int id, String title, String content,
			long time, User user, GetDataCallBack callBack) {
		// progressDialog.show();
		new SyncUserDataAsyncTask1(id, title, content, time, user, callBack)
				.execute();
	}

	public void SyncUserDataInBackground2(User user, GetDataCallBack callBack) {
		// progressDialog.show();
		new SyncUserDataAsyncTask2(user, callBack).execute();
	}

	public void SyncUserDataInBackground3(User user, GetUserCallBack callBack) {
		// progressDialog.show();
		new SyncUserDataAsyncTask3(user, callBack).execute();
	}

	public void SyncUserDataInBackground(JSONArray data, GetJsonCallBack callBack) {
		progressDialog.show();
		new SyncUserDataAsyncTask(data, callBack).execute();
	}

	public class SyncUserDataAsyncTask extends AsyncTask<Void, Void, JSONArray> {

		JSONArray array;
		GetJsonCallBack callBack;

		// User user;

		SyncUserDataAsyncTask(JSONArray data, GetJsonCallBack callBack) {

			this.array = data;
			// this.user = user;
			this.callBack = callBack;
		}

		@Override
		protected JSONArray doInBackground(Void... params) {

			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams,
					CONN_TIME);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONN_TIME);

			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost("http://pratikbsp.comoj.com/sync.php");

			JSONArray returnedArray = null;
			try {
				post.setEntity(new StringEntity(array.toString(), "UTF-8"));
				post.setHeader("Content-Type", "application/json");
				post.setHeader("Accept-Encoding", "application/json");
				post.setHeader("Accept-Language", "en-US");

				HttpResponse httpResponse = client.execute(post);

				HttpEntity entity = httpResponse.getEntity();
				String result = EntityUtils.toString(entity);
				JSONArray jArray = new JSONArray(result);
				if (jArray.length() == 0) {
					returnedArray = null;
				} else {
					returnedArray = jArray;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return returnedArray;
		}

		@Override
		protected void onPostExecute(JSONArray returnedArray) {
			progressDialog.dismiss();
			callBack.done(returnedArray);
			super.onPostExecute(returnedArray);
		}
	}

	public class SyncUserDataAsyncTask3 extends AsyncTask<Void, Void, Void> {

		User user;
		GetUserCallBack callBack;

		SyncUserDataAsyncTask3(User user, GetUserCallBack callBack) {
			this.user = user;
			this.callBack = callBack;
		}

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			// dataToSend.add(new BasicNameValuePair("name", user.name));
			dataToSend.add(new BasicNameValuePair("email", user.email));
			dataToSend.add(new BasicNameValuePair("password", user.password));
			// dataToSend.add(new BasicNameValuePair("mobNo", user.mobNo));
			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams,
					CONN_TIME);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONN_TIME);

			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost(SERVER_ADDRESS + "sync3.php");
			try {
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				client.execute(post);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

	}

	public class SyncUserDataAsyncTask1 extends AsyncTask<Void, Void, Data> {

		User user;
		GetDataCallBack callBack;
		int id;
		long time;
		String title, content;

		SyncUserDataAsyncTask1(int id, String title, String content, long time,
				User user, GetDataCallBack callBack) {
			this.id = id;
			this.title = title;
			this.content = content;
			this.time = time;
			this.user = user;
			this.callBack = callBack;
		}

		@Override
		protected Data doInBackground(Void... params) {
			ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			dataToSend.add(new BasicNameValuePair("id", id + ""));
			dataToSend.add(new BasicNameValuePair("title", title));
			dataToSend.add(new BasicNameValuePair("content", content));
			dataToSend.add(new BasicNameValuePair("time", time + ""));

			dataToSend.add(new BasicNameValuePair("name", user.name));
			dataToSend.add(new BasicNameValuePair("email", user.email));
			dataToSend.add(new BasicNameValuePair("password", user.password));
			dataToSend.add(new BasicNameValuePair("mobNo", user.mobNo));
			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams,
					CONN_TIME);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONN_TIME);

			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost("http://pratikbsp.comoj.com/sync1.php");

			Data data = null;
			try {
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				HttpResponse httpResponse = client.execute(post);

				HttpEntity entity = httpResponse.getEntity();
				String result = EntityUtils.toString(entity);
				JSONObject jObject = new JSONObject(result);
				if (jObject.length() == 0) {
					data = null;
				} else {
					int id = Integer.parseInt(jObject.getString("id"));
					String title = jObject.getString("title");
					String content = jObject.getString("content");
					long time = Long.parseLong(jObject.getString("time"));
					data = new Data(id, title, content, time);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return data;
		}

		@Override
		protected void onPostExecute(Data data) {
			// progressDialog.dismiss();
			callBack.done(data);
			super.onPostExecute(data);
		}
	}

	public class SyncUserDataAsyncTask2 extends AsyncTask<Void, Void, Data> {

		User user;
		GetDataCallBack callBack;

		SyncUserDataAsyncTask2(User user, GetDataCallBack callBack) {
			this.user = user;
			this.callBack = callBack;
		}

		@Override
		protected Data doInBackground(Void... params) {
			ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			// dataToSend.add(new BasicNameValuePair("id", id + ""));
			// dataToSend.add(new BasicNameValuePair("title", title));
			// dataToSend.add(new BasicNameValuePair("content", content));
			// dataToSend.add(new BasicNameValuePair("time", time + ""));

			// dataToSend.add(new BasicNameValuePair("name", user.name));
			dataToSend.add(new BasicNameValuePair("email", user.email));
			dataToSend.add(new BasicNameValuePair("password", user.password));
			// dataToSend.add(new BasicNameValuePair("mobNo", user.mobNo));
			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams,
					CONN_TIME);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONN_TIME);

			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost("http://pratikbsp.comoj.com/sync2.php");

			Data data = null;
			try {
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				HttpResponse httpResponse = client.execute(post);

				HttpEntity entity = httpResponse.getEntity();
				String result = EntityUtils.toString(entity);
				JSONObject jObject = new JSONObject(result);
				if (jObject.length() == 0) {
					data = null;
				} else {
					int id = Integer.parseInt(jObject.getString("id"));
					String title = jObject.getString("title");
					String content = jObject.getString("content");
					long time = Long.parseLong(jObject.getString("time"));
					data = new Data(id, title, content, time);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return data;
		}

		@Override
		protected void onPostExecute(Data data) {
			// progressDialog.dismiss();
			callBack.done(data);
			super.onPostExecute(data);
		}
	}

	public class SyncUserDataAsyncTask4 extends AsyncTask<Void, Void, Data> {

		User user;
		GetDataCallBack callBack;

		SyncUserDataAsyncTask4(User user, GetDataCallBack callBack) {
			this.user = user;
			this.callBack = callBack;
		}

		@Override
		protected Data doInBackground(Void... params) {
			ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			// dataToSend.add(new BasicNameValuePair("id", id + ""));
			// dataToSend.add(new BasicNameValuePair("title", title));
			// dataToSend.add(new BasicNameValuePair("content", content));
			// dataToSend.add(new BasicNameValuePair("time", time + ""));

			// dataToSend.add(new BasicNameValuePair("name", user.name));
			dataToSend.add(new BasicNameValuePair("email", user.email));
			dataToSend.add(new BasicNameValuePair("password", user.password));
			// dataToSend.add(new BasicNameValuePair("mobNo", user.mobNo));
			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams,
					CONN_TIME);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONN_TIME);

			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost("http://pratikbsp.comoj.com/sync4.php");

			int count = 0;
			Data data = null;
			try {
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				HttpResponse httpResponse = client.execute(post);

				HttpEntity entity = httpResponse.getEntity();
				String result = EntityUtils.toString(entity);
				JSONObject jObject = new JSONObject(result);
				if (jObject.length() == 0) {
					data = null;
				} else {
					count = Integer.parseInt(jObject.getString("count"));
					data = new Data(count);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return data;
		}

		@Override
		protected void onPostExecute(Data data) {
			// progressDialog.dismiss();
			callBack.done(data);
			super.onPostExecute(data);
		}
	}

	public class DeleteUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

		User user;
		GetUserCallBack callBack;

		DeleteUserDataAsyncTask(User user, GetUserCallBack callBack) {
			this.user = user;
			this.callBack = callBack;
		}

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			dataToSend.add(new BasicNameValuePair("email", user.email));
			dataToSend.add(new BasicNameValuePair("password", user.password));
			// dataToSend.add(new BasicNameValuePair("mobNo", user.mobNo));
			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams,
					CONN_TIME);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONN_TIME);

			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost(SERVER_ADDRESS + "delete.php");
			try {
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				client.execute(post);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			// progressDialog.dismiss();
			callBack.done(null);
			super.onPostExecute(aVoid);
		}
	}

	public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {

		User user;
		GetUserCallBack callBack;

		FetchUserDataAsyncTask(User user, GetUserCallBack callBack) {
			this.user = user;
			this.callBack = callBack;
		}

		@Override
		protected User doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			// dataToSend.add(new BasicNameValuePair("name",user.name));
			dataToSend.add(new BasicNameValuePair("email", user.email));
			dataToSend.add(new BasicNameValuePair("password", user.password));
			// dataToSend.add(new BasicNameValuePair("mobNo",user.mobNo));

			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams,
					CONN_TIME);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONN_TIME);

			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost(
					"http://pratikbsp.comoj.com/FetchUserData.php");

			User returnedUser = null;
			try {
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				HttpResponse httpResponse = client.execute(post);

				HttpEntity entity = httpResponse.getEntity();
				String result = EntityUtils.toString(entity);
				JSONObject jObject = new JSONObject(result);
				if (jObject.length() == 0) {
					returnedUser = null;
				} else {
					String name = jObject.getString("name");
					String mobNo = jObject.getString("mobNo");
					// String = jObject .getString("name");
					returnedUser = new User(name, user.email, user.password,
							mobNo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return returnedUser;

		}

		@Override
		protected void onPostExecute(User user) {
			progressDialog.dismiss();
			callBack.done(user);
			super.onPostExecute(user);
		}

	}

	public class CheckMobUserDataAsyncTask extends AsyncTask<Void, Void, User> {

		String mobNo;
		GetUserCallBack callBack;

		CheckMobUserDataAsyncTask(String mobNo, GetUserCallBack callBack) {
			this.mobNo = mobNo;
			this.callBack = callBack;
		}

		@Override
		protected User doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			// dataToSend.add(new BasicNameValuePair("name",user.name));
			dataToSend.add(new BasicNameValuePair("mobNo", this.mobNo));
			// dataToSend.add(new BasicNameValuePair("password",
			// user.password));
			// dataToSend.add(new BasicNameValuePair("mobNo",user.mobNo));

			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams,
					CONN_TIME);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONN_TIME);

			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost(
					"http://pratikbsp.comoj.com/check_mob.php");

			User returnedUser = null;
			try {
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				HttpResponse httpResponse = client.execute(post);

				HttpEntity entity = httpResponse.getEntity();
				String result = EntityUtils.toString(entity);
				JSONObject jObject = new JSONObject(result);
				if (jObject.length() == 0) {
					returnedUser = null;
				} else {
					String name = jObject.getString("name");
					String emailId = jObject.getString("email");
					String password = jObject.getString("password");
					String mobNo = jObject.getString("mobNo");
					// String = jObject .getString("name");
					returnedUser = new User(name, emailId, password, mobNo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return returnedUser;

		}

		@Override
		protected void onPostExecute(User user) {
			progressDialog.dismiss();
			callBack.done(user);
			super.onPostExecute(user);
		}

	}

	public void CheckUserDataInBackground(String email, GetUserCallBack callBack) {
		progressDialog.show();
		new CheckUserDataAsyncTask(email, callBack).execute();
	}

	public void CheckMobUserDataInBackground(String mobNo,
			GetUserCallBack callBack) {
		progressDialog.show();
		new CheckMobUserDataAsyncTask(mobNo, callBack).execute();
	}

	public class CheckUserDataAsyncTask extends AsyncTask<Void, Void, User> {

		String email;
		GetUserCallBack callBack;

		CheckUserDataAsyncTask(String email, GetUserCallBack callBack) {
			this.email = email;
			this.callBack = callBack;
		}

		@Override
		protected User doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			// dataToSend.add(new BasicNameValuePair("name",user.name));
			dataToSend.add(new BasicNameValuePair("email", this.email));
			// dataToSend.add(new BasicNameValuePair("password",
			// user.password));
			// dataToSend.add(new BasicNameValuePair("mobNo",user.mobNo));

			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams,
					CONN_TIME);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONN_TIME);

			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost(
					"http://pratikbsp.comoj.com/check_email.php");

			User returnedUser = null;
			try {
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				HttpResponse httpResponse = client.execute(post);

				HttpEntity entity = httpResponse.getEntity();
				String result = EntityUtils.toString(entity);
				JSONObject jObject = new JSONObject(result);
				if (jObject.length() == 0) {
					returnedUser = null;
				} else {
					String name = jObject.getString("name");
					String emailId = jObject.getString("email");
					String password = jObject.getString("password");
					String mobNo = jObject.getString("mobNo");
					// String = jObject .getString("name");
					returnedUser = new User(name, emailId, password, mobNo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return returnedUser;

		}

		@Override
		protected void onPostExecute(User user) {
			progressDialog.dismiss();
			callBack.done(user);
			super.onPostExecute(user);
		}

	}


	public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

		User user;
		GetUserCallBack callBack;

		StoreUserDataAsyncTask(User user, GetUserCallBack callBack) {
			this.user = user;
			this.callBack = callBack;
		}

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
			dataToSend.add(new BasicNameValuePair("name", user.name));
			dataToSend.add(new BasicNameValuePair("email", user.email));
			dataToSend.add(new BasicNameValuePair("password", user.password));
			dataToSend.add(new BasicNameValuePair("mobNo", user.mobNo));
			HttpParams httpRequestParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpRequestParams,
					CONN_TIME);
			HttpConnectionParams.setSoTimeout(httpRequestParams, CONN_TIME);

			HttpClient client = new DefaultHttpClient(httpRequestParams);
			HttpPost post = new HttpPost(SERVER_ADDRESS + "register.php");
			try {
				post.setEntity(new UrlEncodedFormEntity(dataToSend));
				client.execute(post);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			progressDialog.dismiss();
			callBack.done(null);
			super.onPostExecute(aVoid);
		}
	}

}
