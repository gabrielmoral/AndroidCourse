package course.labs.fragmentslab;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements
		FriendsFragment.SelectionListener {

	private static final String TAG = "Lab-Fragments";

	private FriendsFragment mFriendsFragment;
	private FeedFragment mFeedFragment;

	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// If the layout is single-pane, create the FriendsFragment 
		// and add it to the Activity

		if (!isInTwoPaneMode()) {
			
			mFriendsFragment = new FriendsFragment();

			mFragmentManager = getFragmentManager();
			
			FragmentTransaction fragmentTrasaction  = mFragmentManager.beginTransaction();
			
			fragmentTrasaction.add(R.id.fragment_container, mFriendsFragment);
			
			fragmentTrasaction.commit();

		} else {

			// Otherwise, save a reference to the FeedFragment for later use

			mFeedFragment = (FeedFragment) getFragmentManager()
					.findFragmentById(R.id.feed_frag);
		}

	}

	// If there is no fragment_container ID, then the application is in
	// two-pane mode

	private boolean isInTwoPaneMode() {

		return findViewById(R.id.fragment_container) == null;
	
	}

	// Display selected Twitter feed

	public void onItemSelected(int position) {

		Log.i(TAG, "Entered onItemSelected(" + position + ")");

		// If there is no FeedFragment instance, then create one

		if (mFeedFragment == null)
			mFeedFragment = new FeedFragment();

		// If in single-pane mode, replace single visible Fragment

		if (!isInTwoPaneMode()) {

			mFragmentManager = getFragmentManager();
			
			FragmentTransaction fragmentTrasaction  = mFragmentManager.beginTransaction();
			
			fragmentTrasaction.replace(R.id.fragment_container, mFeedFragment);
			fragmentTrasaction.addToBackStack(null);
			fragmentTrasaction.setTransition(4);
			// execute transaction now
			fragmentTrasaction.commit();
			mFragmentManager.executePendingTransactions();

		}

		// Update Twitter feed display on FriendFragment
		mFeedFragment.updateFeedDisplay(position);

	}

}
