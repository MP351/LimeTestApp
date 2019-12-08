package com.example.limetestapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.limetestapp.data.CbrApi;
import com.example.limetestapp.data.ServiceGenerator;
import com.example.limetestapp.data.pojo.Valute;
import com.example.limetestapp.data.pojo.Valutes;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValutesViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isQuerySuccessful = new MutableLiveData<>();
    private MutableLiveData<Boolean> isQueryInProcess = new MutableLiveData<>();
    private CbrApi api;
    private MutableLiveData<SortType> sortType = new MutableLiveData<>(SortType.NAME_ASC);
    private ArrayList<Valute> data = new ArrayList<>();
    private LiveData<ArrayList<Valute>> valutes = Transformations.switchMap(sortType, this::sort);

    public ValutesViewModel(@NonNull Application application) {
        super(application);

        api = ServiceGenerator.createService(CbrApi.class);

    }

    public void updateData() {
        isQueryInProcess.setValue(true);
        api.getValutesList().enqueue(new Callback<Valutes>() {
            @Override
            public void onResponse(Call<Valutes> call, Response<Valutes> response) {
                if (response.body() != null) {
                    data = new ArrayList<>(response.body().getValutes().values());
                    isQuerySuccessful.setValue(true);
                } else {
                    isQuerySuccessful.setValue(false);
                }
                isQueryInProcess.setValue(false);
            }

            @Override
            public void onFailure(Call<Valutes> call, Throwable t) {
                isQuerySuccessful.setValue(false);
                isQueryInProcess.setValue(false);
            }
        });
    }

    public LiveData<ArrayList<Valute>> getData() {
        return valutes;
    }

    public LiveData<Boolean> getIsQuerySuccessful() {
        return isQuerySuccessful;
    }

    public LiveData<Boolean> getIsQueryInProcess() {
        return isQueryInProcess;
    }

    public void setSortType(SortType sortType) {
        this.sortType.setValue(sortType);
    }

    public SortType getSortType() {
        return sortType.getValue();
    }

    private LiveData<ArrayList<Valute>> sort(SortType type) {
        // Если не скопировать, то не обновится список на экране.
        // submitList проверяет ссылки
        ArrayList<Valute> data = new ArrayList<>(this.data);
        switch (type) {
            case NAME_ASC:
                Collections.sort(data, (v1, v2) -> (
                        v1.getName().compareTo(v2.getName())
                ));
                break;
            case CODE_ASC:
                Collections.sort(data, (v1, v2) -> (
                        v1.getCharCode().compareTo(v2.getCharCode())
                ));
                break;
            case RATE_ASC:
                Collections.sort(data, (v1, v2) -> (
                        v1.getCurrentValue().compareTo(v2.getCurrentValue())
                ));
                break;
            case NAME_DESC:
                Collections.sort(data, (v1, v2) -> (
                        v2.getName().compareTo(v1.getName())
                ));
                break;
            case CODE_DESC:
                Collections.sort(data, (v1, v2) -> (
                        v2.getCharCode().compareTo(v1.getCharCode())
                ));
                break;
            case RATE_DESC:
                Collections.sort(data, (v1, v2) -> (
                        v2.getCurrentValue().compareTo(v1.getCurrentValue())
                ));
                break;
        }

        return new MutableLiveData<>(data);
    }

    public enum SortType {
        NAME_ASC,
        NAME_DESC,
        RATE_ASC,
        RATE_DESC,
        CODE_ASC,
        CODE_DESC
    }
}
