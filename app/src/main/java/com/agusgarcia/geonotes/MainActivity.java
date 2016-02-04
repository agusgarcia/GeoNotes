package com.agusgarcia.geonotes;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements FirstFragment.FirstFragmentListener {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragment(new FirstFragment());
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(fragment.getTag())
                .replace(R.id.home_container, fragment)
                .commit();
    }

    @Override
    public void onButtonMapClick() {
        Log.d(TAG, "Button Map Clicked");
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

}
