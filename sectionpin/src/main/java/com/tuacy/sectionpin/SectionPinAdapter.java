package com.tuacy.sectionpin;

import android.widget.BaseAdapter;


public abstract class SectionPinAdapter extends BaseAdapter {

	/**
	 * 判断是否是section
	 * @param position adapter position
	 * @return true or false
	 */
	public abstract boolean isSection(int position);

}
