package org.tensorflow.lite.examples.detection;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MessagesAdapter extends MessagesListAdapter<Message> {


    private final Map<String, Bitmap> images = new HashMap<>();

    private static Author me = new Author(UUID.randomUUID().toString(), "Me");
    private static Author server = new Author(UUID.randomUUID().toString(), "Kaizen");


    private OkHttpClient client = new OkHttpClient();

    private WebSocketListener websock = new MyWebSocketListener();

    // label -> value
    private Map<String, String> lastOptions = new HashMap<>();

    public static MessagesAdapter INSTANCE = new MessagesAdapter(me.getId(), new ImageLoader() {

        @Override
        public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
            Bitmap bitmap = INSTANCE.images.get(url);
            imageView.setImageBitmap(bitmap);
        }
    });
    private WebSocket ws;
    private OptionsChangeListener optionsChangeListener;

    public MessagesAdapter(String senderId, ImageLoader imageLoader) {
        super(senderId, imageLoader);
    }

    public MessagesAdapter(String senderId, MessageHolders holders, ImageLoader imageLoader) {
        super(senderId, holders, imageLoader);
    }


    private synchronized void ensureStarted() {
        if (ws == null) {
            start();
        }
    }

    public void addServerImage(Bitmap bitmap, String label) {
        ensureStarted();
        String key = String.valueOf(System.currentTimeMillis());
        Message msg = new Message(key, "",  me);
        msg.setImageUrl(key, bitmap);
        Bitmap bm = Bitmap.createBitmap(bitmap);
        images.put(key, bm);
        this.addToStart(msg, true);
        sendToServer(msg, label);
    }

    private void sendToServer(Message msg, String label) {

        try {

            JSONObject req = new JSONObject();
            req.put("type", "message");
            req.put("text", label != null ? label : msg.getText());
            req.put("user", msg.getUser().getId());

            Log.i("WS", "sending " + req.toString());

            ws.send(req.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendToServerQuick(String quickMsg) {

        try {

            JSONObject req = new JSONObject();
            req.put("type", "quickresponse");
            req.put("value", quickMsg);
            req.put("user", me.getId());
            req.put("channel","socket");

            Log.i("WS", "sending " + req.toString());

            ws.send(req.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void sendToServer(Message msg) {
        sendToServer(msg, null);
    }

    public void addUserMessage(String text) {
        ensureStarted();
        String key = String.valueOf(System.currentTimeMillis());
        Message msg = new Message(key, text,  me);
        this.addToStart(msg, true);
        sendToServer(msg);
    }



    private void addText(Author author, String text)
    {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                addToStart(new Message(text, text, author), true);
            }
        });
    }

    public void setOptionsChangeListener(OptionsChangeListener optionsChangeListener) {
        this.optionsChangeListener = optionsChangeListener;
    }

    public void addUserQuickMessage(String value) {
        ensureStarted();
        String key = String.valueOf(System.currentTimeMillis());
        Message msg = new Message(key, lastOptions.get(value),  me);
        addToStart(msg, true);
        sendToServerQuick(value);
        lastOptions.clear();
        if (optionsChangeListener != null) {
            optionsChangeListener.changed(lastOptions);
        }
    }


    private final class MyWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Log.i("WS", "On open");
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {

            Log.i("WS", "On Message: " + text);

            try {
                JSONObject res = new JSONObject(text);

                if ("typing".equals(res.optString("type"))) {
                    return;
                }


                JSONArray array = res.optJSONArray("generic");

                lastOptions.clear();
                if (array != null) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = (JSONObject) array.get(i);
                        if (obj.has("title")) {
                            String t = obj.getString("title");
//                            addText(server, t);
                            JSONArray options = obj.getJSONArray("options");
                            for (int j = 0; j < options.length(); j ++) {
                                JSONObject option = (JSONObject) options.get(j);
                                String value = option.getJSONObject("value").getJSONObject("input").getString("text");
                                lastOptions.put(value, option.getString("label"));
                            }
                        } else {
                            String t = obj.getString("text");
                            addText(server, t);
                        }
                    }
                } else {
                    String r = res.optString("text");
                    if (r != null) {
                        addText(server, r);
                    }
                }
                if (optionsChangeListener != null) {
                    optionsChangeListener.changed(lastOptions);
                }


            } catch (JSONException|ClassCastException e) {

                e.printStackTrace();
            }

        }
        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {

            Log.i("WS", "On Message bytestring");
            this.onMessage(webSocket, bytes.toString());
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {

            Log.i("WS", "On closing, code = " + code + " reason = " + reason);
        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Log.i("WS", "On failure ");
            t.printStackTrace();
        }
    }

    private void start() {
        Request request = new Request.Builder().url("wss://unit2019.herokuapp.com").build();;
        ws = client.newWebSocket(request, websock);
        ws.send("test2");
        // client.dispatcher().executorService().shutdown();

    }

    public interface OptionsChangeListener {

        void changed(Map<String, String> options);

    }

}
