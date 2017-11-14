package com.example.jh.gridviewselectdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AbsListView.MultiChoiceModeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GridView mGridView;
    private GridAdapter mGridAdapter;
    private TextView mActionText;
    private static final int MENU_SELECT_ALL = 0;
    private Map<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();


    // 加载图片数据
    private int[] mImgIds = new int[]{R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_3,
            R.drawable.img_4, R.drawable.img_5, R.drawable.img_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = (GridView) findViewById(R.id.gridview);
        // 多项选择模式
        mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        mGridAdapter = new GridAdapter(this);
        mGridView.setAdapter(mGridAdapter);
        // 设置多选择模式监听
        mGridView.setMultiChoiceModeListener(this);
    }

    //————————————下方是重写的方法————————————————
    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        mActionText.setText(formatString(mGridView.getCheckedItemCount()));
        mSelectMap.put(position, checked);
        mode.invalidate();
    }

    /**
     * 重写MultiChoiceModeListener end
     * @param checkedItemCount
     * @return
     */
    @SuppressLint("StringFormatMatches")
    private String formatString(int checkedItemCount) {
        return String.format(getString(R.string.selection), checkedItemCount);
    }

    /**
     * 多选监听开始执行的方法
     *
     * @param mode
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        View v = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
        mActionText = v.findViewById(R.id.action_text);
        mActionText.setText(formatString(mGridView.getCheckedItemCount()));
        mode.invalidate();
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        menu.getItem(MENU_SELECT_ALL).setEnabled(
                mGridView.getCheckedItemCount() != mGridView.getCount());
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_select:
                for (int i = 0; i < mGridView.getCount(); i++){
                    mGridView.setItemChecked(i, true);
                    mSelectMap.put(i, true);
                }
                break;
            case R.id.menu_unselect:
                for (int i = 0; i < mGridView.getCount(); i++){
                    mGridView.setItemChecked(i, false);
                    mSelectMap.clear();
                }
                break;
        }

        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mGridAdapter.notifyDataSetChanged();
    }

    // 适配器
    private class GridAdapter extends BaseAdapter {

        private Context mContext;

        public GridAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mImgIds.length;
        }

        @Override
        public Integer getItem(int position) {
            return mImgIds[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.e(TAG, "getView 方法执行");
            GridItem item;
            if (convertView == null) {
                item = new GridItem(mContext);
                Log.e(TAG, "GridItem 初始化");
                item.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT,
                        AbsListView.LayoutParams.FILL_PARENT));
            } else {
                item = (GridItem) convertView;
            }
            // 设置数据
            item.setImgResId(getItem(position));
            item.setChecked(mSelectMap.get(position) == null ? false
                    : mSelectMap.get(position));
            return item;
        }
    }
}
