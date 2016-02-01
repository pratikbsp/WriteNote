package com.freakypulse.writenote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FrontActivity extends Activity {

	public static final String FIRST_TIME = "oneTimeCome";
	SharedPreferences firstTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_front);

		firstTime = getSharedPreferences(FIRST_TIME, Context.MODE_PRIVATE);
		Button button1 = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		TextView textView = (TextView) findViewById(R.id.loading);

		SharedPreferences.Editor firstTimeEditor = firstTime.edit();
		UserLocalStore userLocalStore = new UserLocalStore(this);
		if (!firstTime.getBoolean("firstTime", false)) {
			firstTimeEditor.putBoolean("firstTime", true);
			firstTimeEditor.commit();
			if (userLocalStore.getUserLoggedIn()) {

				Thread timer = new Thread() {
					public void run() {
						try {
							sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							Intent openStartingPoint = new Intent(
									FrontActivity.this, MainActivity.class);
							startActivity(openStartingPoint);
							finish();
						}

					}
				};
				timer.start();

			} else {
				textView.setVisibility(View.GONE);
				button1.setVisibility(View.VISIBLE);
				button2.setVisibility(View.VISIBLE);

			}

		} else {
			Thread timer = new Thread() {
				public void run() {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						Intent openStartingPoint = new Intent(
								FrontActivity.this, MainActivity.class);
						startActivity(openStartingPoint);
						finish();
					}

				}
			};
			timer.start();

		} 
		

		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FrontActivity.this,
						AccountActivity.class));
				finish();
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FrontActivity.this, MainActivity.class));
				finish();
			}
		});

	}

}
