package com.ff.modealapp.listformat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ff.modealapp.R;
import com.ff.modealapp.AddressListActivity;
import com.ff.modealapp.SearchToMapActivity;
import com.ff.modealapp.maps.SearchShopToJoinActivity;
import com.ff.modealapp.vo.ItemVo;

import java.util.List;

public class AddressListArrayAdapter extends ArrayAdapter<ItemVo> {

    private LayoutInflater layoutInflater;
    private Context context;

    public AddressListArrayAdapter(Context context) {

        super(context, R.layout.activity_address_list);
        layoutInflater = layoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.row_address_list, parent, false);
        }
        //내부 ArrayList에서 해당 포지션의 User 객체를 받아옴

        final ItemVo ItemVo = getItem(position);


        //이름 세팅
        TextView textViewId = (TextView) view.findViewById(R.id.textView_address);
        textViewId.setText(ItemVo.getTitle());
        //버튼 세팅이다


        view.findViewById(R.id.button_select).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("===ItemVo.getLng()===", "" + ItemVo.getLng());
                Log.d("===ItemVo.getLat()===", "" + ItemVo.getLat());

                Intent intent = ((AddressListActivity) context).getIntent();
                intent.putExtra("title", ItemVo.getTitle());
                intent.putExtra("lng", "" + ItemVo.getLng());
                intent.putExtra("lat", "" + ItemVo.getLat());

                ((AddressListActivity) context).setResult(((AddressListActivity) context).RESULT_OK, intent);
                ((AddressListActivity) context).finish();
            }
        });

        view.findViewById(R.id.button_map).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchToMapActivity.class);
                intent.putExtra("lng", "" + ItemVo.getLng());
                intent.putExtra("lat", "" + ItemVo.getLat());
                context.startActivity(intent);
            }
        });

        return view;
    }

    public void add(List<ItemVo> itemVos) {
        if (itemVos == null || itemVos.size() == 0) {
            return;
        }
        for (ItemVo itemVo : itemVos) {
            add(itemVo);
        }
        //add에 이미 있음
        //notifyDataSetChanged();
    }

}
