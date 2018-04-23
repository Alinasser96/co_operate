package com.co_operate.aly.co_operate;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {
    private AutoCompleteTextView actv ;
    private ArrayList<Emails> feedsList4=new ArrayList<>();
    private ArrayList<String> feedsList5=new ArrayList<>();
    private ArrayList<String> feedsList6=new ArrayList<>();
    private ArrayAdapter<String> feedsList3;
    private ArrayList<Post> newposts=new ArrayList<>();
    private ArrayList<Post> oldposts=new ArrayList<>();
    private ArrayList<Post> posts=new ArrayList<>();
    private ArrayList<Post> friendsPosts=new ArrayList<>();
    private ArrayList<String> searched=new ArrayList<>();
    String posttext,name;
    String  currentuser;
    EditText et;
    ListView generalPostslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        et = (EditText)findViewById(R.id.editText);
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        generalPostslist = (ListView) findViewById(R.id.generalpostslist);

        feedsList3= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        actv = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference usersRef2 = rootRef.child("all_emails");
        usersRef2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                feedsList3.clear();
                feedsList4.clear();
                feedsList5.clear();
                searched.clear();
                friendsPosts.clear();


                for (DataSnapshot ds :dataSnapshot.getChildren()) {
                    Emails emails = ds.getValue(Emails.class);
                    String suggestion = emails.getName();
                    feedsList3.add(suggestion);
                    feedsList5.add(suggestion);
                    feedsList4.add(emails);
                    if(emails.getEmail_id().equals(currentuser))
                    {
                        if (emails.getFriends()!=null){
                        searched=emails.getFriends();}

                    }
                }


                for(int i = 0 ; i <feedsList4.size();i++){
                    for (int j=0 ;j<searched.size();j++){
                        if(feedsList4.get(i).getEmail_id().equals(searched.get(j)))
                        {
                            if(feedsList4.get(i).getPosts()!=null){
                            friendsPosts.addAll(feedsList4.get(i).getPosts());}

                        }
                    }

                }
                Myadapter myAdapter = new Myadapter(SigninActivity.this, R.layout.accountpost, friendsPosts);
                generalPostslist.setAdapter(myAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        actv.setAdapter(feedsList3);




        final Intent intent = new Intent(this,account.class);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int index = feedsList5.indexOf(actv.getText().toString());
                Emails email = feedsList4.get(index);



                String name =email.getName();
                posts= email.getPosts();
                String id = email.getEmail_id();
                intent.putExtra("currentuser2",currentuser);
                intent.putExtra("currentuser",id);
                intent.putExtra("emails",feedsList4);
                intent.putExtra("arraylist",posts);
                intent.putExtra("STRING_I_NEED", name);
                startActivity(intent);
            }
        });
    }

    public void post(View view) {
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        Query query = reference.child("all_emails").orderByChild("email_id").equalTo(currentuser);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                Emails emails =nodeDataSnapshot.getValue(Emails.class);
                if(emails.getPosts()!=null){
                oldposts = emails.getPosts();
                name=emails.getName();
                posttext = et.getText().toString();
                oldposts.add(new Post(posttext,0,currentuser,formattedDate,name));
                newposts= oldposts;
                String path = "/" + dataSnapshot.getKey() + "/" + key;
                HashMap<String, Object> result = new HashMap<>();
                result.put("posts", newposts);
                reference.child(path).updateChildren(result);}
                else
                {
                    name=emails.getName();
                    posttext = et.getText().toString();
                    newposts.add(new Post(posttext,0,currentuser,formattedDate,name));

                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("posts", newposts);
                    reference.child(path).updateChildren(result);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }

    public void out(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
        finish();
    }
}
