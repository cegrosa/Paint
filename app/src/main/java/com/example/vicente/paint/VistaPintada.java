package com.example.vicente.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;

/**
 * Created by vicente on 2/02/18.
 */

public class VistaPintada extends View {
    private Bitmap mapaDeBits;
    private Canvas canvasFondo;
    private Paint pincel;
    private String colorCur = "#000000";
    private String colorPast = "#000000";
    private int size = 10;
    private boolean draw = true,
            lines = false,
            circles = false;

    public VistaPintada(Context context) {
        super(context);
    }

    public VistaPintada(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public String getColorCur() {
        return colorCur;
    }

    public void setColorCur(String colorCur) {
        this.colorCur = colorCur;
        this.colorPast = colorCur;
    }

    public String getColorPast() {
        return colorPast;
    }

    public void setColorPast(String colorPast) {
        this.colorPast = colorPast;
    }

    public void setDraw(boolean erase) {
        this.draw = true;
        this.lines = false;
        this.circles = false;

        if (erase) {
            this.colorCur = "#ffcccccc";
        } else {

            setColorCur(getColorPast());
        }
    }

    public void setLines() {
        this.lines = true;
        this.draw = false;
        this.circles = false;

        setColorCur(getColorPast());
    }

    public void setCircles() {
        this.circles = true;
        this.draw = false;
        this.lines = false;

        setColorCur(getColorPast());
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void vaciarLienzo() {
        pincel = new Paint();
        pincel.setColor(Color.LTGRAY);
        canvasFondo.drawRect(0, 0, getWidth(), getHeight(), pincel);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pincel = new Paint();
        pincel.setColor(Color.LTGRAY);
        pincel.setAntiAlias(true);
        pincel.setStrokeWidth(size);
        canvas.drawRect(0, 0, getWidth(), getHeight(), pincel);
        /* --- ^ Siempre ^ --- */
        canvas.drawBitmap(mapaDeBits, 0, 0, null);
        colorCur = getColorCur();
        pincel.setColor(Color.parseColor(colorCur));

        if (draw) {
            canvas.drawLine(xi, yi, xf, yf, pincel);
            canvasFondo.drawLine(xi, yi, xf, yf, pincel);
        }
        pincel.setStyle(Paint.Style.STROKE);
        if (pintando) {
            // Mientras que se dibuja de forma provisional:
            if (lines) {
                canvas.drawLine(xi, yi, xf, yf, pincel);
            }

            if (circles) {
                pincel.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(xi, yi, radio, pincel);
            }
        } else {
//             Cuando he terminado de pintar la recta:
            if (lines) {
                canvasFondo.drawLine(xi, yi, xf, yf, pincel);
            }

            if (circles) {
                pincel.setStyle(Paint.Style.STROKE);
                canvasFondo.drawCircle(xi, yi, radio, pincel);
            }
        }


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mapaDeBits = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvasFondo = new Canvas(mapaDeBits);
    }

    //xi, yi para iniciar
    //xf, yf para finalizar
    private float xi, yi, xf, yf;
    private float radio;
    private boolean pintando = false;
//    private Path rectaPoligonal = new Path();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            //Se pulsa la pantalla
            case MotionEvent.ACTION_DOWN:
                pintando = true;
                Log.v("xyzyx", "Ontouch Down: " + pintando);
                xf = xi = x;
                yf = yi = y;
//                rectaPoligonal.reset();
//                rectaPoligonal.moveTo(xi, yi);
                break;
            //Se mueve la pulsacion por la pantalla
            case MotionEvent.ACTION_MOVE:
                if (draw) {
                    xi = xf;
                    yi = yf;
                }
                xf = x;
                yf = y;
//                rectaPoligonal.quadTo(xi, yi,
//                        (x + xi) / 2, (y + yi) / 2);
                break;
            //Se deja de pulsar la pantalla
            case MotionEvent.ACTION_UP:
                pintando = false;
                Log.v("xyzyx", "Ontouch: " + pintando);
                xf = x;
                yf = y;
                break;
            default:
                Log.v("xyzyx", "Ontouch Def: " + pintando);
        }

        radio = (float) Math.sqrt(Math.pow(xf - xi, 2) + Math.pow(yf - yi, 2));

        invalidate();
        return true;
    }
}
