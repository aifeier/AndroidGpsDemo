package demo.cwf.com.androidgpsdemo;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent gpsService;
    private ListView list;
    private ArrayAdapter<String> mAdapter;
    private List<String> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.start_listener_gps).setOnClickListener(this);
        findViewById(R.id.stop_listener).setOnClickListener(this);
        findViewById(R.id.clear_list).setOnClickListener(this);
        locationList = new ArrayList<>();
        list = (ListView) findViewById(R.id.list);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locationList);
        list.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
//                Location location = GPSLocation.getInstance(this).getLocation(LocationManager.GPS_PROVIDER);
//                Location location = GPSLocation.getInstance(this).getLocation(LocationManager.NETWORK_PROVIDER);
                Location location = GPSLocation.getInstance(this).getLocation(LocationManager.PASSIVE_PROVIDER);
                if (location != null) {
                    locationChangeListener.onChange(location);
                }
                break;
            case R.id.start_listener_gps:
                startListener(true);
                break;
            case R.id.start_listener_network:
                startListener(false);
                break;
            case R.id.stop_listener:
                stopListener();
                break;
            case R.id.clear_list:
                locationList.clear();
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void startListener(boolean useGPS) {
//                if (gpsService == null)
//                    gpsService = new Intent(GPSService.ACTION);
//                startService(gpsService);
        GPSLocation.getInstance(this).removeAllChangeListener();
        GPSLocation.getInstance(this).addChangeListener(locationChangeListener);
        if (useGPS)
            GPSLocation.getInstance(this).useGPS();
        else
            GPSLocation.getInstance(this).useNetWork();
        GPSLocation.getInstance(this).startListener();
    }

    private void stopListener() {
//        if (gpsService != null)
//            stopService(gpsService);
        GPSLocation.getInstance(this).stopListener();
    }

    private LocationChangeListener locationChangeListener = new LocationChangeListener() {
        @Override
        public void onChange(Location location) {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                stringBuffer.append(simpleDateFormat.format(new Date(location.getTime())));
                stringBuffer.append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
            stringBuffer.append(location.getLongitude());
            stringBuffer.append("，");
            stringBuffer.append(location.getLatitude());
            locationList.add(stringBuffer.toString());
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onDestroy() {
//        if (gpsService != null)
//            stopService(gpsService);
        super.onDestroy();
    }
}
