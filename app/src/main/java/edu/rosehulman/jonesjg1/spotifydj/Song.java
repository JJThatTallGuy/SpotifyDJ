package edu.rosehulman.jonesjg1.spotifydj;

import java.util.Arrays;

public class Song {

    private String mName;
    private String mUri;
    private String key;
    private String mUserID;
    private String mArtist;

    public Song(String name, String uri, String userID, String artist) {
        mName = name;
        mUri = uri;
        mUserID = userID;
        mArtist = artist;
    }

    public Song() {
        //empty constructor
    }

    public String getmArtist() {
        return mArtist;
    }

    public void setmArtist(String mArtist) {
        this.mArtist = mArtist;
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

    public String getmUserID() {
        return mUserID;
    }

    public void setmUserID(String mUserID) {
        this.mUserID = mUserID;
    }
}
