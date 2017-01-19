/**
 * 
 */
package com.pr.im.activites;

import java.util.Calendar;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pr.im.R;
import com.pr.im.d3View.D3View;
import com.pr.im.util.DES;

/**
 * @author MZH
 *
 */
public class WebActivity extends BaseActivity {
	@D3View public WebView webView;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.acti_web1);
		initTitle();
		
		long now = Calendar.getInstance().getTimeInMillis();
		String timestamp = String.valueOf(now);
		String sign = DES.encryptDES(String.valueOf(now));
		
		String url = getIntent().getStringExtra("url");

		webView.getSettings().setDefaultTextEncodingName("utf-8"); 
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		//����WebView���ԣ��ܹ�ִ��Javascript�ű� 
		webView.getSettings().setJavaScriptEnabled(true); 
		webView.loadUrl(url);
		//����Web��ͼ 
		webView.setWebViewClient(new HelloWebViewClient ()); 
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		webView.reload();
	}
	
	
	 //Web��ͼ 
    private class HelloWebViewClient extends WebViewClient { 
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { 
            view.loadUrl(url); 
            return false; 
        }
        
    } 
}
