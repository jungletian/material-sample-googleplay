package demo.mapbar.com.googleplay;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by root on 15-8-9.
 */
public class GpApplication extends Application {

    /** 新建全局请求队列*/
    private static RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
