package com.freakypulse.writenote;

import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends ActionBarActivity {

	String randomText;
	int index, pos;
	String text_2, text;
	EditText editText_2, editText;
	ImageView save;
	TextView discard;
	boolean Allowback;
	LinearLayout bottomView,topView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppBaseTheme1);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		Allowback = true;
		randomText = getIntent().getExtras().getString("Data");
		index = Integer.parseInt(randomText);
		pos = MainActivity.keyList.get(index);
		text = MainActivity.settings.getString("data" + pos, "");
		text_2 = MainActivity.settings_2.getString("data" + pos, "");
		editText = (EditText) findViewById(R.id.head);
		editText_2 = (EditText) findViewById(R.id.editText);
		editText.setText(text);
		editText_2.setText(text_2);
		editText.setSelection(editText.getText().length());
		save = (ImageView) findViewById(R.id.imageView1);
		// discard = (TextView) findViewById(R.id.discard);
		
		
		bottomView = (LinearLayout)findViewById(R.id.bottomView);
		topView = (LinearLayout)findViewById(R.id.topView);
		
		bottomView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editText_2.setFocusableInTouchMode(true);
			    editText_2.requestFocus();
			    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			    imm.showSoftInput(editText_2, InputMethodManager.SHOW_IMPLICIT);
			    
			}
		});
		topView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editText.setFocusableInTouchMode(true);
			    editText.requestFocus();
			    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

			}
		});

		
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				saveIt();
			}
		});
	}

	public void saveIt() {
		String newItem = editText.getText().toString();
		String newItem_2 = editText_2.getText().toString();
		String slice;
		if (newItem.matches("")) {
			if (newItem_2.matches("")) {
				if (Allowback) {
					Toast.makeText(getBaseContext(),
							"Note area can't be empty", Toast.LENGTH_SHORT)
							.show();
					return;
				} else {
					finish();
					return;
				}
			}
			// Toast.makeText(getBaseContext(), "Entered when head is null",
			// Toast.LENGTH_SHORT).show();
			if (newItem_2.length() > 23) {
				slice = newItem_2.substring(0, 22) + "...";

			}

			else
				slice = newItem_2;
			// add new item to arraylist
			// MainActivity.arrayList.set(index, slice);

			MainActivity.arrayList_2.set(index, newItem_2);
			// notify listview of data changed
			// MainActivity.adapter.notifyDataSetChanged();
			SharedPreferences.Editor editor = MainActivity.sharedpreferences
					.edit();
			SharedPreferences.Editor editor_2 = MainActivity.sharedpreferences_2
					.edit();
			int count = pos;
			// MainActivity.keyList.add(count);
			editor.putString("data" + pos, slice);
			editor.apply();

			editor_2.putString("data" + pos, newItem_2);
			editor_2.apply();

			long seconds = System.currentTimeMillis() / 1000;
			Calendar cl = Calendar.getInstance();
			cl.setTimeInMillis(seconds * 1000);
			String saved_day = "" + cl.get(Calendar.DAY_OF_MONTH);
			int month = cl.get(Calendar.MONTH);
			String saved_year = "" + cl.get(Calendar.YEAR);

			int hour = cl.get(Calendar.HOUR);
			int min = cl.get(Calendar.MINUTE);
			int hour1 = cl.get(Calendar.HOUR_OF_DAY);
			String date;

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

			MainActivity.lList.set(index, new ListContent(slice, date));

			//Toast.makeText(getBaseContext(), seconds + "", Toast.LENGTH_LONG)
				//	.show();
			MainActivity.timeList.set(index, seconds);
			SharedPreferences.Editor timeEditor = MainActivity.timepreferences
					.edit();
			timeEditor.putLong("data" + pos, seconds);
			timeEditor.apply();

		}

		else {
			if (newItem_2.matches("")) {
				if (Allowback) {
					Toast.makeText(getBaseContext(),
							"Note area can't be empty", Toast.LENGTH_SHORT)
							.show();
					return;
				} else {
					finish();
					return;
				}
			}
			// Toast.makeText(getBaseContext(), "Entered when head is not null",
			// Toast.LENGTH_SHORT).show();
			slice = newItem;
			// add new item to arraylist
			MainActivity.arrayList_2.set(index, newItem_2);
			// notify listview of data changed
			// MainActivity.adapter.notifyDataSetChanged();
			SharedPreferences.Editor editor = MainActivity.sharedpreferences
					.edit();
			SharedPreferences.Editor editor_2 = MainActivity.sharedpreferences_2
					.edit();
			int count = pos;
			// MainActivity.keyList.add(count);
			editor.putString("data" + pos, slice);
			editor.apply();

			editor_2.putString("data" + pos, newItem_2);
			editor_2.apply();

			long seconds = System.currentTimeMillis() / 1000;
			Calendar cl = Calendar.getInstance();
			cl.setTimeInMillis(seconds * 1000);
			String saved_day = "" + cl.get(Calendar.DAY_OF_MONTH);
			int month = cl.get(Calendar.MONTH);
			String saved_year = "" + cl.get(Calendar.YEAR);

			int hour = cl.get(Calendar.HOUR);
			int min = cl.get(Calendar.MINUTE);
			int hour1 = cl.get(Calendar.HOUR_OF_DAY);
			String date;

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

			MainActivity.lList.set(index, new ListContent(slice, date));
			MainActivity.lAdapter.notifyDataSetChanged();
		//	Toast.makeText(getBaseContext(), seconds + "", Toast.LENGTH_LONG)
			//		.show();
			MainActivity.timeList.set(index, seconds);
			SharedPreferences.Editor timeEditor = MainActivity.timepreferences
					.edit();
			timeEditor.putLong("data" + pos, seconds);
			timeEditor.apply();

		}
		String str = "" + index;
		Intent newIntent = new Intent(EditActivity.this, DisplayActivity.class);
		newIntent.putExtra("Data", str);
		startActivity(newIntent);
		finish();

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
		Allowback = false;
		saveIt();

	}

}
