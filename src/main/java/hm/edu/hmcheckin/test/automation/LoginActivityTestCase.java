/**
 * 
 */
package hm.edu.hmcheckin.test.automation;

import hm.edu.hmcheckin.ui.LoginActivity;
import hm.edu.hmcheckin.ui.RegisterActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

/**
 * @author <a href="mailto:podolski@hm.edu">Roman Podolski</a>
 * 
 */
public class LoginActivityTestCase extends
		ActivityInstrumentationTestCase2<LoginActivity> {

	/**
	 * @author <a href="mailto:podolski@hm.edu">Roman Podolski</a>
	 */
	private Solo solo;

	/**
	 * @author <a href="mailto:podolski@hm.edu">Roman Podolski</a>
	 */
	public LoginActivityTestCase() {
		super("hm.edu.hmcheckin.ui.LoginActivity", LoginActivity.class);
	}

	/**
	 * @author <a href="mailto:podolski@hm.edu">Roman Podolski</a>
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	/**
	 * 
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:podolski@hm.edu">Roman Podolski</a>
	 * @throws Exception
	 */
	public void testLoginSuccess() throws Exception {
		solo.assertCurrentActivity("Login Activity was not called",
				LoginActivity.class);
		solo.clickOnMenuItem("Login");
		solo.typeText(0, "test");
		solo.clickOnMenuItem("Password");
		solo.typeText(1, "testpw");
		solo.clickOnButton("Login");
		// solo.waitForActivity(activityClass);
	}

	/**
	 * 
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:podolski@hm.edu">Roman Podolski</a>
	 * @throws Exception
	 */
	public void testLoginWrongCredentials() throws Exception {
		solo.assertCurrentActivity("Login Activity was not called",
				LoginActivity.class);

		solo.clickOnMenuItem("Login");
		solo.typeText(0, "Foo");
		solo.clickOnMenuItem("Password");
		solo.typeText(1, "Baar");
		solo.clickOnButton("Login");

		solo.assertCurrentActivity("Login Activity is not running anmore",
				LoginActivity.class);
	}

	/**
	 * 
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:podolski@hm.edu">Roman Podolski</a>
	 * @throws Exception
	 */
	public void testRegister() throws Exception {
		solo.assertCurrentActivity("Login Activity was not called",
				LoginActivity.class);

		solo.clickOnButton("Registrieren");

		solo.waitForActivity(RegisterActivity.class);
		solo.assertCurrentActivity("Register Activity was not called",
				RegisterActivity.class);
	}

}
