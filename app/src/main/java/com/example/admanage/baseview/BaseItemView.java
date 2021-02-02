package com.example.admanage.baseview;/*
package com.example.admanage.baseview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;
import android.widget.Scroller;

public class BaseItemView extends Scroller {
    public BaseItemView(Context context) {
        super(context);
    }

    public void setView(BaseListView listView, int contentId, int menuId, float menuScale){
        this.listView = listView;
        this.content = View.inflate(getContext(), contentId, null);
        this.menu = View.inflate(getContext(), menuId, null);
        this.scale = menuScale;
        LayoutParams param1 = new LayoutParams(Width, LayoutParams.MATCH_PARENT);
        addView(content, param1);
        LayoutParams param2 = new LayoutParams((int) (Width * menuScale), LayoutParams.MATCH_PARENT);
        addView(menu, param2);
    }

    public View getContent(){
        return content;
    }

    public View getMenu(){
        return menu;
    }

    public void showContent(){
        Scroller mScroller = null;
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), -mScroller.getFinalX(), 0);
        invalidate();
    }

    public void showMenu(){
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), menu.getWidth() - mScroller.getFinalX(), 0);
        invalidate();
    }

    public boolean shouldShowContent(int dx){
        //初始化
        if(menu.getWidth() == 0){
            resetWidth();
        }
        if(dx > 0){
            //右滑，当滑过1/4的时候开始变化
            if(mScroller.getFinalX() < menu.getWidth() * 3 / 4){
                return true;
            }else{
                return false;
            }
        }else{
            //左滑，当滑过1/4的时候开始变化
            if(mScroller.getFinalX() < menu.getWidth() / 4){
                return true;
            }else{
                return false;
            }
        }
    }

    public void scroll(int dx){
        if(dx > 0){
            //右滑
            if(mScroller.getFinalX() > 0){
                if(dx > mScroller.getFinalX()){
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), -mScroller.getFinalX(), 0);
                }else{
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), -dx, 0);
                }
            }else{
                mScroller.setFinalX(0);
            }
            invalidate();
        }else{
            //左滑
            if(mScroller.getFinalX() < menu.getWidth()){
                if(mScroller.getFinalX() - dx > menu.getWidth()){
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), menu.getWidth()- mScroller.getFinalX(), 0);
                }else{
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), -dx, 0);
                }
            }else{
                mScroller.setFinalX(menu.getWidth());
            }
            invalidate();
        }
    }

    public void resetWidth(){
        ViewGroup.LayoutParams param1 = content.getLayoutParams();
        if(param1 == null){
            param1 = new ViewGroup.LayoutParams(Width, ViewGroup.LayoutParams.MATCH_PARENT);
        }else{
            param1.width = Width;
        }
        content.setLayoutParams(param1);
        ViewGroup.LayoutParams param2 = menu.getLayoutParams();
        if(param2 == null){
            param2 = new ViewGroup.LayoutParams((int) (Width * scale), ViewGroup.LayoutParams.MATCH_PARENT);
        }else{
            param2.width = (int) (Width * scale);
        }
        menu.setLayoutParams(param2);
    }
}
*/
