package com.codingcrusader.bricksortapp;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingcrusader.bricksort.set.Part;
import com.codingcrusader.bricksortapp.task.PartImageTask;

public class PartListItem {
    private Part part;
    private Button addButton;
    private Button removeButton;
    private TextView partNameView;
    private TextView partAmountView;
    private ImageView partImgView;

    public PartListItem(View view, Part part) {
        this.part = part;
        this.addButton = view.findViewById(R.id.button_add_part);
        this.removeButton = view.findViewById(R.id.button_remove_part);
        this.partNameView = view.findViewById(R.id.textview_part_name);
        this.partAmountView = view.findViewById(R.id.textview_part_amount);
        this.partImgView = view.findViewById(R.id.imageview_part);

        partNameView.setTextColor(Color.BLACK);

        if(part.getQuantity() >= part.getInitialQuantity()) {
            partAmountView.setTextColor(Color.GREEN);
        } else {
            partAmountView.setTextColor(Color.BLACK);
        }

        final Part part1 = part;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                part1.add();
                partAmountView.setText(part1.getQuantity() + "");

                if(part1.getQuantity() >= part1.getInitialQuantity()) {
                    partAmountView.setTextColor(Color.GREEN);
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                part1.remove();
                partAmountView.setText(part1.getQuantity() + "");

                if(part1.getQuantity() < part1.getInitialQuantity()) {
                    partAmountView.setTextColor(Color.BLACK);
                }
            }
        });

        partNameView.setText(part.getName());
        partAmountView.setText(part.getQuantity() + "");

        PartImageTask imageTask = new PartImageTask(partImgView);
        imageTask.execute(part);
    }
}
