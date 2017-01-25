package com.ff.modealapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ff.modealapp.maps.SearchShopToJoinActivity;
import com.ff.modealapp.maps.search.JoinMapInfoVo;
import com.ff.modealapp.network.SafeAsyncTask;
import com.ff.modealapp.service.MapsService;
import com.ff.modealapp.vo.ShopVo;

import java.util.List;

public class JoinFormActivity extends AppCompatActivity {
    Intent intent = null;

    private MapsService mapsService = new MapsService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_form);

        findViewById(R.id.button_getInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(JoinFormActivity.this, SearchShopToJoinActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        findViewById(R.id.button_rangeform).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(JoinFormActivity.this, SearchExampleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent2);
            }
        });

        findViewById(R.id.button_json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchShopListAsyncTask().execute();
            }
        });


        findViewById(R.id.button_mygps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationService();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RESULT========>", "" + requestCode);
        Log.d("RESULT========>", "" + resultCode);
        //Log.d("RESULT========>", data.getStringExtra("joinMapInfoVo".toString()));

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            Toast.makeText(JoinFormActivity.this, "완료!!!!", Toast.LENGTH_SHORT).show();

            TextView thisShopName = (TextView) findViewById(R.id.textView_shopname);
            TextView thisShopAddress = (TextView) findViewById(R.id.textView_address);
            TextView thisShopHp = (TextView) findViewById(R.id.textView_ph);
            TextView thisShopX = (TextView) findViewById(R.id.textView_coodiX);
            TextView thisShopY = (TextView) findViewById(R.id.textView_CoodiY);
            JoinMapInfoVo joinMapInfoVo = (JoinMapInfoVo) data.getSerializableExtra("joinMapInfoVo");
            thisShopName.setText(joinMapInfoVo.getTitle());
            thisShopAddress.setText(joinMapInfoVo.getAddress());
            thisShopHp.setText(joinMapInfoVo.getPhone());
            thisShopX.setText(joinMapInfoVo.getLongitude());
            thisShopY.setText(joinMapInfoVo.getLatitude());
        }
    }
/*
    private class FetchAddressAsyncTask extends SafeAsyncTask<MapsService.JSONAddress> {
        @Override
        public MapsService.JSONAddress call() throws Exception {
            EditText editText = (EditText) findViewById(R.id.editText);
            String addr = editText.getText().toString();
            MapsService.JSONAddress jsonAddress = mapsService.fetchAddress(addr);
            return jsonAddress;
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            Log.d("---err---->", "fail:" + e);
            super.onException(e);
        }

        @Override
        protected void onSuccess(MapsService.JSONAddress jsonAddress) throws Exception {
            Log.d("---address---->", "" + jsonAddress);


            Log.d("=========>", "" + jsonAddress.getChannel());
            Toast.makeText(JoinFormActivity.this, "" + jsonAddress.getChannel().getItem(), Toast.LENGTH_LONG).show();
            super.onSuccess(jsonAddress);
        }
    }
*/

    private class FetchShopListAsyncTask extends SafeAsyncTask<List<ShopVo>> {
        @Override
        public List<ShopVo> call() throws Exception {
            List<ShopVo> list = mapsService.fetchShopList();
            return list;
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            Log.d("------->", "fail:" + e);
            super.onException(e);
        }

        @Override
        protected void onSuccess(List<ShopVo> shopVos) throws Exception {
            for (int i = 0; i < shopVos.size(); i++) {
                ShopVo myShopVo = shopVos.get(i);
                shopVos.set(i, myShopVo);
                Log.d("--usersVo[" + (i + 1) + "번째]-->", "" + shopVos.get(i));

            }
            Toast.makeText(JoinFormActivity.this, "" + shopVos, Toast.LENGTH_LONG).show();
            super.onSuccess(shopVos);
        }
    }

    /**
     * 위치 정보 확인을 위해 정의한 메소드
     */
    private void startLocationService() {
        // 위치 관리자 객체 참조
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // 위치 정보를 받을 리스너 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 0;
        float minDistance = 0;

        try {
            // GPS를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 네트워크를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();

                Toast.makeText(getApplicationContext(), "Last Known Location : " + "Latitude : " + latitude + "\nLongitude:" + longitude, Toast.LENGTH_LONG).show();
            }

        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "위치 확인이 시작되었습니다. 잠시만 기다려 주세요.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 리스너 클래스 정의
     */
    private class GPSListener implements LocationListener {
        /**
         * 위치 정보가 확인될 때 자동 호출되는 메소드
         */
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "Latitude : " + latitude + "\nLongitude:" + longitude;
            Log.i("GPSListener", msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
}
