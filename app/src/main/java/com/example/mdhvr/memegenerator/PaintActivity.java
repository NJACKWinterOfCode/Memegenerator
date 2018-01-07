package com.example.mdhvr.memegenerator;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

/*
   Created by Ishita sharma on 15/12/17
 */

public class PaintActivity extends AppCompatActivity {

    private Bitmap paintBitmap;
    private ImageView paintImageView;

    private  Canvas canvas;
    private Matrix matrix;
    private Paint paint;
    float downx = 0;
    float downy = 0;
    float upx = 0;
    float upy = 0;
    Bitmap alteredBitmap;
    private int brush_color= Color.MAGENTA;
    Toolbar bottom_toolbar;
    private int stroke_width=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        getSupportActionBar().hide();
        final ImageView pen = findViewById(R.id.paint_pen);
        paintImageView=  findViewById(R.id.paint_image);
        ImageView done= findViewById(R.id.paint_done);
        ImageView cancel = findViewById(R.id.paint_cancel);
        final ImageView pallete = findViewById(R.id.paint_pallete);
        bottom_toolbar= findViewById(R.id.paint_bottom_toolbar);
        //set image bitmap
        paintImageView.setImageBitmap(ImageActivity.imageBitmap);
        paintBitmap=ImageActivity.imageBitmap;

        drawOnBitmap();

        //set onclick for done
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alteredBitmap!=null)
                ImageActivity.imageBitmap= paintBitmap;
                finish();
            }
        });

        //set onclick for cancel
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //set onclick for pen
        pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SeekBar seekbar=new SeekBar(PaintActivity.this);
                seekbar.setMax(40);
                seekbar.setProgress(stroke_width);

                new AlertDialog.Builder(PaintActivity.this)
                        .setMessage("Select stroke width :")
                        .setTitle("Stroke Width")
                        .setView(seekbar)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                stroke_width=seekbar.getProgress();
                                drawOnBitmap();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

            }
        });

        //add onclick for pallete
        pallete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPicker c= new ColorPicker(PaintActivity.this,89,77,255);
                c.show();
                c.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(int color) {
                        brush_color=color;
                        //again call this method so that color gets updated
                        drawOnBitmap();
                        c.dismiss();
                    }
                });
            }
        });
    }

    private void drawOnBitmap() {

        alteredBitmap= Bitmap.createBitmap(paintBitmap.getWidth(),paintBitmap.getHeight(),paintBitmap.getConfig());
       canvas =new Canvas(alteredBitmap);
        paint= new Paint();
        paint.setColor(brush_color);
        paint.setStrokeWidth(stroke_width);
        matrix= new Matrix();
        canvas.drawBitmap(paintBitmap,matrix,paint);
        paintImageView.setImageBitmap(alteredBitmap);
        paintBitmap=alteredBitmap;
        paintImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        bottom_toolbar.setVisibility(View.INVISIBLE);
                        downx = event.getX();
                        downy = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        bottom_toolbar.setVisibility(View.INVISIBLE);
                        upx = event.getX();
                        upy = event.getY();
                        canvas.drawLine(downx, downy, upx, upy, paint);
                        paintImageView.invalidate();
                        downx = upx;
                        downy = upy;
                        break;
                    case MotionEvent.ACTION_UP:
                        bottom_toolbar.setVisibility(View.VISIBLE);
                        view.performClick();
                        upx = event.getX();
                        upy = event.getY();
                        canvas.drawLine(downx, downy, upx, upy, paint);
                        paintImageView.invalidate();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        bottom_toolbar.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                return true;
            }

        });

    }

}
