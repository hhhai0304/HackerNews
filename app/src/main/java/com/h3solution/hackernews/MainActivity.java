package com.h3solution.hackernews;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.h3solution.adapter.MainAdapter;
import com.h3solution.api.ApiIml;
import com.h3solution.listener.OnDataReceived;
import com.h3solution.model.Story;
import com.h3solution.util.UtilFunctions;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    final int STORY_PER_LOADING = 10;

    @BindView(R.id.rv_stories)
    RecyclerView rvStories;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    MainAdapter adapter;
    ArrayList<Story> itemList;
    LinearLayoutManager linearLayoutManager;

    String[] topStories;
    int index;

    ApiIml storyDetail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.main_activity_title));

        initializeViews();
        initializeEvents();
        showProgressDialog("Get data");
        getBestStoryList();
    }

    private void initializeViews() {
        itemList = new ArrayList<>();
        adapter = new MainAdapter(this, itemList);
        linearLayoutManager = new LinearLayoutManager(this);
        rvStories.setLayoutManager(linearLayoutManager);
        rvStories.setAdapter(adapter);

        index = 0;
    }

    private void initializeEvents() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataRefresh();
            }
        });

        rvStories.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLastItemDisplaying(rvStories)) {
                    setProgressBarVisible(true);
                    getStoryDetailList();
                }
            }
        });
    }

    private void getBestStoryList() {
        new ApiIml().getDataFromApi("topstories.json", new OnDataReceived() {
            @Override
            public void onReceived(String result) {
                getTopStories(result);
            }

            @Override
            public void onFail(Exception e) {
                showToast(e.getMessage());
            }
        });
    }

    private void getStoryDetailList() {
        String url = "https://hacker-news.firebaseio.com/v0/item/" + topStories[index] + ".json";
        storyDetail = new ApiIml();
        storyDetail.getDataFromApi(url, new OnDataReceived() {
            @Override
            public void onReceived(String result) {
                getStoryDetail(new Gson().fromJson(result, Story.class));
            }

            @Override
            public void onFail(Exception e) {
                showToast(e.getMessage());
            }
        });
    }

    private void dataRefresh() {
        if (storyDetail != null)
            storyDetail.cancel();
        itemList.clear();
        index = 0;
        getBestStoryList();
    }

    private void onDataLoaded() {
        if (storyDetail != null)
            storyDetail.cancel();
        swipeContainer.setRefreshing(false);
        hideProgressDialog();
        setProgressBarVisible(false);
    }

    private void setProgressBarVisible(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void getTopStories(String result) {
        topStories = UtilFunctions.getTopStories(result);
        getStoryDetailList();
    }

    private void getStoryDetail(Story story) {
        itemList.add(story);
        index++;

        if (index <= topStories.length) {
            if (index % STORY_PER_LOADING == 0) {
                adapter.notifyDataSetChanged();
                onDataLoaded();
            } else {
                getStoryDetailList();
            }
        }
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }
}