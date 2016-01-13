package ciandt.timetrackinutils.timetracking;

/**
 * Created by paulocn on 01/10/15.
 */

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;

import ciandt.timetrackinutils.storage.Constants;

import static java.lang.Thread.sleep;


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

    public HttpResponse RequestAponta(){

        if (Constants.kMOCK){
            try {
                sleep(2000);
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            try {

                httpPost.setEntity(new UrlEncodedFormEntity(ttParameters.mParams));
                for (NameValuePair b : ttParameters.mHeaders) {
                    Header header = new BasicHeader(b.getName(), b.getValue());
                    httpPost.addHeader(header);
                }
                response = httpClient.execute(httpPost);


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return response;
        }
    }

    public static JSONObject parseJsonResponseFromTT(HttpResponse response){
        if (response == null){
            return null;
        }

        String str = getResponseBody(response);
        JSONObject json = null;

        try {
            json = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public static String parseMessageFromTTJSON(JSONObject json){

        try {
            boolean success = json.getBoolean("success");
            if (success) {
                return json.getJSONObject("msg").getString("msg");
            }else{
                return json.getString("error");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Remove from here
    private static String getResponseBody(HttpResponse response) {

        if (response == null){
            return null;
        }
        String response_text = null;
        HttpEntity entity = null;
        try {
            entity = response.getEntity();
            response_text = _getResponseBody(entity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (entity != null) {
                try {
                    entity.consumeContent();
                } catch (IOException e1) {
                }
            }
        }
        return response_text;
    }

    private static String _getResponseBody(final HttpEntity entity) throws IOException, ParseException {

        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }

        InputStream instream = entity.getContent();

        if (instream == null) {
            return "";
        }

        if (entity.getContentLength() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(

                    "HTTP entity too large to be buffered in memory");
        }

        String charset = getContentCharSet(entity);

        if (charset == null) {

            charset = HTTP.DEFAULT_CONTENT_CHARSET;

        }

        Reader reader = new InputStreamReader(instream, charset);

        StringBuilder buffer = new StringBuilder();

        try {

            char[] tmp = new char[1024];

            int l;

            while ((l = reader.read(tmp)) != -1) {

                buffer.append(tmp, 0, l);

            }

        } finally {

            reader.close();

        }

        return buffer.toString();

    }

    private static String getContentCharSet(final HttpEntity entity) throws ParseException {

        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }

        String charset = null;

        if (entity.getContentType() != null) {

            HeaderElement values[] = entity.getContentType().getElements();

            if (values.length > 0) {

                NameValuePair param = values[0].getParameterByName("charset");

                if (param != null) {

                    charset = param.getValue();

                }

            }

        }

        return charset;

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
