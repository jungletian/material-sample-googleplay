package demo.mapbar.com.googleplay;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by root on 15-8-10.
 */
public class BitmapCache implements ImageLoader.ImageCache {

    public LruCache<String,Bitmap> cache;
    public int max = 10 * 1024 * 1024;

    public BitmapCache() {

        this.cache = new LruCache<String, Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String key) {
        return cache.get(key);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {

    }
}
