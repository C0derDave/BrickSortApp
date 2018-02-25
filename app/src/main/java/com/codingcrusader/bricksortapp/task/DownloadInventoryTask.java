package com.codingcrusader.bricksortapp.task;

import com.codingcrusader.bricksort.BrickSort;
import com.codingcrusader.bricksort.set.SetInventory;
import com.codingcrusader.bricksortapp.InventoryActivity;

public class DownloadInventoryTask extends InventoryTask {
    @Override
    protected SetInventory doInBackground(String... args) {
        BrickSort brickSort = InventoryActivity.BRICKSORT;
        SetInventory setInventory = brickSort.getInventoryFromID(args[0]);
        setInventory.resetQuantities();

        return setInventory;
    }
}
