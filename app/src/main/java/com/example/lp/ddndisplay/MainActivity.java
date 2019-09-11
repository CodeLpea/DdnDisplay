package com.example.lp.ddndisplay;

import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Toast;

import com.example.lp.ddndisplay.BroadcastReceiver.NetBroadcastReceiver;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;

import static com.example.lp.ddndisplay.Utils.Config.BASEINFOURL;
import static com.example.lp.ddndisplay.Utils.Config.BASEURL;
import static com.example.lp.ddndisplay.Utils.Config.BASEURLNOVIODO;
import static com.example.lp.ddndisplay.Utils.Config.BEIDAGOGNXUE;
import static com.example.lp.ddndisplay.Utils.Config.BEIYIDAHAPPY;
import static com.example.lp.ddndisplay.Utils.Config.BEIYIDAZONGHE;
import static com.example.lp.ddndisplay.Utils.Config.DEBUG;
import static com.example.lp.ddndisplay.Utils.Config.LOACALTEST2;
import static com.example.lp.ddndisplay.Utils.Config.TEST;
import static com.example.lp.ddndisplay.Utils.Config.TEST2;

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

    private void WebViewSetting() {
        mWebView.setDrawingCacheEnabled(true);


        // 修改ua使得web端正确判断(加标识+++++++++++++++++++++++++++++++++++++++++++++++++++++)
//        String ua = webSettings.getUserAgentString();
//        webSettings.setUserAgentString(ua + "这里是增加的标识");

        // 网页内容的宽度是否可大于WebView控件的宽度
        mWebView.getSettings().setLoadWithOverviewMode(false);
        // 保存表单数据
        mWebView.getSettings().setSaveFormData(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        mWebView.getSettings().setDisplayZoomControls(false);

        mWebView.requestFocus(); //此句可使html表单可以接收键盘输入
        mWebView.setFocusable(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setSavePassword(true);
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 启动应用缓存
        mWebView.getSettings().setAppCacheEnabled(false);
        // 设置缓存模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置此属性，可任意比例缩放。
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mWebView.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        //  页面加载好以后，再放开图片
        //mSettings.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        mWebView.getSettings().setDomStorageEnabled(true);
        // 排版适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setUseWideViewPort(true); // 关键点
        mWebView.getSettings().setAllowFileAccess(true); // 允许访问文件
        //将图片调整到适合webview的大小
        mWebView.getSettings().setUseWideViewPort(true);
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);
       }
        // 缩放至屏幕的大小
        mWebView.getSettings().setLoadWithOverviewMode(true);
        //其他细节操作
        mWebView.getSettings().setAllowFileAccess(true); //设置可以访问文件
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        mWebView.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebView.getSettings().setDomStorageEnabled(true);//JS在HTML里面设置了本地存储localStorage，java中使用localStorage则必须打开
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true); //自适应屏幕

        //以下接口禁止(直接或反射)调用，避免视频画面无法显示：
        //mWebView.setLayerType();
        mWebView.setDrawingCacheEnabled(true);

        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        //去除QQ浏览器推广广告
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView,"QQ浏览器",View.FIND_VIEWS_WITH_TEXT);
                if(outView.size()>0){
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });

    }

/**
 *
 * 设置WebView
 * */
    private void setWebView() {

        WebViewSetting();

        mWebView.setWebViewClient(mWebWiewClient);

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                Log.i(TAG, "progress i = " + i);
                super.onProgressChanged(webView, i);
            }
            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
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
        mWebView.loadUrl(BEIDAGOGNXUE);
        Log.i(TAG, "loadUrl: "+BEIDAGOGNXUE);

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
