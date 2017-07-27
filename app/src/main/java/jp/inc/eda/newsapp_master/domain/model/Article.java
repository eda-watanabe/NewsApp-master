package jp.inc.eda.newsapp_master.domain.model;

import io.realm.RealmObject;

/**
 * Created by watanabe on 2017/07/27.
 */

public class Article extends RealmObject {
    public int id;
    public String iconUrl;
    public String imageUrl;
    public String title;
    public String description;

}
