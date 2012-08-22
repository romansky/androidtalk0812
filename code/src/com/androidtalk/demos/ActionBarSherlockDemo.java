package com.androidtalk.demos;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidtalk.R;

public class ActionBarSherlockDemo extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.abs_demo);
		getSupportActionBar().setTitle("ActionBarSherlock Demo");


		if (savedInstanceState == null){
			ABSFragmentBigText absFragmentBigText = ABSFragmentBigText.getInstance("Vegy Ipsum",ipsum1);
			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.abs_demo__scrolling_content, absFragmentBigText, "big text fragment");
			fragmentTransaction.commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(R.string.abs_demo__menu_nada)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return super.onCreateOptionsMenu(menu);
	}

	private final String ipsum1 = "Broccoli rabe cucumber komatsuna spring onion watercress tomato swiss chard sierra leone bologi. Nori potato water spinach chickweed green bean avocado earthnut pea eggplant garlic burdock. Yarrow spring onion sweet pepper wattle seed quandong gumbo parsnip tatsoi courgette horseradish yarrow. Tigernut garlic cucumber collard greens courgette quandong soko maize wattle seed scallion broccoli rabe sea lettuce chickpea welsh onion taro tomato. Chickweed yarrow spinach celtuce bitterleaf scallion courgette swiss chard pea welsh onion.\n" +
			"\n" +
			"Seakale fava bean burdock dandelion broccoli rutabaga. Napa cabbage cabbage courgette grape celtuce wattle seed komatsuna garlic bitterleaf yarrow courgette lettuce zucchini water chestnut napa cabbage plantain sea lettuce tatsoi. Sorrel celtuce bok choy parsley komatsuna leek sweet pepper cauliflower grape onion pumpkin amaranth gumbo bell pepper tatsoi silver beet brussels sprout.\n" +
			"\n" +
			"Groundnut garlic horseradish gourd courgette water chestnut swiss chard bunya nuts fennel beetroot welsh onion coriander courgette. Groundnut gumbo quandong cabbage welsh onion broccoli rabe dandelion garlic bunya nuts sea lettuce turnip horseradish burdock courgette watercress azuki bean. Courgette aubergine okra bamboo shoot chickpea beet greens groundnut tomatillo eggplant.\n" +
			"\n" +
			"Lettuce pea sprouts cauliflower okra napa cabbage parsley catsear onion water chestnut cucumber leek black-eyed pea pumpkin sea lettuce turnip kakadu plum swiss chard azuki bean. Silver beet spinach aubergine celery asparagus celery spring onion desert raisin wakame fennel napa cabbage squash. Silver beet kakadu plum scallion napa cabbage shallot earthnut pea cucumber welsh onion celtuce azuki bean chickpea coriander. Pea sprouts water spinach mung bean onion horseradish epazote napa cabbage asparagus pea sprouts winter purslane catsear earthnut pea. Squash bunya nuts celery bok choy azuki bean broccoli sea lettuce aubergine courgette green bean burdock avocado sorrel. ";
}
