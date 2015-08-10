package demo.mapbar.com.googleplay;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by root on 15-8-10.
 */
public abstract class VolleyInterface {

    public Context mContext;
    public Response.Listener mListener;
    public Response.ErrorListener mErrorListener;

    public VolleyInterface(Context mContext, Response.Listener listener, Response.ErrorListener errorListener) {
        this.mErrorListener = errorListener;
        this.mListener = listener;
        this.mContext = mContext;
    }

    public abstract void onSuccess(String result);

    public abstract void onFailure(VolleyError volleyError);

    public Response.Listener loadingListener(){
        mListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                onSuccess(s);
            }
        };
        return mListener;
    }

    public Response.ErrorListener errorListener(){
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onFailure(volleyError);
            }
        };
        return mErrorListener;
    }


}
