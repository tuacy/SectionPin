package com.tuacy.sectionpin;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SectionPinListView extends ListView implements AbsListView.OnScrollListener {

	private OnScrollListener mScrollListener   = null;
	/**
	 * 固定在顶部的View
	 */
	private View             mViewSectionPin   = null;
	/**
	 * 是否允许固定顶部
	 */
	private boolean          mSectionPinEnable = true;
	/**
	 * 固定View偏移顶部的位置，当两个section碰到的时候是要慢慢偏移出去的 <= 0
	 */
	private float            mSectionPinOffset = 0f;
	/**
	 * adapter
	 */
	private SectionPinAdapter mAdapter = null;

	public SectionPinListView(Context context) {
		this(context, null);
	}

	public SectionPinListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SectionPinListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		/**
		 * 监听Scroll事件，因为要在滑动的过程中固定section view在头部(注意这里调用的是super.setOnScrollListener()函数)
		 */
		super.setOnScrollListener(this);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		if (!(adapter instanceof SectionPinAdapter)) {
			throw new IllegalArgumentException("adapter should extends SectionPinAdapter");
		}
		mAdapter = (SectionPinAdapter) adapter;
	}

	@Override
	public void setOnScrollListener(OnScrollListener listener) {
		mScrollListener = listener;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mAdapter == null || mViewSectionPin == null || !mSectionPinEnable) {
			return;
		}
		int saveCount = canvas.save();
		/**
		 * canvas垂直平移
		 */
		canvas.translate(0, mSectionPinOffset);
		canvas.clipRect(0, 0, getWidth(), mViewSectionPin.getMeasuredHeight()); // needed
		mViewSectionPin.draw(canvas);
		canvas.restoreToCount(saveCount);
	}

	/**
	 * 正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调
	 *
	 * @param absListView ListView
	 * @param scrollState 状态 第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动 第2次：scrollState = SCROLL_STATE_FLING(2)
	 *                    手指做了抛的动作（手指离开屏幕前，用力滑了一下） 第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
	 */
	@Override
	public void onScrollStateChanged(AbsListView absListView, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(absListView, scrollState);
		}
	}

	/**
	 * @param absListView      ListView
	 * @param firstVisibleItem 当前能看见的第一个列表项ID（从0开始）
	 * @param visibleItemCount 当前能看见的列表项个数（小半个也算，并且header views 和 footer views 也算在里面了）
	 * @param totalItemCount   列表项总是 (header count + adapter count + footer count)
	 */
	@Override
	public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (mScrollListener != null) {
			mScrollListener.onScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
		}
		int headerViewCount = getHeaderViewsCount();
		if (mAdapter == null || !mSectionPinEnable || firstVisibleItem <= headerViewCount) {
			/**
			 * 第一个section都还没出来
			 */
			mViewSectionPin = null;
			mSectionPinOffset = 0f;
			return;
		}
		int adapterFirstVisibleItem = firstVisibleItem - headerViewCount;
		//TODO: get section view
		/**
		 * 遍历所有可见的View
		 */
		for (int index = 0; index < visibleItemCount; index++) {
			int adapterPosition = index + adapterFirstVisibleItem;
			/**
			 * 判断是不是section
			 */
			if (mAdapter.isSection(adapterPosition)) {
				View sectionView = getChildAt(index);
				int sectionTop = sectionView.getTop();
			}
		}
	}

	/**
	 * 获取固定在顶部的View
	 *
	 * @return View
	 */
	private View getSectionPinView(int adapterPosition) {
		if (getAdapter() == null) {
			return null;
		}
		// 先判断Section Pin View是否变化了,如果没有变化还是用之前的View，变化了就重新去获取
		//TODO:
		return getAdapter().getView(adapterPosition, null, this);
	}
}
