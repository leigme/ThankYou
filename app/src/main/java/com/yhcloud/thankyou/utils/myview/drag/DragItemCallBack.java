package com.yhcloud.thankyou.utils.myview.drag;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Author: leig
 * Date: 2016/7/4 16:52
 * Description: 说明
 * PackageName: com.yhcloud.thankyou.utils.myview.drag
 * Copyright: 阳环科技实业有限公司
 **/
public class DragItemCallBack extends ItemTouchHelper.Callback {

    private RecycleCallBack mCallBack;

    public DragItemCallBack(RecycleCallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        int action = ItemTouchHelper.ACTION_STATE_IDLE | ItemTouchHelper.ACTION_STATE_DRAG;
        return makeFlag(action, dragFlags);
//        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.e("DragItemCallBack","onMove");
        int start = viewHolder.getAdapterPosition();
        int end = target.getAdapterPosition();
        mCallBack.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        Log.e("DragItemCallBack", "onSelectedChanged  "+ actionState);
        if (viewHolder instanceof DragHolderCallBack){
            DragHolderCallBack holder = (DragHolderCallBack) viewHolder;
            holder.onSelect();
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.e("DragItemCallBack","clearView");
        if (viewHolder instanceof DragHolderCallBack){
            DragHolderCallBack holder = (DragHolderCallBack) viewHolder;
            if (!recyclerView.isComputingLayout()) {
                holder.onClear();
            }
        }
    }
}
