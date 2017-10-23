package com.xiaoyuan.campus_order.ShopCartList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.FootList.ShopCartRequest.ShopCartChangeInterface;
import com.xiaoyuan.campus_order.FootList.ShopCartRequest.ShopcartRequest;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.ShopCartList.Adapter.ShopCartListAdapter;
import com.xiaoyuan.campus_order.ShopCartList.Bean.ShopCartItemBean;
import com.xiaoyuan.campus_order.ShopCartList.Bean.ShopCartPriceBean;
import com.xiaoyuan.campus_order.ShopCartList.Interface.ShopCartListInterface;
import com.xiaoyuan.campus_order.ShopCartList.Presenter.ShopCartListPresenter;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.Event.FinishShopCartEvent;
import com.xiaoyuan.campus_order.ShopCartList.ShopCartOrder.ShopCartOrderActivity;
import com.xiaoyuan.campus_order.Tools.Common.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class ShopCartListActivity extends BaseActivity implements ShopCartListInterface {

    @BindView(R.id.shopcart_list_recycle)
    RecyclerView mShopcartListRecycle;

    private ShopCartListPresenter mPresenter = new ShopCartListPresenter(this);
    private String mResId;
    private List<ShopCartItemBean> mList = new ArrayList<>();
    private ShopCartListAdapter mAdapter;
    private String promptStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        customView();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onMessageEvent(FinishShopCartEvent event) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestShopCartList(LoginManage.getInstance().getLoginBean().getId(),mResId);
    }

    private void customView(){

        mPresenter.setContext(ShopCartListActivity.this);
        mResId = getIntent().getStringExtra("resId");
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShopCartListActivity.this);
        mShopcartListRecycle.setLayoutManager(layoutManager);
        mAdapter = new ShopCartListAdapter(mList,ShopCartListActivity.this,this);
        mShopcartListRecycle.setAdapter(mAdapter);
    }

    @OnClick(R.id.button_shopcart_list_submit)
    public void onViewClicked() {

        final List<ShopCartItemBean> selecList = new ArrayList<>();
        int index = 0;
        for (ShopCartItemBean bean:mList){
            if(bean.getSelectType().equals("1")){
                selecList.add(bean);
                if(bean.getItemType().equals("0")){
                    index++;
                }
            }
        }
        if(selecList.size()<1){
            Toasty.error(ShopCartListActivity.this,"请选择您要购买的商品").show();
            return;
        }
        String shopId = "";
        for (ShopCartItemBean bean:selecList){
            if(bean.getItemType().equals("1")){
                shopId = shopId+","+bean.getId();
            }
        }
        shopId = shopId.substring(1);
        requestPrompt(index+"",shopId,selecList);

    }

    @Override
    public void requestShopListSucess(List<ShopCartItemBean> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestShopListError(String error) {
        mList.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestSubmitShopCartSucess(ShopCartPriceBean priceBean, String shopId, List<ShopCartItemBean> selectList) {
        Intent intent = new Intent(ShopCartListActivity.this, ShopCartOrderActivity.class);
        intent.putExtra("shopList", (Serializable) selectList);
        intent.putExtra("priceBean",priceBean);
        intent.putExtra("shopId",shopId);
        startActivity(intent);
    }

    @Override
    public void onClickGroupItem(int poist) {
        ShopCartItemBean itemBean = mList.get(poist);
        if(itemBean.getSelectType().equals("0")){
            itemBean.setSelectType("1");
        }else {
            itemBean.setSelectType("0");
        }

        for (ShopCartItemBean bean:mList){
            if(bean.getRes_name().equals(itemBean.getRes_name())){
                bean.setSelectType(itemBean.getSelectType());
            }
        }
        mAdapter.notifyItemChanged(poist);
    }

    @Override
    public void onClickItemAdd(int poist, final TextView addText, final TextView priceText) {

        final ShopCartItemBean bean = mList.get(poist);
        final String numsStr = (Integer.parseInt(bean.getNum()) + 1) + "";
        ShopcartRequest.requestShopCart(bean.getRes_id(), numsStr, bean.getMenu_id(),bean.getFlag(), ShopCartListActivity.this, new ShopCartChangeInterface() {
            @Override
            public void changeShopCart() {
                bean.setNum(numsStr);
                addText.setText(numsStr);
                priceText.setText("¥"+AppUtils.doubleZhuanMa(bean.getOnePrice()*Double.parseDouble(numsStr)));
            }
        });
    }

    @Override
    public void onClickItemReduce(final int poist, final TextView jianText, final TextView priceText) {
        final ShopCartItemBean bean = mList.get(poist);
        if (Integer.parseInt(bean.getNum()) < 1) {
            Toasty.error(ShopCartListActivity.this, "已经是0了,不能再少了").show();
            return;
        }
        final String numsStr = (Integer.parseInt(bean.getNum()) - 1) + "";
        ShopcartRequest.requestShopCart(bean.getRes_id(), numsStr, bean.getMenu_id(),bean.getFlag(), ShopCartListActivity.this, new ShopCartChangeInterface() {
            @Override
            public void changeShopCart() {
                if(numsStr.equals("0")){
                    int j=0;
                    int groupIndex = -1;
                    ShopCartItemBean cartItemBean = mList.get(poist);
                    for (int i=0;i<mList.size();i++){
                        ShopCartItemBean itemBean = mList.get(i);
                        if(itemBean.getRes_name().equals(cartItemBean.getRes_name())){
                            if(itemBean.getItemType().equals("0")){
                                groupIndex = i;
                            }
                            j++;
                        }
                    }

                    if(j>2){
                        mList.remove(poist);
                        mAdapter.notifyDataSetChanged();
                    }else {
                        mList.remove(poist);
                        mList.remove(groupIndex);
                        mAdapter.notifyDataSetChanged();
                    }
                    return;
                }
                bean.setNum(numsStr);
                jianText.setText(numsStr);
                priceText.setText("¥"+AppUtils.doubleZhuanMa(bean.getOnePrice()*Double.parseDouble(numsStr)));
            }
        });
    }

    @Override
    public void onClickItemDelete(final int poist) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ShopCartListActivity.this);
        builder.setTitle("提示");
        builder.setMessage("确定删除此商品吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface anInterface, int i) {
                ShopCartItemBean bean = mList.get(poist);
                ShopcartRequest.requestShopCart(bean.getRes_id(), "0", bean.getMenu_id(),bean.getFlag(), ShopCartListActivity.this, new ShopCartChangeInterface() {
                    @Override
                    public void changeShopCart() {
                        //mPresenter.requestShopCartList(LoginManage.getInstance().getLoginBean().getId(),mResId);
                        int j=0;
                        int groupIndex = -1;
                        ShopCartItemBean cartItemBean = mList.get(poist);
                        for (int i=0;i<mList.size();i++){
                            ShopCartItemBean itemBean = mList.get(i);
                            if(itemBean.getRes_name().equals(cartItemBean.getRes_name())){
                                if(itemBean.getItemType().equals("0")){
                                    groupIndex = i;
                                }
                                j++;
                            }
                        }

                        if(j>2){
                            mList.remove(poist);
                            mAdapter.notifyDataSetChanged();
                        }else {
                            mList.remove(poist);
                            mList.remove(groupIndex);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    private void requestPrompt(String num, final String Id, final List<ShopCartItemBean> selecList){

        showDialog();
        Map<String,String> map = new HashMap<>();
        map.put("num",num);
        map.put("id",Id);
        RequestTools.getInstance().postRequest("/api/shopping_hint.api.php", false, map, "", new RequestCallBack(ShopCartListActivity.this) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                dismissDialog();
                Toasty.error(ShopCartListActivity.this,"请求失败").show();
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                dismissDialog();
                if(response.isRes()){
                    if(response.getMes()!=null&&response.getMes().length()>0){
                        promptStr = response.getMes();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShopCartListActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage(promptStr);
                        final String finalShopId = Id;
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {
                                mPresenter.requestSubmitShopCart(finalShopId,selecList);
                            }
                        });
                        builder.setNegativeButton("取消",null);
                        builder.show();
                    }else {
                        mPresenter.requestSubmitShopCart(Id,selecList);
                    }
                }else {
                    Toasty.error(ShopCartListActivity.this,response.getMes()).show();
                }
            }
        });
    }

}
