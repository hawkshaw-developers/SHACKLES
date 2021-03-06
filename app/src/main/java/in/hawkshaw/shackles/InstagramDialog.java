package in.hawkshaw.shackles;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.hawkshaw.shackles.model.Constant;


public class InstagramDialog extends Dialog {

    static final float[] DIMENSIONS_LANDSCAPE = { 460, 260 };
    static final float[] DIMENSIONS_PORTRAIT = { 280, 420 };
    static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    static final int MARGIN = 4;
    static final int PADDING = 2;

    private String mUrl;
    private OAuthDialogListener mListener;
    private WebView mWebView;
    private LinearLayout mContent;
    private TextView mTitle;

    private static final String TAG = "Instagram-WebView";

    public InstagramDialog(Context context, String url,
                           OAuthDialogListener listener) {
        super(context);

        mUrl = url;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = new LinearLayout(getContext());
        mContent.setOrientation(LinearLayout.VERTICAL);
        setUpTitle();
        setUpWebView();

        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();

        final float scale = getContext().getResources().getDisplayMetrics().density;
        float[] dimensions = (size.x < size.y) ? DIMENSIONS_PORTRAIT
                : DIMENSIONS_LANDSCAPE;

        addContentView(mContent, new FrameLayout.LayoutParams(
                (int) (dimensions[0] * scale + 0.5f), (int) (dimensions[1]
                * scale + 0.5f)));
    }

    private void setUpTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mTitle = new TextView(getContext());
        mTitle.setText("Instagram");
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTypeface(Typeface.DEFAULT_BOLD);
        mTitle.setBackgroundColor(Color.BLACK);
        mTitle.setPadding(MARGIN + PADDING, MARGIN, MARGIN, MARGIN);
        mContent.addView(mTitle);
    }

    private void setUpWebView() {
        mWebView = new WebView(getContext());
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(new OAuthWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);
        mWebView.setLayoutParams(FILL);
        mContent.addView(mWebView);
    }

    private class OAuthWebViewClient extends WebViewClient {

/*        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "Redirecting URL " + url);

            if (url.startsWith(Constant.REDIRECT_URI)) {
                String urls[] = url.split("=");
                mListener.onComplete(urls[1]);
                Log.d(TAG , urls[1]);
                InstagramDialog.this.dismiss();
                return true;
            }
            return false;
        }
*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            Log.d(TAG, "Redirecting URL " + url);
            if (url.startsWith(Constant.REDIRECT_URI)) {
                String urls[] = url.split("=");
                mListener.onComplete(urls[1]);
                Log.d(TAG , urls[1]);
                InstagramDialog.this.dismiss();
                return true;
            }
            return false;
        }
/*
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Log.d(TAG, "Page error: " + description);

            super.onReceivedError(view, errorCode, description, failingUrl);
            mListener.onError(description);
            InstagramDialog.this.dismiss();
        }
*/
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.d(TAG, "Page error: " + error.toString());
            super.onReceivedError(view, request, error);
            mListener.onError(error.toString());
            InstagramDialog.this.dismiss();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "Loading URL: " + url);

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = mWebView.getTitle();
            if (title != null && title.length() > 0) {
                mTitle.setText(title);
            }
            Log.d(TAG, "onPageFinished URL: " + url);
        }

    }



}