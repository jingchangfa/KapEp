package com.example.jing.kapep.Manager;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by jing on 2017/7/3.
 * 定位管理
 */

public class KapLocationManager extends Service implements LocationListener{
    /**
     * 对外公开的
     * */
    private static KapLocationListener kapLocationListener;
    public interface KapLocationListener{
        void successResult(String country,String locationString);
        void failureResult(String errorMsg);
    }
    private static boolean isLocationPermission(Activity context){
        // PackageManager.PERMISSION_GRANTED //表示已授权
        // PackageManager.PERMISSION_DENIED //表示尚未授权
        boolean isPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
        return isPermission;
    }
    public static void UsersLocation(Activity context, KapLocationListener listener){
        if (!isLocationPermission(context)) {
            // 在activity的 onCreate 方法内 使用KapPermissionUtils申请权限
            Log.e("定位","需要申请动态权限");
            return;
        }
        kapLocationListener = listener;
        // 启动服务
        Intent intent = new Intent(context,KapLocationManager.class);
        context.startService(intent);
    }

    /**
     * 私有的
     * */
    private LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        if (!isOpenLocation(locationManager)){
            kapLocationListener.failureResult("定位未开启");
            locationManager.removeUpdates(this);
            stopSelf();
            return;
        }
        if (isOpenNetWork(locationManager)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,this);
            return;
        }
        if (isOpenGPS(locationManager)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,this);
            return;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) return;
        // 位置改变
        kapLocationListener.successResult(location.toString(),location.toString());
        // 关闭
        locationManager.removeUpdates(this);
        stopSelf();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {

    }
    private boolean isOpenLocation(LocationManager locationManager){
        if (isOpenGPS(locationManager)) return true;
        if (isOpenNetWork(locationManager)) return true;
        return false;
    }
    private boolean isOpenGPS(LocationManager locationManager){
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) return false;
        return true;
    }
    private boolean isOpenNetWork(LocationManager locationManager){
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) return false;
        return true;
    }
}
