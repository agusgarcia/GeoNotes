package com.agusgarcia.geonotes.Notes;

import android.location.Address;
import android.location.Geocoder;

import com.agusgarcia.geonotes.MainActivity;
import com.orm.SugarRecord;

import java.util.List;
import java.util.Locale;

public class Note extends SugarRecord {

    protected String mTitle;
    protected String mDescription;
    protected String mDate;
    protected String mCity;
    protected Double mLat;
    protected Double mLng;


    public Note() {
    }

    public Note(String title, String description, String date, Double lat, Double lng) {
        mTitle = title;
        mDescription = description;
        mDate = date;
        mLat = lat;
        mLng = lng;
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

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        this.mLat = lat;
    }

    public Double getLng() {
        return mLng;
    }

    public void setLng(Double lng) {
        this.mLng = lng;
    }

    public long getNoteId() {
        return getId();
    }

    public void setCity(String city) {
        this.mCity = city;
    }
    public String getCity() {
        return mCity;
    }

    @Override
    public String toString() {
        return "Note " + getId() + " with title: " + mTitle;
    }
}
