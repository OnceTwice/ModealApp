package com.ff.modealapp.maps;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ff.modealapp.R;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class SearchShopToPointActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop_to_point);

        Intent intent = new Intent(this.getIntent());
        String longitude = intent.getStringExtra("longitude");
        String latitude = intent.getStringExtra("latitude");
        String result = intent.getStringExtra("result");

        Log.d("longitude=====>", "" + longitude);
        Log.d("latitude=====>", "" + latitude);
        Log.d("result=====>", "" + result);

        TextView textView = (TextView) findViewById(R.id.textView_response);
        textView.setText(result);
//------------------------------------------------------------------------
        mapView = new MapView(this);
        mapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        //mapView.addCircle();

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view_point);
        mapViewContainer.addView(mapView);
//        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view_point);
//        container.addView(mapView);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Long.valueOf(longitude), Long.valueOf(latitude)), true);
    }
}
