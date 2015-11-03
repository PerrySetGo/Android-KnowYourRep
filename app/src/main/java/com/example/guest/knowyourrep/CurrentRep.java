package com.example.guest.knowyourrep;

import java.io.Serializable;

public class CurrentRep implements Serializable {

    private String mFirstName;
    private String mLastName;
    private String mParty;

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
