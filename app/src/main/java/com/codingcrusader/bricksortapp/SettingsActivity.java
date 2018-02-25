package com.codingcrusader.bricksortapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.icon_and_space);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
        return true;
    }

    public void onResetButtonClicked(View view) {

    }

    public boolean canDownloadThumbnails() {
        CheckBox checkBox = findViewById(R.id.checkbox_save_thumbnails);
        return checkBox.isChecked();
    }
}
