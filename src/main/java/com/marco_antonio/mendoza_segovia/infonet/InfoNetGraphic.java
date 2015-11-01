package com.marco_antonio.mendoza_segovia.infonet;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by marco on 28-10-15.
 */

@CoordinatorLayout.DefaultBehavior(InfoNetGraphic.Behavior.class)
public class InfoNetGraphic extends FloatingActionButton {
    TextView msg;
    ImageView icon;

    public InfoNetGraphic(Context context) {
        this(context, (AttributeSet)null);
    }

    public InfoNetGraphic(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InfoNetGraphic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
