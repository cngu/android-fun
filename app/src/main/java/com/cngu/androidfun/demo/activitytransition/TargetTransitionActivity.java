package com.cngu.androidfun.demo.activitytransition;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cngu.androidfun.R;

public class TargetTransitionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_transition);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Prevent the Status Bar and Navigation Bar from being transitioned
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        int color = getIntent().getIntExtra(SourceTransitionActivity.KEY_COLOR, Color.BLACK);
        final Button selectedButton = (Button) findViewById(R.id.selected_button);
        selectedButton.setBackgroundColor(color);
        selectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finishAfterTransition();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
