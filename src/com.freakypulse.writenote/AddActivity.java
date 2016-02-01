package com.freakypulse.writenote;

import java.util.Calendar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

	EditText editText_2, editText;
	ImageView save;
	TextView discard;
	boolean Allowback;

	LinearLayout bottomView,topView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppBaseTheme1);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		Allowback = true;
		editText_2 = (EditText) findViewById(R.id.editText);
		editText = (EditText) findViewById(R.id.head);
		save = (ImageView) findViewById(R.id.imageView1);
	
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
				Allowback = true;
				saveIt();
			}
		});}

	public void saveIt() {
		long seconds = System.currentTimeMillis() / 1000;
		//Toast.makeText(getBaseContext(), seconds+"",
			//	 Toast.LENGTH_LONG).show();
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
			
			Calendar cl = Calendar.getInstance();
			cl.setTimeInMillis(seconds*1000);
			String saved_day = "" + cl.get(Calendar.DAY_OF_MONTH);
			int month =  cl.get(Calendar.MONTH);
			String saved_year = "" + cl.get(Calendar.YEAR);
			
			int hour = cl.get(Calendar.HOUR);
			int min = cl.get(Calendar.MINUTE);
			int hour1 = cl.get(Calendar.HOUR_OF_DAY);
			String date;
			
			if(hour1>11){
				if(min>9)
				date = hour+":"+min+" PM";
				else
					date = hour+":0"+min+" PM";
			}
			else{
				if(min>9)
					date = hour+":"+min+" AM";
					else
						date = hour+":0"+min+" AM";
			}
			
			
			MainActivity.arrayList.add(slice);
			MainActivity.lList.add(new ListContent(slice,date));
			MainActivity.arrayList_2.add(newItem_2);
			MainActivity.timeList.add(seconds);
			// notify listview of data changed
			MainActivity.adapter.notifyDataSetChanged();
			MainActivity.lAdapter.notifyDataSetChanged();
			SharedPreferences.Editor editor = MainActivity.sharedpreferences
					.edit();
			SharedPreferences.Editor editor_2 = MainActivity.sharedpreferences_2
					.edit();
			SharedPreferences.Editor timeEditor = MainActivity.timepreferences
					.edit();
			MainActivity.i++;
			int count = MainActivity.i;
			MainActivity.keyList.add(count);
			editor.putString("data" + MainActivity.i, slice);
			editor.putInt("count", count);
			editor.commit();

			editor_2.putString("data" + MainActivity.i, newItem_2);
			editor_2.putInt("count", count);
			editor_2.commit();

			timeEditor.putLong("data" + MainActivity.i, seconds);
			timeEditor.putInt("count", count);
			timeEditor.commit();

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
			
			Calendar cl = Calendar.getInstance();
			cl.setTimeInMillis(seconds*1000);
			String saved_day = "" + cl.get(Calendar.DAY_OF_MONTH);
			int month =  cl.get(Calendar.MONTH);
			String saved_year = "" + cl.get(Calendar.YEAR);
			
			int hour = cl.get(Calendar.HOUR);
			int min = cl.get(Calendar.MINUTE);
			int hour1 = cl.get(Calendar.HOUR_OF_DAY);
			String date;
			
			if(hour1>11){
				if(min>9)
				date = hour+":"+min+" PM";
				else
					date = hour+":0"+min+" PM";
			}
			else{
				if(min>9)
					date = hour+":"+min+" AM";
					else
						date = hour+":0"+min+" AM";
			}
			
			MainActivity.arrayList.add(slice);
			MainActivity.lList.add(new ListContent(slice,date));
			MainActivity.arrayList_2.add(newItem_2);
			MainActivity.timeList.add(seconds);
			// notify listview of data changed
			MainActivity.adapter.notifyDataSetChanged();
			MainActivity.lAdapter.notifyDataSetChanged();
			SharedPreferences.Editor editor = MainActivity.sharedpreferences
					.edit();
			SharedPreferences.Editor editor_2 = MainActivity.sharedpreferences_2
					.edit();
			SharedPreferences.Editor timeEditor = MainActivity.timepreferences
					.edit();
			MainActivity.i++;
			int count = MainActivity.i;
			MainActivity.keyList.add(count);
			editor.putString("data" + MainActivity.i, slice);
			editor.putInt("count", count);
			editor.commit();

			editor_2.putString("data" + MainActivity.i, newItem_2);
			editor_2.putInt("count", count);
			editor_2.commit();

			timeEditor.putLong("data" + MainActivity.i, seconds);
			timeEditor.putInt("count", count);
			timeEditor.commit();

		}
		if (MainActivity.length == 1) {

		}
		MainActivity.length++;
		if (MainActivity.length == 1) {
			MainActivity.noteView.setVisibility(View.GONE);
			MainActivity.imageNoteView.setVisibility(View.GONE);
			MainActivity.lView.setVisibility(View.VISIBLE);

		}
		int index = MainActivity.length - 1;
		String str = "" + index;
		Toast.makeText(getBaseContext(), "Note saved", Toast.LENGTH_SHORT);
		Intent newIntent = new Intent(AddActivity.this, DisplayActivity.class);
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
