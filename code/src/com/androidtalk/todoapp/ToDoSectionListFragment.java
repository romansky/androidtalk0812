package com.androidtalk.todoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.androidtalk.R;
import com.androidtalk.todoapp.model.ToDoItem;
import com.androidtalk.todoapp.model.ToDoItems;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

public class ToDoSectionListFragment extends SherlockFragment {

	private ToDoMain toDoMain;
	private ToDoItems.ToDosGroup todoGroup;

	Map<String, View> childViews = new HashMap<String, View>();

	public static ToDoSectionListFragment getInstance(ToDoMain toDoMain, ToDoItems.ToDosGroup todos){
		ToDoSectionListFragment newToDoSectionListFragment = new ToDoSectionListFragment();
		newToDoSectionListFragment.todoGroup = todos;
		newToDoSectionListFragment.toDoMain = toDoMain;
		return newToDoSectionListFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View todoMainView = inflater.inflate(R.layout.todo_section_list_fragment, null, false);
		((TextView)todoMainView.findViewById(R.id.todo_section_list_fragment__title)).setText(todoGroup.name);
		for (ToDoItem item : todoGroup.items) {
			addItemToView(todoMainView, item.content, item.id);
		}
		return todoMainView;
	}

	@Subscribe public void onAddingItemEvent(ToDoItems.AddedItemEvent addedItemEvent){
		if (addedItemEvent.group.equals(todoGroup.name)){
			ToDoItem item = todoGroup.getItemById(addedItemEvent.itemId);
			addItemToView(getView(), item.content, item.id);
		}
	}

	@Subscribe public void onRemovingItemEvent(ToDoItems.RemovedItemEvent removedItemEvent){
		if (removedItemEvent.group.equals(todoGroup.name)){
			removeItemView(removedItemEvent.itemId);
		}
	}

	private void removeItemView(String id){
		((ViewManager)this.childViews.get(id)).removeView(this.childViews.get(id));
		getView().findViewById(R.id.todo_section_list_fragment__scrolling_content).invalidate();
		this.childViews.remove(id);
	}

	private void addItemToView(View view, String text, String id){
		View newItem = this.getLayoutInflater(null).inflate(R.layout.todo_section_item, null, false);
		((TextView)newItem.findViewById(R.id.todo_section_item__text)).setText(text);
		newItem.setTag(id);
		newItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ToDoSectionListFragment.this.toDoMain.editItem(ToDoSectionListFragment.this.todoGroup.name, (String) v.getTag());
			}
		});
		this.childViews.put(id, newItem);
		((LinearLayout)view.findViewById(R.id.todo_section_list_fragment__scrolling_content)).addView(newItem);
	}

	@Override
	public void onResume() {
		super.onResume();
		BusProvider.getInstance().register(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		BusProvider.getInstance().register(this);
	}

}
