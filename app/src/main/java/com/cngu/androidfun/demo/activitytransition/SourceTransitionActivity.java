package com.cngu.androidfun.demo.activitytransition;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.cngu.androidfun.R;

public class SourceTransitionActivity extends Activity {

    public static final String KEY_COLOR = "key_color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_source_transition);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SourceTransitionActivity.this,
                                           TargetTransitionActivity.class);

                // create the transition animation - the images in the layouts
                // of both activities are defined with android:transitionName="my_transition"
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(SourceTransitionActivity.this, view,
                                                      getString(R.string.transition_expand_box));

                intent.putExtra(KEY_COLOR, ((ColorDrawable) view.getBackground()).getColor());

                // start the new activity
                startActivity(intent, options.toBundle());
            }
        };

        Button redButton = (Button) findViewById(R.id.red_button);
        Button greenButton = (Button) findViewById(R.id.green_button);
        Button blueButton = (Button) findViewById(R.id.blue_button);
        Button orangeButton = (Button) findViewById(R.id.orange_button);

        redButton.setOnClickListener(clickListener);
        greenButton.setOnClickListener(clickListener);
        blueButton.setOnClickListener(clickListener);
        orangeButton.setOnClickListener(clickListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                      Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, parentIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
