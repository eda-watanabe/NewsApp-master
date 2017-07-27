package jp.inc.eda.newsapp_master.domain.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by watanabe on 2017/07/27.
 */

public class Article extends RealmObject {
    @PrimaryKey
    public int id;
    public String iconUrl;
    public String imageUrl;
    public String title;
    public String description;

}
