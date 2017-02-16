package com.yhcloud.thankyou.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yhcloud.thankyou.mInterface.Pinnable;

import java.util.List;

/**
 * Created by leig on 2017/2/14.
 */

public class PinnedDivider extends RecyclerView.ItemDecoration {
    private Paint paint;//画笔
    private Rect rect = new Rect();//用于存放测量文字Rect
    private Drawable divider;//分割线颜色
    private int mType;
    private Builder builder;

    private PinnedDivider(Builder builder) {
        this.builder = builder;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
//        this.paint.setTextSize(builder.tagTitleTextSize);
        this.divider = new ColorDrawable(builder.dividerColor);
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    /**
     * 设置分组悬停视图的显示区域
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        int position = params.getViewLayoutPosition() - builder.headerCount;

        //防止越界
        if (position > builder.data.size() - 1 || position < 0) {
            return;
        }
        //第1项肯定要有tag
        if (position == 0) {
            outRect.set(0, builder.tagHeight, 0, 0);
        }
        //其余项，不为空且跟前一个tag不一样了，说明是新的分类，也要tag
        else if (!builder.data.get(position).getPinnedTag().equals(builder.data.get(position - 1).getPinnedTag())) {
            outRect.set(0, builder.tagHeight, 0, 0);
        }
        //和下一项一样的，都需要分割线
        for (int i = 0; i < builder.data.size() - 1; i++) {
            String tag1 = builder.data.get(i).getPinnedTag();
            String tag2 = builder.data.get(i + 1).getPinnedTag();
            if (tag1.equals(tag2)) {
                int top = outRect.top;
                outRect.set(0, top, 0, builder.dividerHeight);
            }
        }
    }

    /**
     * 绘制最底层
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();

        //绘制随着滚动的分组视图
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition() - builder.headerCount;

            //防止越界
            if (position > builder.data.size() - 1 || position < 0) {
                continue;
            }

            String tag = builder.data.get(position).getPinnedTag();

            int top = child.getTop() - params.topMargin - builder.tagHeight;
            int bottom = child.getTop() - params.topMargin;

            //等于0肯定要有title的
            if (position == 0) {
                drawView(c, position, parent, top, bottom);
            }
            //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
            else if (null != tag && !tag.equals(builder.data.get(position - 1).getPinnedTag())) {
                drawView(c, position, parent, top, bottom);
            }
        }

        //和下一项一样的，都需要分割线
        int left, top, right, bottom;
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            String tag1 = builder.data.get(position).getPinnedTag();
            String tag2 = builder.data.get(position + 1).getPinnedTag();
            if (tag1.equals(tag2)) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                left = parent.getPaddingLeft();
                right = parent.getWidth() - parent.getPaddingRight();
                top = child.getBottom() + params.bottomMargin;
                bottom = top + builder.dividerHeight;
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }

    /**
     * 绘制最上层
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        int next = findNextTagPosition(position);

        //绘制悬停的分组视图
        boolean flag = false;
        if (next != -1 && next < position - 1) {
            View child = parent.findViewHolderForLayoutPosition(next).itemView;
            int dy = child.getTop() - builder.tagHeight * 2;
            if (dy <= 0) {
                c.save();
                c.translate(0, dy);
                flag = true;
            }
        }
        drawView(c, position, parent, parent.getPaddingTop(), parent.getPaddingTop() + builder.tagHeight);
        if (flag) {
            c.restore();
        }
    }

    /**
     * 查找下一个Tag的位置
     */
    private int findNextTagPosition(int position) {
        String curTag = builder.data.get(position).getPinnedTag();
        for (int i = position + 1; i < builder.data.size(); i++) {
            String tag = builder.data.get(i).getPinnedTag();
            if (!tag.equals(curTag)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 绘制分组悬停视图
     */
//    private void drawView(Canvas c, int position, RecyclerView parent, int top, int bottom) {
//        //绘制背景
//        paint.setColor(builder.tagBgColor);
//        c.drawRect(parent.getPaddingLeft(), top, parent.getRight() - parent.getPaddingRight(), bottom, paint);
//        //绘制文字
//        String tag = builder.data.get(position).getPinnedTag();
//        paint.setColor(builder.tagTitleTextColor);
//        paint.getTextBounds(tag, 0, tag.length(), rect);
//        c.drawText(tag, parent.getPaddingLeft() + builder.tagTextLeftPadding, bottom - (builder.tagHeight - rect.height()) / 2, paint);
//    }

    /**
     * 绘制分组悬停视图
     */
    private void drawView(Canvas c, int position, RecyclerView parent, int top, int bottom) {
        //绘制背景
        if (0 != builder.data.get(position).getPinnedColor()) {
            paint.setColor(builder.data.get(position).getPinnedColor());
        } else {
            paint.setColor(builder.tagBgColor);
        }
        c.drawRect(parent.getPaddingLeft(), top, parent.getRight() - parent.getPaddingRight(), bottom, paint);
        //绘制标题文字
        String tag = builder.data.get(position).getPinnedTag();
        paint.setColor(builder.tagTitleTextColor);
        paint.setTextSize(builder.tagTitleTextSize);
        paint.getTextBounds(tag, 0, tag.length(), rect);
        c.drawText(tag, (parent.getRight() - rect.width()) / 2, bottom - (builder.tagHeight) / 2 - 5, paint);
        //绘制简介文字
        String info = builder.data.get(position).getPinnedInfo();
        paint.setColor(builder.tagInfoTextColor);
        paint.setTextSize(builder.tagInfoTextSize);
        paint.getTextBounds(info, 0, info.length(), rect);
        c.drawText(info, (parent.getRight() - rect.width()) / 2, bottom - (builder.tagHeight - rect.height() * 2) / 2 + 5, paint);
    }

    public static class Builder {
        //必填部分
        private Resources resources;
        private List<? extends Pinnable> data;

        //可选部分
        private int headerCount;//其余的HeaderView数量

        //Tag
        private int tagBgColor = 0xFFFFEBCD;
        private int tagHeight = 300;
        private int tagTitleTextColor = 0xFF111111;
        private int tagInfoTextColor = 0xFF787878;
        private int tagTitleTextSize = 38;
        private int tagInfoTextSize = 28;
        private int tagTextLeftPadding = 30;

        //分割线
        private int dividerColor = 0xFF20B2AA;
        private int dividerHeight = 3;

        public Builder(Context context, List<? extends Pinnable> data) {
            this.resources = context.getResources();
            this.data = data;
            if (data == null || data.isEmpty()) {
                throw new RuntimeException("data can not be empty");
            }
        }

        public Builder tagBgColor(@ColorRes int tagBgColor) {
            this.tagBgColor = resources.getColor(tagBgColor);
            return this;
        }

        public Builder tagHeight(@DimenRes int tagHeight) {
            this.tagHeight = resources.getDimensionPixelSize(tagHeight);
            return this;
        }

        public Builder tagTitleTextColor(@ColorRes int tagTitleTextColor) {
            this.tagTitleTextColor = resources.getColor(tagTitleTextColor);
            return this;
        }

        public Builder tagTitleTextSize(@DimenRes int tagTitleTextSize) {
            this.tagTitleTextSize = resources.getDimensionPixelSize(tagTitleTextSize);
            return this;
        }

        public Builder tagInfoTextColor(@ColorRes int tagTextColor) {
            this.tagTitleTextColor = resources.getColor(tagTextColor);
            return this;
        }

        public Builder tagInfoTextSize(@DimenRes int tagTextSize) {
            this.tagTitleTextSize = resources.getDimensionPixelSize(tagTextSize);
            return this;
        }

        public Builder tagTextLeftPadding(@DimenRes int tagTextLeftPadding) {
            this.tagTextLeftPadding = resources.getDimensionPixelSize(tagTextLeftPadding);
            return this;
        }

        public Builder dividerColor(@ColorRes int dividerColor) {
            this.dividerColor = resources.getColor(dividerColor);
            return this;
        }

        public Builder dividerHeight(@DimenRes int dividerHeight) {
            this.dividerHeight = resources.getDimensionPixelSize(dividerHeight);
            return this;
        }

        public PinnedDivider build() {
            return new PinnedDivider(this);
        }

    }
}
