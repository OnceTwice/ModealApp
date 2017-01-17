package com.ff.modealapp.maps;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
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

public class SearchShopToPointActivity extends AppCompatActivity {

    private MapsService mapsService = new MapsService();
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

        MapCircle circle = new MapCircle(
                MapPoint.mapPointWithGeoCoord(Double.valueOf(longitude), Double.valueOf(latitude)),
                Integer.valueOf(result),
                Color.argb(25, 255, 0, 0),
                Color.argb(25, 255, 0, 0));
        circle.setTag(1234);

        mapView.addCircle(circle);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(longitude), Double.valueOf(latitude)), true);
        //mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(Double.valueOf(longitude), Double.valueOf(latitude)), 2, true);

        new FetchShopListAsyncTask().execute();
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


            }
            Log.d("리스트2번째 값 출력->", ""+shopVos.get(1));
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
            //tagItemMap.put(poiItem.getTag(), shopVoList);

        }

        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));

        MapPOIItem[] poiItems = mapView.getPOIItems();
        if (poiItems.length > 0) {
            mapView.selectPOIItem(poiItems[0], false);
        }
    }

}
