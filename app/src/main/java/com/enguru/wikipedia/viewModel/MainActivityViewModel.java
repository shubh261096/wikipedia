package com.enguru.wikipedia.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.enguru.wikipedia.service.repo.Events;
import com.enguru.wikipedia.service.repo.Repository;


public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Events.SearchResponseEvent> searchResponseEventMutableLiveData;
    private Repository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        searchResponseEventMutableLiveData = new MutableLiveData<>();
        repository = new Repository(application.getApplicationContext());
    }

    public void sendRequest(String city) {
        repository.getSearchResult(city, searchResponseEventMutableLiveData);
    }

    public LiveData<Events.SearchResponseEvent> getSearchResult() {
        return searchResponseEventMutableLiveData;
    }


}
