package com.codingcrusader.bricksortapp.task;

import android.os.AsyncTask;

import com.codingcrusader.bricksort.set.SetInventory;
import com.codingcrusader.bricksortapp.InventoryActivity;

public class SaveInventoryTask extends AsyncTask<SetInventory, Void, Boolean> {

    @Override
    protected void onPreExecute() {
        InventoryActivity.showLoadingCircle();
    }

    @Override
    protected Boolean doInBackground(SetInventory... args) {
        return InventoryActivity.INVENTORY_HELPER.saveInventory(args[0], InventoryActivity.getActivity());
    }

    @Override
    protected void onPostExecute(Boolean b) {
        InventoryActivity.hideLoadingCircle();
    }
}