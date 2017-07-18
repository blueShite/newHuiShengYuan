package com.xiaoyuan.campus_order.Circle.CircleDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.Circle.Bean.CircleHeaderBean;
import com.xiaoyuan.campus_order.Circle.Bean.CircleItemBean;
import com.xiaoyuan.campus_order.Circle.CircleDetail.Adapter.CircleDetailAdapter;
import com.xiaoyuan.campus_order.Circle.CircleDetail.Bean.CircleDetailHeaderBean;
import com.xiaoyuan.campus_order.Circle.CircleDetail.Bean.CircleDetailItemBean;
import com.xiaoyuan.campus_order.Circle.CircleDetail.Interface.CircleDetailInterface;
import com.xiaoyuan.campus_order.Circle.CircleDetail.Presenter.CircleDetailPresenter;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class CircleDetailActivity extends BaseActivity implements CircleDetailInterface {

    @BindView(R.id.recyclerview_circleDetail)
    RecyclerView mRecyclerviewCircleDetail;
    @BindView(R.id.refreshLayout_circleDetail)
    TwinklingRefreshLayout mRefreshLayoutCircleDetail;
    @BindView(R.id.edit_circle_detail)
    EditText mEditCircleDetail;
    @BindView(R.id.image_circle_detail)
    ImageView imageCircleDetail;

    private String groupId;
    private String page;

    private List<CircleDetailItemBean> mList = new ArrayList<>();
    private CircleDetailHeaderBean mBean;
    private CircleDetailPresenter mPresenter = new CircleDetailPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_detail);
        ButterKnife.bind(this);
        customView();
        page = "1";
        mPresenter.requestData(groupId, page);
        mPresenter.requestHeaderData(groupId);
    }

    private void customView() {

        mPresenter.setContext(CircleDetailActivity.this);
        CircleHeaderBean headerBean = (CircleHeaderBean) getIntent().getSerializableExtra("circleHeaderBean");
        CircleItemBean itemBean = (CircleItemBean) getIntent().getSerializableExtra("circleItemBean");
        if (headerBean != null && !headerBean.getGroup_id().isEmpty()) {
            groupId = headerBean.getGroup_id();
        }
        if (itemBean != null && !itemBean.getGroup_id().isEmpty()) {
            groupId = itemBean.getGroup_id();
        }
        imageCircleDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDianZan();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(CircleDetailActivity.this);
        mRecyclerviewCircleDetail.setLayoutManager(manager);
        CircleDetailAdapter adapter = new CircleDetailAdapter(mList, mBean, CircleDetailActivity.this);
        mRecyclerviewCircleDetail.setAdapter(adapter);
        adapter.setmInterface(this);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(CircleDetailActivity.this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mRefreshLayoutCircleDetail.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(CircleDetailActivity.this);
        mRefreshLayoutCircleDetail.setBottomView(loadingView);
        mRefreshLayoutCircleDetail.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                page = "1";
                mPresenter.requestData(groupId, page);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                int pageIndex = Integer.parseInt(page) + 1;
                page = pageIndex + "";
                mPresenter.requestData(groupId, page);
            }
        });

    }

    @OnClick(R.id.text_circle_detail_Submit)
    public void onViewClicked() {

        if (mEditCircleDetail.getText().length() < 1) {
            Toasty.error(CircleDetailActivity.this, "请输入要评论的文字").show();
            return;
        }

        mPresenter.requestComment(groupId, mEditCircleDetail.getText().toString(),
                LoginManage.getInstance().getLoginBean().getId());

    }

    @Override
    public void requestHeaderData(CircleDetailHeaderBean headerBean) {

        mBean = headerBean;
        CircleDetailAdapter adapter = (CircleDetailAdapter) mRecyclerviewCircleDetail.getAdapter();
        adapter.reloadHeader(headerBean);
    }

    @Override
    public void requestSucess(List<CircleDetailItemBean> list) {

        mRefreshLayoutCircleDetail.finishLoadmore();
        mRefreshLayoutCircleDetail.finishRefreshing();
        if (page.equals("1")) {
            mList.clear();
        }
        mList.addAll(list);
        CircleDetailAdapter adapter = (CircleDetailAdapter) mRecyclerviewCircleDetail.getAdapter();
        adapter.reoadItem(mList);
    }

    @Override
    public void requestError(String error) {
        mRefreshLayoutCircleDetail.finishLoadmore();
        mRefreshLayoutCircleDetail.finishRefreshing();

    }

    @Override
    public void requestCommentSucess(String commentStr) {

        mEditCircleDetail.setText("");
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditCircleDetail.getWindowToken(), 0);
        page = "1";
        mPresenter.requestData(groupId, "1");

    }

    @Override
    public void onClickZan() {
    }

    private void requestDianZan() {

        Map<String, String> map = new HashMap<>();
        map.put("group_id", groupId);
        map.put("u_id", LoginManage.getInstance().getLoginBean().getId());
        RequestTools.getInstance().postRequest("/api/add_click.api.php", false, map, "", new RequestCallBack(CircleDetailActivity.this) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if (response.isRes()) {
                    int zanIndex = Integer.parseInt(mBean.getNum()) + 1;
                    mBean.setNum(zanIndex + "");
                    CircleDetailAdapter adapter = (CircleDetailAdapter) mRecyclerviewCircleDetail.getAdapter();
                    adapter.reloadHeader(mBean);

                } else {
                    Toasty.error(CircleDetailActivity.this, response.getMes()).show();
                }
            }
        });
    }
}
