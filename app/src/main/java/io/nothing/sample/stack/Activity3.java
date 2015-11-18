package io.nothing.sample.stack;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.nothing.sample.R;
import io.nothing.utils.ActivityUtils;

/**
 * Created by sanvi on 10/23/15.
 */
public class Activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);
        Button btnStack1 = (Button) findViewById(R.id.activity_stack_1);
        TextView textView = (TextView) findViewById(R.id.activity_stack_text);
        textView.setText(Activity3.class.getSimpleName());
        btnStack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.jump(Activity3.this, Activity4.class, 102);
            }
        });
    }
}
