package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;


public class ChatActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        MessagesListAdapter<Message> adapter = MessagesAdapter.INSTANCE;
        MessagesList messagesList = findViewById(R.id.messagesList);
        messagesList.setAdapter(adapter);

        Author me = new Author("me", "Me");
        Author server = new Author("server", "Server");

        MessageInput input = findViewById(R.id.input);
        input.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                MessagesAdapter.INSTANCE.addUserMessage(input.toString());
                return true;
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        // Ensure correct menu item is selected (where the magic happens)
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
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
