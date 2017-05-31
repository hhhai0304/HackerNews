package com.h3solution.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.h3solution.hackernews.BaseActivity;
import com.h3solution.hackernews.R;
import com.h3solution.hackernews.StoryActivity;
import com.h3solution.model.Story;
import com.h3solution.util.UtilFunctions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainAdapter
 * Created by HHHai on 30-05-2017.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ItemViewHolder> {
    private BaseActivity context;
    private ArrayList<Story> itemList;

    public MainAdapter(BaseActivity context, ArrayList<Story> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        final Story story = itemList.get(position);
        holder.txtTitle.setText(story.getTitle());
        holder.txtPoints.setText(story.getScore() >= 0 ? "+" + story.getScore() : String.valueOf(story.getScore()));

        String timeAndBy = UtilFunctions.getTimeDifferent(story.getTime()) + story.getBy();
        String baseUrl;
        try {
            URL url = new URL(story.getUrl());
            baseUrl = url.getHost();
        } catch (MalformedURLException e) {
            baseUrl = story.getUrl();
        }

        holder.txtUrl.setText(baseUrl);
        holder.txtBy.setText(timeAndBy);
        holder.txtCount.setText(String.valueOf(position + 1));
        holder.txtCommentCount.setText(String.valueOf(story.getDescendants()));
        holder.itemMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("STORY", story);
                context.startActivity(intent);
            }
        });

        holder.btnOpenBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(story.getUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_main)
        LinearLayout itemMain;
        @BindView(R.id.txt_count)
        TextView txtCount;
        @BindView(R.id.txt_score)
        TextView txtPoints;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_url)
        TextView txtUrl;
        @BindView(R.id.txt_by)
        TextView txtBy;
        @BindView(R.id.txt_comment_count)
        TextView txtCommentCount;
        @BindView(R.id.btn_open_browser)
        ImageButton btnOpenBrowser;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}