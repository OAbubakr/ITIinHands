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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.Manifest;
import com.iti.itiinhands.R;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;


public class UploadImage extends Fragment implements View.OnClickListener, NetworkResponse {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_upload_image, container, false);
//        setContentView(R.layout.activity_upload_image);
        networkManager = NetworkManager.getInstance(getContext());
        myRef = this;
        choose = (Button) view.findViewById(R.id.choose);
        upload = (Button) view.findViewById(R.id.upload);
        myImage = (ImageView) view.findViewById(R.id.profileImage);
        nameText = (TextView) view.findViewById(R.id.textView3);
        choose.setOnClickListener(this);
        upload.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.choose) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            getActivity().startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        } else if (v.getId() == R.id.upload) {
           networkManager.uploadImage(myRef, picturePath,5700);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE &&  null != data) {
            selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
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
        Toast.makeText(getActivity().getApplicationContext(), "Response is success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity().getApplicationContext(), "Response is failed", Toast.LENGTH_SHORT).show();
    }




}
