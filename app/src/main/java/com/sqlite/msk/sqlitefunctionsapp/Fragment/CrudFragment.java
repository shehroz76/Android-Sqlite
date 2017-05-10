package com.sqlite.msk.sqlitefunctionsapp.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.sqlite.msk.sqlitefunctionsapp.R;
import com.sqlite.msk.sqlitefunctionsapp.data.UserContract;
import com.sqlite.msk.sqlitefunctionsapp.data.UserDbHelper;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by DELL on 5/2/2017.
 */

public class CrudFragment extends Fragment {

    private TextInputEditText mName,mAge,mEmail;
    private CircleImageView mImageView;
    private Button mUpload,mSubmit;
    private Spinner mGenderSpinner;
    UserDbHelper myDb;

    private int mGender = UserContract.UserEntry.GENDER_UNKNOWN;
    private static final int Gallery_request = 1;
    private Uri mImageUri1 = null;
    private long user_id;
    private int genderUpdate;
    private String Image= "";


    public CrudFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_crud,container,false);


            Bundle arguments = getArguments();






        mName = (TextInputEditText) rootView.findViewById(R.id.Name);
        mAge = (TextInputEditText) rootView.findViewById(R.id.Age);
        mEmail = (TextInputEditText) rootView.findViewById(R.id.Email);

        mImageView = (CircleImageView) rootView.findViewById(R.id.Image);
        mUpload = (Button) rootView.findViewById(R.id.Upload);
        mSubmit = (Button) rootView.findViewById(R.id.submit);

        mGenderSpinner = (Spinner) rootView.findViewById(R.id.spinner);
        
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                UploadImage();
                
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Savedata();
            }
        });

        setUpSpinner();

        if(arguments!=null) {

            user_id = arguments.getLong("id");


            final UserDbHelper userDbHelper = new UserDbHelper(getActivity());

            Cursor cursor = userDbHelper.getUser(user_id);

            int idColumnIndex = cursor.getColumnIndex(UserContract.UserEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.Column_name);
            int ageColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.Column_age);
            int emailColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.Column_email);
            int genderColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.Column_gender);
            int ImageColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.Column_Img);

            if (cursor != null) {
                cursor.moveToFirst();

                String UserName = cursor.getString(nameColumnIndex);
                String Email = cursor.getString(emailColumnIndex);
                Image = cursor.getString(ImageColumnIndex);
                String Id = cursor.getString(idColumnIndex);
                String age = cursor.getString(ageColumnIndex);
                String gender = cursor.getString(genderColumnIndex);


                mName.setText(UserName);
                mAge.setText(age);
                mEmail.setText(Email);
                genderUpdate = Integer.parseInt(gender);

                Picasso.with(getActivity()).load(Image).into(mImageView);

                switch (genderUpdate) {
                    case UserContract.UserEntry.GENDER_MALE:
                        mGenderSpinner.setSelection(1);
                        break;
                    case UserContract.UserEntry.GENDER_FEMALE:
                        mGenderSpinner.setSelection(2);
                        break;
                    default:
                        mGenderSpinner.setSelection(0);
                        break;
                }

            }
            mSubmit.setText("Update");
            mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUserdetail();
                }
            });


        }



        return rootView;

    }

    private void updateUserdetail() {

        String Name = mName.getText().toString().trim();
        String Age = mAge.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        int gender = mGender;


        if(Name.equals("") || Age.equals("") || email.equals("") || Image.equals("")){

            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();


        } else {
            myDb = new UserDbHelper(getActivity());

            boolean isInserted = myDb.updateUser(user_id,Name,Age,email,Image,gender);

            if(isInserted){

                Toast.makeText(getActivity(), "User updated " , Toast.LENGTH_SHORT).show();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                MainFragment  mainFragment = new MainFragment();
                ft.replace(R.id.container,mainFragment,"mainfragment").addToBackStack(null).commit();

            }else
                Toast.makeText(getActivity(), "Error with saving User", Toast.LENGTH_SHORT).show();



        }



    }

    private void Savedata() {

        String Name = mName.getText().toString().trim();
        String Age = mAge.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        int gender = mGender;



        if(Name.equals("") || Age.equals("") || email.equals("") || mImageUri1==null){

            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();


        } else {
            String Image1 = mImageUri1.toString();


            myDb = new UserDbHelper(getActivity());
            boolean isInserted = myDb.addUser(Name,Age,email,Image1,gender);

            if(isInserted){

                Toast.makeText(getActivity(), "User saved  " , Toast.LENGTH_SHORT).show();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                MainFragment  mainFragment = new MainFragment();
                ft.replace(R.id.container,mainFragment,"mainfragment").addToBackStack(null).commit();

            }else
                Toast.makeText(getActivity(), "Error with saving User", Toast.LENGTH_SHORT).show();



        }

    }

    private void UploadImage() {

        Intent Image_gallery_intent = new Intent(Intent.ACTION_GET_CONTENT);
        Image_gallery_intent.setType("image/*");
        startActivityForResult(Image_gallery_intent,Gallery_request);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==Gallery_request && resultCode ==RESULT_OK){

            Uri ImageUri = data.getData();

            CropImage.activity(ImageUri).
                    setGuidelines(CropImageView.Guidelines.ON).
                    setAspectRatio(1,1).
                    start(getContext(),this);
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                mImageUri1 = result.getUri();

                mImageView.setImageURI(mImageUri1);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
    }

    private void setUpSpinner() {


        ArrayAdapter mgenderArrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.array_gender_options, android.R.layout.simple_spinner_item);
        mgenderArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mGenderSpinner.setAdapter(mgenderArrayAdapter);

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selection = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection)){
                    if(selection.equals(getString(R.string.gender_male))){

                        mGender = UserContract.UserEntry.GENDER_MALE;

                    }else  if(selection.equals(getString(R.string.gender_female))){

                        mGender = UserContract.UserEntry.GENDER_FEMALE;

                    }else {
                        if(genderUpdate!=0){
                            mGender = genderUpdate;
                        }else {
                            mGender = UserContract.UserEntry.GENDER_UNKNOWN;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                if(genderUpdate != 0){
                    mGender = genderUpdate;
                }else
                mGender = UserContract.UserEntry.GENDER_UNKNOWN;
            }
        });


    }
}
