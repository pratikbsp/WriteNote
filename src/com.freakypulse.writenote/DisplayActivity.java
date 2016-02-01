package com.freakypulse.writenote;


import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

	int Height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppBaseTheme1);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		final TextView textView_header = (TextView) findViewById(R.id.header);

		final String randomText = getIntent().getExtras().getString("Data");
		int index = Integer.parseInt(randomText);
		int pos = MainActivity.keyList.get(index);
		final String text = MainActivity.settings.getString("data" + pos, "");
		final String text_2 = MainActivity.settings_2.getString("data" + pos,"")+"                                                                                                       "
				+ "                                                                                                                   "
				+ "                                                                                                                   "
				+ "                                                                                                                   "
				+ "                                                                                                                   "
				+ "                                                                                                                   "
				;
		// textView.setText(text_2);
		textView_header.setText(text);
		TextView txtView = (TextView)findViewById(R.id.txtView);
		txtView.setText(text_2);
		ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent newIntent = new Intent(DisplayActivity.this,
						EditActivity.class);
				newIntent.putExtra("Data", randomText);
				startActivity(newIntent);
				finish();

			}
		});

	}
}
