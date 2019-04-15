package org.tensorflow.lite.examples.detection;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.tensorflow.lite.examples.detection.dummy.DummyContent;

public class NewsFeed extends AppCompatActivity implements NewsFeedItemFragment.OnListFragmentInteractionListener {

    private TextView mTextMessage;
private NewsFeedItemFragment mFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {



            return false;
        }
    };

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);




        mFragment = new NewsFeedItemFragment();





        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }

}
