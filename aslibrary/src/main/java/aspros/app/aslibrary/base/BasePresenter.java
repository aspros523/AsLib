package aspros.app.aslibrary.base;

import android.content.Context;

import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<M, T> {
    public Context context;
    public M mModel;
    public T mView;
    public CompositeSubscription compositeSubscription=new CompositeSubscription();

    public void setVM(T v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public abstract void onStart();

    public void onDestroy() {
        compositeSubscription.unsubscribe();
    }
}
