package com.example.jing.kapep.Activitys.OtherDetailActivity;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;

/**
 * Created by jing on 2017/7/6.
 * 列表状态，堆叠的样式
 */

public class KapCustomContentLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    // **** 布局子视图 ****
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //如果没有item，直接返回
        if (getItemCount() <= 0) return;
        // 跳过preLayout，preLayout主要用于支持动画
        if (state.isPreLayout()) {
            return;
        }
        //在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
        detachAndScrapAttachedViews(recycler);
        //定义竖直方向的偏移量
        int offsetY = 0;
        totalHeight = 0;
        for (int i = 0; i < getItemCount(); i++) {

            //这里就是从缓存里面取出
            View view = recycler.getViewForPosition(i);
            //将View加入到RecyclerView中
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);
//            // 将View布局 左上右下 的位置
//            layoutDecorated(view, 0, offsetY, width, offsetY + height);
            totalHeight += height;
            Rect frame = allItemFrames.get(i);
            if (frame == null) {
                frame = new Rect();
            }
            frame.set(0, offsetY, width, offsetY + height);
            // 将当前的Item的Rect边界数据保存
            allItemFrames.put(i, frame);
            // 由于已经调用了detachAndScrapAttachedViews，因此需要将当前的Item设置为未出现过
            hasAttachedItems.put(i, false);
            //将竖直方向偏移量增大height
            offsetY += height;
        }
        //如果所有子View的高度和没有填满RecyclerView的高度，
        // 则将高度设置为RecyclerView的高度
        totalHeight = Math.max(totalHeight, getVerticalSpace());
        recycleAndFillItems(recycler, state);
    }
    // **** 添加滑动,滑动 ****
    // 水平
//    @Override
//    public boolean canScrollHorizontally() {
//        return true;
//    }
//    @Override
//    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        return super.scrollHorizontallyBy(dx, recycler, state);
//    }

    // 竖直
    @Override
    public boolean canScrollVertically() {
        return true;
    }
    private int verticalScrollOffset = 0;
    private int totalHeight = 0;
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 先detach掉所有的子View
        detachAndScrapAttachedViews(recycler);
        // 回收 获取
        recycleAndFillItems(recycler, state);
        // 实际要滑动的距离
        int travel = dy;
        // 滑动到最顶部
        if (verticalScrollOffset+dy<0) return -verticalScrollOffset;
        // 滑动到最底部
        if (verticalScrollOffset+dy>totalHeight-getVerticalSpace()) return totalHeight-getVerticalSpace()-verticalScrollOffset;
        travel = totalHeight - getVerticalSpace() - verticalScrollOffset;
        verticalScrollOffset += travel;
        // 平移容器内的item
        offsetChildrenVertical(-travel);
        return travel;
    }
    // getVerticalSpace()函数: 用于获取RecyclerView在垂直方向上的可用空间，即去除了padding后的高度
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }
    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
    // **** 回收子view ****
    // 1. 记录子view的位置，用于判断是不是需要被回收
    // 2.
    /**
     * 回收不需要的Item，并且将需要显示的Item从缓存中取出
     */
    //保存所有的Item的上下左右的偏移量信息
    private SparseArray<Rect> allItemFrames = new SparseArray<>();
    //记录Item是否出现过屏幕且还没有回收。true表示出现过屏幕上，并且还没被回收
    private SparseBooleanArray hasAttachedItems = new SparseBooleanArray();

    private void recycleAndFillItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) { // 跳过preLayout，preLayout主要用于支持动画
            return;
        }

        // 当前scroll offset状态下的显示区域
        Rect displayFrame = new Rect(0, verticalScrollOffset, getHorizontalSpace(), verticalScrollOffset + getVerticalSpace());

        /**
         * 将滑出屏幕的Items回收到Recycle缓存中
         */
        Rect childFrame = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childFrame.left = getDecoratedLeft(child);
            childFrame.top = getDecoratedTop(child);
            childFrame.right = getDecoratedRight(child);
            childFrame.bottom = getDecoratedBottom(child);
            //如果Item没有在显示区域，就说明需要回收
            if (!Rect.intersects(displayFrame, childFrame)) {
                //回收掉滑出屏幕的View
                removeAndRecycleView(child, recycler);

            }
        }
        //重新显示需要出现在屏幕的子View
        for (int i = 0; i < getItemCount(); i++) {

            if (Rect.intersects(displayFrame, allItemFrames.get(i))) {

                View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                addView(scrap);

                Rect frame = allItemFrames.get(i);
                //将这个item布局出来
                layoutDecorated(scrap,
                        frame.left,
                        frame.top - verticalScrollOffset,
                        frame.right,
                        frame.bottom - verticalScrollOffset);

            }
        }
    }
}
