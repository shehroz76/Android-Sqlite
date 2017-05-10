package com.sqlite.msk.sqlitefunctionsapp.data;

import android.provider.BaseColumns;

/**
 * Created by DELL on 5/4/2017.
 */

public class UserContract {


    private UserContract(){

    }

    public static final class UserEntry implements BaseColumns{


        public static final String TABLE_NAME = "users";

        public static final String _ID = BaseColumns._ID;

        public static final String Column_name = "name";

        public static final String Column_age = "age";

        public static final String Column_email = "email";

        public static final String Column_gender = "gender";

        public static final String Column_Img = "img";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;




    }


}
