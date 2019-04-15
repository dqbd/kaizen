package org.tensorflow.lite.examples.detection;

import android.graphics.drawable.Drawable;

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
