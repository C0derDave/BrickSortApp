package com.codingcrusader.bricksortapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.codingcrusader.bricksort.set.Part;
import com.codingcrusader.bricksort.set.SetInventory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class InventoryHelper {
    public InventoryHelper() {

    }

    public String validateSetID(String setID) {
        String result = setID;
        if(result.charAt(result.length()-2) != '-') {
            result += "-1";
        }
        return result;
    }

    public boolean saveInventory(SetInventory inventory, Context context) {
        String json = InventoryActivity.GSON.toJson(inventory.getParts());
        String setID = inventory.getID();
        //FileOutputStream outputStream = null;
        boolean success = false;

        try {
            String filename = setID + ".json";

            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();

            for(Part part : InventoryActivity.partBmpMap.keySet()) {
                String color = part.getColor().replaceAll(" ", "");
                String name = part.getPartId() + color + ".jpg";
                if (!Arrays.asList(context.getFilesDir().list()).contains(name)) {
                    saveBitmap(name, InventoryActivity.partBmpMap.get(part), context);
                }
            }
            success = true;
        } catch (IOException e) {
            Log.d("BrickSort", e.toString());
        } /*finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    Log.d("BrickSort", e.getMessage());
                }
            }
        }*/
        return success;
    }

    public SetInventory readInventory(String setID, Context context) {
        SetInventory inventory = null;
        JsonParser parser = new JsonParser();
        //FileInputStream inputStream = null;
        //BufferedReader reader = null;

        try {
            FileInputStream inputStream = context.openFileInput(setID + ".json");
            StringBuilder builder = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();

            SetInventory result = new SetInventory(setID);
            JsonArray parts = parser.parse(builder.toString()).getAsJsonArray();
            for (JsonElement element : parts) {
                Part part = InventoryActivity.GSON.fromJson(element, Part.class);
                result.addPart(part);
            }

            inventory = result;
        } catch (IOException e) {
            Log.d("BrickSort", e.toString());
        } /*finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.d("BrickSort", e.getMessage());
                }
            }

            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d("BrickSort", e.getMessage());
                }
            }
        }*/
        return inventory;
    }

    public Bitmap getBitmap(Part part, Context context) {
        String color = part.getColor().replaceAll(" ", "");
        String name = part.getPartId() + color + ".jpg";
        Bitmap bitmap = readBitmap(name, context);
        if(bitmap == null) {
            bitmap = downloadBitmap(part, context);
            if(bitmap == null) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), android.R.drawable.ic_menu_help);
            }
        }
        return bitmap;
    }

    public Bitmap readBitmap(String name, Context context) {
        Bitmap bitmap = null;
        FileInputStream inputStream = null;

        try {
            inputStream = context.openFileInput(name);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            Log.d("BrickSort", e.getMessage());
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.d("BrickSort", e.getMessage());
                }
            }
        }
        return bitmap;
    }

    public Bitmap downloadBitmap(Part part, Context context) {
        Bitmap result = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = null;

        try {
            URL url = new URL(part.getPartImgUrl());
            inputStream = (InputStream) url.getContent();
            BitmapDrawable drawable = (BitmapDrawable) Drawable.createFromStream(inputStream, "src");

            Bitmap bitmap = drawable.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);

            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            result = BitmapFactory.decodeStream(byteArrayInputStream);
        } catch (MalformedURLException e) {
            Log.d("BrickSort", e.getMessage());
        } catch (IOException e) {
            Log.d("BrickSort", e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.d("BrickSort", e.getMessage());
                }
            }

            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    Log.d("BrickSort", e.getMessage());
                }
            }
        }

        return result;
    }

    public boolean saveBitmap(String name, Bitmap bitmap, Context context) {
        try {
            FileOutputStream outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            Bitmap bmp = bitmap;
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return true;
        } catch (FileNotFoundException e) {
            Log.d("BrickSort", e.getMessage());
        }

        return false;
    }

    public boolean fileExists(String dir) {
        return new File(dir).exists();
    }
}
