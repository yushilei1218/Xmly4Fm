package com.yushilei.xmly4fm.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.Intents;
import com.yushilei.xmly4fm.BarCodeActivty;
import com.yushilei.xmly4fm.MainActivity;
import com.yushilei.xmly4fm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelfFragment extends Fragment {


    private TextView zXingScanTv;
    private TextView zXingProduceTv;
    private TextView barCodeByZXing;

    public SelfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_self, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        zXingScanTv = ((TextView) view.findViewById(R.id.self_zxing_scan));
        zXingProduceTv = ((TextView) view.findViewById(R.id.self_zxing_produce));
        barCodeByZXing = ((TextView) view.findViewById(R.id.self_zxing_use_zxing));
        final TextView content = (TextView) view.findViewById(R.id.self_zxing_open);
        barCodeByZXing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(content.getText().toString())) {
                    Intent intent = new Intent(Intents.Encode.ACTION);

                    // 设置编码格式，为 QR_CODE 注意一定是 .name()
                    intent.putExtra(Intents.Encode.FORMAT, BarcodeFormat.QR_CODE.name());
                    intent.putExtra(Intents.Encode.DATA, content.getText().toString());
                    intent.putExtra(Intents.Encode.TYPE, Contents.Type.TEXT);
                    startActivity(intent);
                }
            }
        });

        zXingScanTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intents.Scan.ACTION);
                // 设置取景框的宽高
                intent.putExtra(Intents.Scan.WIDTH, 600);
                intent.putExtra(Intents.Scan.HEIGHT, 600);

                getActivity().startActivityForResult(intent, MainActivity.ZXING_REQUEST);
            }
        });
        zXingProduceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BarCodeActivty.class));
            }
        });
    }
}
