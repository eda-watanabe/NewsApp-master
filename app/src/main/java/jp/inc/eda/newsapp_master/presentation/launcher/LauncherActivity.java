package jp.inc.eda.newsapp_master.presentation.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import jp.inc.eda.newsapp_master.MyApp;
import jp.inc.eda.newsapp_master.R;
import jp.inc.eda.newsapp_master.infra.repository.TokenRepository;
import jp.inc.eda.newsapp_master.presentation.BaseActivity;
import jp.inc.eda.newsapp_master.presentation.login.LoginActivity;

public class LauncherActivity extends BaseActivity {

    private TextView textVersionName;

    private TokenRepository tokenRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        init();
        showVersionName();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tokenRepository.getToken().isEmpty()) {
                    startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                    finish();
                } else {
                    // TODO: 2017/07/27
                }
            }
        }, 3000);//ms
    }

    private void init() {
        textVersionName = (TextView) findViewById(R.id.text_version_name);
        tokenRepository = new TokenRepository(this);
    }

    private void showVersionName() {
        textVersionName.setText(MyApp.getVersionName(this));
    }
}
