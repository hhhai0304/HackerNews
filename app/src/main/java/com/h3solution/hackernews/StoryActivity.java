package com.h3solution.hackernews;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.TextView;

import com.h3solution.model.Story;

import butterknife.BindView;

public class StoryActivity extends BaseActivity {

    @BindView(R.id.txt_story_title)
    TextView txtStoryTitle;
    @BindView(R.id.txt_story_url)
    TextView txtStoryUrl;
    @BindView(R.id.txt_story_time_ago)
    TextView txtStoryTimeAgo;
    @BindView(R.id.txt_story_comment_count)
    TextView txtStoryCommentCount;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;

    Story story;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_story;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.story_acticity_title));

        getDataFromIntent();
        initializeViews();
    }

    private void getDataFromIntent() {
        story = getIntent().getParcelableExtra("STORY");
    }

    private void initializeViews() {
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtStoryTitle.setText(story.getTitle());
        txtStoryUrl.setText(story.getUrl());
        txtStoryTimeAgo.setText(Html.fromHtml(getString(R.string.comment_by, 0, story.getBy())));
        txtStoryCommentCount.setText(getResources().getQuantityString(R.plurals.comment, story.getDescendants()));
    }
}