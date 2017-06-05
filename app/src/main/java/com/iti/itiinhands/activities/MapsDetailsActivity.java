package com.iti.itiinhands.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.maps.GridViewDecoration;
import com.iti.itiinhands.adapters.maps.MapsDetailsAdapter;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.List;


public class MapsDetailsActivity extends AppCompatActivity {

    private List<MapTitle> mapTitle = new ArrayList<>();
    private RecyclerView mapsRecyclerView;
    private MapsDetailsAdapter mapsAdapter;

    //    private ImageOverlayView overlayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        setContentView(R.layout.activity_map);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Uri imageUri = Uri.parse("res:/" + R.drawable.ground_floor_model);
//        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.sdvImage);
//        draweeView.setImageURI(imageUri);

//        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        int branchId = getIntent().getIntExtra("branchId", -1);

        switch (branchId) {
            case 1:
                mapTitle.add(new MapTitle(R.drawable.basement_floor, "Basement Floor"));
                mapTitle.add(new MapTitle(R.drawable.ground_floor_model, "Ground Floor"));
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
        }

        ImageViewer imageViewer = new ImageViewer.Builder<>(this, mapTitle).setFormatter(new ImageViewer.Formatter<MapTitle>() {
            @Override
            public String format(MapTitle mapTitle) {
                return mapTitle.getUrl();
            }
        }).build();


        mapsRecyclerView = (RecyclerView) findViewById(R.id.mapsRecycler);

        mapsAdapter = new MapsDetailsAdapter(this,
                R.layout.maps_details_cardview_cell, mapTitle);
        mapsRecyclerView.setHasFixedSize(true);

        mapsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mapsRecyclerView.addItemDecoration(new GridViewDecoration(spacingInPixels));

        mapsRecyclerView.setAdapter(mapsAdapter);


    }

    /*
        private ImageViewer.OnImageChangeListener getImageChangeListener() {
            return new ImageViewer.OnImageChangeListener() {
                @Override
                public void onImageChange(int position) {
                    String url = posters[position];
                    overlayView.setShareText(url);
                    overlayView.setDescription(descriptions[position]);
                }
            };
        }
    */
    public class MapTitle {
        private String title;
        private int drawable;
        private String url;

        public MapTitle(int drawable, String title) {
            this.title = title;
            this.drawable = drawable;
            this.url = "res:/" + drawable;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getDrawable() {
            return drawable;
        }

        public void setDrawable(int drawable) {
            this.drawable = drawable;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
