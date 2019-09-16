package com.belajar.belajarbarcode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;

public class MainActivity extends AppCompatActivity {
ImageView imageView;
Button button;
EditText editText;
String editTextValue;
Thread thread;
Bitmap bitmap;
public  final static int QEcodewidth = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextValue = editText.getText().toString();
                try {
                    bitmap = TextToImageEncode(editTextValue);
                    imageView.setImageBitmap(bitmap);
                }catch (WriterException e){
                    e.printStackTrace();
                }

            }
        });
    }

    private Bitmap TextToImageEncode(String value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QEcodewidth, QEcodewidth,null
            );
        }catch (IllegalArgumentException e){
            return  null;
        }
        int bitMattrixWidth = bitMatrix.getWidth();
        int bitMattrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMattrixWidth * bitMattrixHeight];
        for (int y = 0 ; y < bitMattrixHeight ;y++){
          int offset = y * bitMattrixWidth;
          for(int x = 0; x < bitMattrixWidth;x++){
              pixels[offset + x] = bitMatrix.get(x,y)? getResources().getColor(R.color.CodeBlackColor):getResources().getColor(R.color.CodeWhiteColor);
          }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMattrixWidth,bitMattrixHeight,Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels,0,500,0,0,bitMattrixWidth,bitMattrixHeight);
        return bitmap;
    }
}
