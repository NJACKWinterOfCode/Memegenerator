package com.example.mdhvr.memegenerator;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.takusemba.cropme.CropView;
import com.takusemba.cropme.OnCropListener;

public class CropActivity extends AppCompatActivity {

    private CropView cropView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        cropView= findViewById(R.id.crop_view);
        Bitmap crop_bitmap = Bitmap.createBitmap(ImageActivity.imageBitmap);
        Log.e("CropActivity",crop_bitmap.getWidth()+" "+crop_bitmap.getHeight());
        Log.e("CropActivity",cropView.getLayoutParams().height+" "+cropView.getLayoutParams().width);
        cropView.setBitmap(crop_bitmap);
        setTitle("Crop");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.crop_done){
            cropImage();
            return true;
        }
        if(item.getItemId()==R.id.crop_cancel){
            cancelCropping();
        }
        return super.onOptionsItemSelected(item);
    }

    private void cancelCropping() {
        finish();
    }

    private void cropImage() {
        cropView.crop(new OnCropListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                Toast.makeText(CropActivity.this, "Meme Cropped!", Toast.LENGTH_SHORT).show();
                ImageActivity.imageBitmap = bitmap;
                finish();
            }

            @Override
            public void onFailure() {

                finish();
            }
        });
    }
}
