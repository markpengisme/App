package com.example.android.waitlist.data;

import android.provider.BaseColumns;

public class WaitlistContract {

    public static final class WaitlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "waitlist";
        public static final String COLUMN_GUEST_NAME = "guestName";
        public static final String COLUMN_GUEST_AGE = "age";
        public static final String COLUMN_GUEST_GENDER= "gender";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }

}
