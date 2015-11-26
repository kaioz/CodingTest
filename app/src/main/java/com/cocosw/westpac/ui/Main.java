package com.cocosw.westpac.ui;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cocosw.framework.core.Base;
import com.cocosw.framework.core.JobFragment;
import com.cocosw.framework.exception.CocoException;
import com.cocosw.westpac.R;
import com.cocosw.westpac.app.WestpacApplication;
import com.cocosw.westpac.model.Weather;
import com.cocosw.westpac.model.WeatherModel;
import com.cocosw.westpac.service.ServiceProvider;

public class Main extends Base<Void> implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView icon;
    private TextView temperature;
    private TextView summary;
    private WeatherFragment fragment;

    @Override
    public int layoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void init(Bundle saveBundle) throws Exception {
        swipeRefreshLayout = view(R.id.swipe);
        icon = view(R.id.icon);
        temperature = view(R.id.temperature);
        summary = view(R.id.summary);
        swipeRefreshLayout.setOnRefreshListener(this);
        fragment = WeatherFragment.attach(this, WeatherFragment.class);
    }

    @Override
    public void onRefresh() {
        fragment.start();
    }

    public static class WeatherFragment extends JobFragment<WeatherModel,Main> {
        private ServiceProvider service;
        private Location location;

        @Override
        protected WeatherModel pendingData() throws Exception{
            location = service.getCurrentLocation();
            if (location==null)
                throw new CocoException(getString(R.string.failed_get_location));
            return service.getCurrentWeather(location);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            setRetainInstance(true);
            super.onCreate(savedInstanceState);
            service = ((WestpacApplication) getActivity().getApplication()).getProvider();
            start();
        }

        @Override
        protected void render(WeatherModel model, boolean loading, Exception exception) {
            if (loading) {
                if (!target().swipeRefreshLayout.isRefreshing()) {
                    target().swipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            target().swipeRefreshLayout.setRefreshing(true);
                        }
                    });
                }
            }
            if (exception!=null)  {
                // TODO Better error msg for end user
                target().summary.setText(exception.getMessage());
                target().swipeRefreshLayout.setBackgroundResource(R.color.indianRedColor);
                target().swipeRefreshLayout.setRefreshing(false);
            }
            if (model == null) {
                target().temperature.setText("--");
            } else {
                target().summary.setText(model.summary);
                target().temperature.setText(String.valueOf(model.temperature));
                target().icon.setImageResource(Weather.getWeatherType(model.icon).icon);
                target().swipeRefreshLayout.setBackgroundResource(Weather.getWeatherType(model.icon).bg);
                target().swipeRefreshLayout.setRefreshing(false);
            }
        }

    }


}
