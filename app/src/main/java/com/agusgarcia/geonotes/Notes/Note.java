package com.agusgarcia.geonotes.Notes;

import android.location.Location;

import com.orm.SugarRecord;

import java.util.Date;

public class Note extends SugarRecord {

    protected String mTitle;
    protected String mDescription;
    protected String mDate;
    protected String mLocation;

    public Note() {
    }

    public Note(String title, String description, String date, String location) {
        mTitle = title;
        mDescription = description;
        mDate = date;
        mLocation = location;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getDate() { return mDate; }

    public void setDate(String date) { this.mDate = date; }

    public String getLocation() { return mLocation; }

    public void setLocation(String location) { this.mLocation = location; }

    @Override
    public String toString() {
        return "Note " + getId() + " with title: " + mTitle;
    }
}
