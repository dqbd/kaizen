package org.tensorflow.lite.examples.detection;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.tensorflow.lite.examples.detection.dummy.DummyContent;

public class NewsFeed extends AppCompatActivity implements NewsFeedItemFragment.OnListFragmentInteractionListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
private NewsFeedItemFragment mFragment;
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        mFragment = new NewsFeedItemFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        // Ensure correct menu item is selected (where the magic happens)
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_record: {
                startActivity(new Intent(this, DetectorActivity.class));
                break;
            }
            case R.id.action_stats: {
                startActivity(new Intent(this, MapActivity.class));
                break;
            }
            case R.id.newsfeed: {
                startActivity(new Intent(this, NewsFeed.class));
                break;
            }
        }
        overridePendingTransition(0, 0);
        return true;
    }
}
