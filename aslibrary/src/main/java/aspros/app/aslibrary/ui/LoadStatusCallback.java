package aspros.app.aslibrary.ui;

/**
 * Created by aspros on 16/8/25.
 */
public interface LoadStatusCallback
{
    int STATUS_LOADING = 0;
    int STATUS_LOADED = 1;
    int STATUS_LOADFAIL = 2;
    int STATUS_LOADNULL = 3;

    void onLoading();

    void onLoaded();

    void onLoadFailed();

    void onLoadNull();
}
