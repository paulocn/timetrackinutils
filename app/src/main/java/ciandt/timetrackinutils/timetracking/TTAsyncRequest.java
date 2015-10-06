package ciandt.timetrackinutils.timetracking;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by paulocn on 05/10/15.
 */
public class TTAsyncRequest extends AsyncTask<String, Void, JSONObject> {

    public TTCallbacks mCallback;
    private TTRequester mRequester;

    public TTAsyncRequest(TTCallbacks callback) {
        mCallback = callback;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String username, password;
        username = params[0];
        password = params[1];
        mRequester = new TTRequester();
        mRequester.configureRequest(username, password);
        HttpResponse resp = mRequester.RequestAponta();

        return mRequester.parseJsonResponseFromTT(resp);
    }

    @Override
    protected void onPostExecute(JSONObject responseJSON) {
        if (responseJSON != null) {
            boolean responseSuccess = false;
            try {
                responseSuccess = responseJSON.getBoolean("success");
            } catch (JSONException e) {
                responseSuccess = false;
                e.printStackTrace();
            }

            if (responseSuccess) {
                //Anything to do here?
            }

            mCallback.requestFinished(responseJSON);

        }else {
            mCallback.requestFinished(null);
        }

    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}