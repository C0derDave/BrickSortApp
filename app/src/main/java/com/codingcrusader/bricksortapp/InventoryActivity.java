package com.codingcrusader.bricksortapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.codingcrusader.bricksort.BrickSort;
import com.codingcrusader.bricksort.set.Part;
import com.codingcrusader.bricksort.set.SetInventory;
import com.codingcrusader.bricksortapp.task.DownloadInventoryTask;
import com.codingcrusader.bricksortapp.task.GetLocalInventoryTask;
import com.codingcrusader.bricksortapp.task.SaveInventoryTask;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InventoryActivity extends AppCompatActivity {

    private static InventoryActivity instance;
    public static final BrickSort BRICKSORT = new BrickSort("no api key for you");
    public static final InventoryHelper INVENTORY_HELPER = new InventoryHelper();
    public static final Gson GSON = new Gson();
    public static SetInventory currentInventory;
    public static HashMap<Part, Bitmap> partBmpMap = new HashMap<>();

    public static void showLoadingCircle() {
        ProgressBar loadingCircle = InventoryActivity.instance.findViewById(R.id.loading_circle);
        loadingCircle.setVisibility(View.VISIBLE);
    }

    public static void hideLoadingCircle() {
        ProgressBar loadingCircle = InventoryActivity.instance.findViewById(R.id.loading_circle);
        loadingCircle.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        instance = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Search for a set using the set ID (ex. 7965)");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_PHONE);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    clear();
                    String setId = INVENTORY_HELPER.validateSetID(input.getText().toString());

                    if(Arrays.asList(getFilesDir().list()).contains(setId + ".json")) {
                        new GetLocalInventoryTask().execute(setId);
                    } else {
                        new DownloadInventoryTask().execute(setId);
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
            return true;
        } else if(id == R.id.action_save) {
            if(currentInventory != null) {
                new SaveInventoryTask().execute(currentInventory);
            }
        } else if(id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if(id == R.id.action_quit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);

        initializeListOfParts(currentInventory);
    }

    public static InventoryActivity getActivity() {
        return instance;
    }

    public void clear() {
        ListView listView = findViewById(R.id.listview_parts);
        listView.setAdapter(new PartAdapter(new ArrayList<Part>()));
    }

    public static void initializeListOfParts(SetInventory inventory) {
        ListView listView = instance.findViewById(R.id.listview_parts);

        PartAdapter adapter = new PartAdapter(inventory.getParts());
        listView.setAdapter(adapter);
    }
}
