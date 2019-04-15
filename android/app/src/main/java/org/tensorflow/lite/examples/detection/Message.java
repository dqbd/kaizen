package org.tensorflow.lite.examples.detection;

import android.support.annotation.Nullable;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;

public class Message implements IMessage, MessageContentType.Image {
    private String id;
    private String text;
    private Author author;
    private Date date;
    private String imageUrl;

    public Message(String id, String text, Author author) {
        this.id = id;
        this.text = text;
        this.author = author;
        date = new Date();
    }

    /*...*/

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public IUser getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return date;
    }

    @Nullable
    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

class Author implements IUser
{


    private String id;
    private String name;

    public Author(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return "?";
    }
}