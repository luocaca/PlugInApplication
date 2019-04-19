package me.luocaca.pluginapplication.adapter;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.IOException;
import java.util.List;

import me.luocaca.pluginapplication.R;
import me.luocaca.pluginapplication.entity.Cell;
import me.luocaca.pluginapplication.entity.Lesson;
import me.luocaca.pluginapplication.http.HttpManager;
import me.luocaca.pluginapplication.ui.DesignActivity;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class DesignAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private static final String TAG = "DesignAdapter";


    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DesignAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.adapter_design_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.adapter_design_expandable_lv1);

    }


    private void doConverDefault(final BaseViewHolder helper, final MultiItemEntity item) {
        Log.i(TAG, "doConverDefault: ");

        Lesson lesson = ((Lesson) item);


        helper.setText(R.id.less, lesson.title + "-->celle size = " + lesson.cells.size());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(helper.getAdapterPosition(), true);
                IExpandable multiItemEntity = (IExpandable) item;
                Toast.makeText(v.getContext(), "点击打开-->" + multiItemEntity.getLevel() + "\nsize-->" + multiItemEntity.getSubItems().size(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void doConverTwo(BaseViewHolder helper, final MultiItemEntity item) {
        final Cell cell = ((Cell) item);
        Log.i(TAG, "doConverTwo: ");

        final String sta = cell.status ? "已过" : "立马跳过";
        helper.setText(R.id.cellText, cell.title + sta);


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(v.getContext(), "" + sta, Toast.LENGTH_SHORT).show();
                //点我跳过


                //点击获取个人信息
                String url = String.format("http://www.ahjxjy.cn/study/studying/recordVideoPosition?courseOpenId=%s&cellId=%s&schoolCode=003&position=3004.29166", DesignActivity.courseOpenId, cell.id);
//                String url = String.format("http://www.ahjxjy.cn/study/studying/recordVideoPosition?courseOpenId=gbrqan2ppl1jeegaopet9g&cellId=%s&schoolCode=003&position=2004.29166",item.id ? "nv9bad6poltnuzclvgtc-a" : pasid.getText().toString());
                final Request request = new Request.Builder()
//                        .url("http://www.ahjxjy.cn/study/studying/recordVideoPosition?courseOpenId=gbrqan2ppl1jeegaopet9g&cellId=“+ pasid.getText()+”+“&schoolCode=003&position=2004.29166").method("POST", new RequestBody() {
                        .url(url).method("POST", new RequestBody() {
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

                        Toast.makeText(v.getContext(), "" + resultJson, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }


    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {


        switch (item.getItemType()) {
            case TYPE_LEVEL_0:
                doConverDefault(helper, item);


                break;

            case TYPE_LEVEL_1:
                doConverTwo(helper, item);
                break;

        }
    }


}
