package com.androidtalk.demos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.androidtalk.R;
import com.google.gson.Gson;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class GoogleGsonDemo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_gson_demo);


		List<String> printList = new ArrayList<String>();
		String initialJson = "{ \"name\":\"Inigo Montoya\", \"job\":\"FANCER\", \"playedIn\": [ { \"movieName\":\"The Princess Bride\" } ] }";
		printList.add("original JSON: " + initialJson);
		Gson gson = new Gson();
		FictionalCharacter fictionalCharacter = gson.fromJson(initialJson, FictionalCharacter.class);
		printList.add("name: " + fictionalCharacter.name);
		printList.add("job: " + fictionalCharacter.job);
		printList.add("name of movie played: " + fictionalCharacter.playedIn.get(0).movieName);
		printList.add("back to JSON: " + gson.toJson(fictionalCharacter) );
		String printMe = "";
		for (String s : printList) { printMe += s + "\n"; }
		((TextView)findViewById(R.id.google_gson_demo__json_value)).setText(printMe);

	}

	class FictionalCharacter {
		String name;
		Jobs job;
		List<Movie> playedIn;
	}
	enum Jobs { FANCER, DANCER, JAVA_HAXOR }
	class Movie {
		String movieName;
	}
}
