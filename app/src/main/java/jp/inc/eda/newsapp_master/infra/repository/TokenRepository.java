package jp.inc.eda.newsapp_master.infra.repository;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by watanabe on 2017/07/27.
 * 認証情報リポジトリ
 */

public class TokenRepository {

    private static final String TAG = "TokenRepository";
    private static final String AUTH_TOKEN = "auth_token";

    private final SharedPreferences pref;

    public TokenRepository(Context context) {
        this.pref = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        pref.edit().putString(AUTH_TOKEN, token).apply();
    }

    public String getToken() {
        return pref.getString(AUTH_TOKEN, "");
    }
}
