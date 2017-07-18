package com.xiaoyuan.campus_order.Home.SearchSchool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.Home.SearchSchool.Bean.SearchSchoolBean;
import com.xiaoyuan.campus_order.Home.SearchSchool.Interface.SearchSchoolInterface;
import com.xiaoyuan.campus_order.Home.SearchSchool.Presenter.SearchSchoolPresenter;
import com.xiaoyuan.campus_order.Login.Bean.LoginBean;
import com.xiaoyuan.campus_order.Manage.LoginManage;
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

public class SearchSchoolActivity extends BaseActivity implements SearchSchoolInterface {

    @BindView(R.id.edit_search_school_search)
    EditText mEditSearchSchoolSearch;
    @BindView(R.id.tag_search_school_hot)
    TagFlowLayout mTagSearchSchoolHot;
    @BindView(R.id.tag_search_school_result)
    TagFlowLayout mTagSearchSchoolResult;
    @BindView(R.id.text_search_school_title)
    TextView mTextSearchSchoolTitle;

    private SearchSchoolPresenter mPresenter = new SearchSchoolPresenter(this);
    private List<SearchSchoolBean> hotList = new ArrayList<>();
    private List<SearchSchoolBean> mItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_school);
        ButterKnife.bind(this);
        customView();
        mPresenter.requestHot();
    }

    private void customView() {

        mPresenter.setContext(SearchSchoolActivity.this);

        String dingweiStr = getIntent().getStringExtra("dingwei");
        mTextSearchSchoolTitle.setText("当前位置-"+dingweiStr);
        final LayoutInflater mInflater = LayoutInflater.from(SearchSchoolActivity.this);
        mTagSearchSchoolHot.setAdapter(new TagAdapter<SearchSchoolBean>(hotList) {
            @Override
            public View getView(FlowLayout parent, int position, SearchSchoolBean bean) {
                TextView tv = (TextView) mInflater.inflate(R.layout.view_textview,
                        mTagSearchSchoolHot, false);
                tv.setText(bean.getSch_name());
                return tv;
            }

        });
        mTagSearchSchoolResult.setAdapter(new TagAdapter<SearchSchoolBean>(mItemList) {
            @Override
            public View getView(FlowLayout parent, int position, SearchSchoolBean bean) {
                TextView tv = (TextView) mInflater.inflate(R.layout.view_textview,
                        mTagSearchSchoolHot, false);
                tv.setText(bean.getSch_name());
                return tv;
            }

        });

        mTagSearchSchoolHot.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                //EventBus.getDefault().post(new SearchSchoolEvent(hotList.get(position).getId()));
                LoginBean bean = LoginManage.getInstance().getLoginBean();
                bean.setStuid(hotList.get(position).getId());
                LoginManage.getInstance().saveLoginBean(bean);
                finish();
                return true;
            }
        });

        mTagSearchSchoolResult.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                //EventBus.getDefault().post(new SearchSchoolEvent(mItemList.get(position).getId()));
                LoginBean bean = LoginManage.getInstance().getLoginBean();
                bean.setSch_id(mItemList.get(position).getId());
                LoginManage.getInstance().saveLoginBean(bean);
                finish();
                return true;
            }
        });

    }

    @OnClick(R.id.button_search_school_search)
    public void onViewClicked() {
        if (mEditSearchSchoolSearch.getText().length() < 1) {
            Toasty.error(SearchSchoolActivity.this, "请输入您要搜索的学校").show();
            return;
        }
        mPresenter.requestSearch(mEditSearchSchoolSearch.getText().toString());
    }

    @Override
    public void requestHotSucess(List<SearchSchoolBean> list) {
        hotList.addAll(list);
        mTagSearchSchoolHot.getAdapter().notifyDataChanged();
    }

    @Override
    public void requestSearchSucess(List<SearchSchoolBean> list) {
        mItemList.clear();
        mItemList.addAll(list);
        mTagSearchSchoolResult.getAdapter().notifyDataChanged();
    }
}
