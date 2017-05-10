package com.sqlite.msk.sqlitefunctionsapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sqlite.msk.sqlitefunctionsapp.R;
import com.sqlite.msk.sqlitefunctionsapp.data.UserContract;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 5/4/2017.
 */

public class UserCursorApapter extends CursorAdapter {


    public UserCursorApapter(Context context, Cursor c) {
        super(context, c,0  /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {




        TextView name= (TextView) view.findViewById(R.id.name_item);
        TextView email = (TextView) view.findViewById(R.id.email_item);
        CircleImageView img = (CircleImageView) view.findViewById(R.id.Image_item);

        int idColumnIndex = cursor.getColumnIndex(UserContract.UserEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.Column_name);
        int emailColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.Column_email);
        int ImageColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.Column_Img);

        String UserName = cursor.getString(nameColumnIndex);
        String Email = cursor.getString(emailColumnIndex);
        String Image= cursor.getString(ImageColumnIndex);
        String Id = cursor.getString(idColumnIndex);

//        Uri imgUri = ;

        name.setText(UserName);
        email.setText(Email);
        Picasso.with(context).load(Image).into(img);
//                Picasso.with(MainActivity.this).load(photoUrl).into(mProfileImageMain);

    }
}
