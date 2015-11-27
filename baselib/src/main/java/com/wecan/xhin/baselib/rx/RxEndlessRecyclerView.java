
package com.wecan.xhin.baselib.rx;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class RxEndlessRecyclerView {

    public static Observable<Integer> reachesEnd(final RecyclerView recyclerView) {
        return RxRecyclerView.scrollEvents(recyclerView)
                .filter(new Func1<RecyclerViewScrollEvent, Boolean>() {
                    @Override
                    public Boolean call(RecyclerViewScrollEvent recyclerViewScrollEvent) {
                        return recyclerView.getLayoutManager() != null;
                    }
                })
                .concatMap(new Func1<RecyclerViewScrollEvent, Observable<? extends Integer>>() {
                    @Override
                    public Observable<? extends Integer> call(RecyclerViewScrollEvent recyclerViewScrollEvent) {
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) { // also GridLayoutManager
                            return lastVisibleItemPosition((LinearLayoutManager) layoutManager);
                        }
                        if (layoutManager instanceof StaggeredGridLayoutManager) {
                            return lastVisibleItemPositions((StaggeredGridLayoutManager) layoutManager);
                        }
                        return Observable.empty();
                    }
                })
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer >= recyclerView.getLayoutManager().getItemCount() - 1;
                    }
                })
                .distinctUntilChanged();// 只有在值发生变化时才引发事件(polling)

    }

    public static Observable<Integer> lastVisibleItemPosition(LinearLayoutManager lm) {
        return Observable.just(lm.findLastVisibleItemPosition());
    }

    public static Observable<Integer> lastVisibleItemPositions(final StaggeredGridLayoutManager lm) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int[] positions = new int[lm.getSpanCount()];
                lm.findLastVisibleItemPositions(positions);
                for (int i : positions) {
                    if (subscriber.isUnsubscribed()) {
                        break;
                    } else {
                        subscriber.onNext(i);
                    }
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

}
