package com.iti.itiinhands.adapters.maps;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by home on 5/19/2017.
 */

public class GridViewDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public GridViewDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.top = space;
    }

}
