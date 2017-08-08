package jp.inc.eda.newsapp_master.presentation.article_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.inc.eda.newsapp_master.R;
import jp.inc.eda.newsapp_master.domain.model.Article;


/**
 * Created by watanabe on 2017/07/27.
 */
public class ArticleViewAdapter extends
        RecyclerView.Adapter<ArticleViewAdapter.ViewHolder> {

    private static final String TAG = ArticleViewAdapter.class.getSimpleName();

    private Context context;
    private List<Article> list;
    private OnItemClickListener onItemClickListener;

    public ArticleViewAdapter(Context context, List<Article> list,
                              OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView title;
        TextView description;
        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.image_icon);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
        }

        public void bind(final Article model,
                         final OnItemClickListener listener) {
            Picasso.with(itemView.getContext()).load(model.iconUrl).into(icon);
            title.setText(model.title);
            description.setText(model.description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_article, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article item = list.get(position);
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}