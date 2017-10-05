package forpdateam.ru.forpda.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import forpdateam.ru.forpda.R;

/**
 * Created by radiationx on 14.08.17.
 */

public abstract class RecyclerFragment extends TabFragment {
    protected SwipeRefreshLayout refreshLayout;
    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setListsBackground();
        baseInflateFragment(inflater, R.layout.fragment_base_list);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_list);
        recyclerView = (RecyclerView) findViewById(R.id.base_list);
        contentController.setMainRefresh(refreshLayout);
        //setListsBackground(recyclerView);
        recyclerView.setHasFixedSize(true);
        refreshLayoutStyle(refreshLayout);
        return view;
    }

    protected void listScrollTop() {
        new Handler().postDelayed(() -> {
            recyclerView.smoothScrollToPosition(0);
        }, 225);
    }
}
