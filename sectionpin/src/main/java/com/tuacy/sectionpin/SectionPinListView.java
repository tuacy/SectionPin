package com.tuacy.sectionpin;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SectionPinListView extends ListView implements AbsListView.OnScrollListener {

	private OnScrollListener mScrollListener            = null;
	/**
	 * 固定在顶部的View
	 */
	private View             mViewSectionPin            = null;
	/**
	 * 是否允许固定顶部
	 */
	private boolean          mSectionPinEnable          = true;
	/**
	 * 固定View偏移顶部的位置，当两个section碰到的时候是要慢慢偏移出去的 <= 0
	 */
	private float            mSectionPinOffset          = 0f;
	/**
	 * 固定View在adapter中的位置
	 */
	private int              mSectionPinAdapterPosition = -1;
	/**
	 * list view的width mode
	 */
	private int              mWidthMode                 = MeasureSpec.EXACTLY;

	public SectionPinListView(Context context) {
		this(context, null);
	}

	public SectionPinListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SectionPinListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SectionPinListView);
		mSectionPinEnable = array.getBoolean(R.styleable.SectionPinListView_section_pin, true);
		array.recycle();
		/**
		 * 监听Scroll事件，因为要在滑动的过程中固定section view在头部(注意这里调用的是super.setOnScrollListener()函数)
		 */
		super.setOnScrollListener(this);
	}

	/**
	 * 设置是否section view 固定在顶部
	 *
	 * @param enable 是否固定
	 */
	public void setSectionPinEnable(boolean enable) {
		mSectionPinEnable = enable;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
	}

	@Override
	public void setOnScrollListener(OnScrollListener listener) {
		mScrollListener = listener;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);

		if (getAdapter() == null || !(getAdapter() instanceof SectionPinAdapter) || mViewSectionPin == null || !mSectionPinEnable) {
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

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
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
		if (getAdapter() != null && !(getAdapter() instanceof SectionPinAdapter)) {
			return;
		}
		int headerViewCount = getHeaderViewsCount();
		if (getAdapter() == null || !mSectionPinEnable || firstVisibleItem < headerViewCount) {
			/**
			 * 第一个section都还没出来
			 */
			mViewSectionPin = null;
			mSectionPinOffset = 0f;
			mSectionPinAdapterPosition = -1;
			for (int i = 0; i < visibleItemCount; i++) {
				View view = getChildAt(i);
				if (view != null) {
					view.setVisibility(VISIBLE);
				}
			}
			return;
		}
		if (getAdapter().getCount() <= 0) {
			return;
		}
		int adapterFirstVisibleItem = firstVisibleItem - headerViewCount;
		int pinViewAdapterPosition = getPinViewAdapterPosition(adapterFirstVisibleItem);
		if (pinViewAdapterPosition != -1 && mSectionPinAdapterPosition != pinViewAdapterPosition) {
			/**
			 * pin view 被换掉了
			 */
			mViewSectionPin = getSectionPinView(pinViewAdapterPosition);
			ensurePinViewLayout(mViewSectionPin);
		}
		if (mViewSectionPin == null) {
			return;
		}

		mSectionPinOffset = 0f;
		/**
		 * 遍历所有可见的View
		 */
		for (int index = 0; index < visibleItemCount; index++) {
			int adapterPosition = index + adapterFirstVisibleItem;
			/**
			 * 判断是不是section
			 */
			SectionPinAdapter adapter = (SectionPinAdapter) getAdapter();
			if (adapter.isSection(adapterPosition)) {
				View sectionView = getChildAt(index);
				int sectionTop = sectionView.getTop();
				int pinViewHeight = mViewSectionPin.getHeight();
				sectionView.setVisibility(VISIBLE);
				if (sectionTop < pinViewHeight && sectionTop > 0) {
					mSectionPinOffset = sectionTop - pinViewHeight;
				} else if (sectionTop <= 0) {
					sectionView.setVisibility(INVISIBLE);
				}

			}
		}
		invalidate();
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
		/**
		 * getView的第二个参数一定要传空，因为我们不能用复用的View
		 */
		return getAdapter().getView(adapterPosition, null, this);
	}

	/**
	 * 根据第一个可见的adapter的位置去获取临近的一个section的位置
	 *
	 * @param adapterFirstVisible 第一个可见的adapter的位置
	 * @return -1：未找到 >=0 找到位置
	 */
	private int getPinViewAdapterPosition(int adapterFirstVisible) {
		if (getAdapter() == null || !(getAdapter() instanceof SectionPinAdapter)) {
			return -1;
		}
		SectionPinAdapter adapter = (SectionPinAdapter) getAdapter();
		for (int index = adapterFirstVisible; index >= 0; index--) {
			if (adapter.isSection(index)) {
				return index;
			}
		}
		return -1;
	}

	private void ensurePinViewLayout(View pinView) {
		if (pinView.isLayoutRequested()) {
			/**
			 * 用的是list view的宽度测量，和list view的宽度一样
			 */
			int widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), mWidthMode);

			int heightSpec;
			ViewGroup.LayoutParams layoutParams = pinView.getLayoutParams();
			if (layoutParams != null && layoutParams.height > 0) {
				heightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
			} else {
				heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
			}
			pinView.measure(widthSpec, heightSpec);
			pinView.layout(0, 0, pinView.getMeasuredWidth(), pinView.getMeasuredHeight());
		}
	}
}
