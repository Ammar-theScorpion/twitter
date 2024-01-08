package com.equiptal.otherServices;

import com.equiptal.model.DBConnection;
import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {

    public AppModule() {
        new DBConnection();
    }

    @Override
    protected void configure() {
    }
}
