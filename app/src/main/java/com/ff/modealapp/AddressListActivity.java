package com.ff.modealapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ff.modealapp.R;
import com.ff.modealapp.listformat.AddressListArrayAdapter;
import com.ff.modealapp.network.SafeAsyncTask;
import com.ff.modealapp.service.MapsService;
import com.ff.modealapp.vo.AddressVo;
import com.ff.modealapp.vo.DaumItemVo;

import java.util.ArrayList;
import java.util.List;


public class AddressListActivity extends ListActivity {


    public static Activity FinishAddressListActivity;

    private AddressListArrayAdapter addressListArrayAdapter = null;

    MapsService mapsService = new MapsService();
    Intent intentAddr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FinishAddressListActivity = AddressListActivity.this;

        addressListArrayAdapter = new AddressListArrayAdapter(this);

        setListAdapter(addressListArrayAdapter);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);

        intentAddr = new Intent(this.getIntent());

        new FetchAddressListAsyncTask().execute();

    }

    private class FetchAddressListAsyncTask extends SafeAsyncTask<AddressVo> {

        @Override
        public AddressVo call() throws Exception {
            return mapsService.fetchAddress("" + intentAddr.getStringExtra("addr"));
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            Log.d("Error======>", "" + e);
            super.onException(e);
        }

        @Override
        protected void onSuccess(AddressVo addressVo) throws Exception {
            List<DaumItemVo> daumItemVos = new ArrayList<>();
            for (int i = 0; i < addressVo.getChannel().getItem().size(); i++) {
                daumItemVos.add(i, addressVo.getChannel().getItem().get(i));
                Log.d(i + 1 + "번째", "" + daumItemVos.get(i));
            }
            super.onSuccess(addressVo);
            addressListArrayAdapter.add(daumItemVos);
        }
    }
}
