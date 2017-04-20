package com.tuacy.sectionpin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SectionPinListView extends ListView implements AbsListView.OnScrollListener {

	private OnScrollListener mScrollListener = null;
	/**
	 * 固定在顶部的View
	 */
	private View             mViewPin        = null;

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
	}

	@Override
	public void setOnScrollListener(OnScrollListener listener) {
		mScrollListener = listener;
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
	 * @param visibleItemCount 当前能看见的列表项个数（小半个也算）
	 * @param totalItemCount   列表项共数
	 */
	@Override
	public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (mScrollListener != null) {
			mScrollListener.onScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
		}
		//TODO:
		int headerViewCount = getHeaderViewsCount();
	}

	/**
	 * 获取固定在顶部的View
	 * @return View
	 */
	private View getPinView() {
		return null;
	}
}
