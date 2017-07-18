package com.xiaoyuan.campus_order.Information.Adapter;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by longhengyu on 2017/4/21.
 */

public class PicassoImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        String pathStr = (String) path;
        if(!pathStr.isEmpty()){
            Picasso.with(context).load(pathStr).fit().centerCrop().into(imageView);
        }

    }
}
