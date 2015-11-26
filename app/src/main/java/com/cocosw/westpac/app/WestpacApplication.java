package com.cocosw.westpac.app;


import com.cocosw.framework.app.CocoApp;
import com.cocosw.westpac.service.ServiceProvider;

/**
 * Project: Pactera
 * Created by LiaoKai(soarcn) on 2015/5/10.
 */
public class WestpacApplication extends CocoApp {

    ServiceProvider service;

    @Override
    public void onCreate() {
        service = new ServiceProvider(this);
        super.onCreate();
    }

    @Override
    protected Runnable config() {
        return new Config(this);
    }


    public ServiceProvider getProvider() {
        // No Dagger :(
        return service;
    }
}
