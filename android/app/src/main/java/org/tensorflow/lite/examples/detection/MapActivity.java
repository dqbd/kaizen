package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener {
    RecyclerView wrapList;

    UserAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        wrapList = findViewById(R.id.map_list);



        adapter = new UserAdapter(((MainApplication) getApplication()).USER_MAP_DATASET);

        wrapList.setLayoutManager(new LinearLayoutManager(this));
        wrapList.setAdapter(adapter);
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



    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        ArrayList<User> users;

        public UserAdapter(ArrayList<User> users) {
            this.users = users;
        }

        class MapViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout map;
            Context context;
            public MapViewHolder(@NonNull View itemView) {
                super(itemView);

                context = itemView.getContext();
                map = itemView.findViewById(R.id.relative_map);
            }

            void addUserToMap(User user) {
                if (user.score <= MainApplication.MAX_SECTION) {
                    addImage(user.avatar, 50, 140);
                } else if (user.score > MainApplication.MAX_SECTION && user.score <= MainApplication.MAX_SECTION * 2) {
                    if (user.score <= MainApplication.MAX_SECTION * 1.5) {
                        addImage(user.avatar, 98, 230);
                    } else {
                        addImage(user.avatar, 130, 293);
                    }
                } else if (user.score > MainApplication.MAX_SECTION * 2 && user.score <= MainApplication.MAX_SECTION * 3) {
                    addImage(user.avatar, 220, 165);
                } else if (user.score > MainApplication.MAX_SECTION * 3) {
                    addImage(user.avatar, 255, 220);
                }
            }

            void addImage(Drawable drawable, int top, int left) {
                CircleImageView testImage = new CircleImageView(context);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Math.round(convertDpToPixel(30, context)), Math.round(convertDpToPixel(30, context)));
                params.leftMargin = (int) convertDpToPixel(left, context);
                params.topMargin = (int) convertDpToPixel(top, context);

                testImage.setImageDrawable(drawable);
                map.addView(testImage, params);
            }
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView text;
            ImageView image;
            TextView score;
            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.user_name);
                image = itemView.findViewById(R.id.user_image);
                score = itemView.findViewById(R.id.user_score);
            }
        }

        @Override
        public int getItemViewType(int position) {

            if (position == 0) return 0;
            return 1;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
            switch (type) {
                case 0: return new MapViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_map, viewGroup, false));
                default:
                    return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_user, viewGroup, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

            if (getItemViewType(position) == 0) {
                MapViewHolder holder = (MapViewHolder) viewHolder;

                for (User user : users) {
                    holder.addUserToMap(user);
                }
            } else {
                ItemViewHolder holder = (ItemViewHolder) viewHolder;
                User user = users.get(position - 1);
                if (user != null) {
                    holder.text.setText(user.username);
                    holder.image.setImageDrawable(user.avatar);
                    holder.score.setText(user.score.toString());
                }
            }
        }

        @Override
        public int getItemCount() {
            return users.size() + 1;
        }
    }
}
