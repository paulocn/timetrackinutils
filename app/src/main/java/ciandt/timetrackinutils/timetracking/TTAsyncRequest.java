package ciandt.timetrackinutils.timetracking;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;

import java.net.HttpURLConnection;

/**
 * Created by paulocn on 05/10/15.
 */
public class TTAsyncRequest extends AsyncTask<String, Void, HttpResponse> {

    public TTCallbacks mCallback;
    private TTRequester mRequester;

    public TTAsyncRequest(TTCallbacks callback) {
        mCallback = callback;
    }

    @Override
    protected HttpResponse doInBackground(String... params) {
        String username, password;
        username = params[0];
        password = params[1];
        mRequester = new TTRequester();
        mRequester.configureRequest(username, password);
        HttpResponse resp = mRequester.makeRequest();
        return resp;
    }

    @Override
    protected void onPostExecute(HttpResponse response) {
        if (response != null) {
            int statusCode = response.getStatusLine().getStatusCode();

            //Not the best place to error handle, but...
            switch (statusCode) {
                case HttpURLConnection.HTTP_OK:
                    mCallback.requestFinished(true);
                    break;
                default:
                    mCallback.requestFinished(false);
                    break;

            }

        }else {
            mCallback.requestFinished(false);
        }

    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}