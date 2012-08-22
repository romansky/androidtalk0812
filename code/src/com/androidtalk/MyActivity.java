package com.androidtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.androidtalk.demos.ActionBarSherlockDemo;
import com.androidtalk.demos.GoogleGsonDemo;
import com.androidtalk.demos.NotificationDemo;
import com.androidtalk.demos.OTTODemo;
import com.androidtalk.todoapp.ToDoMain;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {

    private enum Demos {
		NOTIFICATION("Notification Demo"),
		ABS("ActionBarSherlock+Fragments Demo"),
		OTTO("OTTO Demo"),
		GoogleGSON("Google GSON Demo"),
		TODOS("ToDo's App");

		private String description;
		public String toString(){ return this.description; }

		Demos(String demoString){ this.description = demoString; }
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        List<String> demosList = new ArrayList<String>();
        for (Demos demoName : Demos.values()) {
            demosList.add(demoName.toString());
        }
        ArrayAdapter<String> demosAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, demosList);

        ListView examplesList = (ListView) findViewById(R.id.examples_list);
        examplesList.setAdapter(demosAdapter);
        examplesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (Demos.values()[i]) {
                    case NOTIFICATION:
						Intent notificationDemoIntent = new Intent(MyActivity.this, NotificationDemo.class);
						startActivity(notificationDemoIntent);
                        break;
					case ABS:
						Intent ABSdemoIntent = new Intent(MyActivity.this, ActionBarSherlockDemo.class);
						startActivity(ABSdemoIntent);
						break;
					case OTTO:
						Intent OTTODemoIntent = new Intent(MyActivity.this, OTTODemo.class);
						startActivity(OTTODemoIntent);
						break;
					case GoogleGSON:
						Intent googleGsondemoIntent = new Intent(MyActivity.this, GoogleGsonDemo.class);
						startActivity(googleGsondemoIntent);
						break;
					case TODOS:
						Intent todosAppIntent = new Intent(MyActivity.this, ToDoMain.class);
						startActivity(todosAppIntent);
						break;
                }

            }
        });


    }




}
