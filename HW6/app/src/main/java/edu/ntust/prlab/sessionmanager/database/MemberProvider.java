package edu.ntust.prlab.sessionmanager.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by IDIC on 2017/4/23.
 */

public class MemberProvider extends ContentProvider {

    private MemberDatabaseHelper databaseHelper;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int MEMBERS = 100;
    private static final int MEMBERS_WITH_ID = 101;

    /**
     * 事先加入URI的對應情況。
     */
    static {
        URI_MATCHER.addURI(MemberContract.CONTENT_AUTHORITY,
                MemberContract.TABLE_NAME,
                MEMBERS);

        URI_MATCHER.addURI(MemberContract.CONTENT_AUTHORITY,
                MemberContract.TABLE_NAME + "/#",
                MEMBERS_WITH_ID);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new MemberDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * 根據條件讀取Member，
     * 此例題的排序只要修改SortOrder即可。
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (URI_MATCHER.match(uri)) {
            case MEMBERS: {
                return databaseHelper.getReadableDatabase().query(
                        MemberContract.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }
        }
        return null;
    }

    /**
     * 新增Member，要新增的資料包裝在ContentValues裡面。
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long _id = -1;
        switch (URI_MATCHER.match(uri)) {
            case MEMBERS: {
                db.beginTransaction();
                try {
                    _id = db.insert(MemberContract.TABLE_NAME, null, values);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }
        }
        if (-1 != _id && getContext() != null && null != getContext().getContentResolver()) {
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(MemberContract.CONTENT_URI, _id);
        } else {
            return null;
        }
    }

    /**
     * 只接受MEMBERS_WITH_ID的URI，
     * 根據URI後面指定的ID刪除對應的Member。
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (URI_MATCHER.match(uri)) {
            case MEMBERS_WITH_ID:
                long id = ContentUris.parseId(uri);
                selection = String.format("%s = ?", MemberContract.MemberEntry._ID);
                selectionArgs = new String[]{String.valueOf(id)};
                break;
            default:
                throw new IllegalArgumentException("Illegal delete URI");
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int count = db.delete(MemberContract.TABLE_NAME, selection, selectionArgs);

        if (count > 0 && getContext() != null && null != getContext().getContentResolver()) {
            //Notify observers of the change
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    /**
     * 這邊不會用到，所以不實作。
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
