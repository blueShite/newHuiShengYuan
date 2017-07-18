package com.xiaoyuan.campus_order.Information.InformationDetail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.Information.Bean.InformationBean;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        initView();
    }

    private void initView(){

        mTextInformationDetailTitleName.setText(mInformationBean.getHtinfo());
        Picasso.with(InformationDetailActivity.this).load(RequestTools.BaseUrl+mInformationBean.getLitpic()).fit().centerCrop().into(mImageInformationDetail);
        mTextInformationDetailName.setText(mInformationBean.getTitle());
        mTextInformationDetailTime.setText(mInformationBean.getDate());
        mTextInformationDetailFrom.setText("内容来自:"+mInformationBean.getForm()+"  "+"作者:"+mInformationBean.getAuthor());
        mTextInformationDetailSub.setText(mInformationBean.getBody());
    }


}
