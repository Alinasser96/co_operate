package com.co_operate.aly.co_operate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

/**
 * Created by aly on 3/7/18.
 */

public class Myadapter extends ArrayAdapter<Post> {
    private  Context mcontext;
    private int resource;
    private  ArrayList<Post> arrayList;
    private ArrayList<Post> newposts=new ArrayList<>();
    private ArrayList<Post> oldposts=new ArrayList<>();
    private int liko;
    public Myadapter(@NonNull Context context, int resource, @NonNull ArrayList<Post> objects) {
        super(context, resource, objects);
        this.mcontext=context;
        this.resource= resource;
        this.arrayList=objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String post=getItem(position).getPost();
          int likes = getItem(position).getLikes();
          String date = getItem(position).getDate();
          String name= getItem(position).getName();
        final String currentuser = getItem(position).getId();
        final int likes2 =likes;
        Post feeds = new Post(post,likes,currentuser,date,name);
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        convertView = inflater.inflate(resource,parent,false);
        TextView datet = (TextView)convertView.findViewById(R.id.textView6);
        TextView namet = (TextView)convertView.findViewById(R.id.textView7);
        TextView postt = (TextView)convertView.findViewById(R.id.textView);

        Button like = (Button)convertView.findViewById(R.id.button3);
        like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference reference = firebaseDatabase.getReference();
                Query query = reference.child("all_emails").orderByChild("email_id").equalTo(currentuser);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                        String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                        Emails emails =nodeDataSnapshot.getValue(Emails.class);
                        oldposts = emails.getPosts();
                        Post post1=oldposts.get(getCount()-position-1);
                        post1.setLikes(likes2+1);

                        oldposts.set(getCount()-position-1,post1);
                        newposts= oldposts;
                        String path = "/" + dataSnapshot.getKey() + "/" + key;
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("posts", newposts);
                        reference.child(path).updateChildren(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });
            }
        });
        postt.setText(post);
        like.setText("<3  :  "+likes);
        datet.setText(date);
        namet.setText(name);

        return  convertView;
    }
    @Override
    public int getCount(){

        return arrayList.size();

    }
    @Override
    public  Post getItem(int position){
        return arrayList.get(getCount()-position-1);

    }
}
