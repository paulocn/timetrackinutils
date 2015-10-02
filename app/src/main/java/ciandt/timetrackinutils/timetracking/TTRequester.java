package ciandt.timetrackinutils.timetracking;

/**
 * Created by paulocn on 01/10/15.
 */

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;



public class TTRequester {
    public HttpClient httpClient = getNewHttpClient();
    public HttpPost httpPost = new HttpPost(TTConfig.TT_ENDPOINT);
    public TTParams ttParameters;
    public HttpResponse response;

    public TTRequester(){
        initialize();
    }

    private void initialize(){
        ttParameters = new TTParams();
        ttParameters.initialize();
    }

    public void configureRequest(String user, String pass){
        ttParameters.addParam("userName", user);
        ttParameters.addParam("password", pass);
    }

    public void makeRequest(){
        new Thread(new Runnable() {
            public void run(){

                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(ttParameters.mParams));

                    //Weird
                    for (NameValuePair b : ttParameters.mHeaders) {
                        Header header = new BasicHeader(b.getName(), b.getValue());
                        httpPost.addHeader(header);
                    }


                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                try {
                    response = httpClient.execute(httpPost);
                    Log.d("PAULO", response.toString());
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

}
