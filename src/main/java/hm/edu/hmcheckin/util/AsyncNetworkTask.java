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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.util.Log;

/**
 * TODO Javadoc
 */
public class AsyncNetworkTask extends AsyncTask<String, Void, String> {

	/**
	 * TODO Javadoc
	 */
	private String url;
	/**
	 * TODO Javadoc
	 */
	private String request;
	/**
	 * TODO Javadoc
	 */
	private HttpClient httpClient;
	/**
	 * TODO Javadoc
	 */
	private ResponseParserv2 parser;

	/**
	 * TODO Javadoc
	 * 
	 * @param [0] url
	 * @param [1] request Type
	 * 
	 */
	@Override
	protected String doInBackground(String... params) {
		url = params[0];
		request = params[1];
		httpClient = getHttpClient();
		StringBuilder builder = new StringBuilder();
		try {
			HttpResponse response;
			response = httpClient.execute(new HttpGet(url));
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line).append("\n");
				}
			} else {
				Log.i("HttpClient", "Error Status Code: " + response.getStatusLine().getStatusCode() + "  "
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
	 * TODO Javadoc
	 * 
	 * @param result
	 *            from server
	 */
	@Override
	protected void onPostExecute(String result) {
		parser = new ResponseParserv2(result);
		RequestType type = RequestType.valueOf(request.toUpperCase());
		switch (type) {
		case GET_FRIENDS:
			parser.parseFriendList();
			break;
		case GET_INVITATIONS:
			parser.parsePullInvitations();
			break;
		case GET_SEARCH:
			parser.parseFriendSearch();
			break;
		case GET_HOTSPOTS:
			parser.parseHotSpots();
			break;
		case GET_DEFAULT:
			// do nothing
			break;
		default:
			break;
		}
	}

	/**
	 * TODO Javadoc
	 * 
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

	/**
	 * TODO Javadoc
	 */
	public enum RequestType {
		/**
		 * TODO Javadoc
		 */
		GET_FRIENDS,
		/**
		 * TODO Javadoc
		 */
		GET_INVITATIONS,
		/**
		 * TODO Javadoc
		 */
		GET_SEARCH,
		/**
		 * TODO Javadoc
		 */
		GET_DEFAULT, GET_HOTSPOTS;
	}
}
