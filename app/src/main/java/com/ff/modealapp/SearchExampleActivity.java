package com.ff.modealapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ff.modealapp.maps.SearchShopToPointActivity;

public class SearchExampleActivity extends AppCompatActivity {

    public static Activity FinishSearchExampleActivity;
    String range = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FinishSearchExampleActivity = SearchExampleActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_example);

        if(this.getIntent() != null){
            Intent setIntent = new Intent(this.getIntent());
                    ((TextView) findViewById(R.id.textView_address)).setText(setIntent.getStringExtra("title"));
            ((TextView) findViewById(R.id.textView_longitude)).setText(setIntent.getStringExtra("lng"));
            ((TextView) findViewById(R.id.textView_latitude)).setText(setIntent.getStringExtra("lat"));
        }


        findViewById(R.id.button_findAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(SearchExampleActivity.this, AddressListActivity.class
                Intent intent = new Intent(SearchExampleActivity.this, AddressListActivity.class);
                EditText editText = (EditText) findViewById(R.id.editText_address);
                String addr = editText.getText().toString();

                intent.putExtra("addr", addr);
                startActivityForResult(intent, 1000);
            }
        });

        ((SeekBar) findViewById(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                ((TextView) findViewById(R.id.textView)).setText("주변반경 : " + progress * 10);
                range = String.valueOf(progress * 10);
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

                intent.putExtra("longitude", ((TextView) findViewById(R.id.textView_longitude)).getText().toString());
                intent.putExtra("latitude", ((TextView) findViewById(R.id.textView_latitude)).getText().toString());
                intent.putExtra("range", range);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            //Toast.makeText(MainActivity.this, "결과가 성공이 아님.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == 1000) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            ((TextView) findViewById(R.id.textView_address)).setText(data.getStringExtra("title"));
            ((TextView) findViewById(R.id.textView_longitude)).setText(data.getStringExtra("lng"));
            ((TextView) findViewById(R.id.textView_latitude)).setText(data.getStringExtra("lat"));


            //Toast.makeText(MainActivity.this, "결과 : " + resultMsg, Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(MainActivity.this, "REQUEST_ACT가 아님", Toast.LENGTH_SHORT).show();
        }
    }

}
