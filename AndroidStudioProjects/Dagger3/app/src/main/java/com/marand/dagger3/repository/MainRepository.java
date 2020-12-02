package com.marand.dagger3.repository;

import com.marand.dagger3.network.main.MainApi;
import javax.inject.Singleton;

@Singleton
public class MainRepository {
    private MainApi mainApi;

    public MainRepository(MainApi mainApi) {
        this.mainApi = mainApi;
    }

    
}
