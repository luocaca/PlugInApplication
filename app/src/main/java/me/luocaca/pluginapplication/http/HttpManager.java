package me.luocaca.pluginapplication.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpManager {


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    public static interface OnResponse {
        void onResponse(Call call, Response response, String resultJson);
    }

    private static HttpManager instance;

    private List<Cookie> cookieList = new ArrayList<>();

    private static final String TAG = "HttpManager";

    private OkHttpClient okHttpClient;


    public static HttpManager getInstance() {
        if (instance == null) {
            instance = new HttpManager();
        }
        return instance;
    }

    public OkHttpClient getClient() {

        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                    cookieList = cookies;
                    cookieList.addAll(cookies);


//                    Iterator iterator = cookies.iterator();
////
////                    if (iterator.hasNext())
////                    {
////                        cookieList.add((Cookie) iterator.next());
////                    }


                    for (int i = 0; i < cookies.size(); i++) {
                        Log.i(TAG, "cookie: " + cookies.get(i).toString());
                    }

                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    return cookieList;
                }
            }).build();
        }

        return okHttpClient;

    }


    public void doRequest(Request request, final OnResponse onResponse) {
        this.getClient().newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.w(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {

                //异步线程处理
                final String result = response.body().string();


                if (!isMainThread()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onResponse.onResponse(call, response, result);
                        }
                    });


                } else {
                    onResponse.onResponse(call, response,result);
                }
            }
        });
    }


    private boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

}
