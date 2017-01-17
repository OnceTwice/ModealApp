package com.ff.modealapp;

import android.content.Intent;
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
import com.ff.modealapp.vo.AddressVo;
import com.ff.modealapp.vo.ChannelVo;
import com.ff.modealapp.vo.InfoVo;
import com.ff.modealapp.vo.ItemVo;
import com.ff.modealapp.vo.ShopVo;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
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
                startActivity(intent2);
            }
        });

        findViewById(R.id.button_json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchShopListAsyncTask().execute();
            }
        });

        findViewById(R.id.button_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchAddressAsyncTask().execute();
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
            Toast.makeText(JoinFormActivity.this, ""+jsonAddress.getChannel().getItem(), Toast.LENGTH_LONG).show();
            super.onSuccess(jsonAddress);
        }
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

            }
            Toast.makeText(JoinFormActivity.this, ""+shopVos, Toast.LENGTH_LONG).show();
            super.onSuccess(shopVos);
        }
    }

}
