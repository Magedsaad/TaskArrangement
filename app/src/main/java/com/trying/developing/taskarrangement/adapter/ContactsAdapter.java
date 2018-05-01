package com.trying.developing.taskarrangement.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.model.Users;
import com.trying.developing.taskarrangement.ui.ChattingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by developing on 4/28/2018.
 */

public class ContactsAdapter  extends RecyclerView.Adapter<ContactsAdapter.ContactHolder> {

    List<Users> data = new ArrayList<>();
    private DatabaseReference mUserDatabase;
    private Context mContext;
    private Users users;

    public ContactsAdapter(List<Users> data,Context context) {
        this.data = data;
        this.mContext=context;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactlistitem, parent, false);

        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {

        Users users=data.get(position);
        holder.userEmail.setText("User Email: "+users.getEmail());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.userEmaillist_id)
        TextView userEmail;

        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    users=data.get(getAdapterPosition());
                    mUserDatabase= FirebaseDatabase.getInstance().getReference().child("users");
                    final String getbyMail=users.getEmail();
                    Query query=mUserDatabase.orderByChild("email").equalTo(getbyMail);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(final DataSnapshot child : dataSnapshot.getChildren()){
                                final String id=child.getKey();
                                Intent intent=new Intent(mContext.getApplicationContext(), ChattingActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle bundle=new Bundle();
                                bundle.putString("friend_id",id);
                                bundle.putString("friend_email",getbyMail);
                                intent.putExtras(bundle);
                                mContext.startActivity(intent);


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
    }
}
