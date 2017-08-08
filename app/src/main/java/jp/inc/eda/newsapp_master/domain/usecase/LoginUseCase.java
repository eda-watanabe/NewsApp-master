package jp.inc.eda.newsapp_master.domain.usecase;

import java.io.IOException;

import jp.inc.eda.newsapp_master.infra.entity.LoginResponse;
import jp.inc.eda.newsapp_master.infra.network.NewsAppClient;
import jp.inc.eda.newsapp_master.util.GsonFactory;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by watanabe on 2017/07/27.
 */

public class LoginUseCase {

    public void login(final String id, final String pass, final OnLoadingListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NewsAppClient client = new NewsAppClient();
                client.login(id, pass, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        LoginResponse res = GsonFactory.getInstance().get().fromJson(json, LoginResponse.class);
                        if (response.isSuccessful()) {
                            listener.onSuccess(res.authToken);
                        } else {
                            // TODO: 2017/07/27
                            listener.onError(res.errorDescription);

                        }
                    }
                });
            }
        }).start();
    }

    public interface OnLoadingListener {
        void onSuccess(String authToken);
        void onError(String content);
    }
}
