package com.freakypulse.writenote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView.OnMenuItemClickListener;
import android.support.v7.widget.PopupMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
		OnMenuItemClickListener,
		android.support.v7.widget.PopupMenu.OnMenuItemClickListener {
	public static final String MyPREFERENCES = "MyPrefs",
			MyPREFERENCES_2 = "MyPrefs_2", TimePREFERENCES = "TimePrefs",
			DeletePREFERENCES = "DeletePrefs",
			DeletePREFERENCES_2 = "DeletePrefs_2",
			DeleteTimePREFERENCES = "DeleteTimePrefs";
	ScrollView scrollview;
	LinearLayout linearlayout;
	LinearLayout.LayoutParams layoutParams;
	static int i = 0;
	int pos = 0;
	int index = 1;
	int deletePos = 0;
	static int length = 0;
	private EditText mEditText;
	public static SharedPreferences sharedpreferences, settings,
			sharedpreferences_2, settings_2, timepreferences,
			deletepreferences, deletepreferences_2, deleteTimepreferences;
	TextView textView, navigation;
	ImageView imageView, AccountView, DeleteView, EditView, back;
	public static ArrayList<String> arrayList, arrayList_2, deleteList_2;
	public static ArrayList<NavView> navList;
	public static ArrayList<ListContent> lList, deleteList;
	public static ArrayAdapter<ListContent> lAdapter, deleteAdapter;
	public static ArrayList<Integer> keyList, deleteKeyList;
	public static ArrayList<Long> timeList, deleteTimeList;
	public static ArrayAdapter<String> adapter, adapter_2;
	public static ArrayAdapter<NavView> nav_adapter;
	Editor editor, editor_2, settings_editor, settings_editor_2, timeEditor;
	Map<String, ?> keys, keys_2;
	static TextView noteView;
	static ImageView imageNoteView,imageDustbinView;
	static ListView navView, deleteView, lView;
	boolean AllowBackButton, isDrawerOpened;
	ImageView Account, navbar1;
	TextView noteDustbinView;
	static TextView userName;
	UserLocalStore userLocalStore;
	
	boolean isLongPressed;
	boolean deleteLongPressed;
	int longPressedCount = 0;
	ArrayList<Integer> longPressedList;
	ArrayList<Integer> deletePressedList;
	ArrayList<View> viewList, viewDeleteList;
	// for drawer
	RelativeLayout leftRL;
	DrawerLayout drawerLayout;
	ImageView deletePermanent;
	ImageView restore;
	
	int mode = 1;
	int backCount =1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppBaseTheme1);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AllowBackButton = true;
		isDrawerOpened = false;

		viewDeleteList = new ArrayList<View>();
		viewList = new ArrayList<View>();
		deleteLongPressed = false;
		isLongPressed = false;
		longPressedList = new ArrayList<Integer>();
		deletePressedList = new ArrayList<Integer>();

		userName = (TextView) findViewById(R.id.userName1);
		textView = (TextView) findViewById(R.id.header);
		// navbar = (TextView) findViewById(R.id.navbar);
		navigation = (TextView) findViewById(R.id.navigation);
		imageView = (ImageView) findViewById(R.id.imageView1);
		back = (ImageView) findViewById(R.id.back);
		DeleteView = (ImageView) findViewById(R.id.imageDeleteView);
		EditView = (ImageView) findViewById(R.id.imageEditView);
		
		noteView = (TextView) findViewById(R.id.noteView);
		noteDustbinView = (TextView) findViewById(R.id.noteDustbinView);
		imageNoteView = (ImageView) findViewById(R.id.imageNoteView);
		imageDustbinView = (ImageView) findViewById(R.id.imageDustbinView);
		navbar1 = (ImageView) findViewById(R.id.navbar1);
		// Account = (ImageView) findViewById(R.id.imageAccountView);
		deleteView = (ListView) findViewById(R.id.listViewTrash);
		navView = (ListView) findViewById(R.id.navView);

		lView = (ListView) findViewById(R.id.lView);
		lList = new ArrayList<ListContent>();
		populateLList();

		deleteList = new ArrayList<ListContent>();
		populateDeleteList();

		String[] items = {};
		String[] items2 = { "Notes", "Trash", "Sync", "About App" };
		keyList = new ArrayList<Integer>();
		deleteKeyList = new ArrayList<Integer>();
		deleteTimeList = new ArrayList<Long>();
		timeList = new ArrayList<Long>();
		arrayList = new ArrayList<String>(Arrays.asList(items));
		adapter = new ArrayAdapter<String>(this, R.layout.list_item, arrayList);
		arrayList_2 = new ArrayList<String>(Arrays.asList(items));
		navList = new ArrayList<NavView>();
		poupulateNavList();

		deleteList_2 = new ArrayList<String>();

		// for creating drawer
		leftRL = (RelativeLayout) findViewById(R.id.whatYouWantInLeftDrawer);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		navView = (ListView) findViewById(R.id.navView);
		populateNavView();
		sharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		timepreferences = getSharedPreferences(TimePREFERENCES,
				Context.MODE_PRIVATE);
		timeEditor = timepreferences.edit();
		editor = sharedpreferences.edit();
		settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		settings_editor = settings.edit();
		sharedpreferences_2 = getSharedPreferences(MyPREFERENCES_2,
				Context.MODE_PRIVATE);
		editor_2 = sharedpreferences_2.edit();
		settings_2 = getSharedPreferences(MyPREFERENCES_2, Context.MODE_PRIVATE);
		settings_editor_2 = settings_2.edit();

		deletepreferences_2 = getSharedPreferences(DeletePREFERENCES_2,
				Context.MODE_PRIVATE);
		deletepreferences = getSharedPreferences(DeletePREFERENCES,
				Context.MODE_PRIVATE);

		deleteTimepreferences = getSharedPreferences(DeleteTimePREFERENCES,
				Context.MODE_PRIVATE);

		keys = settings.getAll();
		keys_2 = settings_2.getAll();

		int yourEditCount = settings.getInt("count", 0);

		for (int j = 1; j <= yourEditCount; j++) {

			// TextView view = new TextView(MainActivity.this);
			String data = settings.getString("data" + j, "");
			String data_2 = settings_2.getString("data" + j, "");

			String delete_data = deletepreferences.getString("data" + j, "");
			String delete_data_2 = deletepreferences_2
					.getString("data" + j, "");
			long delete_time_data = deleteTimepreferences
					.getLong("data" + j, 0);

			long time = timepreferences.getLong("data" + j, 0);
			if (data != "") {
				arrayList.add(data);
				arrayList_2.add(data_2);

				keyList.add(j);
				timeList.add(time);
				long seconds = System.currentTimeMillis();
				Calendar cl = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				cl.setTimeInMillis(time * 1000);
				c2.setTimeInMillis(seconds);

				String current_year = "" + c2.get(Calendar.YEAR);
				String current_day = "" + c2.get(Calendar.DAY_OF_MONTH);

				String saved_day = "" + cl.get(Calendar.DAY_OF_MONTH);
				int month = cl.get(Calendar.MONTH);
				String saved_year = "" + cl.get(Calendar.YEAR);

				int hour = cl.get(Calendar.HOUR);
				int min = cl.get(Calendar.MINUTE);
				int hour1 = cl.get(Calendar.HOUR_OF_DAY);

				String month_names[] = { "Jan", "Feb", "Mar", "Apr", "May",
						"Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec" };

				String saved_month = month_names[month];
				String date;

				if (Integer.parseInt(current_year) > Integer
						.parseInt(saved_year)) {
					date = saved_month + " " + saved_day + " " + saved_year;
				} else if (Integer.parseInt(current_day) > Integer
						.parseInt(saved_day)) {
					date = saved_month + " " + saved_day;
				} else {
					if (hour1 > 11) {
						if (min > 9)
							date = hour + ":" + min + " PM";
						else
							date = hour + ":0" + min + " PM";
					} else {
						if (min > 9)
							date = hour + ":" + min + " AM";
						else
							date = hour + ":0" + min + " AM";
					}

				}
				lList.add(new ListContent(data, date));
				length++;
			}
			if (delete_data != "") {

				long seconds = System.currentTimeMillis();
				Calendar cl = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				cl.setTimeInMillis(delete_time_data * 1000);
				c2.setTimeInMillis(seconds);

				String current_year = "" + c2.get(Calendar.YEAR);
				String current_day = "" + c2.get(Calendar.DAY_OF_MONTH);

				String saved_day = "" + cl.get(Calendar.DAY_OF_MONTH);
				int month = cl.get(Calendar.MONTH);
				String saved_year = "" + cl.get(Calendar.YEAR);

				int hour = cl.get(Calendar.HOUR);
				int min = cl.get(Calendar.MINUTE);
				int hour1 = cl.get(Calendar.HOUR_OF_DAY);

				String month_names[] = { "Jan", "Feb", "Mar", "Apr", "May",
						"Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec" };

				String saved_month = month_names[month];
				String date;

				if (Integer.parseInt(current_year) > Integer
						.parseInt(saved_year)) {
					date = saved_month + " " + saved_day + " " + saved_year;
				} else if (Integer.parseInt(current_day) > Integer
						.parseInt(saved_day)) {
					date = saved_month + " " + saved_day;
				} else {
					if (hour1 > 11) {
						if (min > 9)
							date = hour + ":" + min + " PM";
						else
							date = hour + ":0" + min + " PM";
					} else {
						if (min > 9)
							date = hour + ":" + min + " AM";
						else
							date = hour + ":0" + min + " AM";
					}

				}

				deleteKeyList.add(j);
				deleteList.add(new ListContent(delete_data, date));
				deleteList_2.add(delete_data_2);
				deleteTimeList.add(delete_time_data);
			}

		}
		i = yourEditCount;
		if (length > 0) {
			noteView.setVisibility(View.GONE);
			imageNoteView.setVisibility(View.GONE);
			// listView.setVisibility(View.VISIBLE);
		} else {

			noteView.setVisibility(View.VISIBLE);
			imageNoteView.setVisibility(View.VISIBLE);
		}
		
		noteDustbinView.setVisibility(View.GONE);
		imageDustbinView.setVisibility(View.GONE);
		
		userLocalStore = new UserLocalStore(this);
		lView.setVisibility(View.VISIBLE);
		adapter.notifyDataSetChanged();
		lAdapter.notifyDataSetChanged();
		deleteAdapter.notifyDataSetChanged();
		// textView.setOnClickListener(new View.OnClickListener());

		if (userLocalStore.getUserLoggedIn()) {
			// Toast.makeText(getBaseContext(), "User is already logged in",
			// Toast.LENGTH_LONG);
			User user = userLocalStore.getLoggedInUser();
			String name = user.name.split(" ")[0];
			userName.setText(name);
		}
		// lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent newIntent = new Intent(MainActivity.this,
						AddActivity.class);
				startActivity(newIntent);
				// finish();
			}

		});

		lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (isLongPressed) {
					EditView.setVisibility(View.GONE);
					int p = 0;
					for (int i = 0; i < longPressedList.size(); i++) {
						if (longPressedList.get(i) == position) {
							ImageView v = (ImageView) view
									.findViewById(R.id.checkList);
							v.setVisibility(View.GONE);
							longPressedList.remove(i);
							viewList.remove(i);
							longPressedCount--;
							if (longPressedCount == 0) {
							//	Toast.makeText(MainActivity.this,
								//		"isLongPressed is made false here ",
									//	Toast.LENGTH_LONG).show();
								isLongPressed = false;
								EditView.setVisibility(View.GONE);
								DeleteView.setVisibility(View.GONE);
								imageView.setVisibility(View.VISIBLE);
							}
							if (longPressedCount == 1) {
								
									EditView.setVisibility(View.VISIBLE);
									
								}
							p = 1;
							//Toast.makeText(
								//	MainActivity.this,
									//"clicked on already pressed element at position "
										//	+ position + " remving checkbox"
											//+ " with isLongpressed true",
									//Toast.LENGTH_LONG).show();
							return;
						}

					}
					if (p == 0) {
						ImageView v = (ImageView) view
								.findViewById(R.id.checkList);
						v.setVisibility(View.VISIBLE);
						longPressedList.add(position);
						longPressedCount++;
						viewList.add(view);
						//Toast.makeText(
							//	MainActivity.this,
								//"adding element at position " + position
									//	+ " with isLongpressed true",
								//Toast.LENGTH_LONG).show();
					}

					// view.setBackgroundColor(Color.GREEN);
				} else {
					String str = "" + position;
					// Toast.makeText(getBaseContext(), str,
					// Toast.LENGTH_LONG).show();
					Intent newIntent = new Intent(MainActivity.this,
							DisplayActivity.class);
					newIntent.putExtra("Data", str);
					startActivity(newIntent);

				}

			}
		});

		lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (isLongPressed) {
					
					EditView.setVisibility(View.GONE);
					int p = 0;
					for (int i = 0; i < longPressedList.size(); i++) {
						if (longPressedList.get(i) == position) {
							ImageView v = (ImageView) view
									.findViewById(R.id.checkList);
							v.setVisibility(View.GONE);
							longPressedList.remove(i);
							viewList.remove(i);
							longPressedCount--;
							if (longPressedCount == 0) {
								isLongPressed = false;
								EditView.setVisibility(View.GONE);
								DeleteView.setVisibility(View.GONE);
								imageView.setVisibility(View.VISIBLE);

							}
							if (longPressedCount == 1) {
								
								EditView.setVisibility(View.VISIBLE);
								
							}
				//			Toast.makeText(
					//				MainActivity.this,
						//			"clicked on already pressed element at position "
							//				+ position + " remving checkbox"
								//			+ " with isLongpressed true",
									//Toast.LENGTH_LONG).show();
						//	;
							p = 1;

							return true;
						}

					}
					if (p == 0) {
						ImageView v = (ImageView) view
								.findViewById(R.id.checkList);
						v.setVisibility(View.VISIBLE);
						longPressedList.add(position);
						longPressedCount++;
						viewList.add(view);
					//	Toast.makeText(
						//		MainActivity.this,
							//	"adding element at position " + position
								//		+ " with isLongpressed true",
								//Toast.LENGTH_LONG).show();
						;
					}

				} else {

					longPressedCount++;
					AllowBackButton = false;
					pos = position;
					EditView.setVisibility(View.VISIBLE);
					DeleteView.setVisibility(View.VISIBLE);
					imageView.setVisibility(View.GONE);
					isLongPressed = true;
					longPressedList.add(pos);
					viewList.add(view);
					// TextView v = (TextView) view.findViewById(R.id.side);
					// v.setBackgroundColor(drawable.darkgreyy_color);
					ImageView v = (ImageView) view.findViewById(R.id.checkList);
					v.setVisibility(View.VISIBLE);
					//Toast.makeText(MainActivity.this,
						//	"adding element at position " + position,
							//Toast.LENGTH_LONG).show();
					// showPopup(view);

				}
				return true;
			}
		});

		DeleteView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// viewList.clear();

	//			 for(int i=0;i<longPressedCount;i++){
		//			 Toast.makeText(MainActivity.this,
			//				 "element at position "+longPressedList.get(i)+"has value "+lList.get(longPressedList.get(i)).title,
				//			 	Toast.LENGTH_LONG).show();
				 //}

				// Toast.makeText(MainActivity.this,
				// "total count is  "+longPressedCount+" "+longPressedList.size(),
				// Toast.LENGTH_LONG).show();

				for (int i = 0; i < viewList.size(); i++) {
					ImageView v1 = (ImageView) viewList.get(i).findViewById(
							R.id.checkList);
					v1.setVisibility(View.GONE);
				}
				viewList.clear();

				isLongPressed = false;

				AllowBackButton = true;
				EditView.setVisibility(View.GONE);
				DeleteView.setVisibility(View.GONE);
				imageView.setVisibility(View.VISIBLE);
				if (length == 1) {
					noteView.setVisibility(View.VISIBLE);
					imageNoteView.setVisibility(View.VISIBLE);
					lView.setVisibility(View.GONE);

				}
				for (int i = 0; i < longPressedList.size(); i++) {
					//Toast.makeText(MainActivity.this,
						//	 "deleting element at  "+longPressedList.get(i)+" with value "+lList.get(longPressedList.get(i)).title,
							// Toast.LENGTH_SHORT).show();

					pos = longPressedList.get(i)-i;
					long seconds = System.currentTimeMillis();
					Calendar cl = Calendar.getInstance();
					Calendar c2 = Calendar.getInstance();
					cl.setTimeInMillis(timeList.get(pos) * 1000);
					c2.setTimeInMillis(seconds);

					String current_year = "" + c2.get(Calendar.YEAR);
					String current_day = "" + c2.get(Calendar.DAY_OF_MONTH);

					String saved_day = "" + cl.get(Calendar.DAY_OF_MONTH);
					int month = cl.get(Calendar.MONTH);
					String saved_year = "" + cl.get(Calendar.YEAR);

					int hour = cl.get(Calendar.HOUR);
					int min = cl.get(Calendar.MINUTE);
					int hour1 = cl.get(Calendar.HOUR_OF_DAY);

					String month_names[] = { "Jan", "Feb", "Mar", "Apr", "May",
							"Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec" };

					String saved_month = month_names[month];
					String date;

					if (Integer.parseInt(current_year) > Integer
							.parseInt(saved_year)) {
						date = saved_month + " " + saved_day + " " + saved_year;
					} else if (Integer.parseInt(current_day) > Integer
							.parseInt(saved_day)) {
						date = saved_month + " " + saved_day;
					} else {
						if (hour1 > 11) {
							if (min > 9)
								date = hour + ":" + min + " PM";
							else
								date = hour + ":0" + min + " PM";
						} else {
							if (min > 9)
								date = hour + ":" + min + " AM";
							else
								date = hour + ":0" + min + " AM";
						}

					}

					deleteList.add(new ListContent(lList.get(pos).getTitle(),
							date));

					deleteList_2.add(arrayList_2.get(pos));
					deleteKeyList.add(keyList.get(pos));
					deleteTimeList.add(timeList.get(pos));
					int index = keyList.get(pos);

					SharedPreferences.Editor deleteTimeEditor = deleteTimepreferences
							.edit();

					SharedPreferences.Editor deleteEditor = deletepreferences
							.edit();
					SharedPreferences.Editor deleteEditor_2 = deletepreferences_2
							.edit();
					deleteEditor.putString("data" + index, lList.get(pos)
							.getTitle());
					deleteEditor.commit();

					deleteEditor_2.putString("data" + index,
							arrayList_2.get(pos));
					deleteEditor_2.commit();

					deleteTimeEditor.putLong("data" + index, timeList.get(pos));
					deleteTimeEditor.commit();

					arrayList.remove(pos);
					arrayList_2.remove(pos);

					lList.remove(pos);
					lAdapter.notifyDataSetChanged();
					// adapter.notifyDataSetChanged();
					deleteAdapter.notifyDataSetChanged();
					index = keyList.get(pos);
					keyList.remove(pos);
					timeList.remove(pos);

					timeEditor.remove("data" + index);
					timeEditor.commit();

					editor.remove("data" + index);
					editor.commit();
					editor_2.remove("data" + index);
					editor_2.commit();
					length--;

				}

				longPressedList.clear();
				if(lList.size()==0){
					imageNoteView.setVisibility(View.VISIBLE);
					noteView.setVisibility(View.VISIBLE);
					//lView.setVisibility(View.GONE);
				}
				else{
					imageNoteView.setVisibility(View.GONE);
					noteView.setVisibility(View.GONE);
					
					lView.setVisibility(View.VISIBLE);
				}
			}

		});

		deletePermanent = (ImageView) findViewById(R.id.imageDeleteView1);
		restore = (ImageView) findViewById(R.id.imageRestoreView);

		deleteView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if (deleteLongPressed) {
							int p = 0;
							for (int i = 0; i < deletePressedList.size(); i++) {
								if (deletePressedList.get(i) == position) {
									ImageView v = (ImageView) view
											.findViewById(R.id.checkList);
									v.setVisibility(View.GONE);
									deletePressedList.remove(i);
									viewDeleteList.remove(i);
									if(deletePressedList.size()==0){
										deleteLongPressed = false;
										deletePermanent.setVisibility(View.GONE);
										restore.setVisibility(View.GONE);
									}
									p = 1;
									return;
								}

							}
							if (p == 0) {
								ImageView v = (ImageView) view
										.findViewById(R.id.checkList);
								v.setVisibility(View.VISIBLE);
								deletePressedList.add(position);
								viewDeleteList.add(view);
							}

							// view.setBackgroundColor(Color.GREEN);
						} else {
							String str = "" + position;
							// Toast.makeText(getBaseContext(), str,
							// Toast.LENGTH_LONG).show();
							Intent newIntent = new Intent(MainActivity.this,
									DisplayDeleteActivity.class);
							newIntent.putExtra("Data", str);
							startActivity(newIntent);

						}

					}
				});
		deleteView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						if (deleteLongPressed) {
							int p = 0;
							for (int i = 0; i < deletePressedList.size(); i++) {
								if (deletePressedList.get(i) == position) {
									ImageView v = (ImageView) view
											.findViewById(R.id.checkList);
									v.setVisibility(View.GONE);
									deletePressedList.remove(i);
									viewDeleteList.remove(i);
									if(deletePressedList.size()==0){
										deleteLongPressed = false;
										deletePermanent.setVisibility(View.GONE);
										restore.setVisibility(View.GONE);
									}
									p = 1;
									return true;
								}

							}
							if (p == 0) {
								ImageView v = (ImageView) view
										.findViewById(R.id.checkList);
								v.setVisibility(View.VISIBLE);
								deletePressedList.add(position);
								viewDeleteList.add(view);
							}

						}

						else {
							AllowBackButton = false;
							deletePermanent.setVisibility(View.VISIBLE);
							restore.setVisibility(View.VISIBLE);
							deleteLongPressed = true;
							deletePressedList.add(position);
							viewDeleteList.add(view);
							// TextView v = (TextView)
							// view.findViewById(R.id.side);
							// v.setBackgroundColor(drawable.darkgreyy_color);
							ImageView v = (ImageView) view
									.findViewById(R.id.checkList);
							v.setVisibility(View.VISIBLE);

						}
						return true;
					}
				});
		deletePermanent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				 /**for(int i=0;i<deletePressedList.size();i++){
									 Toast.makeText(MainActivity.this,
										 "element at position "+deletePressedList.get(i)+"has value "+deleteList.get(deletePressedList.get(i)).title,
											 	Toast.LENGTH_LONG).show();
								}
				**/
				
				for (int i = 0; i < viewDeleteList.size(); i++) {
					ImageView v1 = (ImageView) viewDeleteList.get(i)
							.findViewById(R.id.checkList);
					v1.setVisibility(View.GONE);
				}
				viewDeleteList.clear();

				deleteLongPressed = false;

				AllowBackButton = true;
				deletePermanent.setVisibility(View.GONE);
				restore.setVisibility(View.GONE);
				for (int i = 0; i < deletePressedList.size(); i++) {
					pos = deletePressedList.get(i)-i;

					deleteList.remove(pos);

					deleteList_2.remove(pos);
					// deleteKeyList.remove(pos);
					deleteTimeList.remove(pos);
					int index = deleteKeyList.get(pos);
					deleteKeyList.remove(pos);
					SharedPreferences.Editor deleteTimeEditor = deleteTimepreferences
							.edit();

					SharedPreferences.Editor deleteEditor = deletepreferences
							.edit();
					SharedPreferences.Editor deleteEditor_2 = deletepreferences_2
							.edit();
					deleteEditor.remove("data" + index);
					deleteEditor.commit();

					deleteEditor_2.remove("data" + index);
					deleteEditor_2.commit();

					deleteTimeEditor.remove("data" + index);
					deleteTimeEditor.commit();

					deleteAdapter.notifyDataSetChanged();

				}
				if(deleteList.size()==0){
					noteDustbinView.setVisibility(View.VISIBLE);
					imageDustbinView.setVisibility(View.VISIBLE);
					deleteView.setVisibility(View.GONE);
				}
				else{
					noteDustbinView.setVisibility(View.GONE);
					imageDustbinView.setVisibility(View.GONE);
					deleteView.setVisibility(View.VISIBLE);
				}
				

				deletePressedList.clear();
		}

		});
		restore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < viewDeleteList.size(); i++) {
					ImageView v1 = (ImageView) viewDeleteList.get(i)
							.findViewById(R.id.checkList);
					v1.setVisibility(View.GONE);
				}
				viewDeleteList.clear();

				deleteLongPressed = false;

				AllowBackButton = true;
				deletePermanent.setVisibility(View.GONE);
				restore.setVisibility(View.GONE);
				for (int i = 0; i < deletePressedList.size(); i++) {
					pos = deletePressedList.get(i)-i;
					long seconds = System.currentTimeMillis();
					Calendar cl = Calendar.getInstance();
					Calendar c2 = Calendar.getInstance();
					cl.setTimeInMillis(deleteTimeList.get(pos) * 1000);
					c2.setTimeInMillis(seconds);

					String current_year = "" + c2.get(Calendar.YEAR);
					String current_day = "" + c2.get(Calendar.DAY_OF_MONTH);

					String saved_day = "" + cl.get(Calendar.DAY_OF_MONTH);
					int month = cl.get(Calendar.MONTH);
					String saved_year = "" + cl.get(Calendar.YEAR);

					int hour = cl.get(Calendar.HOUR);
					int min = cl.get(Calendar.MINUTE);
					int hour1 = cl.get(Calendar.HOUR_OF_DAY);

					String month_names[] = { "Jan", "Feb", "Mar", "Apr", "May",
							"Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec" };

					String saved_month = month_names[month];
					String date;

					if (Integer.parseInt(current_year) > Integer
							.parseInt(saved_year)) {
						date = saved_month + " " + saved_day + " " + saved_year;
					} else if (Integer.parseInt(current_day) > Integer
							.parseInt(saved_day)) {
						date = saved_month + " " + saved_day;
					} else {
						if (hour1 > 11) {
							if (min > 9)
								date = hour + ":" + min + " PM";
							else
								date = hour + ":0" + min + " PM";
						} else {
							if (min > 9)
								date = hour + ":" + min + " AM";
							else
								date = hour + ":0" + min + " AM";
						}

					}

					lList.add(new ListContent(deleteList.get(pos).getTitle(),
							date));

					arrayList.add(deleteList.get(pos).getTitle());
					arrayList_2.add(deleteList_2.get(pos));
					keyList.add(deleteKeyList.get(pos));
					timeList.add(deleteTimeList.get(pos));
					int index = deleteKeyList.get(pos);

					timeEditor.putLong("data" + index, deleteTimeList.get(pos));
					timeEditor.commit();

					editor.putString("data" + index, deleteList.get(pos)
							.getTitle());
					editor.commit();
					editor_2.putString("data" + index, deleteList_2.get(pos));
					editor_2.commit();

					SharedPreferences.Editor deleteTimeEditor = deleteTimepreferences
							.edit();

					SharedPreferences.Editor deleteEditor = deletepreferences
							.edit();
					SharedPreferences.Editor deleteEditor_2 = deletepreferences_2
							.edit();
					deleteEditor.remove("data" + index);
					deleteEditor.commit();

					deleteEditor_2.remove("data" + index);
					deleteEditor_2.commit();

					deleteTimeEditor.remove("data" + index);
					deleteTimeEditor.commit();

					deleteList.remove(pos);
					deleteList_2.remove(pos);

					lAdapter.notifyDataSetChanged();
					// adapter.notifyDataSetChanged();
					deleteAdapter.notifyDataSetChanged();
					// index = deleteKeyList.get(pos);
					deleteKeyList.remove(pos);
					deleteTimeList.remove(pos);

					length++;

				}

				deletePressedList.clear();
				if(deleteList.size()==0){
					noteDustbinView.setVisibility(View.VISIBLE);
					imageDustbinView.setVisibility(View.VISIBLE);
					deleteView.setVisibility(View.GONE);
				}
				else{
					noteDustbinView.setVisibility(View.GONE);
					imageDustbinView.setVisibility(View.GONE);
					deleteView.setVisibility(View.VISIBLE);
				}
				
			}

		});

		navView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				onLeftClose();
				if (position == 1) {
				
					mode =2;
					cleanEveryThing();
					if(deleteList.size()==0){
						noteDustbinView.setVisibility(View.VISIBLE);
						imageDustbinView.setVisibility(View.VISIBLE);
						deleteView.setVisibility(View.GONE);
					}
					else{
						noteDustbinView.setVisibility(View.GONE);
						imageDustbinView.setVisibility(View.GONE);
						deleteView.setVisibility(View.VISIBLE);
					}
					imageNoteView.setVisibility(View.GONE);
					noteView.setVisibility(View.GONE);
					
					DeleteView.setVisibility(View.GONE);
					EditView.setVisibility(View.GONE);
					lView.setVisibility(View.GONE);
					imageView.setVisibility(View.GONE);
				} else if (position == 0) {
					cleanEveryThing();
					mode =1;
					if(lList.size()==0){
						imageNoteView.setVisibility(View.VISIBLE);
						noteView.setVisibility(View.VISIBLE);
						lView.setVisibility(View.GONE);
					}
					else{
						imageNoteView.setVisibility(View.GONE);
						noteView.setVisibility(View.GONE);
						
						lView.setVisibility(View.VISIBLE);
					}
					
					noteDustbinView.setVisibility(View.GONE);
					imageDustbinView.setVisibility(View.GONE);
					
					deleteView.setVisibility(View.GONE);
					
					imageView.setVisibility(View.VISIBLE);
					deletePermanent.setVisibility(View.GONE);
					restore.setVisibility(View.GONE);
				} else if (position == 2) {
					cleanEveryThing();
					User user = userLocalStore.getLoggedInUser();
					if (!userLocalStore.getUserLoggedIn()) {

						Toast.makeText(getBaseContext(),
								"Cannot sync,Sign in first !",
								Toast.LENGTH_LONG).show();
						startActivity(new Intent(MainActivity.this,
								AccountActivity.class));
						finish();
					} else {
						ArrayList<String> a1 = new ArrayList<String>(
								MainActivity.arrayList);
						ArrayList<String> a2 = new ArrayList<String>(
								MainActivity.arrayList_2);
						int len = a1.size();
						int i = 0;
						long seconds1 = System.currentTimeMillis() / 1000;
						JSONArray array = new JSONArray();
						JSONObject object = new JSONObject();
						try {
							object.put("email", user.email);
							object.put("password", user.password);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						array.put(object);

						while (i < len) {
							int Id = MainActivity.keyList.get(i);
							String title = MainActivity.arrayList.get(i);
							String content = MainActivity.arrayList_2.get(i);
							long time = MainActivity.timeList.get(i);
							// data=null;
							// SyncTask1(id,title,content,time,user);
							// Toast.makeText(getBaseContext(),"time being sent is "+time,
							// Toast.LENGTH_LONG).show();
							// backup(user,a1.get(i),a2.get(i));
							JSONObject newObject = new JSONObject();
							try {
								newObject.put("id", Id + "");
								newObject.put("title", title);
								newObject.put("content", content);
								newObject.put("time", time + "");
								array.put(newObject);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							i++;
						}

						SyncTask(array);
						// if (length > 0) {
						noteView.setVisibility(View.GONE);
						imageNoteView.setVisibility(View.GONE);
						// listView.setVisibility(View.VISIBLE);

					}
				} else if (position == 3) {
					cleanEveryThing();
					startActivity(new Intent(MainActivity.this,
							InfoActivity.class));
				}

			}
		});
		EditView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AllowBackButton = true;

				EditView.setVisibility(View.GONE);
				DeleteView.setVisibility(View.GONE);
				imageView.setVisibility(View.VISIBLE);
				String str = "" + pos;
				Intent newIntent = new Intent(MainActivity.this,
						EditActivity.class);
				newIntent.putExtra("Data", str);
				startActivity(newIntent);

			}

		});

		navigation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLeft();
			}
		});
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onLeftClose();
			}
		});

		LinearLayout account = (LinearLayout) findViewById(R.id.userID);
		account.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(getBaseContext(), "CLicked account pic",
				// Toast.LENGTH_LONG).show();

				if (userLocalStore.getUserLoggedIn()) {
					// Toast.makeText(getBaseContext(), "user logged in",
					// Toast.LENGTH_LONG).show();
					startActivity(new Intent(MainActivity.this,
							ProfileActivity.class));
				} else {
					// Toast.makeText(getBaseContext(), "user not logged in",
					// Toast.LENGTH_LONG).show();
					Intent newIntent = new Intent(MainActivity.this,
							AccountActivity.class);

					startActivity(newIntent);
					finish();
				}

			}

		});

	}

	public void SyncTask(JSONArray array) {
		ServerRequests serverRequests = new ServerRequests(this);
		serverRequests.SyncUserDataInBackground(array, new GetJsonCallBack() {
			@Override
			public void done(JSONArray returnedArray) {
				if (returnedArray == null) {

				} else {
					// data = returnedData;
					for (int i = 0; i < returnedArray.length(); i++) {
						try {
							JSONObject object = returnedArray.getJSONObject(i);
							int id = Integer.parseInt(object.getString("id"));
							String title = object.getString("title");
							String content = object.getString("content");
							long time = Long.parseLong(object.getString("time"));

							MainActivity.arrayList.add(title);
							MainActivity.arrayList_2.add(content);
							MainActivity.keyList.add(id);
							MainActivity.timeList.add(time);
							long seconds = System.currentTimeMillis();
							Calendar cl = Calendar.getInstance();
							Calendar c2 = Calendar.getInstance();
							cl.setTimeInMillis(time * 1000);
							c2.setTimeInMillis(seconds);

							String current_year = "" + c2.get(Calendar.YEAR);
							String current_day = ""
									+ c2.get(Calendar.DAY_OF_MONTH);

							String saved_day = ""
									+ cl.get(Calendar.DAY_OF_MONTH);
							int month = cl.get(Calendar.MONTH);
							String saved_year = "" + cl.get(Calendar.YEAR);

							int hour = cl.get(Calendar.HOUR);
							int min = cl.get(Calendar.MINUTE);
							int hour1 = cl.get(Calendar.HOUR_OF_DAY);

							String month_names[] = { "Jan", "Feb", "Mar",
									"Apr", "May", "Jun", "July", "Aug", "Sep",
									"Oct", "Nov", "Dec" };

							String saved_month = month_names[month];
							String date;

							if (Integer.parseInt(current_year) > Integer
									.parseInt(saved_year)) {
								date = saved_month + " " + saved_day + " "
										+ saved_year;
							} else if (Integer.parseInt(current_day) > Integer
									.parseInt(saved_day)) {
								date = saved_month + " " + saved_day;
							} else {
								if (hour1 > 11) {
									if (min > 9)
										date = hour + ":" + min + " PM";
									else
										date = hour + ":0" + min + " PM";
								} else {
									if (min > 9)
										date = hour + ":" + min + " AM";
									else
										date = hour + ":0" + min + " AM";
								}

							}

							lList.add(new ListContent(title, date));
							lAdapter.notifyDataSetChanged();
							// MainActivity.adapter.notifyDataSetChanged();
							SharedPreferences.Editor editor = MainActivity.sharedpreferences
									.edit();
							SharedPreferences.Editor editor_2 = MainActivity.sharedpreferences_2
									.edit();
							SharedPreferences.Editor timeEditor = MainActivity.timepreferences
									.edit();
							MainActivity.i++;
							int count1;
							if (MainActivity.i > id)
								count1 = MainActivity.i;
							else {
								count1 = id;
							}
							// MainActivity.keyList.add(count);
							editor.putString("data" + MainActivity.i, title);
							editor.putInt("count", count1);
							editor.apply();

							editor_2.putString("data" + MainActivity.i, content);
							editor_2.putInt("count", count1);
							editor_2.apply();

							timeEditor.putLong("data" + MainActivity.i, time);
							timeEditor.putInt("count", count1);
							timeEditor.apply();
							length++;

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					// MainActivity.adapter.notifyDataSetChanged();
				}
			}
		});

	}

	private void populateDeleteList() {
		// TODO Auto-generated method stub
		deleteAdapter = new MyDeleteAdapter();
		deleteView.setAdapter(deleteAdapter);
	}

	private void populateLList() {
		// TODO Auto-generated method stub
		lAdapter = new MyLAdapter();
		lView.setAdapter(lAdapter);

	}

	class MyDeleteAdapter extends ArrayAdapter<ListContent> {
		public MyDeleteAdapter() {
			super(MainActivity.this, R.layout.list_item2, deleteList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.list_item2,
						parent, false);
			}
			// Find NavView to work with
			ListContent newView = deleteList.get(position);

			// setting text
			TextView textView = (TextView) itemView.findViewById(R.id.title);
			String title = newView.getTitle();
			textView.setText(title);

			TextView textView1 = (TextView) itemView.findViewById(R.id.date);
			String date = newView.getTime();
			textView1.setText(date);
			return itemView;
			// return super.getView(position, convertView, parent);
		}

	}

	class MyLAdapter extends ArrayAdapter<ListContent> {
		public MyLAdapter() {
			super(MainActivity.this, R.layout.list_item2, lList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.list_item2,
						parent, false);
			}
			// Find NavView to work with
			ListContent newView = lList.get(position);

			// setting text
			TextView textView = (TextView) itemView.findViewById(R.id.title);
			String title = newView.getTitle();
			textView.setText(title);

			TextView textView1 = (TextView) itemView.findViewById(R.id.date);
			String date = newView.getTime();
			textView1.setText(date);
			return itemView;
			// return super.getView(position, convertView, parent);
		}

	}

	private void populateNavView() {
		// TODO Auto-generated method stub
		nav_adapter = new MyListAdapter();
		navView.setAdapter(nav_adapter);
	}

	class MyListAdapter extends ArrayAdapter<NavView> {
		public MyListAdapter() {
			super(MainActivity.this, R.layout.list_item1, navList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.list_item1,
						parent, false);
			}
			// Find NavView to work with
			NavView newView = navList.get(position);
			// setting image
			ImageView imageView = (ImageView) itemView
					.findViewById(R.id.item_icon);

			int icon_id = newView.getIcon_id();
			imageView.setImageResource(icon_id);

			// setting text
			TextView textView = (TextView) itemView
					.findViewById(R.id.item_text);
			String icon_text = newView.getIcon_text();
			textView.setText(icon_text);
			return itemView;
			// return super.getView(position, convertView, parent);
		}

	}

	private void poupulateNavList() {
		// TODO Auto-generated method stub
		navList.add(new NavView(R.drawable.pencil1, "Notes"));
		navList.add(new NavView(R.drawable.delete4, "Trash"));

		navList.add(new NavView(R.drawable.sync, "Sync"));
		navList.add(new NavView(R.drawable.about, "About app"));
	}

	/**
	 * @Override protected void onStart(){ super.onStart(); if(authenticate()){
	 * 
	 *           } }
	 **/
	private boolean authenticate() {
		return userLocalStore.getUserLoggedIn();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// Toast.makeText(getBaseContext(), "Back pressed",
			// Toast.LENGTH_LONG).show();
			onBackPressed();
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		if (!AllowBackButton) {
			EditView.setVisibility(View.GONE);
			DeleteView.setVisibility(View.GONE);
			imageView.setVisibility(View.VISIBLE);
			AllowBackButton = true;
			cleanEveryThing();
			
		} else if (isDrawerOpened) {
			onLeftClose();
		}
		else if(mode  == 2) {
			mode =1;
			if(lList.size()==0){
				imageNoteView.setVisibility(View.VISIBLE);
				noteView.setVisibility(View.VISIBLE);
				lView.setVisibility(View.GONE);
			}
			else{
				imageNoteView.setVisibility(View.GONE);
				noteView.setVisibility(View.GONE);
				
				lView.setVisibility(View.VISIBLE);
			}
			
			noteDustbinView.setVisibility(View.GONE);
			imageDustbinView.setVisibility(View.GONE);
			
			deleteView.setVisibility(View.GONE);
			
			imageView.setVisibility(View.VISIBLE);
			deletePermanent.setVisibility(View.GONE);
			restore.setVisibility(View.GONE);
			
		}
		else {
			
			if(backCount!=2){
				backCount++;
				Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
			}
			else{
				finish();
			}
			
		}

	}
	
	public void cleanEveryThing(){
		isLongPressed = false;
		longPressedList.clear();
		for (int i = 0; i < viewList.size(); i++) {
			ImageView v = (ImageView) viewList.get(i).findViewById(
					R.id.checkList);
			v.setVisibility(View.GONE);
		}
		viewList.clear();

		deleteLongPressed = false;
		deletePressedList.clear();
		for (int i = 0; i < viewDeleteList.size(); i++) {
			ImageView v = (ImageView) viewDeleteList.get(i).findViewById(
					R.id.checkList);
			v.setVisibility(View.GONE);
		}
		viewDeleteList.clear();

	}

	public void showPopup(View v) {
		PopupMenu popup = new PopupMenu(this, v);
		popup.setOnMenuItemClickListener(MainActivity.this);
		MenuInflater infalter = popup.getMenuInflater();
		infalter.inflate(R.menu.my_popup, popup.getMenu());
		popup.show();
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.delete:
			if (length == 1) {
				noteView.setVisibility(View.VISIBLE);
				imageNoteView.setVisibility(View.VISIBLE);
				lView.setVisibility(View.GONE);

			}
			arrayList.remove(pos);
			arrayList_2.remove(pos);
			adapter.notifyDataSetChanged();
			index = keyList.get(pos);
			keyList.remove(pos);
			editor.remove("data" + index);
			editor.commit();
			editor_2.remove("data" + index);
			editor_2.commit();
			length--;
		}
		return true;
	}

	public void onLeft() {
		drawerLayout.openDrawer(leftRL);
		isDrawerOpened = true;

	}

	public void onLeftClose() {
		drawerLayout.closeDrawer(leftRL);
		isDrawerOpened = false;
	}

}
