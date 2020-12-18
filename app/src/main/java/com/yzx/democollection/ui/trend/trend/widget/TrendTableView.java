package com.yzx.democollection.ui.trend.trend.widget;

import android.content.Context;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.yzx.democollection.R;
import com.yzx.democollection.ui.trend.trend.UnitUtil;
import com.yzx.democollection.ui.trend.trend.callbak.ScrollChangeCallback;
import com.yzx.democollection.ui.trend.trend.model.ICellList;

import java.util.HashMap;
import java.util.List;

/**
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2018/5/10 下午5:52
 * description:
 */
public class TrendTableView extends LinearLayout implements ScrollChangeCallback {
    private TextView tvTableLeftSideTitle;
    //容器区域
    private LeftNumberSynchScrollView mLeftScroll;
    private TrendScrollViewWidget mContentScroll;
    private HeaderHorizontalScrollView mHeadScroll;
    //数据区域
    private LeftNumberCustomListView mListView;
    private TrendView trendView;
    private LinearLayout mHeaderLayout;


    //key为item中设置背景色view的hashCode,唯一;
    //value为-1的时候是未选中;
    //value为1的时候是选中;
    private HashMap<Object, Integer> mContainer=new HashMap<>();

    public TrendTableView(Context context) {
        super(context);
        initView();
    }

    public TrendTableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TrendTableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.widget_trend_table,this);
        trendView = (TrendView) findViewById(R.id.trendView);
        tvTableLeftSideTitle = (TextView) findViewById(R.id.tv_table_left_side_title);
        mListView = (LeftNumberCustomListView) findViewById(R.id.lv_number);
        mHeaderLayout= (LinearLayout) findViewById(R.id.ll_header);
        mLeftScroll = (LeftNumberSynchScrollView) findViewById(R.id.scroll_left);
        mContentScroll = (TrendScrollViewWidget) findViewById(R.id.scroll_content);
        mHeadScroll= (HeaderHorizontalScrollView) findViewById(R.id.trend_header_scroll);

        //左边期号的监听器
        mLeftScroll.setScrollViewListener(this);
        //中间走势图的监听器
        mContentScroll.setScrollViewListener(this);
        //走势图顶部的监听器
        mHeadScroll.setScrollViewListener(this);

    }
    //绑定显示期号数据
    public void bindTableLeftSideData(String tableLeftSideHeaderTitle, List<String> leftSizeContentList){
        tvTableLeftSideTitle.setText(tableLeftSideHeaderTitle);
        DataAdapter adapter = new DataAdapter();
        adapter.bindData(leftSizeContentList);
        mListView.setAdapter(adapter);

    }

    //走势图顶部区域数据显示
    public void bindTableHeaderData(List<TableHeaderBean> tableHeaderBeans){
        int deltaDp=getResources().getDimensionPixelSize(R.dimen.item_wh);
        for (TableHeaderBean bean : tableHeaderBeans){
            View headerItemView = LayoutInflater.from(getContext()).inflate(R.layout.template_table_header_item,null);
            LinearLayout llLayout = (LinearLayout) headerItemView.findViewById(R.id.ll_layout);
            TextView tvContent = (TextView) headerItemView.findViewById(R.id.tv_content);
            tvContent.setText(bean.getTitle());
            llLayout.setLayoutParams(new LayoutParams(UnitUtil.dp2px(getContext(),bean.getWidth()),deltaDp));
            mHeaderLayout.addView(headerItemView);
        }
    }

    public void bindTableData(List<ICellList> data){
        trendView.setTableContentData(data);
    }


    /***
     * 同步X轴位置
     * @param left
     */
    @Override
    public void changeXScroll(int left) {
        //顶部和底部容器滑动的回调;
        //此时需要同步中间走势的View的位置;
        mContentScroll.scrollTo(left, mContentScroll.getScrollY());
        //同步顶部自身的位置;
        mHeadScroll.scrollTo(left,0);
    }

    /***
     * 同步Y轴的位置
     * @param top
     */
    @Override
    public void changeYScoll(int top) {
        //中间走势View滑动位置的改变回调;
        //同步左边期号的Y轴的位置
        mLeftScroll.scrollTo(0, top);
        //同步中间走势View的位置
        mContentScroll.scrollTo(mContentScroll.getScrollX(), top);
        //有走势图头部...
        mHeadScroll.scrollTo(mContentScroll.getScrollX(),0);
    }

    /***
     * 数据适配器(含期号)
     */
    private class DataAdapter extends BaseAdapter {
        private List listData=null;
        private int mLeftItemH;
        public DataAdapter(){
            mLeftItemH =  getResources().getDimensionPixelSize(R.dimen.item_wh);
        }
        protected  void bindData(List data){
            this.listData=data;
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.template_table_left_side_item, null, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            ViewGroup.LayoutParams contentParams=viewHolder.tv_content.getLayoutParams();
            contentParams.height= UnitUtil.dp2px(getContext(), 29.9f);
            viewHolder.tv_content.setLayoutParams(contentParams);
            viewHolder.tv_content.setText(listData.get(position).toString());
            return convertView;
        }


    }
    static class ViewHolder{
        TextView tv_content;
        public ViewHolder(View convertView) {
            tv_content = (TextView) convertView.findViewById(R.id.tv_content);
        }
    }
}
