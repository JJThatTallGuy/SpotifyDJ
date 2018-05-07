package edu.rosehulman.jonesjg1.spotifydj;

public class Song {

    private String mName;
    private String mUri;

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
}
