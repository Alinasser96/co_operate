package com.co_operate.aly.co_operate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class account extends AppCompatActivity {
    String currentuser,currentuser2;
    Emails signedin,searched;
    ArrayList<String> seList = new ArrayList<>();
    ArrayList<String> siList = new ArrayList<>();
    ArrayList<Emails> allemails = new ArrayList<>();
    Button add;
    TextView status;
    int flag = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ListView listView = (ListView)findViewById(R.id.userpostslist);
        add= (Button)findViewById(R.id.add);
        status = findViewById(R.id.textView3);
        Bundle extras = getIntent().getExtras();
        currentuser = extras.getString("currentuser");
        currentuser2 = extras.getString("currentuser2");
        allemails= (ArrayList<Emails>) extras.get("emails");
        for(int i = 0 ; i <allemails.size();i++){
            if(allemails.get(i).getEmail_id().equals(currentuser))
            {
                searched=allemails.get(i);

            }
            if(allemails.get(i).getEmail_id().equals(currentuser2))
            {
                signedin=allemails.get(i);

            }

        }
        if(signedin.getFriends()!=null){
        if(signedin.getFriends().contains(currentuser)){
            status.setText("1st");
            add.setEnabled(false);
        }
        else{
            for(int j = 0 ; j<signedin.getFriends().size();j++){
            for (int i = 0 ;i<allemails.size();i++){
                if (allemails.get(i).getEmail_id().equals(signedin.getFriends().get(j)))
                {
                    if(allemails.get(i).getFriends().contains(currentuser)){
                        if(flag == 0 ){
                        status.setText("2nd with "+allemails.get(i).getName());
                        flag ++;
                        }
                        else if (flag ==1){
                            String old = status.getText().toString();
                            status.setText(old+"and "+allemails.get(i).getName());
                            flag++;
                        }
                        else{
                            flag ++;
                            status.setText("matual with "+flag+" friends");
                        }

                    }
                }
            }
            }

        }}

        String name = extras.getString("STRING_I_NEED");
        ArrayList<Post> poste= (ArrayList<Post>) extras.get("arraylist");
        TextView tv = (TextView)findViewById(R.id.textView2);
        tv.setText(name);
        if (poste!=null) {
            Myadapter myAdapter = new Myadapter(account.this, R.layout.accountpost, poste);
            listView.setAdapter(myAdapter);
        }

    }

    public void add(View view) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        Query query = reference.child("all_emails").orderByChild("email_id").equalTo(currentuser);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                Emails emails =nodeDataSnapshot.getValue(Emails.class);
                if(emails.getFriends()!=null){
                    seList = emails.getFriends();

                    seList.add(currentuser2);

                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("friends", seList);
                    reference.child(path).updateChildren(result);}
                else
                {

                    seList.add(currentuser2);

                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("friends", seList);
                    reference.child(path).updateChildren(result);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


        Query query2 = reference.child("all_emails").orderByChild("email_id").equalTo(currentuser2);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                Emails emails =nodeDataSnapshot.getValue(Emails.class);
                if(emails.getFriends()!=null){
                    siList = emails.getFriends();

                    siList.add(currentuser);

                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("friends", siList);
                    reference.child(path).updateChildren(result);}
                else
                {

                    siList.add(currentuser);

                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("friends", siList);
                    reference.child(path).updateChildren(result);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

    public void det(View view) {
        if( searched.getFriends()==null){
            Toast.makeText(account.this, "no friends yet",
                    Toast.LENGTH_LONG).show();
        }
        else {
            final Intent intent = new Intent(this, Friends.class);
            intent.putExtra("STRING_I_NEED", searched.getFriends());
            intent.putExtra("allemails", allemails);
            startActivity(intent);
        }
    }
}
