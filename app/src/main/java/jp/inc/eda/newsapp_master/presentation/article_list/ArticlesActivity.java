package jp.inc.eda.newsapp_master.presentation.article_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import jp.inc.eda.newsapp_master.R;
import jp.inc.eda.newsapp_master.domain.model.Article;
import jp.inc.eda.newsapp_master.domain.usecase.GetArticleUseCase;
import jp.inc.eda.newsapp_master.infra.repository.ArticleRepository;
import jp.inc.eda.newsapp_master.infra.repository.ArticleRepositoryImpl;
import jp.inc.eda.newsapp_master.infra.repository.TokenRepository;
import jp.inc.eda.newsapp_master.presentation.BaseActivity;
import jp.inc.eda.newsapp_master.presentation.article_detail.ArticleDetailActivity;
import jp.inc.eda.newsapp_master.presentation.login.LoginActivity;

public class ArticlesActivity extends BaseActivity {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private GetArticleUseCase getArticleUseCase;
    private ArticleViewAdapter adapter;
    private ArticleRepository articleRepository;
    private TokenRepository tokenRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        init();

        toolbar.inflateMenu(R.menu.menu_articles);
        toolbar.setNavigationIcon(R.drawable.newspaper);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_logout) {
                    tokenRepository.saveToken("");
                    startActivity(new Intent(ArticlesActivity.this, LoginActivity.class));
                    finish();
                }
                return false;
            }
        });

        if (isOnLine(this)) {
            getArticleUseCase.get(new GetArticleUseCase.OnLoadingListener() {
                @Override
                public void onSuccess(final List<Article> articles) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            articleRepository.createAll(articles);
                            adapter = new ArticleViewAdapter(ArticlesActivity.this, articles, new ArticleViewAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    int id = articles.get(position).id;
                                    startActivity(ArticleDetailActivity.newIntent(ArticlesActivity.this, id));
                                }
                            });
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }

                @Override
                public void onError(final String content) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            showToast(content);
                        }
                    });
                }
            });
        }
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getArticleUseCase = new GetArticleUseCase(this);
        articleRepository = new ArticleRepositoryImpl();
        tokenRepository = new TokenRepository(this);
    }
}
