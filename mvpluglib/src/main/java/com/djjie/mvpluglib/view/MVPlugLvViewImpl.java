package com.djjie.mvpluglib.view;

import com.djjie.mvpluglib.presenter.MVPlugAdapter;
import com.djjie.mvpluglib.presenter.MVPlugLvPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shf2 on 2016/12/20.
 */

public class MVPlugLvViewImpl<T extends MVPlugLvPresenter,M extends MVPlugAdapter> extends MVPlugViewImpl<T> implements MVPlugLvView<T,M> {

    private M adapter;
    private List<M> adapterList;
    private boolean canBeLoad;

    @Override
    public void onLoadMore(int tabId,long pageFlag) {
        presenter.onLoadMore(tabId,pageFlag);
    }

    @Override
    public void onLoadMoreError() {
        if (adapterList != null)adapterList.get(currenTabIndex).onLoadMoreError();
        if (adapter != null)adapter.onLoadMoreError();
    }

    @Override
    public void enableLoadMore(M adapter) {
        this.adapter = adapter;
        adapter.setOnLoadMoreListener(new MVPlugAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!canBeLoad)return;
                MVPlugLvViewImpl.this.onLoadMore(currenTabIndex,getPageFlag(currenTabIndex));
                canBeLoad = false;
            }
        });
    }

    @Override
    public void enableLoadMore(final int tabId, M adapter) {
        if (adapterList == null) adapterList = new ArrayList<>();
        if (adapterList.indexOf(adapter) > -1)return;
        adapterList.add(tabId,adapter);
        adapter.setOnLoadMoreListener(new MVPlugAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!canBeLoad)return;
                MVPlugLvViewImpl.this.onLoadMore(tabId,getPageFlag(currenTabIndex));
                canBeLoad = false;
            }
        });
    }

    @Override
    public void setCanBeLoad(boolean canBeLoad) {
        this.canBeLoad = canBeLoad;
    }
}
