package com.yxliu.demo.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.yxliu.demo.R;
import com.yxliu.demo.activity.base.BaseActivity;

import static android.view.KeyEvent.KEYCODE_BACK;

public class MainActivity extends BaseActivity {

    /**
     * WebView 显示网页
     */
    private WebView mWebView;

    private LinearLayout mLayout;

    /**
     * 对WebView进行配置和管理
     * 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
     * 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
     */
    private WebSettings mWebSetting;


    /**
     * 创建
     *
     * @param savedInstanceState 保存Activity的状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        //初始化
        init();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        String url = "http://www.baidu.com";
        loadWebView(url);
    }

    /**
     * 开始
     */
    @Override
    protected void onStart() {
        super.onStart();
        mWebSetting.setJavaScriptEnabled(true);
    }

    /**
     * 暂停
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 继续
     */
    @Override
    protected void onResume() {
        super.onResume();
        mWebSetting.setJavaScriptEnabled(true);
    }

    /**
     * 结束
     */
    @Override
    protected void onStop() {
        super.onStop();
        mWebSetting.setJavaScriptEnabled(false);
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
//        2、在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空

        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }

        super.onDestroy();
    }

    /**
     * 初始化
     */
    private void init() {
//        mWebView = findViewById(R.id.web_view);

        /*--------避免WebView内存泄露------*/
//       1、 不在xml中定义 WebView ，而是在需要的时候在Activity中创建，并且Context使用 getApplicationgContext()
        mLayout = findViewById(R.id.ll_web_view_home);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        mLayout.addView(mWebView);
        /*-------------------------------*/


        mWebSetting = mWebView.getSettings();
        initWebSetting();
    }

    /**
     * 加载
     */
    private void loadWebView(final String url) {
        /*处理各种通知 & 请求事件*/
        mWebView.setWebViewClient(new WebViewClient() {

            //            复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }

            //            开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //设定加载开始的操作
            }

            //            在页面加载结束时调用。我们可以关闭loading 条，切换程序动作
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //设定加载结束的操作
            }

            //            在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                //设定加载资源的操作
            }

            //            加载页面的服务器出现错误时（如404）调用
            /*
                App里面使用webView控件的时候遇到了诸如404这类的错误的时候，
                若也显示浏览器里面的那种错误提示页面就显得很丑陋了，
                那么这个时候我们的app就需要加载一个本地的错误提示页面，
                即webView如何加载一个本地的页面
            */
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //步骤1：写一个html文件（error_handle.html），用于出错时展示给用户看的提示页面
                //步骤2：将该html文件放置到代码根目录的assets文件夹下
                //步骤3：复写WebViewClient的onReceivedError方法
                //该方法传回了错误码，根据错误类型可以进行不同的错误分类处理

//                view.loadUrl("file:///android_assets/error_handle.html");
            }

            //            处理https请求
            /*webView默认是不处理https请求的，页面显示空白，需要进行如下设置：*/
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();    //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }
        });
        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        /*辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等*/
        mWebView.setWebChromeClient(new WebChromeClient() {
            //            获得网页的加载进度并显示
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

//                if (newProgress < 100) {
//                    String progress = newProgress + "%";
//                    progress.setText(progress);
//                }
            }

            //            获取Web页中的标题
            /*每个网页的页面都有一个标题，比如www.baidu.com这个页面的标题即“百度一下，你就知道”，那么如何知道当前webView正在加载的页面的title并进行设置呢？*/
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

//                titleView.setText(title)
            }

            //            支持javascript的警告框
//            一般情况下在 Android 中为 Toast，在文本里面加入\n就可以换行
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            //            支持javascript的确认框
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            //            支持javascript输入框
//            点击确认返回输入框中的值，点击取消返回 null
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });

        mWebView.loadUrl(url);
    }

    /**
     * WebView的相关设置
     */
    private void initWebSetting() {
//        mWebSetting.setPluginsEnabled(true);//支持插件

        //设置自适应屏幕，两者合用
        mWebSetting.setUseWideViewPort(true); //将图片调整到适合webView的大小
        mWebSetting.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        mWebSetting.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mWebSetting.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mWebSetting.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        /*
            缓存模式如下：
            LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
            LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
            LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
            LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        */
        mWebSetting.setCacheMode(mWebSetting.LOAD_NO_CACHE); //关闭webView中缓存
        mWebSetting.setAllowFileAccess(true); //设置可以访问文件
        mWebSetting.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        mWebSetting.setLoadsImagesAutomatically(true); //支持自动加载图片
        mWebSetting.setDefaultTextEncodingName("utf-8");//设置编码格式

        /*播放视频需要加入的设置*/
//        mWebSetting.setJavaScriptEnabled(true);
        mWebSetting.setDomStorageEnabled(true);
        mWebSetting.setUseWideViewPort(true);// 可任意比例缩放
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setBlockNetworkImage(false);

//        mWebSetting.setBuiltInZoomControls(true); // 设置显示缩放按钮
//        mWebSetting.setSupportZoom(true);/// 支持缩放
        mWebSetting.setMediaPlaybackRequiresUserGesture(true);//设置WebView是否通过手势触发播放媒体，默认是true，需要手势触发。
        mWebSetting.setPluginState(WebSettings.PluginState.ON);
//        mWebSetting.setJavaScriptCanOpenWindowsAutomatically(true);
//        mWebSetting.setAllowFileAccess(true);//允许访问文件
        /*-----------------*/
    }

    /**
     * Back键控制网页后退
     *
     * @param keyCode 状态码
     * @param event   事件
     * @return boolean
     * <p>
     * //是否可以后退
     * WebView.canGoBack()
     * //后退网页
     * WebView.goBack()
     * <p>
     * //是否可以前进
     * WebView.canGoForward()
     * //前进网页
     * WebView.goForward()
     * <p>
     * //以当前的index为起始点前进或者后退到历史记录中指定的steps
     * //如果steps为负数则为后退，正数则为前进
     * WebView.goBackOrForward(intSteps)
     * <p>
     * 在当前Activity中处理并消费掉该 Back 事件
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
