package me.luocaca.pluginapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.luocaca.pluginapplication.R;
import me.luocaca.pluginapplication.adapter.DesignAdapter;
import me.luocaca.pluginapplication.entity.Lesson;
import me.luocaca.pluginapplication.entity.gson.DesignGson;
import me.luocaca.pluginapplication.http.HttpManager;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class DesignActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "DesignActivity";


    public static String courseOpenId = "gbrqan2plyjebi2f1vfkpa";

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    DesignAdapter mDesignAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_design);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        mDesignAdapter = new DesignAdapter(new ArrayList<MultiItemEntity>());

        TextView button = new TextView(this);
        button.setGravity(Gravity.CENTER);
        button.setText("--空空乳业--");
        mDesignAdapter.setEmptyView(button);
        mDesignAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setAdapter(mDesignAdapter);


        this.onRefresh();

    }

    @Override
    public void onRefresh() {

        mSwipeRefreshLayout.setRefreshing(true);
        requestData(courseOpenId);

    }


    public void requestData(String courseOpenId) {

        Log.i(TAG, "courseOpenId =  " + courseOpenId);

        //gbrqan2plyjebi2f1vfkpa
        final Request request = new Request.Builder()
                .url(String.format("http://www.ahjxjy.cn/study/design/design?courseOpenId=%s&schoolCode=003", courseOpenId))
                .method("POST", new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return null;
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {

                    }
                })
//                        .addHeader("Cookie", "ASP.NET_SessionId=unestvsstmq0nxbe0053edeb; auth=0102535F805D48C2D608FE531FEA8711C3D6080016680073006F00660061006E006500700079007A00760067002D0078006200730034006300640032003000670000012F00FFA72219F26454785012FC48D22FD0DE6A9DFD3FB7")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "9282cee6-4c60-4761-a5af-3422be56be9a")
                .build();


        HttpManager.getInstance().doRequest(request, new HttpManager.OnResponse() {
            @Override
            public void onResponse(Call call, Response response, String resultJson) {
                Log.i(TAG, "onResponse: " + resultJson);
                Gson gson = new Gson();
                DesignGson designGson = gson.fromJson(resultJson, DesignGson.class);
                try {
                    List<Lesson> lessons = new ArrayList<>();
                    for (DesignGson.ListBean listBean : designGson.list) {
                        for (Lesson lesson : listBean.lessons) {
                            lessons.add(lesson);
                        }
                    }

                    mDesignAdapter.getData().clear();

                    mDesignAdapter.addData(lessons);
                } catch (Exception e) {
                    Log.w(TAG, "onResponse: gson 解析失败");
                }


                mSwipeRefreshLayout.setRefreshing(false);

                mDesignAdapter.expandAll();


            }
        });

    }


    public static void start(Activity activity, String id) {
        Log.i(TAG, "start: " + id);
        DesignActivity.courseOpenId = id;

        activity.startActivity(new Intent(activity, DesignActivity.class));
    }


}
