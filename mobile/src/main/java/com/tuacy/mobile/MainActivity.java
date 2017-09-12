package com.tuacy.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andview.refreshview.XRefreshView;
import com.tuacy.sectionpin.SectionPinListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private XRefreshView       mRefreshCurrent;
	private SectionPinListView mListPinSection;
	private PinAdapter         mAdapter;
	private List<String>       mData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mRefreshCurrent = (XRefreshView) findViewById(R.id.refresh_alarm_install_current);
		mListPinSection = (SectionPinListView) findViewById(R.id.list_section_pin);
		initRefreshView(mRefreshCurrent);
	}

	private void initEvent() {

	}

	private void initData() {
		mData = getData();
		mAdapter = new PinAdapter(this, mData);
		mListPinSection.setAdapter(mAdapter);
	}

	private void initRefreshView(XRefreshView ptrFrame) {
		ptrFrame.setPullRefreshEnable(true);
		ptrFrame.setPullLoadEnable(true);
		ptrFrame.setAutoRefresh(false);
		ptrFrame.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
			@Override
			public void onRefresh() {
				mData.clear();
				mData.add("2017-07-20");
				mData.add("萍乡");
				mData.add("高安");
				mData.add("江西");
				mData.add("南昌");
				mAdapter.setData(mData);
				mRefreshCurrent.stopLoadMore();
				mRefreshCurrent.stopRefresh();
			}

			@Override
			public void onLoadMore(boolean isSilence) {
				mData.add("2017-07-20");
				mData.add("萍乡");
				mData.add("高安");
				mData.add("江西");
				mData.add("南昌");
				mAdapter.setData(mData);
				mRefreshCurrent.stopLoadMore();
				mRefreshCurrent.stopRefresh();
			}
		});
	}

	private ArrayList<String> getData() {
		ArrayList<String> list = new ArrayList<>();
		list.add("2016-07-20");
		list.add("萍乡");
		list.add("高安");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-21");
		list.add("江西");
		list.add("南昌");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-22");
		list.add("中国");
		list.add("北京");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-23");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-24");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-25");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-26");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-27");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-28");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-29");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-30");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		list.add("2016-07-21");
		list.add("辽宁");
		list.add("沈阳");
		list.add("江西");
		list.add("南昌");
		return list;
	}
}
