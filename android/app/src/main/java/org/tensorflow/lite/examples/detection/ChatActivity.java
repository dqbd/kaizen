package org.tensorflow.lite.examples.detection;

import android.animation.Animator;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Map;


public class ChatActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MessagesAdapter.OptionsChangeListener, MessagesAdapter.ConfettiListener {

    LottieAnimationView confetti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        confetti = findViewById(R.id.confetti);

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
        MessagesAdapter.INSTANCE.setOptionsChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MessagesAdapter.INSTANCE.setOptionsChangeListener(this);
        MessagesAdapter.INSTANCE.setConfettiListener(this);
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

    @Override
    public void changed(Map<String, String> options) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                LinearLayout btns = findViewById(R.id.btns);
                btns.removeAllViews();

                int count = 0;
                for (Map.Entry<String, String> e: options.entrySet()) {
                    Button btn = new Button(btns.getContext());
                    btn.setText(e.getValue());
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MessagesAdapter.INSTANCE.addUserQuickMessage(e.getKey());
                        }
                    });
                    btns.addView(btn);
                    count++;
                }
                btns.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
                findViewById(R.id.input).setVisibility(count > 0 ? View.INVISIBLE : View.VISIBLE);
            }
        });
    }

    @Override
    public void showConfetti() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                confetti.playAnimation();
                confetti.setVisibility(View.VISIBLE);
            }
        });
    }
}
