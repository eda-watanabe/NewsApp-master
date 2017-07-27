package jp.inc.eda.newsapp_master.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import jp.inc.eda.newsapp_master.R;
import jp.inc.eda.newsapp_master.domain.usecase.LoginUseCase;
import jp.inc.eda.newsapp_master.infra.repository.TokenRepository;
import jp.inc.eda.newsapp_master.presentation.BaseActivity;
import jp.inc.eda.newsapp_master.presentation.article_list.ArticlesActivity;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private EditText id;
    private EditText pass;
    private ProgressBar progress;

    private LoginUseCase loginUseCase;
    private TokenRepository tokenRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {

        loginUseCase = new LoginUseCase();
        tokenRepository = new TokenRepository(this);

        id = (EditText) findViewById(R.id.edit_id);
        pass = (EditText) findViewById(R.id.edit_pass);
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progress.getVisibility() == View.GONE) {
                    progress.setVisibility(View.VISIBLE);

                    String i = id.getText().toString();
                    String p = pass.getText().toString();
                    if (i.isEmpty() || p.isEmpty()) {
                        showToast("入力してください");
                        return;
                    }
                    loginUseCase.login(i, p, new LoginUseCase.OnLoadingListener() {
                        @Override
                        public void onSuccess(String authToken) {
                            tokenRepository.saveToken(authToken);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(LoginActivity.this, ArticlesActivity.class));
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onError(final String content) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast(content);
                                }
                            });
                        }
                    });
                } else {
                    showToast("ログイン中です");
                }
            }
        });
    }
}
