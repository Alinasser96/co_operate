package com.co_operate.aly.co_operate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Friends extends AppCompatActivity {
ArrayList<Emails> feedsList4 = new ArrayList<>();
    ArrayList<String> searched = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ListView lv = (ListView)findViewById(R.id.friendsList);

        Bundle extras = getIntent().getExtras();
         ArrayList<String> serached= (ArrayList<String>) extras.get("STRING_I_NEED");
        feedsList4 = (ArrayList<Emails>) extras.get("allemails");




        for(int j = 0 ; j <serached.size();j++) {
            String friend = serached.get(j);
            for(int i = 0 ; i <feedsList4.size();i++) {
                if(feedsList4.get(i).getEmail_id().equals(friend))
                {
                    searched.add(feedsList4.get(i).getName());


                }
            }


        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                searched );

        lv.setAdapter(arrayAdapter);
    }
}
