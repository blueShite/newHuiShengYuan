package com.xiaoyuan.campus_order.Information.InformationDetail;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.Information.Bean.InformationBean;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class InformationDetailActivity extends BaseActivity {

    @BindView(R.id.text_information_detail_titleName)
    TextView mTextInformationDetailTitleName;
    @BindView(R.id.text_information_detail_name)
    TextView mTextInformationDetailName;
    @BindView(R.id.text_information_detail_time)
    TextView mTextInformationDetailTime;
    @BindView(R.id.text_information_detail_from)
    TextView mTextInformationDetailFrom;
    @BindView(R.id.image_information_detail)
    ImageView mImageInformationDetail;
    @BindView(R.id.text_information_detail_sub)
    TextView mTextInformationDetailSub;

    InformationBean mInformationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        ButterKnife.bind(this);
        mInformationBean = (InformationBean) getIntent().getSerializableExtra("informationBean");
        requestInformation(mInformationBean.getId());
    }

    private void requestInformation(String inforId){

        Map<String,String> map = new HashMap<>();
        map.put("htinfo_id",inforId);
        RequestTools.getInstance().postRequest("/api/getHTinfoDet.api.php", false, map, "", new RequestCallBack(InformationDetailActivity.this) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    InformationBean bean = JSON.parseArray(response.getData(),InformationBean.class).get(0);
                    mTextInformationDetailTitleName.setText(bean.getHtinfo());
                    Picasso.with(InformationDetailActivity.this).load(RequestTools.BaseUrl+bean.getLitpic()).fit().centerCrop().into(mImageInformationDetail);
                    mTextInformationDetailName.setText(bean.getTitle());
                    mTextInformationDetailTime.setText(bean.getDate());
                    mTextInformationDetailFrom.setText("内容来自:"+bean.getForm()+"  "+"作者:"+bean.getAuthor());
                    mTextInformationDetailSub.setText(bean.getBody());
                }else {
                    Toasty.error(InformationDetailActivity.this,response.getMes()).show();
                }
            }
        });

    }
}
