package jp.inc.eda.newsapp_master.presentation.article_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.inc.eda.newsapp_master.R;
import jp.inc.eda.newsapp_master.domain.model.Article;
import jp.inc.eda.newsapp_master.infra.repository.ArticleRepository;
import jp.inc.eda.newsapp_master.infra.repository.ArticleRepositoryImpl;

public class ArticleDetailActivity extends AppCompatActivity {

    private static final String ID = "id";

    private ImageView image;
    private TextView title;
    private TextView description;

    private ArticleRepository articleRepository;

    public static Intent newIntent(Context context, int id) {
        Intent i = new Intent(context, ArticleDetailActivity.class);
        i.putExtra(ID, id);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        int id = getIntent().getIntExtra(ID, -1);
        if (id != -1) {
            init(id);
        }
    }

    private void init(int id) {

        image = (ImageView) findViewById(R.id.image_article);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);

        articleRepository = new ArticleRepositoryImpl();
        Article a = articleRepository.read(id);

        Picasso.with(this).load(a.imageUrl).into(image);
        title.setText(a.title);
        description.setText(a.description);

    }
}
