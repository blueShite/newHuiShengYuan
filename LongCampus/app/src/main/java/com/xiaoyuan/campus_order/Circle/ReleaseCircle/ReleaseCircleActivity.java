package com.xiaoyuan.campus_order.Circle.ReleaseCircle;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.xiaoyuan.campus_order.Circle.ReleaseCircle.Adapter.ReleaseCircleAdapter;
import com.xiaoyuan.campus_order.Circle.ReleaseCircle.Interface.ReleaseCircleInterface;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.NetWorks.RequestBean;
import com.xiaoyuan.campus_order.NetWorks.RequestCallBack;
import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;
import com.xiaoyuan.campus_order.Tools.Common.utils.BitmapUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class ReleaseCircleActivity extends TakePhotoActivity implements ReleaseCircleInterface {

    @BindView(R.id.edit_release_circle_sub)
    EditText mEditReleaseCircleSub;
    @BindView(R.id.release_circle_recycle)
    RecyclerView mReleaseCircleRecycle;
    @BindView(R.id.edit_release_circle_title)
    EditText mEditReleaseCircleTitle;

    private TakePhoto mPhoto;
    private CropOptions.Builder builder;
    private List<String> mList = new ArrayList<>();
    private ReleaseCircleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_circle);
        ButterKnife.bind(this);
        mPhoto = getTakePhoto();
        customTakePhoto();
    }

    private void customTakePhoto() {

        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(102400)
                .setMaxPixel(800 >= 800 ? 800 : 800)
                .enableReserveRaw(true)
                .create();
        mPhoto.onEnableCompress(config, true);
        builder = new CropOptions.Builder();
        builder.setOutputX(800).setOutputY(800);
        builder.setWithOwnCrop(true);
        TakePhotoOptions.Builder builderOptions = new TakePhotoOptions.Builder();
        builderOptions.setWithOwnGallery(true);
        mPhoto.setTakePhotoOptions(builderOptions.create());

        GridLayoutManager layoutManager = new GridLayoutManager(ReleaseCircleActivity.this, 4);
        mReleaseCircleRecycle.setLayoutManager(layoutManager);
        mAdapter = new ReleaseCircleAdapter(mList, ReleaseCircleActivity.this, this);
        mReleaseCircleRecycle.setAdapter(mAdapter);

    }

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
        for (TImage image:result.getImages()){
            mList.add(image.getOriginalPath());
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.button_release_circle_submit)
    public void onViewClicked() {
        requestSubmit(mList,mEditReleaseCircleTitle.getText().toString(),mEditReleaseCircleSub.getText().toString());
    }


    @Override
    public void onClickAddImage() {
        mPhoto.onPickMultipleWithCrop(9 - mList.size(), builder.create());
    }

    @Override
    public void onClickDeleteImage(final int poist) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReleaseCircleActivity.this);
        builder.setTitle("提示");
        builder.setMessage("确定删除这张照片吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface anInterface, int i) {
                mList.remove(poist);
                mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    private void requestSubmit(List<String> pathList,String titleStr,String subStr){
        Map<String, String> litpic = new HashMap<>();
        String fileName = UUID.randomUUID().toString();
        for (int i = 0; i < pathList.size(); i++) {
            String bimpStr = disposeImage(BitmapUtils.getCompressedBitmap(pathList.get(i)));
            litpic.put(fileName + i, bimpStr);
        }
        Map<String,String> map = new HashMap<>();
        map.put("title",titleStr);
        map.put("text",subStr);
        map.put("uid", LoginManage.getInstance().getLoginBean().getId());
        map.put("litpic",JSON.toJSONString(litpic));
        RequestTools.getInstance().postRequest("/api/addDietCir_a.api.php", false, map, "", new RequestCallBack(ReleaseCircleActivity.this) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(RequestBean response, int id) {
                super.onResponse(response, id);
                if(response.isRes()){
                    Toasty.success(ReleaseCircleActivity.this,"提交成功").show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);

                }else {
                    Toasty.error(ReleaseCircleActivity.this,response.getMes()).show();
                }

            }
        });
    }

    public String disposeImage(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);// 参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        String imgBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
        return imgBase64;
    }
}
