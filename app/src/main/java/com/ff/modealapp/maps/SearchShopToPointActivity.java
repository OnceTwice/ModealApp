package com.ff.modealapp.maps;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ff.modealapp.R;
import com.ff.modealapp.network.SafeAsyncTask;
import com.ff.modealapp.service.MapsService;
import com.ff.modealapp.vo.ShopVo;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.List;

public class SearchShopToPointActivity extends AppCompatActivity implements MapView.MapViewEventListener {
    Double longitude;
    Double latitude;
    String range;
    MapCircle circle;
    private MapsService mapsService = new MapsService();
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop_to_point);

        Intent intent = new Intent(this.getIntent());
        longitude = Double.valueOf(intent.getStringExtra("longitude"));
        latitude = Double.valueOf(intent.getStringExtra("latitude"));
        range = intent.getStringExtra("range");

        Log.d("longitude=====>", "" + longitude);
        Log.d("latitude=====>", "" + latitude);
        Log.d("result=====>", range);

        TextView textView = (TextView) findViewById(R.id.textView_response);
        textView.setText(range);

//------------------------------------------------------------------------

        mapView = new MapView(this);
        mapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);

        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view_point);

        circle = new MapCircle(
                MapPoint.mapPointWithGeoCoord(latitude, longitude),
                Integer.valueOf(range),
                Color.argb(25, 255, 0, 0),
                Color.argb(25, 255, 0, 0)
        );
        circle.setTag(1234);
        mapView.addCircle(circle);

        container.addView(mapView);

        new FetchShopListAsyncTask().execute();
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
                //------------------풍선 생성---------------------------------
                showResult(shopVos);

                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);//화면이동
            }
            super.onSuccess(shopVos);
        }
    }

    private void showResult(List<ShopVo> shopVoList) {
        MapPointBounds mapPointBounds = new MapPointBounds();
        for (int i = 0; i < shopVoList.size(); i++) {
            ShopVo shopVo = shopVoList.get(i);

            MapPOIItem poiItem = new MapPOIItem();
            poiItem.setItemName(shopVoList.get(i).getName());
            poiItem.setTag(i);
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(shopVoList.get(i).getLatitude(), shopVoList.get(i).getLongitude());
            poiItem.setMapPoint(mapPoint);
            mapPointBounds.add(mapPoint);
            poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            poiItem.setCustomImageResourceId(R.drawable.map_pin_blue);
            poiItem.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
            poiItem.setCustomSelectedImageResourceId(R.drawable.map_pin_red);
            poiItem.setCustomImageAutoscale(false);
            poiItem.setCustomImageAnchor(0.5f, 1.0f);

            mapView.addPOIItem(poiItem);
        }

        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));

        MapPOIItem[] poiItems = mapView.getPOIItems();
        if (poiItems.length > 0) {
            mapView.selectPOIItem(poiItems[0], false);
        }
    }

}
