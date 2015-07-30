package com.seanrobinson.eightminuteempirelegends_randomiser;

import android.content.res.AssetManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWidgets();
        loadChoices();
        loadPossibleLocations();
    }

    private void loadPossibleLocations() {
        AssetManager assetManager = getAssets();
        String[] possibleLocations;
        try {
            possibleLocations = assetManager.list("locations");
            remainingLocations = new ArrayList<String>(Arrays.asList(possibleLocations));
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNewImage();
    }

    private void loadNewImage() {
        choiceTextView.setText(choices.get(0));
        choices.remove(0);
        setNewImage();
    }

    private void setNewImage() {
        int resID = getResources().getIdentifier(remainingLocations.get(0), "drawable",  getPackageName());
        locationImageView.setImageResource(resID);
    }
}
