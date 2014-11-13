/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.AsyncHttpClient;
import hm.edu.hmcheckin.util.AsyncHttpClient.AsyncHttpListener;
import hm.edu.hmcheckin.util.ResponseParserv2;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * TODO Javadoc
 */
public class LoginActivity extends Activity implements AsyncHttpListener {

	/**
	 * TODO Javadoc
	 */
	private EditText name;
	/**
	 * TODO Javadoc
	 */
	private EditText password;
	/**
	 * TODO Javadoc
	 */
	private Button login;
	/**
	 * TODO Javadoc
	 */
	private Button register;

	/**
	 * TODO Javadoc
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}

	/**
	 * TODO Javadoc
	 */
	private void init() {
		name = (EditText) findViewById(R.id.editText_name);
		password = (EditText) findViewById(R.id.editText_password);
		login = (Button) findViewById(R.id.button_login);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				login();
			}
		});
		register = (Button) findViewById(R.id.button_register);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * TODO Javadoc
	 */
	private void login() {
		String userName = name.getText().toString().trim();
		String userPassword = password.getText().toString().trim();
		if (userName.equals("") || userPassword.equals("")) {
			Toast.makeText(this, getString(R.string.error_login),
					Toast.LENGTH_LONG).show();
		} else {
			StringBuilder builder = new StringBuilder(
					"http://moan.cs.hm.edu:8080/HmCheckIn/UserLogin?");
			builder.append("name=" + userName + "&password=" + userPassword);
			new AsyncHttpClient(this).execute(builder.toString());
		}
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public void onAsyncTaskComplete(String result) {
		boolean success = new ResponseParserv2(result).parseLogin();
		if (success) {
			Intent intent = new Intent(this, LandingActivity.class);
			startActivity(intent);
			finish();
		} else {
			showDialog(getString(R.string.login_errorTitle),
					getString(R.string.login_errorMessage));
		}
	}

	/**
	 * TODO Javadoc
	 */
	public void showDialog(String title, String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder
				.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(getString(R.string.login_positiveButton),
						null);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

}
