package edu.ntust.prlab.sessionmanager.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 這邊跟課堂上的例題一致，用來提供存取Member的ContentProvider需要的資訊
 */
public class MemberContract {

    public static final String TABLE_NAME = "members";

    public static final String CONTENT_AUTHORITY = "edu.ntust.prlab.sessionmanager";

    public static final String ORDER_BY_NAME = MemberEntry.NAME + " ASC";
    public static final String ORDER_BY_GENDER = MemberEntry.GENDER + " ASC";
    public static final String ORDER_BY_AGE = MemberEntry.AGE + " ASC";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    /**
     * 紀錄取資料需要的欄位名稱
     */
    public static abstract class MemberEntry implements BaseColumns {
        public static final String NAME = "name";
        public static final String GENDER = "gender";
        public static final String AGE = "age";
    }

    private MemberContract() {
    }

}
