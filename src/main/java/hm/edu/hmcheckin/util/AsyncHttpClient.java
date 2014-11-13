/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.util.Log;

/**
 * This class provides network access.
 * 
 * @author <a href="knake@hm.edu">Philipp Knake</a>
 * @version 1.0
 */
public class AsyncHttpClient extends AsyncTask<String, Void, String> {

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="knake@hm.edu">Philipp Knake</a>
	 */
	public interface AsyncHttpListener {
		void onAsyncTaskComplete(String result);
	}

	/**
	 * @author <a href="knake@hm.edu">Philipp Knake</a> TODO Javadoc
	 */
	private final AsyncHttpListener listener;
	/**
	 * @author <a href="knake@hm.edu">Philipp Knake</a> TODO Javadoc
	 */
	private String url, httpMethod = "0";
	/**
	 * @author <a href="knake@hm.edu">Philipp Knake</a> TODO Javadoc
	 */
	private HttpClient httpClient;

	/**
	 * @author <a href="knake@hm.edu">Philipp Knake</a> TODO Javadoc
	 */
	public AsyncHttpClient(AsyncHttpListener listener) {
		this.listener = listener;
	}

	/**
	 * @author <a href="knake@hm.edu">Philipp Knake</a>
	 * @param [0] url
	 * @param [1] http method [GET->0/null, POST->1]
	 * @param [2] POST request body
	 * 
	 */
	@Override
	protected String doInBackground(String... params) {
		url = params[0];
		if (params.length > 1)
			httpMethod = params[1];

		httpClient = getHttpClient();
		StringBuilder builder = new StringBuilder();
		try {
			HttpResponse response;
			if (Integer.parseInt(httpMethod) == 1) {
				// http POST
				HttpPost post = new HttpPost(url);
				post.setEntity(new ByteArrayEntity(params[2].getBytes("UTF8")));
				response = httpClient.execute(post);

			} else {
				// http GET
				response = httpClient.execute(new HttpGet(url));
			}

			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent()));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line).append("\n");
				}
			} else {
				Log.i("HttpClient", "Error Status Code: "
						+ response.getStatusLine().getStatusCode() + "  "
						+ response.getStatusLine().getReasonPhrase());
			}
			entity.consumeContent();

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("HttpClient", builder.toString());
		return builder.toString();
	}

	/**
	 * @author <a href="knake@hm.edu">Philipp Knake</a>
	 * @param result
	 *            from server
	 */
	@Override
	protected void onPostExecute(String result) {
		listener.onAsyncTaskComplete(result);
	}

	/**
	 * @author <a href="knake@hm.edu">Philipp Knake</a>
	 * @return a http client object
	 */
	private HttpClient getHttpClient() {
		if (httpClient == null) {
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);
			httpClient = new DefaultHttpClient(params);
		}
		return httpClient;
	}

}
