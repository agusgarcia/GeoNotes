package com.agusgarcia.geonotes.Notes;

import android.location.Location;

import com.orm.SugarRecord;

import java.util.Date;

public class Note extends SugarRecord {

    protected String mTitle;
    protected String mDescription;
    protected Date mDate;
    protected Location mLocation;

    public Note() {
    }

    public Note(String title, String description, Date date, Location location) {
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

    public Date getDate() { return mDate; }

    public void setDate(Date date) { this.mDate = date; }

    public Location getLocation() { return mLocation; }

    public void setLocation(Location location) { this.mLocation = location; }

    @Override
    public String toString() {
        return "Note " + "getId()" + " with title: " + mTitle;
    }
}
