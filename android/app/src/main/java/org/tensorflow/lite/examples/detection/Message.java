package org.tensorflow.lite.examples.detection;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class Message implements IMessage {
    private String id;
    private String text;
    private Author author;
    private Date date;

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