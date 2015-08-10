package demo.mapbar.com.googleplay;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import demo.mapbar.com.googleplay.fragment.CheeseListFragment;

public class HomeActivity extends AppCompatActivity {
    public static final String WEATHER_REQUEST = "weather_request";

    public static final String IMAGE_REQUEST = "image_request";
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout; // 抽屉

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this); // 小刀

        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = ButterKnife.findById(this, R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        volleyGetRequest();

        volleyPostRequest();

        volleyImageRequest();

//        volleyImageLoader();

//        volleyNetworkImageView();
    }

    private void volleyNetworkImageView(int errorImage, int defaultImage, int id) {


        NetworkImageView networkImageView = (NetworkImageView) findViewById(id);
        String url = "https://www.baidu.com/img/bdlogo.png";
        ImageLoader imageLoader = new ImageLoader(GpApplication.getRequestQueue(),new BitmapCache());

        networkImageView.setDefaultImageResId(defaultImage);
        networkImageView.setErrorImageResId(errorImage);
        networkImageView.setImageUrl(url,imageLoader);

    }

    private void volleyImageLoader(ImageView imageView, int successId, int failureId) {
        String url = "https://www.baidu.com/img/bdlogo.png";
        ImageLoader imageLoader = new ImageLoader(GpApplication.getRequestQueue(),new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, successId, failureId);
        imageLoader.get(url,listener);
    }


    private void volleyImageRequest() {
        GpApplication.getRequestQueue().cancelAll(IMAGE_REQUEST);
        String url = "https://www.baidu.com/img/bdlogo.png";
        int maxHeight = 100;
        int maxWidth = 100;
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {

            }
        }, maxWidth, maxHeight, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        imageRequest.setTag(IMAGE_REQUEST);
        GpApplication.getRequestQueue().add(imageRequest);
    }

    private void volleyPostRequest() {
        String url = "http://www.weather.com.cn/adat/cityinfo/101010100.html";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(HomeActivity.this, "s : " + s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            // post request invoke this
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> hashMap = new HashMap<>();
                hashMap.put("key","101010100");
                return hashMap;
            }
        };
        request.setTag(WEATHER_REQUEST);

        GpApplication.getRequestQueue().add(request);

    }

    private void volleyGetRequest() {
        String url = "http://www.weather.com.cn/adat/cityinfo/101010100.html";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(HomeActivity.this, "s : " + s, Toast.LENGTH_SHORT).show();
                Log.d("ZTJ",s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setTag("weather_request");

        GpApplication.getRequestQueue().add(request);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment(), "Category 1");
        adapter.addFragment(new CheeseListFragment(), "Category 2");
        adapter.addFragment(new CheeseListFragment(), "Category 3");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
    /**
     * 设置导航View
     * @param navigationView 导航
     */
    private void setupDrawerContent(NavigationView navigationView) {
        // 设置导航item点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        // activity life-cycle together
        GpApplication.getRequestQueue().cancelAll(WEATHER_REQUEST);
        super.onStop();
    }
}
