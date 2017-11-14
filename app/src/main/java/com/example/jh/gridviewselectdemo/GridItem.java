package com.example.jh.gridviewselectdemo;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by jinhui on 2017/11/14.
 *
 * 自定义gridview的item类
 */

public class GridItem extends RelativeLayout implements Checkable{

    private Context mContext;
    private boolean mChecked;
    private ImageView mImgView = null;
    private ImageView mSecletView = null;

    public GridItem(Context context) {
//        super(context);
        this(context, null, 0);   // 指向方法2
    }

    public GridItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 方法2
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public GridItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.grid_item, this);
        mImgView = (ImageView) findViewById(R.id.img_view);
        mSecletView = (ImageView) findViewById(R.id.select);
    }


    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        setBackgroundDrawable(checked ? getResources().getDrawable(
                R.drawable.background) : null);
        mSecletView.setVisibility(checked ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
    //————————————————————
    public void setImgResId(int resId){
        if (mImgView != null) {
            mImgView.setBackgroundResource(resId);
        }
    }

}
