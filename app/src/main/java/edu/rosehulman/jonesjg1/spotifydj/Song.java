package edu.rosehulman.jonesjg1.spotifydj;

import java.util.Arrays;

public class Song {

    private String mName;
    private String mUri;
    private String key;

    public Song(String name, String uri) {
        mName = name;
        mUri = uri;

    }

    public Song() {
        //empty constructor
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmUri() {
        return mUri;
    }

    public void setmUri(String mUri) {
        this.mUri = mUri;
    }

    public void setValues(Song values) {
        this.mName = values.getmName();
        this.mUri = values.getmUri();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
