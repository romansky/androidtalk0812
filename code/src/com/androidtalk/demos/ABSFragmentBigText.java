package com.androidtalk.demos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.androidtalk.R;

public class ABSFragmentBigText extends SherlockFragment {

	private String title;
	private String content;

	public static ABSFragmentBigText getInstance(String title, String content){
		ABSFragmentBigText absFragmentBigText = new ABSFragmentBigText();
		absFragmentBigText.title = title;
		absFragmentBigText.content = content;
		return absFragmentBigText;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View absDemoView = inflater.inflate(R.layout.big_text_fragment, container, false);
		((TextView)absDemoView.findViewById(R.id.big_text_fragment__title)).setText(this.title);
		((TextView)absDemoView.findViewById(R.id.big_text_fragment__content)).setText(this.content);
		return absDemoView;
	}
}
