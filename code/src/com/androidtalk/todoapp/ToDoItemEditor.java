package com.androidtalk.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidtalk.R;
import com.androidtalk.todoapp.model.ToDoItems;

public class ToDoItemEditor extends SherlockActivity {

	public static enum INTENT_PARAMS { GROUP, ITEM_ID, CONTENT }

	private String group;
	private String itemId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_item_editor);

		Intent recievedIntent = getIntent();
		this.group = recievedIntent.getStringExtra(INTENT_PARAMS.GROUP.toString());
		this.itemId = recievedIntent.getStringExtra(INTENT_PARAMS.ITEM_ID.toString());
		if (this.group != null && this.itemId != null){
			String content = ToDoItems.getInstance(this).getTodosGroup(this.group).getItemById(this.itemId).content;
			((EditText)findViewById(R.id.todo_item_editor__editor)).setText(content);
		}
		findViewById(R.id.todo_item_editor__editor).setFocusable(true);
		findViewById(R.id.todo_item_editor__editor).requestFocus();

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (this.group != null && this.itemId != null){
			menu.add(R.string.todo_item_editor__done_task_button)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}
		menu.add(R.string.todo_item_editor__done_editing_button)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		menu.add(R.string.todo_item_editor__cancel_button)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getTitle().equals(getResources().getString(R.string.todo_item_editor__done_editing_button))){
			String content = ((EditText)findViewById(R.id.todo_item_editor__editor)).getText().toString();
			if (group == null){
				ToDoItems.getInstance(this).addToDoItem(ToDoItems.GROUP_NAMES.LEFT.toString(), content);
			} else {
				ToDoItems.getInstance(this).updateTodoItem(group, itemId, content);
			}
			this.finish();
			return true;
		} else if (item.getTitle().equals(getResources().getString(R.string.todo_item_editor__done_task_button))){
			String content = ((EditText)findViewById(R.id.todo_item_editor__editor)).getText().toString();
			ToDoItems.getInstance(this).deleteToDoItem(group, itemId);
			ToDoItems.getInstance(this).addToDoItem(ToDoItems.GROUP_NAMES.DONE.toString(), content);
			this.finish();
			return true;
		} else if (item.getTitle().equals(getResources().getString(R.string.todo_item_editor__cancel_button))){
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
