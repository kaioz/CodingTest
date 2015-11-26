package com.cocosw.westpac.app;

import android.app.Application;

/**
 * Project: Pactera
 * Created by LiaoKai(soarcn) on 2015/5/9.
 */
class Config implements Runnable {

    private final Application app;

    public Config(Application commonBankApplication) {
        this.app = commonBankApplication;
    }

    @Override
    public void run() {

    }
}
