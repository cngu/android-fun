package com.cngu.androidfun.demo.activitytransition;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cngu.androidfun.R;

public class TargetTransitionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_transition);

        getActionBar().setDisplayHomeAsUpEnabled(true);

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
