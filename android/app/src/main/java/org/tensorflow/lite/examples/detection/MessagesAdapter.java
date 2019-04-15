package org.tensorflow.lite.examples.detection;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.HashMap;
import java.util.Map;

public class MessagesAdapter extends MessagesListAdapter<Message> {


    private final Map<String, Bitmap> images = new HashMap<>();

    private Author me = new Author("me", "Me");
    private Author server = new Author("kaizen", "Kaizen");

    public static MessagesAdapter INSTANCE = new MessagesAdapter("me", new ImageLoader() {
        @Override
        public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
            Bitmap bitmap = INSTANCE.images.get(url);
            imageView.setImageBitmap(bitmap);
        }
    });

    public MessagesAdapter(String senderId, ImageLoader imageLoader) {
        super(senderId, imageLoader);
    }

    public MessagesAdapter(String senderId, MessageHolders holders, ImageLoader imageLoader) {
        super(senderId, holders, imageLoader);
    }


    public void addServerImage(Bitmap bitmap) {
        String key = String.valueOf(bitmap.hashCode());
        Message msg = new Message(key, "",  server);
        msg.setImageUrl(key);
        images.put(key, bitmap);
        this.addToStart(msg, true);
    }
}
