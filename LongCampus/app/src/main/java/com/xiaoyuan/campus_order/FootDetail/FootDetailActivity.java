package com.xiaoyuan.campus_order.FootDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.FootDetail.Adapter.FootDetailAdapter;
import com.xiaoyuan.campus_order.FootDetail.Bean.FootDetailBean;
import com.xiaoyuan.campus_order.FootDetail.Bean.FootDetailItemBean;
import com.xiaoyuan.campus_order.FootDetail.Interface.FootDetailInterface;
import com.xiaoyuan.campus_order.FootDetail.Presenter.FootDetailPresenter;
import com.xiaoyuan.campus_order.FootList.CollectionRequest.CollectionRequest;
import com.xiaoyuan.campus_order.FootList.CollectionRequest.CollectionRequestInterface;
import com.xiaoyuan.campus_order.FootList.FootListActivity;
import com.xiaoyuan.campus_order.FootList.ShopCartRequest.ShopCartChangeInterface;
import com.xiaoyuan.campus_order.FootList.ShopCartRequest.ShopcartRequest;
import com.xiaoyuan.campus_order.FootList.SubFootList.Bean.FeatureBean;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class FootDetailActivity extends BaseActivity implements FootDetailInterface {

    @BindView(R.id.recycle_footDetail)
    RecyclerView mRecycleFootDetail;
    @BindView(R.id.button_footDetail_collection)
    Button mButtonFootDetailCollection;
    @BindView(R.id.text_footDetail_shopCart)
    TextView mTextFootDetailShopCart;

    private FeatureBean mBean;
    private FootDetailPresenter mPresenter = new FootDetailPresenter(this);
    private String isMyMenu;
    private String resId;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_detail);
        ButterKnife.bind(this);
        customView();
        if (isMyMenu.equals("0")) {
            mPresenter.requestDetail("/api/getDishCon.api.php", LoginManage.getInstance().getLoginBean().getId(), mBean.getMenu_id());
        } else {
            mPresenter.requestDetail("/api/getMealCont.api.php", LoginManage.getInstance().getLoginBean().getId(), mBean.getMenu_id());
        }

    }

    private void customView() {

        mPresenter.setContext(FootDetailActivity.this);
        mBean = (FeatureBean) getIntent().getSerializableExtra("featureBean");
        isMyMenu = getIntent().getStringExtra("isMyMenu");
        resId = getIntent().getStringExtra("resId");
        flag = getIntent().getStringExtra("flag");
        LinearLayoutManager manager = new LinearLayoutManager(FootDetailActivity.this);
        mRecycleFootDetail.setLayoutManager(manager);
        mTextFootDetailShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FootDetailActivity.this, ShopCartListActivity.class);
                intent.putExtra("resId",resId);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.button_footDetail_collection)
    public void onViewClicked() {
        if (mButtonFootDetailCollection.isSelected()) {
            Toasty.info(FootDetailActivity.this, "已经收藏过了").show();
            return;
        }
        CollectionRequest.requestCollection(LoginManage.getInstance().getLoginBean().getId(),
                mBean.getMenu_id(), FootDetailActivity.this, new CollectionRequestInterface() {
                    @Override
                    public void collectionSucess() {
                        mButtonFootDetailCollection.setSelected(true);
                    }
                });
    }

    @Override
    public void requestDetailSucess(FootDetailBean detailBean) {

        flag = detailBean.getFlag();
        resId = detailBean.getRes_id();
        if (detailBean.getIfkeep().equals("1")) {
            mButtonFootDetailCollection.setSelected(true);
        } else {
            mButtonFootDetailCollection.setSelected(false);
        }
        List<FootDetailItemBean> list;

        if (isMyMenu.equals("0")) {
            list = mPresenter.hanbleFootDetailData(detailBean);
        } else {
            list = mPresenter.hanbleMyDetailData(detailBean);
        }
        FootDetailAdapter adapter = new FootDetailAdapter(list, detailBean, FootDetailActivity.this, this);
        mRecycleFootDetail.setAdapter(adapter);
        String isCollection = getIntent().getStringExtra("isCollection");
        if (isCollection == null) {
            adapter.reloadHeader("0");
        } else {
            adapter.reloadHeader(isCollection);
        }

    }

    @Override
    public void onClickAdd(final TextView numTextView) {
        final String numStr = (Integer.parseInt(numTextView.getText().toString()) + 1) + "";
        ShopcartRequest.requestShopCart(resId, numStr,
                mBean.getMenu_id(), flag, FootDetailActivity.this, new ShopCartChangeInterface() {
                    @Override
                    public void changeShopCart() {

                        numTextView.setText(numStr);
                    }
                });
    }

    @Override
    public void onClickRedux(final TextView numTextView) {
        final String numStr = (Integer.parseInt(numTextView.getText().toString()) - 1) + "";
        ShopcartRequest.requestShopCart(resId, numStr,
                mBean.getMenu_id(), flag, FootDetailActivity.this, new ShopCartChangeInterface() {
                    @Override
                    public void changeShopCart() {
                        numTextView.setText(numStr);
                    }
                });
    }


}
