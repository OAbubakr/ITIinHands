package com.iti.itiinhands.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.abanoub.walkthrough.WalkthroughActivity;
import com.abanoub.walkthrough.WalkthroughItem;
import com.iti.itiinhands.R;

public class OurWalkthrough extends WalkthroughActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("checkForWalkThough",0);
          String check = preferences.getString("checkforFirst","first");
          if(check.equals("done")){

              Intent intent = new Intent(this,LoginActivity.class);
              startActivity(intent);

              finish();

          }else{
              SharedPreferences.Editor editor =preferences.edit();
              editor.putString("checkforFirst","done");
              editor.commit();

          }




        WalkthroughItem page1 =new WalkthroughItem(R.drawable.androidmobile_2);
        WalkthroughItem page2 =new WalkthroughItem(R.drawable.androidmobile_3);
        WalkthroughItem page3 =new WalkthroughItem(R.drawable.androidmobile_4);
        setProgressType(DOTS_TYPE);
      //  setProgressBarColor(R.color.colorPrimary);
        page1.setTitleColorID(R.color.colorPrimary);
        page1.setSubTitleColorID(R.color.colorPrimary);
        page2.setTitleColorID(R.color.colorPrimary);
        page2.setSubTitleColorID(R.color.colorPrimary);
        page3.setTitleColorID(R.color.colorPrimary);
        page3.setSubTitleColorID(R.color.colorPrimary);


        addPage(page1);
        addPage(page2);
        addPage(page3);




    }

    @Override
    public void onFinish() {
        super.onFinish();

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);



    }
}

