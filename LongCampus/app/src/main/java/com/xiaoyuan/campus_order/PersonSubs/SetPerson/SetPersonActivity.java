package com.xiaoyuan.campus_order.PersonSubs.SetPerson;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Login.Bean.LoginBean;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.PersonSubs.Address.AddressListActivity;
import com.xiaoyuan.campus_order.PersonSubs.SetPerson.Adapter.NationalityBean;
import com.xiaoyuan.campus_order.PersonSubs.SetPerson.Adapter.SetPersonAdapter;
import com.xiaoyuan.campus_order.PersonSubs.SetPerson.Bean.SetPersonBean;
import com.xiaoyuan.campus_order.PersonSubs.SetPerson.Interface.SetPersonInterface;
import com.xiaoyuan.campus_order.PersonSubs.SetPerson.Presenter.SetPersonPresenter;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.Tools.Common.utils.BitmapUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class SetPersonActivity extends TakePhotoActivity implements SetPersonInterface {

    @BindView(R.id.set_person_recycler)
    RecyclerView mSetPersonRecycler;

    private SetPersonPresenter mPresenter = new SetPersonPresenter(this);
    private List<SetPersonBean> mList = new ArrayList<>();
    private SetPersonAdapter mPersonAdapter;
    private List<NationalityBean> nationalityBeanList = new ArrayList<>();

    private String[] sexArray = new String[]{"男", "女"};
    private String[] nationalityArray;
    int mYear, mMonth, mDay;

    private TakePhoto mPhoto;
    private CropOptions.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_person);
        ButterKnife.bind(this);
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        customView();
        customTakePhoto();
        requestNavion(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mList.clear();
        mList.addAll(mPresenter.addPersonData());
        mPersonAdapter.notifyDataSetChanged();
    }

    //定制视图
    private void customView() {

        mPresenter.setContext(SetPersonActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SetPersonActivity.this);
        mSetPersonRecycler.setLayoutManager(layoutManager);
        mPersonAdapter = new SetPersonAdapter(mList, SetPersonActivity.this, this);
        mSetPersonRecycler.setAdapter(mPersonAdapter);
        mPersonAdapter.reloadPersonHeader(LoginManage.getInstance().getLoginBean().getHeadimg());

    }

    //定制照片选择器
    private void customTakePhoto() {

        mPhoto = getTakePhoto();
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(102400)
                .setMaxPixel(800)
                .enableReserveRaw(true)
                .create();
        mPhoto.onEnableCompress(config, true);
        builder = new CropOptions.Builder();
        builder.setOutputX(800).setOutputY(800);
        builder.setWithOwnCrop(true);
        TakePhotoOptions.Builder builderOptions = new TakePhotoOptions.Builder();
        builderOptions.setWithOwnGallery(true);
        mPhoto.setTakePhotoOptions(builderOptions.create());

    }

    //选取照片的操作
    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String headeStr = result.getImages().get(0).getOriginalPath();
        String bimpStr = disposeImage(BitmapUtils.getCompressedBitmap(headeStr));
        requestSubmitHeader(bimpStr,headeStr);
    }

    //实现弹出框
    private void showEditAlert(final int index, String hintStr) {
        LayoutInflater factory = LayoutInflater.from(SetPersonActivity.this);//提示框
        final View view = factory.inflate(R.layout.view_alert_edittext, null);//这里必须是final的
        final EditText edit = (EditText) view.findViewById(R.id.alert_editText);//获得输入框对象
        edit.setHint(hintStr);
        new AlertDialog.Builder(SetPersonActivity.this)
                .setTitle("提示")//提示框标题
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface anInterface, int i) {
                        if (edit.getText().toString().length() < 1) {
                            Toasty.error(SetPersonActivity.this, "请输入您要填写的文字").show();
                            return;
                        }
                        switch (index){
                            case 0:
                                requestSubmitPerson("tname",edit.getText().toString(),index);
                                break;
                            case 2:
                                requestSubmitPerson("tel",edit.getText().toString(),index);
                                break;
                            case 3:
                                requestSubmitPerson("stuid",edit.getText().toString(),index);
                                break;
                            case 6:
                                requestSubmitPerson("school",edit.getText().toString(),index);
                                break;
                            case 8:
                                requestSubmitPerson("qq",edit.getText().toString(),index);
                                break;
                            case 9:
                                requestSubmitPerson("wechat",edit.getText().toString(),index);
                                break;
                            case 10:
                                requestSubmitPerson("email",edit.getText().toString(),index);
                                break;
                            default:
                                break;
                        }

                    }
                })
                .setNegativeButton("取消", null).create().show();
    }

    //item点击事件
    @Override
    public void onClickPersonItem(final int itemIndex) {
        switch (itemIndex) {
            case 0:
                showEditAlert(itemIndex, "请输入名称");
                break;
            case 1:
                int selectIndex;
                if(mList.get(1).getSub().equals("男")){
                    selectIndex = 0;
                }else {
                    selectIndex = 1;
                }
                new AlertDialog.Builder(this)
                        .setTitle("选择性别")
                        .setSingleChoiceItems(sexArray, selectIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    requestSubmitPerson("sex","男",1);
                                }else {
                                    requestSubmitPerson("sex","女",1);
                                }
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case 2:
                showEditAlert(itemIndex, "请输入电话");
                break;
            case 3:
                showEditAlert(itemIndex, "请输入学号");
                break;
            case 4:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int i, int i1, int i2) {
                        mYear = i;
                        mMonth = i1;
                        mDay = i2;
                        StringBuffer string = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" ");
                        requestSubmitPerson("birth",String.valueOf(string),4);
//                        mList.get(itemIndex).setSub(String.valueOf(string));
//                        mPersonAdapter.notifyItemChanged(itemIndex+1);
                    }
                }, mYear, mMonth, mDay).show();
                break;
            case 5:
                Intent intent = new Intent(SetPersonActivity.this, AddressListActivity.class);
                startActivity(intent);
                break;
            case 6:
                showEditAlert(itemIndex, "请输入学校");
                break;
            case 7:
                int index = 0;

                if(mList.get(7).getSub()==null||mList.get(7).getSub().length()<1){
                    index = 0;
                }else {
                    for (int i=0;i<nationalityBeanList.size();i++){
                        NationalityBean bean = nationalityBeanList.get(i);
                        if(mList.get(7).getSub().equals(bean.getNation())){
                            index = i;
                            break;
                        }
                    }

                }
                new AlertDialog.Builder(this)
                        .setTitle("选择民族")
                        .setSingleChoiceItems(nationalityArray, index, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestSubmitPerson("natid",nationalityBeanList.get(which).getId(),7);
                                mPersonAdapter.notifyItemChanged(itemIndex+1);
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case 8:
                showEditAlert(itemIndex, "请输入QQ");
                break;
            case 9:
                showEditAlert(itemIndex, "请输入微信");
                break;
            case 10:
                showEditAlert(itemIndex, "请输入邮箱");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClickHeaderView() {

        mPhoto.onPickMultipleWithCrop(1, builder.create());
    }

    @Override
    public void requestSubmitSucess() {

    }

    //请求民族数据
    private void requestNavion(boolean isFirst){
        RequestTools.getInstance().postRequest("/api/getNationality.api.php", false, null, "", new RequestCallBack(SetPersonActivity.this) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    Log.e("民族数据",response.getData());
                    nationalityBeanList = JSON.parseArray(response.getData(),NationalityBean.class);
                    List<String> stringList = new ArrayList<String>();
                    for (NationalityBean bean:nationalityBeanList){
                        stringList.add(bean.getNation());
                    }
                    nationalityArray = stringList.toArray(new String[stringList.size()]);
                }else {
                    Toasty.error(SetPersonActivity.this,response.getMes()).show();
                }
            }
        });
    }

    //请求设置个人信息
    private void requestSubmitPerson(String key, final String vaule, final int index){
        Map<String,String> map = new HashMap<>();
        map.put("key",key);
        map.put("value",vaule);
        map.put("id",LoginManage.getInstance().getLoginBean().getId());
        RequestTools.getInstance().postRequest("/api/updata_datum.api.php", false, map, "", new RequestCallBack(SetPersonActivity.this) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    SetPersonBean bean = mList.get(index);
                    if(index==7){
                        for (NationalityBean naBean:nationalityBeanList){
                            if(naBean.getId().equals(vaule)){
                                bean.setSub(naBean.getNation());
                                break;
                            }
                        }
                    }else {
                        bean.setSub(vaule);
                    }
                    mPersonAdapter.notifyItemChanged(index + 1);
                    LoginBean loginBean = LoginManage.getInstance().getLoginBean();
                    switch (index){
                        case 0:
                            loginBean.setTname(vaule);
                            break;
                        case 1:
                            loginBean.setSex(vaule);
                            break;
                        case 2:
                            loginBean.setTel(vaule);
                            break;
                        case 3:
                            loginBean.setStuid(vaule);
                            break;
                        case 4:
                            loginBean.setBirth(vaule);
                            break;
                        case 6:
                            loginBean.setSchool(vaule);
                            break;
                        case 7:
                            for (NationalityBean naBean:nationalityBeanList){
                                if(naBean.getId().equals(vaule)){
                                    loginBean.setNation(naBean.getNation());
                                    loginBean.setNatid(naBean.getId());
                                    break;
                                }
                            }
                            break;
                        case 8:
                            loginBean.setQq(vaule);
                            break;
                        case 9:
                            loginBean.setWechat(vaule);
                            break;
                        case 10:
                            loginBean.setEmail(vaule);
                            break;
                        default:
                            break;
                    }
                    LoginManage.getInstance().saveLoginBean(loginBean);
                }else {
                    Toasty.error(SetPersonActivity.this,response.getMes()).show();
                }

            }
        });
    }

    private void requestSubmitHeader(String headerPath, final String imagePath){
        Map<String,String> map = new HashMap<>();
        map.put("headimg",headerPath);
        map.put("imgname","header"+LoginManage.getInstance().getLoginBean().getId());
        map.put("uid",LoginManage.getInstance().getLoginBean().getId());
        RequestTools.getInstance().postRequest("/api/UpdataPersonImg.api.php", false, map, "", new RequestCallBack(SetPersonActivity.this) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    mPersonAdapter.reloadPersonHeader(imagePath);
                    LoginBean bean = LoginManage.getInstance().getLoginBean();
                    bean.setHeadimg(imagePath);
                    LoginManage.getInstance().saveLoginBean(bean);
                }else {
                    Toasty.error(SetPersonActivity.this,response.getMes()).show();
                }
            }
        });
    }

    //图片处理
    public String disposeImage(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);// 参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        String imgBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
        return imgBase64;
    }
}
