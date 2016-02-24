package com.yushilei.xmly4fm;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class BarCodeActivty extends AppCompatActivity {

    private EditText inputEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);
        inputEt = (EditText) findViewById(R.id.barcode_input);
        TextView produceTv = (TextView) findViewById(R.id.barcode_produce);
        final ImageView image = (ImageView) findViewById(R.id.barcode_image);
        produceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = inputEt.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    MultiFormatWriter writer = new MultiFormatWriter();
                    try {
                        BitMatrix encode = writer.encode(s, BarcodeFormat.QR_CODE, 300, 300);
                        Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
                        int width = encode.getWidth();
                        int height = encode.getHeight();
                        for (int i = 0; i < height; i++) {
                            for (int j = 0; j < width; j++) {
                                boolean b = encode.get(j, i);
                                if (b) {
                                    bitmap.setPixel(j, i, Color.BLACK);
                                } else {
                                    bitmap.setPixel(j, i, Color.WHITE);
                                }
                            }
                        }
                        image.setImageBitmap(bitmap);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
