package demo.mapbar.com.googleplay;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 15-8-9.
 */
public class VolleyRequest {

    public static StringRequest sStringRequest;

    public static void requestGet(Context mContext, String url, String tag, VolleyInterface listener) {
        GpApplication.getRequestQueue().cancelAll(tag);

        sStringRequest = new StringRequest(Request.Method.GET, url, listener.loadingListener(), listener.errorListener());
        sStringRequest.setTag(tag);
        GpApplication.getRequestQueue().add(sStringRequest);
        GpApplication.getRequestQueue().start();
    }

    public static void requestPost(Context mContext, String url, String tag, Map<String,String> params ,VolleyInterface listener){

        GpApplication.getRequestQueue().cancelAll(tag);

        sStringRequest = new StringRequest(Request.Method.POST,url,listener.loadingListener(),listener.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("key","101010100");
                return params;
            }
        };
        sStringRequest.setTag(tag);
        GpApplication.getRequestQueue().add(sStringRequest);
        GpApplication.getRequestQueue().start();

    }
}
