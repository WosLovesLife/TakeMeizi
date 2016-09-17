package com.wosloveslife.takemeizi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by YesingBeijing on 2016/9/13.
 */
public class AutoFitImageView extends ImageView {

    public AutoFitImageView(Context context) {
        super(context);
    }

    public AutoFitImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFitImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
    }

    @Override
    public void setImageIcon(Icon icon) {
        super.setImageIcon(icon);
    }
}
