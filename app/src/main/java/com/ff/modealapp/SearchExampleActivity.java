package com.ff.modealapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ff.modealapp.maps.SearchShopToPointActivity;

public class SearchExampleActivity extends AppCompatActivity {

    String result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_example);

        ((SeekBar) findViewById(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                ((TextView) findViewById(R.id.textView)).setText("주변반경 : " + progress * 10);
                result = String.valueOf(progress * 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("SearchExampleActivity=>", "onStartTrackingTouch() called");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("SearchExampleActivity=>", "onStopTrackingTouch() called");
            }
        });

        findViewById(R.id.button_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SearchExampleActivity.this, SearchShopToPointActivity.class);

//                intent.putExtra("longitude", "37.49456429671521");
//                intent.putExtra("latitude", "127.0280215767454");
                intent.putExtra("longitude", "37.49456429671521");
                intent.putExtra("latitude", "127.0280215767454");
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });
    }
}
