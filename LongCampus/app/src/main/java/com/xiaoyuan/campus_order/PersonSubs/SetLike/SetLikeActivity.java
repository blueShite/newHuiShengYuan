package com.xiaoyuan.campus_order.PersonSubs.SetLike;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.Login.Bean.LoginBean;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.PersonSubs.SetLike.Bean.SetLikeBean;
import com.xiaoyuan.campus_order.PersonSubs.SetLike.Interface.SetLikeInterface;
import com.xiaoyuan.campus_order.PersonSubs.SetLike.Presenter.SetLikePresenter;
import com.xiaoyuan.campus_order.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SetLikeActivity extends BaseActivity implements SetLikeInterface {

    @BindView(R.id.text_setLike_submit)
    TextView mTextSetLikeSubmit;
    @BindView(R.id.button_setLike_TopLike)
    Button mButtonSetLikeTopLike;
    @BindView(R.id.button_setLike_TopHate)
    Button mButtonSetLikeTopHate;
    @BindView(R.id.tagLayout_Top)
    TagFlowLayout mTagLayoutTop;
    @BindView(R.id.editText_setLike_search)
    EditText mEditTextSetLikeSearch;
    @BindView(R.id.tagLayout_bottom)
    TagFlowLayout mTagLayoutBottom;
    @BindView(R.id.button_setLike_bottomFootType)
    Button mButtonSetLikeBottomFootType;
    @BindView(R.id.button_setLike_bottomFlavor)
    Button mButtonSetLikeBottomFlavor;

    private SetLikePresenter mPresenter = new SetLikePresenter(this);
    //食材数组
    private List<SetLikeBean> footTypeList = new ArrayList<>();
    //口味数组
    private List<SetLikeBean> flavorList = new ArrayList<>();
    //喜好数组
    private List<SetLikeBean> likeList = new ArrayList<>();
    //厌恶数组
    private List<SetLikeBean> hateList = new ArrayList<>();
    //上面的数据源
    private List<SetLikeBean> topList = new ArrayList<>();
    //下面的数据源
    private List<SetLikeBean> bottomList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_like);
        ButterKnife.bind(this);
        customView();
        mPresenter.requestFootType();
    }

    private void customView() {

        mPresenter.setContext(SetLikeActivity.this);
        mButtonSetLikeTopLike.setSelected(true);
        mButtonSetLikeTopHate.setSelected(false);
        mButtonSetLikeBottomFootType.setSelected(true);
        mButtonSetLikeBottomFlavor.setSelected(false);
        String likeStr = LoginManage.getInstance().getLoginBean().getLike_id();
        final String hateStr = LoginManage.getInstance().getLoginBean().getHate();

        if (likeStr != null && likeStr.length() > 0) {
            String[] likeArray = likeStr.split(",");
            if (likeArray.length > 0) {
                for (int i = 0; i < likeArray.length; i++) {
                    SetLikeBean bean = new SetLikeBean();
                    bean.setFood(likeArray[i]);
                    likeList.add(bean);
                }
                topList.addAll(likeList);
            }
        }

        if (hateStr != null && hateStr.length() > 0) {
            String[] hateArray = hateStr.split(",");
            if (hateArray.length > 0) {
                for (int i = 0; i < hateArray.length; i++) {
                    SetLikeBean bean = new SetLikeBean();
                    bean.setFood(hateArray[i]);
                    hateList.add(bean);
                }
            }
        }

        final LayoutInflater mInflater = LayoutInflater.from(SetLikeActivity.this);
        mTagLayoutTop.setAdapter(new TagAdapter<SetLikeBean>(topList) {
            @Override
            public View getView(FlowLayout parent, int position, SetLikeBean bean) {
                TextView tv = (TextView) mInflater.inflate(R.layout.view_textview,
                        mTagLayoutTop, false);
                tv.setText(bean.getFood());
                return tv;
            }

        });
        mTagLayoutBottom.setAdapter(new TagAdapter<SetLikeBean>(bottomList) {
            @Override
            public View getView(FlowLayout parent, int position, SetLikeBean bean) {
                TextView tv = (TextView) mInflater.inflate(R.layout.view_textview,
                        mTagLayoutTop, false);
                if (mButtonSetLikeBottomFootType.isSelected()) {
                    tv.setText(bean.getFood());
                } else {
                    tv.setText(bean.getPrefer());
                }
                return tv;
            }
        });
        mTagLayoutBottom.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (mButtonSetLikeTopLike.isSelected()) {
                    SetLikeBean bean = bottomList.get(position);
                    if (bean.getFood() != null && bean.getFood().length() > 0) {
                        for (SetLikeBean likeBean : topList) {
                            if (likeBean.getFood().equals(bean.getFood())) {
                                return true;
                            }
                        }
                        for (SetLikeBean hateBean : hateList){
                            if (hateBean.getFood().equals(bean.getFood())) {
                                Toasty.error(SetLikeActivity.this,"添加过厌恶的口味不能添加到喜欢").show();
                                return true;
                            }
                        }
                        topList.add(bean);
                    } else {
                        bean.setFood(bean.getPrefer());
                        for (SetLikeBean likeBean : topList) {
                            if (likeBean.getFood().equals(bean.getFood())) {
                                return true;
                            }
                        }
                        for (SetLikeBean hateBean : hateList){
                            if (hateBean.getFood().equals(bean.getFood())) {
                                Toasty.error(SetLikeActivity.this,"添加过厌恶的口味不能添加到喜欢").show();
                                return true;
                            }
                        }
                        topList.add(bean);
                    }
                    likeList.add(bean);
                } else {
                    SetLikeBean bean = bottomList.get(position);
                    if (bean.getFood() != null && bean.getFood().length() > 0) {
                        for (SetLikeBean likeBean : topList) {
                            if (likeBean.getFood().equals(bean.getFood())) {
                                return true;
                            }
                        }
                        for (SetLikeBean likeBean : likeList){
                            if (likeBean.getFood().equals(bean.getFood())) {
                                Toasty.error(SetLikeActivity.this,"添加过喜好的口味不能添加到厌恶").show();
                                return true;
                            }
                        }
                        topList.add(bean);
                    } else {
                        bean.setFood(bean.getPrefer());
                        for (SetLikeBean likeBean : topList) {
                            if (likeBean.getFood().equals(bean.getFood())) {
                                return true;
                            }
                        }
                        for (SetLikeBean likeBean : likeList){
                            if (likeBean.getFood().equals(bean.getFood())) {
                                Toasty.error(SetLikeActivity.this,"添加过喜好的口味不能添加到厌恶").show();
                                return true;
                            }
                        }
                        topList.add(bean);
                    }
                    hateList.add(bean);
                }
                mTagLayoutTop.getAdapter().notifyDataChanged();
                return true;
            }
        });

        mTagLayoutTop.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(mButtonSetLikeTopLike.isSelected()){

                    topList.remove(position);
                    likeList.remove(position);

                }else {
                    topList.remove(position);
                    hateList.remove(position);
                }
                mTagLayoutTop.getAdapter().notifyDataChanged();
                return true;
            }
        });

    }

    @OnClick({R.id.button_setLike_TopLike, R.id.button_setLike_TopHate, R.id.button_setLike_bottomFootType, R.id.button_setLike_bottomFlavor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_setLike_TopLike:
                mButtonSetLikeTopLike.setSelected(true);
                mButtonSetLikeTopHate.setSelected(false);
                topList.clear();
                topList.addAll(likeList);
                mTagLayoutTop.getAdapter().notifyDataChanged();
                break;
            case R.id.button_setLike_TopHate:
                mButtonSetLikeTopLike.setSelected(false);
                mButtonSetLikeTopHate.setSelected(true);
                topList.clear();
                topList.addAll(hateList);
                mTagLayoutTop.getAdapter().notifyDataChanged();
                break;
            case R.id.button_setLike_bottomFootType:
                mButtonSetLikeBottomFootType.setSelected(true);
                mButtonSetLikeBottomFlavor.setSelected(false);
                if (footTypeList.size() < 1) {
                    mPresenter.requestFootType();
                } else {
                    bottomList.clear();
                    bottomList.addAll(footTypeList);
                    mTagLayoutBottom.getAdapter().notifyDataChanged();
                }
                break;
            case R.id.button_setLike_bottomFlavor:
                mButtonSetLikeBottomFootType.setSelected(false);
                mButtonSetLikeBottomFlavor.setSelected(true);
                if (flavorList.size() < 1) {
                    mPresenter.requestFlavor();
                } else {
                    bottomList.clear();
                    bottomList.addAll(flavorList);
                    mTagLayoutBottom.getAdapter().notifyDataChanged();
                }
                break;
        }
    }

    @OnClick(R.id.text_setLike_submit)
    public void onViewClicked() {

        String likeData = "";
        String hadeData = "";
        if(likeList.size()>0){
            StringBuilder likeSb = new StringBuilder();
            for (SetLikeBean bean : likeList) {// 增强for循环.
                likeSb.append(bean.getFood() + ",");
            }
            String likeResult = likeSb.toString();
            likeData = likeResult.substring(0, likeResult.length()-1);
        }
        if(hateList.size()>0){
            StringBuilder sb = new StringBuilder();
            for (SetLikeBean bean : hateList) {// 增强for循环.
                sb.append(bean.getFood() + ",");
            }
            String result = sb.toString();
            hadeData = result.substring(0, result.length()-1);
        }
        mPresenter.requestSubmitLikeHate(likeData,hadeData,LoginManage.getInstance().getLoginBean().getId());

    }

    @Override
    public void requestLikeSubmit(String likeStr) {
        LoginBean bean = LoginManage.getInstance().getLoginBean();
        bean.setLike_id(likeStr);
        LoginManage.getInstance().saveLoginBean(bean);
        Toasty.success(SetLikeActivity.this,"提交成功").show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    @Override
    public void requestHateSubmit(String hateStr) {
        LoginBean bean = LoginManage.getInstance().getLoginBean();
        bean.setHate(hateStr);
        LoginManage.getInstance().saveLoginBean(bean);
        Toasty.success(SetLikeActivity.this,"提交成功").show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    @Override
    public void requestSubmitAll(String likeStr, String hateStr) {
        LoginBean bean = LoginManage.getInstance().getLoginBean();
        bean.setLike_id(likeStr);
        bean.setHate(hateStr);
        LoginManage.getInstance().saveLoginBean(bean);
        Toasty.success(SetLikeActivity.this,"提交成功").show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    @Override
    public void requestFootTypeList(List<SetLikeBean> list) {

        footTypeList.clear();
        footTypeList.addAll(list);

        bottomList.clear();
        bottomList.addAll(list);
        mTagLayoutBottom.getAdapter().notifyDataChanged();
    }

    @Override
    public void requestFlavorList(List<SetLikeBean> list) {
        flavorList.clear();
        flavorList.addAll(list);

        bottomList.clear();
        bottomList.addAll(list);
        mTagLayoutBottom.getAdapter().notifyDataChanged();
    }
}
