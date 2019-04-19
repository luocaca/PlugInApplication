package me.luocaca.pluginapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.luocaca.pluginapplication.R;
import me.luocaca.pluginapplication.adapter.CourseListAdapter;
import me.luocaca.pluginapplication.entity.gson.CourseGson;
import me.luocaca.pluginapplication.http.HttpManager;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class CourseListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "CourseListActivity";
    RecyclerView mRecyclerView;
    CourseListAdapter mCourseListAdapter;
    List<String> stringList = new ArrayList<>();


    public static String titleText = "未登录";
    TextView title;


    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_course_list);


        initAdapter();
        findView();
        initRecycle();

        mSwipeRefreshLayout.setOnRefreshListener(this);
        this.onRefresh();


    }


    private void initAdapter() {
        mCourseListAdapter = new CourseListAdapter(R.layout.adapter_course_list,this);


        Button button = new Button(this);
        button.setText("空--");
        mCourseListAdapter.setEmptyView(button);
        mCourseListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mCourseListAdapter.setEnableLoadMore(true);
        mCourseListAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }


    private void findView() {


        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);


        title = new TextView(this);
        title.setWidth(mRecyclerView.getWidth());
        title.setHeight(80);


    }


    /**
     * 初始化recycle view
     */
    private void initRecycle() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mCourseListAdapter);
//        courseListAdapter.addData(getStringList(10));

        title.setGravity(Gravity.CENTER_VERTICAL);
        title.setText(Html.fromHtml("  " + titleText));
        mCourseListAdapter.addHeaderView(title);

    }


    public List<String> getStringList(int count) {
        for (int i = 0; i < count; i++) {
            stringList.add("数据-->" + i);
        }
        return stringList;

    }


    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, CourseListActivity.class));
    }

    /**
     * 网络请求接口
     */
    private void requestData() {

        final Request request = new Request.Builder()
                .url("http://www.ahjxjy.cn/studentstudio/ajax-course-list?type=studying&courseType=0&getschoolcode=003&studyYear=&studyTerm=&courseName=")
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


        //manager 默认把cookie 存在内存中。所以每次 请求前需要先登录
        HttpManager.getInstance().doRequest(request, new HttpManager.OnResponse() {
            @Override
            public void onResponse(Call call, Response response, String resultJson) {
                Log.i(TAG, "onResponse: " + resultJson);


                try {


                    Gson gson = new Gson();
                    CourseGson courseGson = gson.fromJson(resultJson, CourseGson.class);

                    if (courseGson.isSucceed()) {
                        mCourseListAdapter.addData(courseGson.list);
                        Log.i(TAG, "onResponse: 解析成功");
                    } else {
                        Log.e(TAG, "onResponse: " + "请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w(TAG, "onResponse: json 解析失败------");
                    Log.w(TAG, "onResponse: json 解析失败------");
                }


                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        requestData();
    }


    @Override
    public void onLoadMoreRequested() {

        try {
            mCourseListAdapter.loadMoreEnd();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}