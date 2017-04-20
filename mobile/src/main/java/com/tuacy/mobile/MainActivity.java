package com.tuacy.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.tuacy.sectionpin.SectionPinListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private SectionPinListView mListPinSection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mListPinSection = (SectionPinListView) findViewById(R.id.list_section_pin);
	}

	private void initEvent() {

	}

	private void initData() {
//		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, getData());
//		mListPinSection.setAdapter(adapter);
	}

	private ArrayList<String> getData() {
		ArrayList<String> list = new ArrayList<>();
		list.add("180平米的房子");
		list.add("一个勤劳漂亮的老婆");
		list.add("一辆宝马");
		list.add("一个强壮且永不生病的身体");
		list.add("一个喜欢的事业");
		list.add("180平米的房子");
		list.add("一个勤劳漂亮的老婆");
		list.add("一辆宝马");
		list.add("一个强壮且永不生病的身体");
		list.add("一个喜欢的事业");
		list.add("180平米的房子");
		list.add("一个勤劳漂亮的老婆");
		list.add("一辆宝马");
		list.add("一个强壮且永不生病的身体");
		list.add("一个喜欢的事业");
		list.add("180平米的房子");
		list.add("一个勤劳漂亮的老婆");
		list.add("一辆宝马");
		list.add("一个强壮且永不生病的身体");
		list.add("一个喜欢的事业");
		list.add("180平米的房子");
		list.add("一个勤劳漂亮的老婆");
		list.add("一辆宝马");
		list.add("一个强壮且永不生病的身体");
		list.add("一个喜欢的事业");
		list.add("180平米的房子");
		list.add("一个勤劳漂亮的老婆");
		list.add("一辆宝马");
		list.add("一个强壮且永不生病的身体");
		list.add("一个喜欢的事业");
		return list;
	}
}
