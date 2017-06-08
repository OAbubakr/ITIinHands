package com.iti.itiinhands.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.Manifest;
import com.iti.itiinhands.R;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;


public class UploadImage extends AppCompatActivity implements View.OnClickListener, NetworkResponse {

    Button choose, upload;
    TextView nameText;
    String imagePath;
    ImageView myImage;
    Bitmap bitmap = null;
    private static int RESULT_LOAD_IMAGE = 1;
    String picturePath;
    String mediaPath;
    NetworkManager networkManager;
    private NetworkResponse myRef;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        networkManager = NetworkManager.getInstance(this);
        myRef = this;
        choose = (Button) findViewById(R.id.choose);
        upload = (Button) findViewById(R.id.upload);
        myImage = (ImageView) findViewById(R.id.profileImage);
        nameText = (TextView) findViewById(R.id.textView3);
        choose.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.choose) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        } else if (v.getId() == R.id.upload) {
           networkManager.uploadImage(myRef, picturePath,5700);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            myImage.setImageURI(selectedImage);
            nameText.setText(picturePath);
        }

    }

    @Override
    public void onResponse(Response response) {
        Toast.makeText(getApplicationContext(), "Response is success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getApplicationContext(), "Response is failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
