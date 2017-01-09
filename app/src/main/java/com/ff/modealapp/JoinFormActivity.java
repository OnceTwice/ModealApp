package com.ff.modealapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ff.modealapp.maps.SearchShopToJoinActivity;
import com.ff.modealapp.maps.search.JoinMapInfoVo;

public class JoinFormActivity extends AppCompatActivity {
    Intent intent = null;
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RESULT========>", ""+requestCode);
        Log.d("RESULT========>", ""+resultCode);
        //Log.d("RESULT========>", data.getStringExtra("joinMapInfoVo".toString()));

        if(requestCode == 1000 && resultCode ==RESULT_OK){
            Toast.makeText(JoinFormActivity.this, "완료!!!!",Toast.LENGTH_SHORT).show();

            TextView thisShopName = (TextView)findViewById(R.id.textView_shopname);
            TextView thisShopAddress = (TextView)findViewById(R.id.textView_address);
            TextView thisShopHp = (TextView)findViewById(R.id.textView_ph);
            TextView thisShopX = (TextView)findViewById(R.id.textView_coodiX);
            TextView thisShopY = (TextView)findViewById(R.id.textView_CoodiY);
            JoinMapInfoVo joinMapInfoVo = (JoinMapInfoVo)data.getSerializableExtra("joinMapInfoVo");
            thisShopName.setText(joinMapInfoVo.getTitle());
            thisShopAddress.setText(joinMapInfoVo.getAddress());
            thisShopHp.setText(joinMapInfoVo.getPhone());
            thisShopX.setText(joinMapInfoVo.getLongitude());
            thisShopY.setText(joinMapInfoVo.getLatitude());
        }
    }
}
