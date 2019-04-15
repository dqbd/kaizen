package org.tensorflow.lite.examples.detection;

import android.app.Application;

import java.util.ArrayList;

public class MainApplication extends Application {

    public ArrayList<User> USER_MAP_DATASET = new ArrayList<>();
    public static final int MAX_SCORE = 200;
    public static final int MAX_SECTION = MAX_SCORE / 4;

    @Override
    public void onCreate() {
        super.onCreate();

        USER_MAP_DATASET.add(new User("Ladislav Vagner", 0, getDrawable(R.drawable.avatar)));
        USER_MAP_DATASET.add(new User("Jana Šetrná", MAX_SECTION + 2, getDrawable(R.drawable.avatar2)));
        USER_MAP_DATASET.add(new User("Petr Přírodní", MAX_SECTION * 2 + 2, getDrawable(R.drawable.avatar3)));
        USER_MAP_DATASET.add(new User("Petr Přírodní", MAX_SECTION * 3 + 2, getDrawable(R.drawable.avatar3)));

    }
}
