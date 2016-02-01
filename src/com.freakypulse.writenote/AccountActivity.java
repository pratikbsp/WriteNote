package com.freakypulse.writenote;

import java.util.regex.Pattern;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AccountActivity extends Activity {

	TextView header, register_here, existing_user;
	LinearLayout ll1, ll2;
	EditText editText_username1, editText_password1, editText_name2,
			editText_username2, editText_password2, editText_confirm_password2,
			editText_MobNo;
	Button submit1, submit2;
	UserLocalStore userLocalStore;
	boolean exists, mobExists;
	public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

	// here exists two mode 1 for login screen call it 1 and other for
	// registration screen 2 ;
	int mode = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppBaseTheme1);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		exists = false;
		mobExists = false;
		header = (TextView) findViewById(R.id.header);
		register_here = (TextView) findViewById(R.id.register_here);
		existing_user = (TextView) findViewById(R.id.loggedIn);
		ll1 = (LinearLayout) findViewById(R.id.linearLayout1);
		ll2 = (LinearLayout) findViewById(R.id.linearLayout2);
		editText_username1 = (EditText) findViewById(R.id.editText_username1);
		editText_password1 = (EditText) findViewById(R.id.editText_password1);
		editText_name2 = (EditText) findViewById(R.id.editText_name2);
		editText_username2 = (EditText) findViewById(R.id.editText_username2);
		editText_password2 = (EditText) findViewById(R.id.editText_password2);
		editText_confirm_password2 = (EditText) findViewById(R.id.editText_confirm_password2);
		editText_MobNo = (EditText) findViewById(R.id.editText_MobNo);
		submit1 = (Button) findViewById(R.id.submit1);
		submit2 = (Button) findViewById(R.id.submit2);

		userLocalStore = new UserLocalStore(this);
		ll1.setVisibility(View.VISIBLE);
		ll2.setVisibility(View.GONE);

		register_here.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				header.setText("Registration");
				ll1.setVisibility(View.GONE);
				ll2.setVisibility(View.VISIBLE);
				mode = 2;
			}
		});
		existing_user.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				header.setText("Login");
				ll1.setVisibility(View.VISIBLE);
				ll2.setVisibility(View.GONE);
				mode = 1;
			}
		});
		submit1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String email = editText_username1.getText().toString();
				String password = editText_password1.getText().toString();
				User user = new User(email, password);
				authenticate(user);

				// User user1 = userLocalStore.getLoggedInUser();

			}
		});

		submit2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = editText_name2.getText().toString();
				String email = editText_username2.getText().toString();
				String password = editText_password2.getText().toString();
				String confirm_password = editText_confirm_password2.getText()
						.toString();
				String mobNo = editText_MobNo.getText().toString();
				if (name.matches("") || email.matches("")
						|| password.matches("") || confirm_password.matches("")
						|| mobNo.matches("")) {
					Toast.makeText(getBaseContext(),
							"All fields are mandatory", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (!password.equals(confirm_password)) {
					Toast.makeText(getBaseContext(),
							"Please enter same password in both fields",
							Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(mobNo.length()<7 || mobNo.length()>13){
					Toast.makeText(getBaseContext(),
							"Please enter valid mobile number", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				
				boolean value=checkEmail(email);
				if(!value){
					//showEmailIncorrectMessage();
					Toast.makeText(getBaseContext(),
							"Please enter valid email address", Toast.LENGTH_SHORT)
							.show();
					
					return;
				}
				
				
				check(new User(name, email, password, mobNo), getBaseContext());
			}
		});

	}

	private void registerUser(User user) {
		ServerRequests serverRequests = new ServerRequests(this);
		serverRequests.storeUserDataInBackground(user, new GetUserCallBack() {
			@Override
			public void done(User returnedUser) {
				// startActivity(new Intent(AccountActivity.this,));
				header.setText("Login");
				ll1.setVisibility(View.VISIBLE);
				ll2.setVisibility(View.GONE);
				mode = 1;
			}
		});

	}

	private void authenticate(final User user) {
		ServerRequests serverRequests = new ServerRequests(this);
		serverRequests.fetchUserDataInBackground(user, new GetUserCallBack() {
			@Override
			public void done(User returnedUser) {
				if (returnedUser == null) {
					showErrorMessage();
				} else {
					// String name = user.name.split(" ")[0];
					// MainActivity.userName.setText(user.name);
					// MainActivity.userName.setTextColor(Color.WHITE);

					logUserIn(returnedUser);
				}
			}
		});
	}

	private void check(final User user, Context context) {
		boolean ans = false;
		final ServerRequests serverRequests = new ServerRequests(this);
		serverRequests.CheckUserDataInBackground(user.email,
				new GetUserCallBack() {
					@Override
					public void done(User returnedUser) {
						if (returnedUser == null) {
							serverRequests.CheckMobUserDataInBackground(
									user.mobNo, new GetUserCallBack() {
										@Override
										public void done(User returnedUser1) {
											if (returnedUser1 == null) {
												registerUser(user);

											} else {

												showErrorMessage2();
											}
										}
									});

						} else {
							showErrorMessage1();
						}
					}
				});

	}

	private void showErrorMessage() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setMessage("Incorrect user details");
		dialogBuilder.setPositiveButton("Ok", null);
		dialogBuilder.show();
	}

	private void showErrorMessage1() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setMessage("email address already exists");
		dialogBuilder.setPositiveButton("Ok", null);
		dialogBuilder.show();
	}
	
	private void showEmailIncorrectMessage() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setMessage("Please enter a valid email address");
		dialogBuilder.setPositiveButton("Ok", null);
		dialogBuilder.show();
	}

	private void showErrorMessage2() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setMessage("This number is already registerd");
		dialogBuilder.setPositiveButton("Ok", null);
		dialogBuilder.show();
	}

	private void logUserIn(User user) {
		userLocalStore.storeUserData(user);
		userLocalStore.setUserLoggedIn(true);
		startActivity(new Intent(AccountActivity.this, MainActivity.class));
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

		// checking which mode is user in....
		if (mode == 1) {
			startActivity(new Intent(AccountActivity.this, MainActivity.class));
			finish();
		} else {
			mode=1;
			ll1.setVisibility(View.VISIBLE);
			ll2.setVisibility(View.GONE);
		}

	}
	private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
}

}
