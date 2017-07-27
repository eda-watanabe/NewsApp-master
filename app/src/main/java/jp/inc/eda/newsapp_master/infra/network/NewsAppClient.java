package jp.inc.eda.newsapp_master.infra.network;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by watanabe on 2017/07/27.
 */

public class NewsAppClient {

    private static final String TAG = "NewsAppClient";
    private static final String URL = "https://mysterious-retreat-42661.herokuapp.com/";
    private static final String LOGIN = "api/login.php?user_id=%s&password=%s";
    private static final String ARTICLES = "api/article.php?auth_token=%s";

    private OkHttpClient client;

    public NewsAppClient() {
        client = new OkHttpClient().newBuilder()
                .readTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .build();
    }

    public void login(String id, String pass, Callback callback) {
        Request request = new Request.Builder()
                .url(URL + String.format(LOGIN, id, pass))
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void articles(String token, Callback callback) {
        Request request = new Request.Builder()
                .url(URL + String.format(ARTICLES, token))
                .get().build();
        client.newCall(request).enqueue(callback);
    }
}
