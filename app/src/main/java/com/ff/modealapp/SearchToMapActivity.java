package com.ff.modealapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ff.modealapp.listformat.AddressListArrayAdapter;
import com.ff.modealapp.maps.MapApiConst;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import static com.ff.modealapp.AddressListActivity.FinishAddressListActivity;

public class SearchToMapActivity extends AppCompatActivity {
    private MapView mapView;
    Double longitude;
    Double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_to_map);

        final Intent intent = new Intent(this.getIntent());

        longitude = Double.valueOf(intent.getStringExtra("lng"));
        latitude = Double.valueOf(intent.getStringExtra("lat"));

        mapView = new MapView(this);
        mapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view_SearchToMap);

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
        container.addView(mapView);

        findViewById(R.id.fab_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hello!!!!!!!!!!!!!!!!!!!!!!!");
                Intent returnIntent = new Intent(SearchToMapActivity.this, SearchExampleActivity.class);


                returnIntent.putExtra("lat", mapView.getMapCenterPoint().getMapPointGeoCoord().latitude);
                returnIntent.putExtra("lng", mapView.getMapCenterPoint().getMapPointGeoCoord().longitude);
                returnIntent.putExtra("title", "안녕!!!");

                /*setResult(1000, returnIntent);*/

                startActivity(returnIntent);

                FinishAddressListActivity.finish();
                finish();


            }
        });

        //getMapCenterPoint--> 화면 중심점을 조회
    }
}
