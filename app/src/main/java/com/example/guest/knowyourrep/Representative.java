package com.example.guest.knowyourrep;

import java.io.Serializable;

public class Representative implements Serializable {

    private String mFirstName;
    private String mLastName;
    private String mParty;

    public Representative(String firstName, String lastName, String party) {
        mFirstName = firstName;
        mLastName = lastName;
        mParty = party;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getParty() {
        return mParty;
    }

    public void setParty(String party) {
        mParty = party;
    }
}
