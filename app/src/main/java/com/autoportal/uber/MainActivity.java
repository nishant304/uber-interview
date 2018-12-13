package com.autoportal.uber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.autoportal.uber.view.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main,new SearchFragment())
                    .commit();
        }
    }

}
