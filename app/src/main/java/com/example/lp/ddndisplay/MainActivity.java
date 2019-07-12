package com.example.lp.ddndisplay;

import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.lp.ddndisplay.BroadcastReceiver.NetBroadcastReceiver;

import static com.example.lp.ddndisplay.Utils.Config.BASEINFOURL;
import static com.example.lp.ddndisplay.Utils.Config.BASEURL;
import static com.example.lp.ddndisplay.Utils.Config.TEST;

public class MainActivity extends BaseActivity implements NetBroadcastReceiver.NetEvevt{
    private static final String TAG="MainActivity";
    private WebView mWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NetBroadcastReceiver netBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setWebView();
    }


    @Override
    protected void onStart() {
        super.onStart();
        //注册网络状态监听的广播
        registerBroadcastReceiver();
    }
    /**
 * 初始化
 * */
    private void initView() {
        mSwipeRefreshLayout =findViewById(R.id.swipe_container);
        mWebView =findViewById(R.id.webview);
    }

    /**
     * 注册网络状态广播
     */
    private void registerBroadcastReceiver() {
        //注册广播
        if (netBroadcastReceiver == null) {
            netBroadcastReceiver = new NetBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netBroadcastReceiver, filter);
            //设置监听
            netBroadcastReceiver.setStatusMonitor(this);
        }
    }

/**
 *
 * 设置WebView
 * */
    private void setWebView() {
        // 设置WebView属性，能够执行JavaScript脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        // 为图片添加放大缩小功能
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.setInitialScale(10);   //100代表不缩放
        //mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //资源加载超时操作
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        mWebView.setWebViewClient(mWebWiewClient);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                Log.i(TAG, "progress i = " + i);
                super.onProgressChanged(webView, i);
            }
        });
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        //设置进度View的组合颜色，在手指上下滑时使用第一个颜色，在刷新中，会一个个颜色进行切换
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLACK, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE);
        // 设置刷新监听器
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新刷新页面
                mWebView.setVisibility(View.GONE);
                mWebView.reload();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        mWebView.loadUrl(BASEURL);
        Log.i(TAG, "loadUrl: "+BASEURL);

    }



    private final WebViewClient mWebWiewClient = new WebViewClient(){

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i(TAG, "shouldOverrideUrlLoading url : " + url);
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i(TAG, "onPageStarted....");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i(TAG, "onPageFinished....");
            mWebView.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);//关闭刷新旋转圈
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.i(TAG, "onReceivedError...");
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            Log.i(TAG, "onReceivedHttpError...");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放资源
        mWebView.destroy();
        mWebView=null;

        if (netBroadcastReceiver != null) {
            //注销广播
            unregisterReceiver(netBroadcastReceiver);
        }
    }

    /**
     * 网络状态改变通知
     * */
    @Override
    public void onNetChange(boolean netStatus) {
        if (netStatus){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    /*当检测到网络变化为正常的时候*/
                    mSwipeRefreshLayout.setRefreshing(true);//刷新动作
                    mWebView.reload();//加载
                }
            });

        }else {
            Toast.makeText(MainActivity.this,"网络异常",Toast.LENGTH_LONG).show();
        }
    }
}
