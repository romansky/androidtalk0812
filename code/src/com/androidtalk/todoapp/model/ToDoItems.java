package com.androidtalk.todoapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import com.androidtalk.todoapp.BusProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class ToDoItems {

	public static enum GROUP_NAMES { LEFT, DONE }

	private final String SHARED_PREF_STORAGE_KEY = "todolistitems_storage_key";

	Map<String, ToDosGroup> todoItemsGrouped;
	private SharedPreferences sharedPreferences;

//	Event objects

	public static class AddedItemEvent{
		public String group;
		public String itemId;
		public AddedItemEvent(String group, String itemId){ this.group = group; this.itemId = itemId; }
	}

	public static class RemovedItemEvent {
		public String group;
		public String itemId;
		public RemovedItemEvent(String group, String itemId){ this.group = group; this.itemId = itemId; }
	}

	public static class UpdatedItemEvent {
		public String group;
		public String itemId;
		public UpdatedItemEvent(String group, String itemId){ this.group = group; this.itemId = itemId; }
	}

// Instantiation

	private static ToDoItems _instance;
	private ToDoItems(Context context){
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		this.readStateFromDisk();
	}

	public static ToDoItems getInstance(Context context){
		if (_instance == null){
			_instance = new ToDoItems(context);
		}
		return _instance;
	}

//	CRUD methods

	public ToDosGroup getTodosGroup(String group){
		return this.todoItemsGrouped.get(group);
	}

	public void updateTodoItem(String group, String itemId, String newContent){
		this.todoItemsGrouped.get(group).getItemById(itemId).content = newContent;
		this.saveState();
		BusProvider.getInstance().post(new UpdatedItemEvent(group, itemId));
	}

	public void addToDoItem(String group, String content){
		ToDoItem newItem = new ToDoItem();
		newItem.id = UUID.randomUUID().toString();
		newItem.content = content;
		this.todoItemsGrouped.get(group).items.add(newItem);
		this.saveState();
		BusProvider.getInstance().post(new AddedItemEvent(group, newItem.id));
	}

	public void deleteToDoItem(String group, String itemId){
		this.todoItemsGrouped.get(group).items.remove( this.todoItemsGrouped.get(group).getItemById(itemId) );
		this.saveState();
		BusProvider.getInstance().post(new RemovedItemEvent(group, itemId));
	}

	private void addTodosGroup(String groupName){
		ToDosGroup newGroup = new ToDosGroup();
		newGroup.name = groupName;
		newGroup.items = new ArrayList<ToDoItem>();
		this.todoItemsGrouped.put(groupName, newGroup);
		this.saveState();
	}

//	Helper grouping class

	public class ToDosGroup {
		public String name;
		public List<ToDoItem> items;

		public ToDoItem getItemById(String itemId){
			for (ToDoItem item : this.items) {
				if (item.id.equals(itemId)) return item;
			}
			return null;
		}
	}


//	Persistance helper methods
	private void readStateFromDisk() {
		if (this.sharedPreferences.getString(SHARED_PREF_STORAGE_KEY, "").equals("")){
			this.todoItemsGrouped = new HashMap<String, ToDosGroup>();
			this.addTodosGroup(GROUP_NAMES.LEFT.toString());
			this.addTodosGroup(GROUP_NAMES.DONE.toString());
		} else {
			Gson gson = new Gson();
			Type type = new TypeToken<Collection<ToDosGroup>>() {}.getType();
			List<ToDosGroup> listTodosGroup = gson.fromJson(this.sharedPreferences.getString(SHARED_PREF_STORAGE_KEY, ""),type);
			this.todoItemsGrouped = new HashMap<String, ToDosGroup>();
			for (ToDosGroup toDosGroup : listTodosGroup) {
				this.todoItemsGrouped.put(toDosGroup.name, toDosGroup);
			}
		}

	}

	private void saveState(){
		if (this.todoItemsGrouped.size() > 0){
			Gson gson = new Gson();
			List<ToDosGroup> listTodosGroup = new ArrayList<ToDosGroup>(this.todoItemsGrouped.values());
			this.sharedPreferences.edit().putString( SHARED_PREF_STORAGE_KEY, gson.toJson(listTodosGroup) ).commit();
		}
	}
}
