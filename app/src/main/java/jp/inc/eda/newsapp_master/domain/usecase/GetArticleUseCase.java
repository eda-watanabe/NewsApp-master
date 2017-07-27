package jp.inc.eda.newsapp_master.domain.usecase;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import jp.inc.eda.newsapp_master.domain.model.Article;
import jp.inc.eda.newsapp_master.infra.network.NewsAppClient;
import jp.inc.eda.newsapp_master.infra.repository.TokenRepository;
import jp.inc.eda.newsapp_master.util.GsonFactory;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by watanabe on 2017/07/27.
 */

public class GetArticleUseCase {

    private NewsAppClient client;
    private TokenRepository tokenRepository;

    public GetArticleUseCase(Context context) {
        client = new NewsAppClient();
        tokenRepository = new TokenRepository(context);
    }

    public void get(final OnLoadingListener listener) {
        client.articles(tokenRepository.getToken(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = GsonFactory.getInstance().get();
                    String json = response.body().string();
                    Type listType = new TypeToken<Collection<Article>>(){}.getType();
                    List<Article> res = gson.fromJson(json, listType);
                    listener.onSuccess(res);
                } else {
                    listener.onError("エラー");
                }
            }
        });
    }

    public interface OnLoadingListener {
        void onSuccess(List<Article> articles);
        void onError(String content);
    }
}
