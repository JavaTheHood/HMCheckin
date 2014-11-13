/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.AsyncHttpClient;
import hm.edu.hmcheckin.util.AsyncHttpClient.AsyncHttpListener;
import hm.edu.hmcheckin.util.RegisterResponse;
import hm.edu.hmcheckin.util.ResponseParserv2;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * TODO Javadoc
 */
public class RegisterActivity extends Activity implements AsyncHttpListener {

	/**
	 * TODO Javadoc
	 */
	private EditText login;
	/**
	 * TODO Javadoc
	 */
	private EditText firstname;
	/**
	 * TODO Javadoc
	 */
	private EditText lastname;
	/**
	 * TODO Javadoc
	 */
	private EditText password;
	/**
	 * TODO Javadoc
	 */
	private EditText confirmPassword;
	/**
	 * TODO Javadoc
	 */
	private Button register;
	/**
	 * TODO Javadoc
	 */
	private String loginIn;
	/**
	 * TODO Javadoc
	 */
	private String firstnameIn;
	/**
	 * TODO Javadoc
	 */
	private String lastnameIn;
	/**
	 * TODO Javadoc
	 */
	private String passwordIn;
	/**
	 * TODO Javadoc
	 */
	private String confirmIn;

	/**
	 * TODO Javadoc
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
	}

	/**
	 * TODO Javadoc
	 */
	public void init() {
		login = (EditText) findViewById(R.id.editText_login);
		firstname = (EditText) findViewById(R.id.editText_firstname);
		lastname = (EditText) findViewById(R.id.editText_lastname);
		password = (EditText) findViewById(R.id.editText_password);
		confirmPassword = (EditText) findViewById(R.id.editText_confirmPassword);
		register = (Button) findViewById(R.id.button_register);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				register();
			}
		});
	}

	/**
	 * TODO Javadoc
	 */
	public void register() {
		if (checkInput()) {
			StringBuilder builder = new StringBuilder(
					"http://moan.cs.hm.edu:8080/HmCheckIn/UserRegister?");
			builder.append("username=" + loginIn + "&firstname=" + firstnameIn
					+ "&lastname=" + lastnameIn + "&password=" + passwordIn);
			Log.i("HMCheckin", builder.toString());
			new AsyncHttpClient(this).execute(builder.toString());
		}
	}

	/**
	 * TODO Javadoc
	 */
	public boolean checkInput() {
		loginIn = login.getText().toString().trim();
		firstnameIn = firstname.getText().toString().trim();
		lastnameIn = lastname.getText().toString().trim();
		passwordIn = password.getText().toString().trim();
		confirmIn = confirmPassword.getText().toString().trim();

		// check empty
		if (loginIn.equals("") || firstnameIn.equals("")
				|| lastnameIn.equals("") || passwordIn.equals("")
				|| confirmIn.equals("")) {
			showDialog(getString(R.string.register_dialogTitle),
					getString(R.string.register_errorNoInput));
			return false;
		}
		// check password
		if (passwordIn.length() < 6) {
			showDialog(getString(R.string.register_dialogTitle),
					getString(R.string.register_errorShortPw));
			return false;
		}
		if (!passwordIn.equals(confirmIn)) {
			showDialog(getString(R.string.register_dialogTitle),
					getString(R.string.register_errorConfirmPw));
			return false;
		}
		return true;
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public void onAsyncTaskComplete(String result) {
		RegisterResponse response = new ResponseParserv2(result)
				.parseRegister();
		if (response == null) {
			showDialog(getString(R.string.register_dialogTitle),
					getString(R.string.register_dialogMessage));
		} else {
			if (response.isSuccess()) {
				Toast.makeText(this, getString(R.string.register_toastSucces),
						Toast.LENGTH_LONG).show();
				finish();
			} else {
				showDialog(getString(R.string.register_dialogTitle),
						response.getMessage());
			}
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
