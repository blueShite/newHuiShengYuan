package com.xiaoyuan.campus_order.ShopCart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.FootDetail.FootDetailActivity;
import com.xiaoyuan.campus_order.Home.Bean.CanteenBean;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.ShopCart.Adapter.ShopCartAdapter;
import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartBean;
import com.xiaoyuan.campus_order.ShopCart.Bean.ShopCartHeaderBean;
import com.xiaoyuan.campus_order.ShopCart.Interface.ShopCartInterface;
import com.xiaoyuan.campus_order.ShopCart.Presenter.ShopCartPresenter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ShopCartActivity extends BaseActivity implements ShopCartInterface {


    @BindView(R.id.text_shopCart_titleName)
    TextView mTextShopCartTitleName;
    @BindView(R.id.recyclerview_shopCart)
    RecyclerView mRecyclerviewShopCart;
    @BindView(R.id.refreshLayout_shopCart)
    TwinklingRefreshLayout mRefreshLayoutShopCart;
    @BindView(R.id.text_shopCart_submit)
    TextView mTextShopCartSubmit;
    @BindView(R.id.text_shopCart_sub)
    TextView mTextShopCartSub;

    private CanteenBean mBean;
    private ShopCartPresenter mPresenter = new ShopCartPresenter(this);
    private List<ShopCartBean> mList = new ArrayList<>();
    private List<ShopCartBean> addList = new ArrayList<>();

    double totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);
        ButterKnife.bind(this);
        mBean = (CanteenBean) getIntent().getSerializableExtra("canteenBean");
        customView();
        mPresenter.requestItem("/api/getDishList.api.php","1",mBean.getRes_id(),"1");
    }

    private void customView(){

        mTextShopCartTitleName.setText(mBean.getRes_names());
        mPresenter.setContext(ShopCartActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(ShopCartActivity.this);
        mRecyclerviewShopCart.setLayoutManager(manager);
        ShopCartAdapter shopCartAdapter = new ShopCartAdapter(mList,new ShopCartHeaderBean(),this,ShopCartActivity.this);
        mRecyclerviewShopCart.setAdapter(shopCartAdapter);

        //定制刷新加载
        SinaRefreshView headerView = new SinaRefreshView(ShopCartActivity.this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mRefreshLayoutShopCart.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(ShopCartActivity.this);
        mRefreshLayoutShopCart.setBottomView(loadingView);
        mRefreshLayoutShopCart.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                mRefreshLayoutShopCart.finishRefreshing();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayoutShopCart.finishLoadmore();
                    }
                },2000);
            }
        });
    }


    @OnClick(R.id.text_shopCart_submit)
    public void onViewClicked() {
    }

    @Override

    public void requestItemSuccess(List<ShopCartBean> list, ShopCartHeaderBean headerBean,String tag) {

        ShopCartAdapter adapter = (ShopCartAdapter) mRecyclerviewShopCart.getAdapter();
        mList = list;
        mList.add(0,new ShopCartBean());
        adapter.reloadItem(mList,headerBean);
        if(tag.equals("1")){
            adapter.reloadHeaderData(R.drawable.tuijian,"今日推荐");
        }else if(tag.equals("2")){
            adapter.reloadHeaderData(R.drawable.youhui,"特价优惠");
        }else if(tag.equals("3")){
            adapter.reloadHeaderData(R.drawable.taocan,"特色套餐");
        }else{
            adapter.reloadHeaderData(R.drawable.caidan,"我的菜单");
        }
    }

    @Override
    public void requestItemError(String error) {

    }

    @Override
    public void onClickHeader(int poist) {
        switch (poist){
            case 0:
                Intent todayIntent = new Intent(ShopCartActivity.this,TodayFeatureActivity.class);
                todayIntent.putExtra("TitleName","今日推荐");
                todayIntent.putExtra("path","/api/getDishList.api.php");
                todayIntent.putExtra("res_id",mBean.getRes_id());
                todayIntent.putExtra("index","1");
                startActivity(todayIntent);
                //mPresenter.requestItem("/api/getDishList.api.php","1",mBean.getRes_id(),"1");
                break;
            case 1:
                Intent saleIntent = new Intent(ShopCartActivity.this,TodayFeatureActivity.class);
                saleIntent.putExtra("TitleName","特价优惠");
                saleIntent.putExtra("path","/api/getDishList.api.php");
                saleIntent.putExtra("res_id",mBean.getRes_id());
                saleIntent.putExtra("index","2");
                startActivity(saleIntent);
                //mPresenter.requestItem("/api/getDishList.api.php","1",mBean.getRes_id(),"2");
                break;
            case 2:
                Intent freatureIntent = new Intent(ShopCartActivity.this,TodayFeatureActivity.class);
                freatureIntent.putExtra("TitleName","特色套餐");
                freatureIntent.putExtra("path","/api/getMeal.api.php");
                freatureIntent.putExtra("res_id",mBean.getRes_id());
                freatureIntent.putExtra("index","3");
                startActivity(freatureIntent);
                //mPresenter.requestItem("/api/getMeal.api.php","1",mBean.getRes_id(),"3");
                break;
            case 3:
                mPresenter.requestItem("/api/getDishList.api.php","1",mBean.getRes_id(),"4");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClickItem(int poist) {

        Intent intent = new Intent(ShopCartActivity.this, FootDetailActivity.class);
        intent.putExtra("shopCartBean",mList.get(poist));
        startActivity(intent);

    }

    @Override
    public void onClickItemAdd(int poist, TextView addText) {

        ShopCartBean bean = mList.get(poist);
        totalPrice = Double.parseDouble(bean.getPrice())+totalPrice;
        mTextShopCartSub.setText("总价:"+totalPrice+"元");
        Integer num = Integer.parseInt(bean.getAddNum())+1;
        bean.setAddNum(num+"");
        addText.setText(num+"");
        for (int i=0;i<addList.size();i++){

            if(bean.getMenu_id().equals(addList.get(i).getMenu_id())){
                bean.setAddNum(num+"");
                return;
            }
        }
        bean.setAddNum("1");
        addList.add(bean);

    }

    @Override
    public void onClickItemReduce(int poist, TextView jianText) {

        ShopCartBean bean = mList.get(poist);
        if(Integer.parseInt(bean.getAddNum())<1){
            Toasty.error(ShopCartActivity.this,"数量为零了,不能再少了").show();
            return;
        }
        totalPrice = totalPrice-Double.parseDouble(bean.getPrice());
        mTextShopCartSub.setText("总价:"+totalPrice+"元");
        Integer num = Integer.parseInt(bean.getAddNum())-1;
        bean.setAddNum(num+"");
        jianText.setText(num+"");
        for (int i=0;i<addList.size();i++){

            if(bean.getMenu_id().equals(addList.get(i).getMenu_id())){
                bean.setAddNum(num+"");
                return;
            }
        }
    }
}
