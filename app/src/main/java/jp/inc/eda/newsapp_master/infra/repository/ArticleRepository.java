package jp.inc.eda.newsapp_master.infra.repository;

import java.util.List;

import jp.inc.eda.newsapp_master.domain.model.Article;

/**
 * Created by watanabe on 2017/07/27.
 */

public interface ArticleRepository {

    void createAll(List<Article> articles);
    Article read(int id);
    List<Article> findAll();

}
