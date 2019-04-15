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
import java.util.logging.Logger;
import java.util.logging.SocketHandler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MessagesAdapter extends MessagesListAdapter<Message> {


    private final Map<String, Bitmap> images = new HashMap<>();

    private Author me = new Author("me", "Me");
    private Author server = new Author("kaizen", "Kaizen");


    private OkHttpClient client = new OkHttpClient();

    private WebSocketListener websock = new WebSocketListener() {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

            webSocket.send("test");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
        }
    };

    private Handler handler = new Handler();

    Thread thread;

    public static MessagesAdapter INSTANCE = new MessagesAdapter("me", new ImageLoader() {



        @Override
        public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
            Bitmap bitmap = INSTANCE.images.get(url);
            imageView.setImageBitmap(bitmap);
        }
    });
    private WebSocket ws;

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

    public void addServerImage(Bitmap bitmap) {
        ensureStarted();
        String key = String.valueOf(System.currentTimeMillis());
        Message msg = new Message(key, "",  me);
        msg.setImageUrl(key, bitmap);
        Bitmap bm = Bitmap.createBitmap(bitmap);
        images.put(key, bm);
        this.addToStart(msg, true);
        sendToServer(msg);
    }


    private void sendToServer(Message msg) {
        try {

            JSONObject req = new JSONObject();
            if (msg.getImage() != null) {

                req.put("type", "image");
                req.put("data", "nothing yet");
            } else {

                req.put("type", "message");
                req.put("text", msg.getText());
            }
            req.put("user", msg.getUser().getId());

            Log.i("WS", "sending " + req.toString());

            ws.send(req.toString());
            ws.close(1000, "Goodbye !");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Log.i("WS", "On open");
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {

            Log.i("WS", "On Message: " + text.substring(0, 30));

            try {
                JSONObject res = new JSONObject(text);

                JSONArray array = res.getJSONArray("generic");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = (JSONObject) array.get(i);
                    String t = obj.getString("text");
                    addToStart(new Message(t, t, server), true);
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
            Log.i("WS", "On failure");
            t.printStackTrace();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    start();
//                }
//            }).start();
//            start();
        }
    }

    private void start() {
        Request request = new Request.Builder().url("wss://unit2019.herokuapp.com").build();;
        ws = client.newWebSocket(request, websock);
       ws.send("test2");
        // client.dispatcher().executorService().shutdown();

    }

}
