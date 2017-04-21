package com.tuacy.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuacy.sectionpin.SectionPinAdapter;

import java.util.List;

public class PinAdapter extends SectionPinAdapter {

	private static final int VIEW_TYPE_ITEM_TIME    = 0;
	private static final int VIEW_TYPE_ITEM_CONTENT = 1;

	private List<String> mData    = null;
	private Context      mContext = null;

	public PinAdapter(Context context, List<String> data) {
		mContext = context;
		mData = data;
	}

	public void setData(List<String> data) {
		mData = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public String getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (position % 5 == 0) {
			return VIEW_TYPE_ITEM_TIME;
		} else {
			return VIEW_TYPE_ITEM_CONTENT;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		if (getItemViewType(position) == VIEW_TYPE_ITEM_TIME) {
			ViewSectionHolder viewHolder;
			if (convertView == null) {
				v = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_title, parent, false);
				viewHolder = new ViewSectionHolder(v);
				v.setTag(viewHolder);
			} else {
				v = convertView;
				viewHolder = (ViewSectionHolder) v.getTag();
			}
			viewHolder.mViewSectionName.setText(getItem(position));
		} else if (getItemViewType(position) == VIEW_TYPE_ITEM_CONTENT) {
			ViewContentHolder viewHolder;
			if (convertView == null) {
				v = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_content, parent, false);
				viewHolder = new ViewContentHolder(v);
				v.setTag(viewHolder);
			} else {
				v = convertView;
				viewHolder = (ViewContentHolder) v.getTag();
			}
			viewHolder.mViewContentName.setText(getItem(position));
		}
		return v;
	}

	@Override
	public boolean isSection(int position) {
		return getItemViewType(position) == VIEW_TYPE_ITEM_TIME;
	}

	private class ViewSectionHolder {

		private TextView mViewSectionName;

		ViewSectionHolder(View v) {
			mViewSectionName = (TextView) v.findViewById(R.id.text_adapter_title_name);
		}
	}

	private class ViewContentHolder {

		private TextView mViewContentName;

		ViewContentHolder(View v) {
			mViewContentName = (TextView) v.findViewById(R.id.text_adapter_content_name);
		}
	}
}
