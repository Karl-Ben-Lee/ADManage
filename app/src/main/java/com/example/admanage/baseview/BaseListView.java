package com.example.admanage.baseview;/*
package com.example.admanage.baseview;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ListView;

public class BaseListView extends ListView {
    public BaseListView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width= MeasureSpec.getSize(widthMeasureSpec);
        //宽度适配，改变ItemView的宽度
        BaseItemView.Width = width;
        for(int i = 0; i < getChildCount(); i++){
            SlideItemView item = (SlideItemView) getChildAt(i);
            item.resetWidth();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float dx = 0;
        float dy = 0;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mTouchX = ev.getX();
                mTouchY = ev.getY();
                mMoveX = ev.getX();
                mMoveY = ev.getY();
                mTouchPosition = pointToPosition((int)ev.getX(), (int)ev.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                dx = ev.getX() - mMoveX;
                dy = ev.getY() - mMoveY;
                if(Math.abs(dx) > Math.abs(dy)){
                    //根据坐标点得到索引值
                    int position = pointToPosition((int)ev.getX(), (int)ev.getY());
                    if(mTouchPosition != ListView.INVALID_POSITION && position == mTouchPosition){
                        //得到内存中真实的Item
                        BaseItemView itemView = (BaseItemView) getChildAt(position - getFirstVisiblePosition());
                        itemView.scroll((int) dx);
                    }
                }
                mMoveX = ev.getX();
                mMoveY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                dx = ev.getX() - mTouchX;
                dy = ev.getY() - mTouchY;
                if(Math.abs(dx) > Math.abs(dy) && Math.abs(dx) >= mTouchSlop){
                    int position = pointToPosition((int)ev.getX(), (int)ev.getY());
                    if(mTouchPosition != ListView.INVALID_POSITION && position == mTouchPosition){
                        //得到真正在内存中的Item
                        BaseItemView itemView = (BaseItemView) getChildAt(position - getFirstVisiblePosition());
                        //根据当前scrollX以及dx判断是否显示正文内容
                        if (itemView.shouldShowContent((int) dx)){
                            itemView.showContent();
                        }else{
                            itemView.showMenu();
                        }
                    }else if(position != mTouchPosition){
                        BaseItemView itemView = (BaseItemView) getChildAt(mTouchPosition - getFirstVisiblePosition());
                        //根据当前scrollX以及dx判断是否显示正文内容
                        if (itemView.shouldShowContent((int) dx)){
                            itemView.showContent();
                        }else{
                            itemView.showMenu();
                        }
                    }
                }else{
                    BaseItemView itemView = (BaseItemView) getChildAt(mTouchPosition - getFirstVisiblePosition());
                    //根据当前scrollX以及dx判断是否显示正文内容
                    if (itemView.shouldShowContent((int) dx)){
                        itemView.showContent();
                    }else{
                        itemView.showMenu();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if(mTouchPosition != ListView.INVALID_POSITION){
                    BaseItemView itemView = (BaseItemView) getChildAt(mTouchPosition - getFirstVisiblePosition());
                    itemView.showContent();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
*/
