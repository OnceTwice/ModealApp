package com.ff.modealapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ff.modealapp.maps.SearchShopToJoinActivity;

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RESULT========>", ""+requestCode);
        Log.d("RESULT========>", ""+resultCode);
        Log.d("RESULT========>", data.getStringExtra("joinMapInfoVo"));

        if(requestCode == 1000 && resultCode ==RESULT_OK){
            Toast.makeText(JoinFormActivity.this, "완료!!!!",Toast.LENGTH_SHORT).show();
        }

    }
}
