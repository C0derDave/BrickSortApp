package com.codingcrusader.bricksortapp.task;

import android.os.AsyncTask;

import com.codingcrusader.bricksort.set.SetInventory;
import com.codingcrusader.bricksortapp.InventoryActivity;

public abstract class InventoryTask extends AsyncTask<String, Void, SetInventory> {
    @Override
    protected void onPreExecute() {
        InventoryActivity.showLoadingCircle();
    }

    @Override
    protected SetInventory doInBackground(String... args) {
        return null;
    }

    @Override
    protected void onPostExecute(SetInventory inventory) {
        InventoryActivity.currentInventory = inventory;
        InventoryActivity.initializeListOfParts(InventoryActivity.currentInventory);

        InventoryActivity.hideLoadingCircle();
    }
}
