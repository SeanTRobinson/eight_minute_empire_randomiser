package com.seanrobinson.eightminuteempirelegends_randomiser;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    ImageView locationImageView;
    TextView choiceTextView;
    List<String> choices;
    List<String> remainingLocations;
    Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWidgets();
        loadChoices();
        loadPossibleLocations();
        setClickListenerForImage();
        setClickListenerForSkipButton();
    }

    private void setClickListenerForSkipButton() {
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewImage(true);
            }
        });
    }

    private void setClickListenerForImage() {
        locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNewImage();
            }
        });
    }

    private void loadPossibleLocations() {
        AssetManager assetManager = getAssets();
        String[] possibleLocations;
        try {
            possibleLocations = assetManager.list("");
            remainingLocations = new ArrayList<String>(Arrays.asList(possibleLocations));
            remainingLocations.remove("images");
            remainingLocations.remove("sounds");
            remainingLocations.remove("webkit");
            long seed = System.nanoTime();
            Collections.shuffle(remainingLocations, new Random(seed));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Could not load location names.", Toast.LENGTH_LONG);
        }
    }

    private void loadChoices() {
        choices = new ArrayList<>();
        choices.add("Starting Location");
        choices.add("Additional Location");
        choices.add("Tree");
        choices.add("Magic Spring");
        choices.add("Cottage");
        choices.add("Hidden Treasure");
        choices.add("Stable");
        choices.add("Dragon");
        choices.add("Portal 1");
        choices.add("Portal 2");
        choices.add("Band of Rogues");
        choices.add("Citadel 1");
        choices.add("Citadel 2");
        choices.add("Citadel 3");
    }

    private void setupWidgets() {
        locationImageView = (ImageView)findViewById(R.id.location);
        choiceTextView = (TextView)findViewById(R.id.choice);
        skipButton = (Button)findViewById(R.id.skip_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNewImage();
    }

    private void loadNewImage() {
        closeIfDone();

        choiceTextView.setText(choices.get(0));
        choices.remove(0);
        setNewImage(true);
    }

    private void closeIfDone() {
        if(choices.isEmpty()) {
            Toast.makeText(this, "No more choices!", Toast.LENGTH_SHORT);
            finish();
        }
    }

    private void setNewImage(boolean removeImage) {
        closeIfNoMoreImages();

        setNewDrawable(remainingLocations.get(0));

        if (removeImage) {
            remainingLocations.remove(0);
        }
    }

    private void setNewDrawable(String filename) {
        try {
            InputStream ims = getAssets().open(filename);
            Drawable d = Drawable.createFromStream(ims, null);
            locationImageView.setImageDrawable(d);
        }
        catch(IOException ex)
        {
            Toast.makeText(this, "Could not set new drawable.", Toast.LENGTH_SHORT);
            finish();
        }
    }

    private void closeIfNoMoreImages() {
        if(remainingLocations.isEmpty()) {
            Toast.makeText(this, "No more locations!", Toast.LENGTH_SHORT);
            finish();
        }
    }
}
