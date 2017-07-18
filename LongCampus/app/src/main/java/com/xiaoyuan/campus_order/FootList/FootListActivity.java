package com.xiaoyuan.campus_order.FootList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoyuan.campus_order.FootList.Event.FootListShopEvent;
import com.xiaoyuan.campus_order.FootList.FootListBanner.FootListBannerActivity;
import com.xiaoyuan.campus_order.FootList.Interface.FootListInterface;
import com.xiaoyuan.campus_order.FootList.Presenter.FootListPresenter;
import com.xiaoyuan.campus_order.FootList.SubFootList.FeatureFragment;
import com.xiaoyuan.campus_order.FootList.SubFootList.MyPackageFragment;
import com.xiaoyuan.campus_order.FootList.SubFootList.RecommendFragment;
import com.xiaoyuan.campus_order.FootList.SubFootList.SaleFragment;
import com.xiaoyuan.campus_order.Home.Bean.CanteenBean;
import com.xiaoyuan.campus_order.Information.Adapter.PicassoImageLoader;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartHeaderBean;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartListActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class FootListActivity extends SupportActivity implements FootListInterface {

    @BindView(R.id.layout_footList)
    FrameLayout mLayoutFootList;
    @BindView(R.id.text_footList_title)
    TextView mTextFootListTitle;
    @BindView(R.id.banner_shopCart)
    Banner mBannerShopCart;
    @BindView(R.id.relative_shopCart_jinrituijian)
    RelativeLayout mRelativeShopCartJinrituijian;
    @BindView(R.id.relative_shopCart_tejiayouhui)
    RelativeLayout mRelativeShopCartTejiayouhui;
    @BindView(R.id.relative_shopCart_tesetaocan)
    RelativeLayout mRelativeShopCartTesetaocan;
    @BindView(R.id.relative_shopCart_wodecaidan)
    RelativeLayout mRelativeShopCartWodecaidan;
    @BindView(R.id.image_shopCart_tuijian)
    ImageView mImageShopCartTuijian;
    @BindView(R.id.text_shopCartHeader_name)
    TextView mTextShopCartHeaderName;
    @BindView(R.id.text_footList_shopCartSub)
    TextView mTextFootListShopCartSub;
    @BindView(R.id.text_footList_shopCart)
    TextView mTextFootListShopCart;

    private RecommendFragment mRecommendFragment;
    private FeatureFragment mFeatureFragment;
    private SaleFragment mSaleFragment;
    private MyPackageFragment mMyPackageFragment;
    private CanteenBean mBean;
    private FootListPresenter mPresenter = new FootListPresenter(this);
    private String mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mBean = (CanteenBean) getIntent().getSerializableExtra("canteenBean");
        customView();
        mPresenter.requestFootListHeader(mBean.getRes_id());
        //mPresenter.requestShopCartNum(LoginManage.getInstance().getLoginBean().getId(),mBean.getRes_id());

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe
    public void onMessageEvent(FootListShopEvent event) {
        //mPresenter.requestShopCartNum(LoginManage.getInstance().getLoginBean().getId(),mBean.getRes_id());
    }

    private void customView() {

        mBannerShopCart.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBannerShopCart.setImageLoader(new PicassoImageLoader());

        mPresenter.setContext(FootListActivity.this);
        mRecommendFragment = RecommendFragment.newInstance(mBean);
        mFeatureFragment = FeatureFragment.newInstance(mBean);
        mSaleFragment = SaleFragment.newInstance(mBean);
        mMyPackageFragment = MyPackageFragment.newInstance(mBean);
        loadMultipleRootFragment(R.id.layout_footList, 0, mRecommendFragment, mSaleFragment, mFeatureFragment, mMyPackageFragment);
        mTextFootListShopCart.setVisibility(View.VISIBLE);
        mTextFootListShopCartSub.setVisibility(View.INVISIBLE);

        mTextFootListShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FootListActivity.this, ShopCartListActivity.class);
                intent.putExtra("resId",mBean.getRes_id());
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.relative_shopCart_jinrituijian, R.id.relative_shopCart_tejiayouhui, R.id.relative_shopCart_tesetaocan, R.id.relative_shopCart_wodecaidan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_shopCart_jinrituijian:
                mTextShopCartHeaderName.setText("今日推荐");
                mImageShopCartTuijian.setImageResource(R.drawable.tuijian);
                mRecommendFragment = findFragment(RecommendFragment.class);
                if (mRecommendFragment != null) {
                    showHideFragment(mRecommendFragment);
                }
                break;
            case R.id.relative_shopCart_tejiayouhui:
                mTextShopCartHeaderName.setText("特价优惠");
                mImageShopCartTuijian.setImageResource(R.drawable.youhui);
                mSaleFragment = findFragment(SaleFragment.class);
                if (mSaleFragment != null) {
                    showHideFragment(mSaleFragment);
                }
                break;
            case R.id.relative_shopCart_tesetaocan:
                mTextShopCartHeaderName.setText("特色套餐");
                mImageShopCartTuijian.setImageResource(R.drawable.taocan);
                mFeatureFragment = findFragment(FeatureFragment.class);
                if (mFeatureFragment != null) {
                    showHideFragment(mFeatureFragment);
                }
                break;
            case R.id.relative_shopCart_wodecaidan:
                mTextShopCartHeaderName.setText("我的菜单");
                mImageShopCartTuijian.setImageResource(R.drawable.caidan);
                mMyPackageFragment = findFragment(MyPackageFragment.class);
                if (mMyPackageFragment != null) {
                    showHideFragment(mMyPackageFragment);
                }
                break;
        }
    }

    @Override
    public void requestSucess(ShopCartHeaderBean headerBean, final String uri) {

        mUri = uri;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < headerBean.getRes_img().size(); i++) {
            list.add(RequestTools.BaseUrl + headerBean.getRes_img().get(i));
        }
        mBannerShopCart.setImages(list);
        mBannerShopCart.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int posit) {
                Intent intent = new Intent(FootListActivity.this, FootListBannerActivity.class);
                intent.putExtra("uri",mUri);
                intent.putExtra("res_id",mBean.getRes_id());
                intent.putExtra("titleName",mBean.getRes_names());
                startActivity(intent);
            }
        });
        mBannerShopCart.start();
        mTextFootListTitle.setText(headerBean.getRes_names());
    }

    @Override
    public void requestShopNum(String shopNum) {
        if(Integer.parseInt(shopNum)>0){
            mTextFootListShopCart.setVisibility(View.VISIBLE);
            mTextFootListShopCartSub.setVisibility(View.INVISIBLE);
        }else {
            mTextFootListShopCart.setVisibility(View.INVISIBLE);
            mTextFootListShopCartSub.setVisibility(View.VISIBLE);
        }
    }
}
