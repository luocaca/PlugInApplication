package me.luocaca.pluginapplication.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.luocaca.pluginapplication.util.JsonFormateUtil;
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

                    Log.i(TAG, "saveFromResponse: ");


                    for (Cookie cookie : cookies) {
                        //cookieList 是否存在当前cookie
                        if (cookieList.size() == 0) {
                            cookieList.add(cookie);
                        } else {
                            boolean isFind = false;
                            Cookie cookie1 = null;
                            int findIndex = -1;
                            for (int i = 0; i < cookieList.size(); i++) {
                                if (cookieList.get(i).name().equals(cookie.name())) {
                                    isFind = true;
                                    findIndex = i;
                                    break;
                                } else {
                                    isFind = false;
                                    continue;
                                }
                            }

                            if (isFind) {
                                cookieList.set(findIndex, cookie);
                            }else {
                                cookieList.add(cookie);
                            }


                        }

                    }


//                    boolean isFind = false;

//                    if (cookieList.size() == 0) {
//                        cookieList.addAll(cookies);
//                    } else {
//                        for (int i = 0; i < cookieList.size(); i++) {
////                          isFind = false;
//
//                            for (Cookie cookie : cookies) {
//                                if (cookieList.get(i).name().equals(cookie.name())) {
//                                    cookieList.set(i, cookie);
////                                isFind = true;
//                                } else {
////                                isFind = false;
////                                cookieList.add(cookie);
//                                    cookieList.add(cookie);
//                                }
//                            }
//
////                        if (!isFind) {
////                            cookieList.add(cookies.get(i));
////                        }
//
//                        }
//
//                    }


//                    for (int i = 0; i < cookies.size(); i++) {
//
//                        boolean isFind = false;
//
//                        for (Cookie cookie : cookieList) {
//                            if (cookies.get(i).name().equals(cookie.name())) {
//                                cookieList.set(i, cookies.get(i));
//                                isFind = true;
//                            } else {
//                                isFind = false;
////                                cookieList.add(cookie);
//                            }
//                        }

//                        for (int i1 = 0; i1 < cookieList.size(); i1++) {
//
//                             if (cookies.get(i).name().equals(cookieList.get(i1).name())) {
//                                cookies.set(i, cookieList.get(i1));
//                                isFind = true;
//                            } else {
//                                cookies.add(cookieList.get(i1));
//                            }
//
//
//                        }

//
//                        if (!isFind) {
//                            cookieList.add(cookies.get(i));
//                        }
//
//                    }

//                    cookieList.addAll(cookies);


//                    Iterator iterator = cookies.iterator();
////
////                    if (iterator.hasNext())
////                    {
////                        cookieList.add((Cookie) iterator.next());
////                    }


                    for (int i = 0; i < cookieList.size(); i++) {
                        Log.i(TAG, "cookie: " + cookieList.get(i).toString());
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
                            onResponse.onResponse(call, response, JsonFormateUtil.formatJson(result));
                        }
                    });


                } else {
                    onResponse.onResponse(call, response, JsonFormateUtil.formatJson(result));
                }
            }
        });
    }


    private boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

}
