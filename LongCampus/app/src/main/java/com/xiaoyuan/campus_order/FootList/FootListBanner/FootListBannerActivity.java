package com.xiaoyuan.campus_order.FootList.FootListBanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.xiaoyuan.campus_order.NetWorks.RequestTools;
import com.xiaoyuan.campus_order.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootListBannerActivity extends AppCompatActivity {

    @BindView(R.id.text_footListBanner_title)
    TextView mTextFootListBannerTitle;
    @BindView(R.id.web_footlist_banner)
    WebView mWebFootlistBanner;

    String uri = "";
    String res_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_list_banner);
        ButterKnife.bind(this);
        customView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebFootlistBanner.loadUrl(RequestTools.BaseUrl + uri + "?nid=" + res_id);
    }

    private void customView() {

        uri = getIntent().getStringExtra("uri");
        res_id = getIntent().getStringExtra("res_id");
        String titleName = getIntent().getStringExtra("titleName");
        if(titleName!=null&&titleName.length()>0){
            mTextFootListBannerTitle.setText(titleName);
        }
        WebSettings webSettings = mWebFootlistBanner.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        mWebFootlistBanner.setWebViewClient(new webViewClient());
    }

    // Webserver
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
