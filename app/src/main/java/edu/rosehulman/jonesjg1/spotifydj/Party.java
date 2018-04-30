package edu.rosehulman.jonesjg1.spotifydj;

public class Party {

    private String mName;
    private String mPass;
    private boolean mIsPasswordProtected;
    private int mMembers;
    // private ArrayList of users

    public Party() {
        // empty constructor
    }

    public Party(String name, String pass) {
        mName = name;
        mPass = pass;
        if (mPass.equals("")) {
            mIsPasswordProtected = false;
        } else {
            mIsPasswordProtected = true;
        }
        mMembers = 1;
    }

    public void addMember() {
        mMembers++;
        // will add user in arraylist as well
    }

    public int getmMembers() {
        return mMembers;
    }

    public void setmMembers(int mMembers) {
        this.mMembers = mMembers;
    }

    public boolean ismIsPasswordProtected() {
        return mIsPasswordProtected;
    }

    public void setmIsPasswordProtected(boolean mIsPasswordProtected) {
        this.mIsPasswordProtected = mIsPasswordProtected;
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
