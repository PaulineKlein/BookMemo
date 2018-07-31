package com.pklein.bookmemo;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pklein.bookmemo.data.Book;
import com.pklein.bookmemo.tools.JsonUtils;
import com.pklein.bookmemo.tools.NetworkUtils;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadMoreActivity extends AppCompatActivity {

    private static final String TAG= ReadMoreActivity.class.getSimpleName();
    private static final String LIFECYCLE_BOOK_FILTER_KEY = "filter";
    private Book mbookToReadMore;

    @BindView(R.id.title_book_readMore)    TextView title_book_readMore;
    @BindView(R.id.content_book_readMore) TextView content_book_readMore;
    @BindView(R.id.tv_error_message_display_readMore) TextView mErrorMessageDisplay;
    @BindView(R.id.loading_indicator) ProgressBar mLoadingIndicator;
    @BindView(R.id.toolbar_container_wiki)CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        setContentView(R.layout.activity_read_more);
        ButterKnife.bind(this);

        if (savedInstanceState != null) { // if a filter is already saved, read it
            if (savedInstanceState.containsKey(LIFECYCLE_BOOK_FILTER_KEY)) {
                mbookToReadMore = savedInstanceState.getParcelable(LIFECYCLE_BOOK_FILTER_KEY);
            }
        } else {
            if(this.getIntent().hasExtra("BookToLookFor")) {
                mbookToReadMore = this.getIntent().getExtras().getParcelable("BookToLookFor");
            }
        }

        title_book_readMore.setText(mbookToReadMore.getTitle());
        mCollapsingToolbarLayout.setTitle(mbookToReadMore.getTitle());
        loadWikiData(mbookToReadMore.getTitle());
    }

    private void loadWikiData(String filter) {
        new FetchWikiTask().execute(filter);
    }

    public class FetchWikiTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            /* If there's no filter, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String title = params[0];
            String content = "";

            if(!NetworkUtils.isconnected((ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE)))
            {
                return null;
            }
            else {
                title = title.replace(" ","%20");
                URL movieRequestUrl = NetworkUtils.buildListUrl(title, getApplicationContext());
                try {
                    String jsonWikiResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                    content = JsonUtils.parseWikiJson(jsonWikiResponse);
                    return content;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(String content) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (content != null && !content.equals("")) {
                showWikiView();
                content_book_readMore.setText(Html.fromHtml(content));
            } else {
                Log.e(TAG,"ERROR");
                showErrorMessage();
            }
        }
    }

    private void showWikiView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        content_book_readMore.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        content_book_readMore.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIFECYCLE_BOOK_FILTER_KEY, mbookToReadMore);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
