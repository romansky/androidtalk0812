package com.androidtalk.demos;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;
import com.androidtalk.R;

import java.util.HashMap;
import java.util.Map;


public class NotificationDemo extends Activity {

	private Map<Integer, Integer> ongoingCounters = new HashMap<Integer, Integer>();
	private final String EXTRA_IS_ONGOING = "EXTRA_IS_ONGOING";
	private final String EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID";
	private final String EXTRA_BUNDLE = "EXTRA_BUNDLE";

	private final String[] lines = new String[] { "A person who never made a mistake", "never tried anything new", "Albert Einstein", "This  line  has  been  left  too  long  on  purpose " };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_demo);

		findViewById(R.id.notification_demo__button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				notificationManager.notify(
						1,
						getSimple(
								"Just in! Apple Sucks!",
								"News Piece",
								"A researcher has found a page on google that says so"
						).build());
			}
		});

		findViewById(R.id.notification_demo__inbox_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				notificationManager.notify(
						2,
						getInboxStyledNotification(
								2,
								"How dare you call fancy notification",
								"I'm fancy and you know it!",
								"you know you want to click me..",
								lines
						).build());
			}
		});

		findViewById(R.id.notification_demo__bigpicture_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				Bitmap bigPicture = BitmapFactory.decodeResource(getResources(), R.drawable.nyan_cat);
				notificationManager.notify(
						3,
						getBigPictureStyledNotification(
								3,
								"How dare you call fancy notification",
								"I'm fancy and you know it!",
								"you know you want to click me..",
								bigPicture
						).build()
				);
			}
		});

		findViewById(R.id.notification_demo__bigtext_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				notificationManager.notify(4, getBigTextStyledNotification(4, "How dare you call fancy notification", "I'm fancy and you know it!", "you know you want to click me..",lines).build());
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent.getBundleExtra(EXTRA_BUNDLE) != null && intent.getBundleExtra(EXTRA_BUNDLE).getBoolean(EXTRA_IS_ONGOING, false)){
			Bundle bundle = intent.getBundleExtra(EXTRA_BUNDLE);
			if (ongoingCounters.get(bundle.getInt(EXTRA_NOTIFICATION_ID)) == 1){
				((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(bundle.getInt(EXTRA_NOTIFICATION_ID));
				Toast.makeText(this, "Your time has come, " + intent.getAction() + " !!! prepare to die!", 2000).show();
			} else {
				Toast.makeText(this, "Notification which goes by the name " + intent.getAction() +
						" you have " + ongoingCounters.get(bundle.getInt(EXTRA_NOTIFICATION_ID)) + " lives left!", 500).show();
				ongoingCounters.put(bundle.getInt(EXTRA_NOTIFICATION_ID),
						ongoingCounters.get(bundle.getInt(EXTRA_NOTIFICATION_ID)) -1);
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				notificationManager.notify(bundle.getInt(EXTRA_NOTIFICATION_ID),getInboxStyledNotification(2,
						"How dare you call fancy notification",
						"I'm fancy and you know it!",
						"you know you want to click me..",
						lines).build());
			}
		} else {
			Toast.makeText(this, "Intent text: " + intent.getAction(), 500).show();
		}
	}

	private NotificationCompat.Builder getSimple(String ticker, String title, String content) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		NotificationCompat.InboxStyle styled = new NotificationCompat.InboxStyle(builder);
		// set the default notification options (Vibrate / Sound / Light / All)
		builder.setDefaults(Notification.DEFAULT_ALL);
		// set the notification to be automatically cancelled when pressed on
		builder.setAutoCancel(true); // still need to set content intent in-order for this to work..
		// define action when "deleting" (swiping / clearing) the notification
		Intent deleteIntent = new Intent(this, NotificationDemo.class);
		deleteIntent.setAction("Delete intent");
		deleteIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, deleteIntent, 0);
		builder.setDeleteIntent(pendingIntent);
		builder.setContentIntent(pendingIntent);
		// when to show the notification?
		builder.setWhen(System.currentTimeMillis());
		// ticker text that runs in the tray for few seconds
		builder.setTicker(ticker);
		// set the title
		builder.setContentTitle(title);
		// only appears when specifically collapsed / on devices which don't have notification extending
		builder.setContentText(content);
		styled.setSummaryText(content);
		// the drawable icon
		builder.setSmallIcon(android.R.drawable.btn_plus);
		// set priority (for API level 16 devices)
		builder.setPriority(Notification.PRIORITY_DEFAULT);
		// add action button to a notification
		builder.addAction(android.R.drawable.btn_star, "Like", pendingIntent);
		builder.addAction(android.R.drawable.btn_minus, "Dislike", pendingIntent);
		return builder;
	}

	private NotificationCompat.Builder getInboxStyledNotification(int notificationId, String ticker, String title, String content, String[] lines) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		NotificationCompat.InboxStyle styled = new NotificationCompat.InboxStyle(builder);

		builder.setDefaults(Notification.DEFAULT_ALL);
		builder.setSmallIcon(android.R.drawable.btn_plus);
		// only appears when collapsed / on devices which don't have notification extending
		builder.setTicker(ticker);
		builder.setContentTitle(title);
		builder.setContentText(content);

		// the "richer" experience
		styled.setBigContentTitle("Quote of the day");
		for (String line : lines) { styled.addLine(line); }

		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_compass));

		// set the max priority of this alert
		builder.setPriority(Notification.PRIORITY_MAX);

		styled.setSummaryText(content);
		//  makes it un-cancellable
		builder.setOngoing(true);
		Intent contentIntent = new Intent(this, NotificationDemo.class);
		Bundle bundle = new Bundle();
		bundle.putInt(EXTRA_NOTIFICATION_ID, notificationId);
		bundle.putBoolean(EXTRA_IS_ONGOING, true);
		contentIntent.putExtra(EXTRA_BUNDLE, bundle);
		contentIntent.setAction("Fancy Intent");
		contentIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		builder.setContentIntent(PendingIntent.getActivity(this, notificationId, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT));
		// set the number of clicks needed to release this notification
		if (!this.ongoingCounters.containsKey(notificationId) || this.ongoingCounters.get(notificationId) == 0 )
			this.ongoingCounters.put(notificationId, 4);
		// set a number that will appear in the bottom right corner
		builder.setNumber(this.ongoingCounters.get(notificationId));
		return builder;
	}


	private NotificationCompat.Builder getBigPictureStyledNotification(int notificationId, String ticker, String title, String content, Bitmap bigPicture) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setAutoCancel(true);
		NotificationCompat.BigPictureStyle styled = new NotificationCompat.BigPictureStyle(builder);
		builder.setDefaults(Notification.DEFAULT_ALL);
		builder.setSmallIcon(android.R.drawable.btn_plus);
		// only appears when collapsed / on devices which don't have notification extending
		builder.setTicker(ticker);
		builder.setContentTitle(title);
		builder.setContentText(content);
		// the "richer" experience
		styled.setBigContentTitle("@"+ title);
		styled.bigPicture(bigPicture);
		styled.setSummaryText(content);

		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_compass));
		//  makes it un-cancellable
		Intent contentIntent = new Intent(this, NotificationDemo.class);
		contentIntent.setAction("Fancy Intent");
		contentIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		builder.setContentIntent(PendingIntent.getActivity(this, notificationId, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT));
		// set the number of clicks needed to release this notification
		return builder;
	}

	private NotificationCompat.Builder getBigTextStyledNotification(int notificationId, String ticker, String title, String content, String[] lines) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		NotificationCompat.BigTextStyle styled = new NotificationCompat.BigTextStyle(builder);

		builder.setDefaults(Notification.DEFAULT_ALL);
		builder.setSmallIcon(android.R.drawable.btn_plus);
		// only appears when collapsed / on devices which don't have notification extending
		builder.setTicker(ticker);
		builder.setContentTitle(title);
		builder.setContentText(content);
		// the "richer" experience
		styled.setBigContentTitle("@"+ title);
		styled.bigText("Chickweed gourd quandong plantain spring onion bush tomato carrot komatsuna mustard. Quandong potato water spinach asparagus rutabaga peanut tigernut amaranth okra. Daikon komatsuna bunya nuts kohlrabi tatsoi mung bean salsify gourd eggplant beet greens silver beet garlic.");
		styled.setSummaryText(content);

		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_compass));
		//  makes it un-cancellable
		Intent contentIntent = new Intent(this, NotificationDemo.class);
		contentIntent.setAction("Fancy Intent");
		contentIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		builder.setContentIntent(PendingIntent.getActivity(this, notificationId, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT));
		// set the number of clicks needed to release this notification
		return builder;
	}

}
