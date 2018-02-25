package com.codingcrusader.bricksortapp.task;

import com.codingcrusader.bricksort.set.SetInventory;
import com.codingcrusader.bricksortapp.InventoryActivity;

public class GetLocalInventoryTask extends InventoryTask {
    @Override
    protected SetInventory doInBackground(String... args) {
        return InventoryActivity.INVENTORY_HELPER.readInventory(args[0], InventoryActivity.getActivity());
    }
}
