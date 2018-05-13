package edu.rosehulman.jonesjg1.spotifydj;

public class Party {

    private String mName;
    private String mPass;
    private boolean mIsPasswordProtected;
    private int mMembers;
    private String mOwner;

    public QueueAdapter getAdapter() {
        return adapter;
    }

    private QueueAdapter adapter;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    // private ArrayList of users

    public Party() {
        // empty constructor
    }

    public Party(String name, String pass, String owner) {
        mName = name;
        mPass = pass;
        if (mPass.equals("")) {
            mIsPasswordProtected = false;
        } else {
            mIsPasswordProtected = true;
        }
        mOwner = owner;
        mMembers = 1;
    }

    public void addMember() {
        mMembers++;
        // will add user in arraylist as well
    }

    public String getmOwner() {
        return mOwner;
    }

    public void setmOwner(String mOwner) {
        this.mOwner = mOwner;
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

    public void setValues(Party updatedParty) {
        setmMembers(updatedParty.getmMembers());
        setmPass(updatedParty.getmPass());
        setmIsPasswordProtected(updatedParty.ismIsPasswordProtected());
        setmName(updatedParty.getmName());
    }

    public void setAdapter(QueueAdapter adapter) {
        this.adapter = adapter;
    }
}
