package com.codingcrusader.bricksortapp.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.codingcrusader.bricksort.set.Part;
import com.codingcrusader.bricksortapp.InventoryActivity;

public class PartImageTask extends AsyncTask<Part, Void, Bitmap> {
    private ImageView imageView;
    private Part part;

    public PartImageTask(ImageView imageView) {
        super();
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Part... args) {
        this.part = args[0];

        return InventoryActivity.INVENTORY_HELPER.getBitmap(part, InventoryActivity.getActivity());
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        String color = part.getColor().replaceAll(" ", "");
        String name = part.getPartId() + color + ".jpg";

        InventoryActivity.partBmpMap.put(part, bitmap);
        InventoryActivity.INVENTORY_HELPER.saveBitmap(name, InventoryActivity.partBmpMap.get(part), InventoryActivity.getActivity());

        imageView.setImageBitmap(InventoryActivity.partBmpMap.get(part));
    }
}
