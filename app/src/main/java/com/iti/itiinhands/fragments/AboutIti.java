package com.iti.itiinhands.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iti.itiinhands.R;


public class AboutIti extends Fragment {


    ImageView callIcon;
    ImageView emailIcon;
    ImageView fbIcon;
    ImageView twitterIcon;
    ImageView youtube;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about_iti, container, false);
        callIcon=(ImageView)view.findViewById(R.id.call);
        callIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber="0235355656";
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
                startActivity(callIntent);
            }
        });

        emailIcon=(ImageView)view.findViewById(R.id.mail);
        emailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("click","email clicked");
                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
                intent.setData(Uri.parse("mailto:ITIinfo@iti.gov.eg")); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);

            }
        });


        emailIcon=(ImageView)view.findViewById(R.id.mail);
        emailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("click","email clicked");
                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
                intent.setData(Uri.parse("mailto:ITIinfo@iti.gov.eg")); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);

            }
        });


        fbIcon=(ImageView)view.findViewById(R.id.fb);
        fbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                String FACEBOOK_URL="https://www.facebook.com/ITIchannel/";

                    try {
                        int versionCode=getActivity().getApplicationContext().getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                        if(versionCode >= 3002850) {

                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + FACEBOOK_URL));

                        }else{
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/ITIchannel"));
                        }
                    } catch (Exception e) {
                        intent=new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
                    }

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });



        twitterIcon=(ImageView)view.findViewById(R.id.twitter);
        twitterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;


                try {
                    getActivity().getApplicationContext().getPackageManager().getPackageInfo("com.twitter.android", 0);
                     intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=itichannel2"));
                } catch (ActivityNotFoundException e) {
                    intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/itichannel2"));
                }catch (Exception e)
                {
                    intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/itichannel2"));
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });


        youtube=(ImageView)view.findViewById(R.id.youtube);
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                try {
                    getActivity().getApplicationContext().getPackageManager().getPackageInfo("com.google.android.youtube", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:guIgMrHVvcM"));
                } catch(ActivityNotFoundException e){
                    intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/itichannel"));

                }catch (Exception e) {
                    intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/itichannel"));
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                String url = "https://www.youtube.com/user/itichannel";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
            }
        });





        return view;
    }


}
