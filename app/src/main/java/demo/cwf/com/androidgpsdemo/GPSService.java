package demo.cwf.com.androidgpsdemo;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Created at 陈 on 2016/9/14.
 * 定位服务
 *
 * @author cwf
 * @email 237142681@qq.com
 */
public class GPSService extends Service {
    public static final String ACTION = "cwf.service.gps";
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        RxPermissions.getInstance(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean)
                            GPSLocation.getInstance(mContext).startListener();
                        else {
                            Toast.makeText(mContext, "缺少访问地理位置的权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        GPSLocation.getInstance(mContext).stopListener();
        super.onDestroy();
    }
}
