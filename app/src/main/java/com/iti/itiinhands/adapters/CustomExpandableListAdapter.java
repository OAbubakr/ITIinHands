package com.iti.itiinhands.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by HP on 22/05/2017.
 */

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {


    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private int[] images;
    private int[] trackImages;
    private int[] itiImages;
    private int[] third;
    private int type;
    //int[] images={R.drawable.social,R.drawable.home_512,R.drawable.forums,R.drawable.info_512,R.drawable.outbox};


    public CustomExpandableListAdapter(Context context, List<String> listDataHeader,
                                       HashMap<String, List<String>> listChildData, int[] images , int[] trackImages , int[] itiImages,int[] third ,int type) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.images=images;
        this.third=third;
        this.trackImages=trackImages;
        this.itiImages=itiImages;
        this.type=type;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        ImageView childImage = (ImageView) convertView
                .findViewById(R.id.child_image);

        switch (type) {

            //student
            case 1:

                switch (groupPosition)
                {
                    case 1:
                        childImage.setImageResource(trackImages[childPosition]);
                        break;

                    case 3 :
                        childImage.setImageResource(itiImages[childPosition]);
                        break;
                    case 2:
                        childImage.setImageResource(third[childPosition]);
                        break;
                    default:
                        break;
                }


            break;

            //staff
            case 2:

                switch (groupPosition)
                {
                    //staff work
                    case 0:
                        childImage.setImageResource(trackImages[childPosition]);
                        break;
                    //iti
                    case 2:
                        childImage.setImageResource(itiImages[childPosition]);
                        break;

                    //itians
                    case 3:
                        childImage.setImageResource(third[childPosition]);
                        break;
                    default:
                        break;
                }


                break;

            case 3:

                switch (groupPosition)
                {
                    case 1:
                        childImage.setImageResource(trackImages[childPosition]);
                        break;
                    default:
                        break;
                }


                break;


            case 4:

                switch (groupPosition)
                {
                    case 1:
                        childImage.setImageResource(trackImages[childPosition]);
                        break;
                    case 2:
                        childImage.setImageResource(itiImages[childPosition]);
                        break;
                    default:
                        break;
                }


                break;



            default:
                break;



        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                       .size();
        //int type=1;
        //switch (type) {

           // case 0:
                //zero for student type
//            if (groupPosition == 0 || groupPosition == 4) {
//                return 0;
//            } else {
//                return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                        .size();
//            }
//
//            case 1:
//                //one for staff type
//                if (groupPosition == 3 || groupPosition == 4) {
//                    return 0;
//                } else {
//                    return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                            .size();
//                }
//
//
//            default:
//                return 0;

        //}
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);


        ImageView image=(ImageView)convertView.findViewById(R.id.imageView);
        image.setImageResource(images[groupPosition]);


        ImageView arrawImage=(ImageView)convertView.findViewById(R.id.arrow);


        if(_listDataChild.get(_listDataHeader.get(groupPosition)).size()>1)
        {
            arrawImage.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }




    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}



