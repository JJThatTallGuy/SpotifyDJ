package edu.rosehulman.jonesjg1.spotifydj;

public class Party {

    private String mName;
    private String mPass;

    public Party() {
        // empty constructor
    }

    public Party(String name, String pass) {
        mName = name;
        mPass = pass;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPass() {
        return mPass;
    }

    public void setmPass(String mPass) {
        this.mPass = mPass;
    }
}
