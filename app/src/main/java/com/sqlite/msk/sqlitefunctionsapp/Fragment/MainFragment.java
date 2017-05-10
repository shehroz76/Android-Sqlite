package com.sqlite.msk.sqlitefunctionsapp.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sqlite.msk.sqlitefunctionsapp.R;
import com.sqlite.msk.sqlitefunctionsapp.Adapter.UserCursorApapter;
import com.sqlite.msk.sqlitefunctionsapp.data.UserDbHelper;

/**
 * Created by DELL on 5/1/2017.
 */

public class MainFragment extends Fragment{



    private Button mUpload,mSubmit;
    private FloatingActionButton mFab;


    private static final int USER_LOADER = 0;

    UserCursorApapter mUserCursorApapter;
    private long Id;

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main,container,false);

        ListView userListView = (ListView)  rootView.findViewById(R.id.list_view);

        View emptyView = rootView.findViewById(R.id.empty_view);
        userListView.setEmptyView(emptyView);

        final UserDbHelper userDbHelper = new UserDbHelper(getActivity());

        Cursor userCursor = userDbHelper.getallUsers();


        mUserCursorApapter = new UserCursorApapter(getContext(),userCursor);
        userListView.setAdapter(mUserCursorApapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Id = id;

//
//                Toast.makeText(getActivity(),"id"+ id,Toast.LENGTH_SHORT).show();

                // custom dialog
                final Dialog dialog = new Dialog(getActivity() , R.style.Theme_AppCompat_Dialog_Alert);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.edit_delete_alert_dialog);


                // set the custom dialog components - text, image and button

                Button buttonedit = (Button) dialog.findViewById(R.id.editUser);

                buttonedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CrudFragment fragment = new CrudFragment();
                        Bundle arguments = new Bundle();
                        arguments.putLong( "id" , Id);
                        fragment.setArguments(arguments);
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.container, fragment , "crud");
                        ft.commit();
                        dialog.dismiss();


                    }
                });

                Button buttondelete = (Button) dialog.findViewById(R.id.deletetUser);

                buttondelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setMessage("Do you want to delete User?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        userDbHelper.deleteUser(Id);

                                        mUserCursorApapter.notifyDataSetChanged();

                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        dialog.dismiss();




                    }
                });

                dialog.show();






            }
        });

        mFab = (FloatingActionButton) rootView.findViewById(R.id.fab_Button) ;
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                CrudFragment crudFragment = new CrudFragment();
                ft.replace(R.id.container,crudFragment,"crud").addToBackStack(null).commit();
            }
        });



        return rootView;


    }

    @Override
    public void onStart() {
        super.onStart();
        mUserCursorApapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserCursorApapter.notifyDataSetChanged();
    }
}
