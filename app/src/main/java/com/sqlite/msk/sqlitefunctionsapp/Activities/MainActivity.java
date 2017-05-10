package com.sqlite.msk.sqlitefunctionsapp.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sqlite.msk.sqlitefunctionsapp.Fragment.MainFragment;
import com.sqlite.msk.sqlitefunctionsapp.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){

            MainFragment mainFragment = new MainFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container,mainFragment);
            ft.commit();

        }


    }


}
