package com.agusgarcia.geonotes.Notes;

import java.util.Date;

public class Note {

    protected String mTitle;
    protected String mDescription;
    protected Date mDate;

    public Note() {
    }

    public Note(String title, String description, Date date) {
        mTitle = title;
        mDescription = description;
        mDate = date;
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

    @Override
    public String toString() {
        return "Note " + "getId()" + " with title: " + mTitle;
    }
}
