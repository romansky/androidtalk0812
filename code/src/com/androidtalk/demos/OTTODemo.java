package com.androidtalk.demos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.androidtalk.R;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class OTTODemo extends Activity {

	private Bus demoBusInstance = new Bus();

	private int counter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.otto_demo);

		findViewById(R.id.otto_demo__add_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OTTODemo.this.counter +=1;
				OTTODemo.this.demoBusInstance.post(new DemoEvent());
			}
		});
	}

//	register otto listeners in the class

	@Override
	protected void onResume() {
		super.onResume();
		demoBusInstance.register(this);
	}

//	when pausing un-register listeners

	@Override
	protected void onPause() {
		super.onPause();
		demoBusInstance.unregister(this);
	}

// All the otto specific code goes here

	@Subscribe public void onDemoUpdateEvent(DemoEvent demoEvent){
		((TextView)findViewById(R.id.otto_demo__counter)).setText(String.valueOf(this.counter));
	}

	@Subscribe
	public void onAlsoDemoUpdateEvent(DemoEvent demoEvent){
		((TextView)findViewById(R.id.otto_demo__last_event)).setText(String.valueOf(System.currentTimeMillis()));
	}

	public class DemoEvent { }


}
