package com.ff.modealapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.ff.modealapp.maps.MapApiConst;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import static com.ff.modealapp.AddressListActivity.FinishAddressListActivity;
import static com.ff.modealapp.SearchExampleActivity.FinishSearchExampleActivity;

public class SearchToMapActivity extends AppCompatActivity implements MapReverseGeoCoder.ReverseGeoCodingResultListener , MapView.MapViewEventListener{
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


        container.addView(mapView);

        findViewById(R.id.fab_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapPoint thisMapPoint = MapPoint.mapPointWithGeoCoord(mapView.getMapCenterPoint().getMapPointGeoCoord().latitude, mapView.getMapCenterPoint().getMapPointGeoCoord().longitude);
                MapReverseGeoCoder mapReverseGeoCoder = new MapReverseGeoCoder(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, thisMapPoint, SearchToMapActivity.this, SearchToMapActivity.this);

                mapReverseGeoCoder.startFindingAddress();
            }
        });
    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {

        Intent returnIntent = new Intent(SearchToMapActivity.this, SearchExampleActivity.class);

        returnIntent.putExtra("lat", String.valueOf(mapView.getMapCenterPoint().getMapPointGeoCoord().latitude));
        returnIntent.putExtra("lng", String.valueOf(mapView.getMapCenterPoint().getMapPointGeoCoord().longitude));
        returnIntent.putExtra("title", s);

        FinishAddressListActivity.finish();
        FinishSearchExampleActivity.finish();

        startActivity(returnIntent);

        Log.d("엑티비티 꺼짐-->", "OK!!!!!!!!");
        finish();
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}
