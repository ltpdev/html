package com.example.yls.adhtml;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button Button1,
            Button2;
    private WebView mWebView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        Button1= (Button) findViewById(R.id.btn_1);
        Button2= (Button) findViewById(R.id.btn_2);
        mWebView1= (WebView) findViewById(R.id.webview_1);
        mWebView1.getSettings().setJavaScriptEnabled(true);
        mWebView1.loadUrl("file:///android_asset/index.html");
       mWebView1.addJavascriptInterface(MainActivity.this,"android");

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView1.loadUrl("javascript:javacalljs()");
            }
        });

        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView1.loadUrl("javascript:javacalljswith("+"'http://www.hupu.com'"+")");
            }
        });


    }
    @JavascriptInterface
    public void startFunction(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"Jerry",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @JavascriptInterface
    public void startFunction(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this).setMessage(text).show();
            }
        });
    }
    @JavascriptInterface
    public void add(int a, int b) {
                String c=String.valueOf(a+b);
                Toast.makeText(MainActivity.this,c,Toast.LENGTH_SHORT).show();
    }
    @JavascriptInterface
    public void call(String callNum){
                Intent intent=new Intent();
                intent.setAction("android.intent.action.CALL");
                intent.setData(Uri.parse("tel:"+callNum));
                startActivity(intent);
    }

    @JavascriptInterface
    public void sendMessage(String message,String messageNum){

            Intent in4 = new Intent();
            in4.setAction(Intent.ACTION_SENDTO);
            in4.setData(Uri.parse("smsto:" + messageNum));
            in4.putExtra("sms_body", message);
            startActivity(in4);

    }
}
