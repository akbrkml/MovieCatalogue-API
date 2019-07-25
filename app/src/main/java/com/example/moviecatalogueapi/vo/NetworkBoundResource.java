package com.example.moviecatalogueapi.vo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.moviecatalogueapi.data.source.remote.ApiResponse;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();


    protected void onFetchFailed() {
    }

    protected abstract LiveData<ResultType> loadFromDB();

    protected abstract Boolean shouldFetch(ResultType data);

    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    protected abstract void saveCallResult(RequestType data);

    public NetworkBoundResource() {

        result.setValue(Resource.loading(null));

        LiveData<ResultType> dbSource = loadFromDB();

        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> result.setValue(Resource.success(newData)));
            }
        });

    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {

        LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        result.addSource(dbSource, newData ->
                result.setValue(Resource.loading(newData))
        );
        result.addSource(apiResponse, response -> {

            result.removeSource(apiResponse);
            result.removeSource(dbSource);

            switch (response.status) {
                case SUCCESS:
                    Completable.fromAction(() ->
                            saveCallResult(response.body))
                            .subscribeOn(Schedulers.computation())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onComplete() {
                                    Log.e("onComplete", "Berhasil disimpan");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("onError", e.getLocalizedMessage());
                                }
                            });
                    result.addSource(loadFromDB(),
                            newData -> result.setValue(Resource.success(newData)));
                    break;

                case EMPTY:
                    result.addSource(loadFromDB(),
                            newData -> result.setValue(Resource.success(newData)));
                    break;
                case ERROR:
                    onFetchFailed();
                    result.addSource(dbSource, newData ->
                            result.setValue(Resource.error(response.message, newData)));
                    break;
            }
        });
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}
