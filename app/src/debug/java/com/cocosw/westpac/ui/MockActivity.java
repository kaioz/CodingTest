package com.cocosw.westpac.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.cocosw.framework.core.Base;
import com.cocosw.westpac.R;
import com.cocosw.westpac.app.WestpacApplication;
import com.cocosw.westpac.service.MockLocation;
import com.cocosw.westpac.service.MockServiceProvider;
import com.cocosw.westpac.service.MockWeather;


/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */
public class MockActivity extends Base<Void> implements View.OnClickListener {

    private MockServiceProvider service;

    @Override
    public int layoutId() {
        return R.layout.mock_activty;
    }

    @Override
    protected void init(Bundle bundle) throws Exception {

        service = (MockServiceProvider) ((WestpacApplication)getApplication()).getProvider();

        Spinner mockLocation = view(R.id.mock_location);
        Spinner mockWeather = view(R.id.mock_weather);
        configureResponseSpinner(mockLocation, MockLocation.class);
        configureResponseSpinner(mockWeather, MockWeather.class);

        Spinner delay = view(R.id.delay);
        configureResponseSpinner(delay,MockDelay.class);
        view(R.id.launch).setOnClickListener(this);
    }

    /**
     * Populates a {@code Spinner} with the values of an {@code enum} and binds it to the value set
     * in
     * the mock service.
     */
    private <T extends Enum<T>> void configureResponseSpinner(Spinner spinner, final Class<T> responseClass) {
        final EnumAdapter<T> adapter = new EnumAdapter<>(this, responseClass);
        spinner.setAdapter(adapter);
        spinner.setSelection(service.getResponse(responseClass).ordinal());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                service.setResponse(responseClass,adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
        startActivity(new Intent(this,Main.class));
    }
}
