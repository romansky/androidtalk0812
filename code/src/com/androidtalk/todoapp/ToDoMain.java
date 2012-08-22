package com.androidtalk.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidtalk.R;
import com.androidtalk.todoapp.model.ToDoItems;
import com.squareup.otto.Subscribe;

public class ToDoMain extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_app);

		if (savedInstanceState == null){
			ToDoItems toDoItems = ToDoItems.getInstance(this);
			ToDoSectionListFragment leftToDoSectionListFragment = ToDoSectionListFragment.getInstance(this, toDoItems.getTodosGroup(ToDoItems.GROUP_NAMES.LEFT.toString()));
			ToDoSectionListFragment doneTodoSectionListFragment = ToDoSectionListFragment.getInstance(this, toDoItems.getTodosGroup(ToDoItems.GROUP_NAMES.DONE.toString()));

			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.todo_app__things_left_list, leftToDoSectionListFragment, "things left");
			fragmentTransaction.add(R.id.todo_app__things_done_list, doneTodoSectionListFragment, "things done");
			fragmentTransaction.commit();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(R.string.todo_app__add_todo_button)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals(getResources().getString(R.string.todo_app__add_todo_button))){
			Intent openEditor = new Intent(this, ToDoItemEditor.class);
//			openEditor.putExtra(ToDoItemEditor.INTENT_PARAMS.GROUP.toString(), )
			startActivity(openEditor);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void editItem(String group, String id){
		Intent openEditor = new Intent(this, ToDoItemEditor.class);
		openEditor.putExtra(ToDoItemEditor.INTENT_PARAMS.GROUP.toString(), group);
		openEditor.putExtra(ToDoItemEditor.INTENT_PARAMS.ITEM_ID.toString(), id);
		startActivity(openEditor);
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