package com.example.vicente.paint;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    private android.widget.Button btGoma;
    private android.widget.Button btPincel;
    private android.widget.Button btVaciar;
    private android.widget.Button btCirculo;
    private android.widget.Button btLinea;
    private android.widget.Button btColor;
    private android.widget.Button btMas;
    private android.widget.Button btMenos;
    private TextView tvSize;
    private VistaPintada lienzo;

    private void init() {
        this.lienzo = findViewById(R.id.lienzo);

        this.btColor = (Button) findViewById(R.id.btColor);
        btColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(false);
            }
        });

        this.btLinea = (Button) findViewById(R.id.btLinea);
        btLinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lienzo.setLines();
            }
        });

        this.btCirculo = (Button) findViewById(R.id.btCirculo);
        btCirculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lienzo.setCircles();
            }
        });

        this.btVaciar = (Button) findViewById(R.id.btVaciar);
        btVaciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lienzo.vaciarLienzo();
            }
        });

        this.btPincel = (Button) findViewById(R.id.btPincel);
        btPincel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lienzo.setDraw(false);
            }
        });

        this.tvSize = findViewById(R.id.tvSize);

        this.btMas = (Button) findViewById(R.id.btMas);
        btMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s = lienzo.getSize();
                if (s < 100) {
                    s++;
                    tvSize.setText(s + "");
                    lienzo.setSize(s);
                }
            }
        });

        this.btMenos = (Button) findViewById(R.id.btMenos);
        btMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s = lienzo.getSize();
                if (s > 1) {
                    s--;
                    tvSize.setText(s + "");
                    lienzo.setSize(s);
                }
            }
        });

        this.btGoma = (Button) findViewById(R.id.btGoma);
        btGoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lienzo.setDraw(true);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//        );
        setContentView(R.layout.activity_main);
//        setContentView(new VistaPintada(this));
        init();
    }

    private void openColorPicker(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(MainActivity.this,
                Color.parseColor(lienzo.getColorCur()),
                supportsAlpha,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        String hexColor = String.format("#%06X", (0xFFFFFF & color));
                        lienzo.setColorCur(hexColor);
                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                });
        dialog.show();
    }

}
