package me.luocaca.pluginapplication.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import me.luocaca.pluginapplication.R;
import me.luocaca.pluginapplication.entity.Course;
import me.luocaca.pluginapplication.ui.DesignActivity;

public class CourseListAdapter extends BaseQuickAdapter<Course, BaseViewHolder> implements BaseQuickAdapter.OnItemChildClickListener {

    private Activity mActivity;
    int id = R.layout.adapter_course_list;
    private ImageView banner;
    private TextView content;

    public CourseListAdapter(int layoutResId, Activity activity) {
        super(layoutResId);
        this.mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Course item) {

        Log.i(TAG, "convert: " + item);


//        Glide.with(helper.itemView.getContext()).load(item);
//        helper.setImageBitmap(R.id.banner, );
        helper.setText(R.id.content, item.courseName + "\n" + item.displayName);


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "----666", Toast.LENGTH_SHORT).show();
                DesignActivity.start(mActivity, item.courseOpenId);
            }
        });

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }


    /**
     * "courseOpenId":"gbrqan2plyjebi2f1vfkpa",
     *     			"courseName":"高等数学三：线性代数",
     *     			"studyYear":2019,
     *     			"studyTerm":"春季",
     *     			"displayName":"胡彦",
     *     			"schedule":100.0,
     *     			"isPracticeCourse":false,
     *     			"completed":33,
     *     			"totalCount":33,
     *     			"hoplinks":"/study/html/content/process/?courseOpenId=gbrqan2plyjebi2f1vfkpa&schoolCode=003",
     *     			"noStudyNoExam":"0",
     *     			"expiredTime":"\/Date(1556639999000)\/"
     */


}
