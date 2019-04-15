package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener {

    RelativeLayout map;
    ListView list;

    ArrayList<User> users = new ArrayList<>();

    UserAdapter adapter;

    static final int MAX_SCORE = 16000;
    static final int MAX_SECTION = MAX_SCORE / 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = findViewById(R.id.map);
        list = findViewById(R.id.user_list);


        adapter = new UserAdapter(this, R.layout.list_item_user);

        users.add(new User("Ladislav Vagner", 0, getDrawable(R.drawable.avatar)));
        users.add(new User("Jana Šetrná", MAX_SECTION + 2, getDrawable(R.drawable.avatar2)));
        users.add(new User("Petr Přírodní", MAX_SECTION * 2 + 2, getDrawable(R.drawable.avatar3)));
        users.add(new User("Petr Přírodní", MAX_SECTION * 3 + 2, getDrawable(R.drawable.avatar3)));

        adapter.addAll(users);
        list.setAdapter(adapter);


        for (User user : users) {
            addUserToMap(user);
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        // Ensure correct menu item is selected (where the magic happens)
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
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
                break;
            }
        }
        overridePendingTransition(0, 0);
        return true;
    }

    void addUserToMap(User user) {
        if (user.score <= MAX_SECTION) {
            addImage(user.avatar, 50, 140);
        } else if (user.score > MAX_SECTION && user.score <= MAX_SECTION * 2) {
            if (user.score <= MAX_SECTION * 1.5) {
                addImage(user.avatar, 98, 230);
            } else {
                addImage(user.avatar, 130, 293);
            }
        } else if (user.score > MAX_SECTION * 2 && user.score <= MAX_SECTION * 3) {
            addImage(user.avatar, 220, 165);
        } else if (user.score > MAX_SECTION * 3) {
            addImage(user.avatar, 255, 220);
        }
    }

    void addImage(Drawable drawable, int top, int left) {
        CircleImageView testImage = new CircleImageView(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Math.round(convertDpToPixel(30, this)), Math.round(convertDpToPixel(30, this)));
        params.leftMargin = (int) convertDpToPixel(left, this);
        params.topMargin = (int) convertDpToPixel(top, this);

        testImage.setImageDrawable(drawable);
        map.addView(testImage, params);
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    class User {
        String username;
        Integer score;
        Drawable avatar;

        public User(String username, Integer score, Drawable avatar) {
            this.username = username;
            this.score = score;
            this.avatar = avatar;
        }
    }

    class UserAdapter extends ArrayAdapter<User> {

        UserAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.list_item_user, parent, false);
            }

            User user = getItem(position);

            if (user != null) {
                TextView text = convertView.findViewById(R.id.user_name);
                ImageView image = convertView.findViewById(R.id.user_image);
                TextView score = convertView.findViewById(R.id.user_score);

                text.setText(user.username);
                image.setImageDrawable(user.avatar);
                score.setText(user.score.toString());
            }

            return convertView;
        }
    }
}
