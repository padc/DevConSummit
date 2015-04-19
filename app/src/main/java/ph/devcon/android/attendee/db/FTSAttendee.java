package ph.devcon.android.attendee.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import ph.devcon.android.base.DatabaseHelper;
import ph.devcon.android.user.db.User;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 11/21/14.
 */
public class FTSAttendee {
    public static final String TABLE_ATTENDEE_FTS = "ATTENDEE_FTS";
    public static final String COL_ID = "_id";
    public static final String COL_EMAIL = "COL_EMAIL";
    public static final String COL_FULL_NAME = "COL_FULL_NAME";
    public static final String COL_POSITION = "COL_POSITION";
    public static final String COL_COMPANY = "COL_COMPANY";
    public static final String COL_LOCATION = "COL_LOCATION";
    public static final String COL_CONTACT_NUMBER = "COL_CONTACT_NUMBER";
    public static final String COL_DESCRIPTION = "COL_DESCRIPTION";
    public static final String COL_WEBSITE = "COL_WEBSITE";
    public static final String COL_FACEBOOK_URL = "COL_FACEBOOK_URL";
    public static final String COL_TWITTER_HANDLE = "COL_TWITTER_HANDLE";
    public static final String COL_TECH_PRIMARY = "COL_TECH_PRIMARY";
    public static final String COL_TECH_2 = "COL_TECH_2";
    public static final String COL_TECH_3 = "COL_TECH_3";
    public static final String COL_PHOTO_URL = "COL_PHOTO_URL";
    private static final String TAG = FTSAttendee.class.getName();
    private static FTSAttendee mInstance;
    DatabaseHelper helper;

    private FTSAttendee() {
    }

    private FTSAttendee(DatabaseHelper helper) {
        this.helper = helper;
    }

    public static FTSAttendee getInstance(DatabaseHelper helper) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://developer.android.com/resources/articles/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new FTSAttendee(helper);
        }
        return mInstance;
    }

    public static String buildTable() {
        return "CREATE VIRTUAL TABLE " + TABLE_ATTENDEE_FTS + " USING fts3("
                + COL_ID + ", "
                + COL_EMAIL + ", "
                + COL_FULL_NAME + ", "
                + COL_POSITION + ", "
                + COL_COMPANY + ", "
                + COL_LOCATION + ", "
                + COL_CONTACT_NUMBER + ", "
                + COL_DESCRIPTION + ", "
                + COL_WEBSITE + ", "
                + COL_FACEBOOK_URL + ", "
                + COL_TWITTER_HANDLE + ", "
                + COL_TECH_PRIMARY + ", "
                + COL_TECH_2 + ", "
                + COL_TECH_3 + ", "
                + COL_PHOTO_URL + ", "
                + ");";
    }

    public static String dropTable() {
        return "DROP TABLE " + TABLE_ATTENDEE_FTS + ";";
    }

    public static void clear() {
        // TODO
    }

    public void create(Attendee attendee) {
        create(helper.getReadableDatabase(), attendee);
    }

    public void create(SQLiteDatabase database, Attendee attendee) {
        ContentValues contentValues = new ContentValues();
        User user = attendee.getUser();
        if (Optional.of(user).isPresent()) {
            contentValues.put(COL_ID, attendee.getId());
            contentValues.put(COL_EMAIL, user.getEmail());
            contentValues.put(COL_FULL_NAME, user.getFullName());
            contentValues.put(COL_POSITION, user.getPosition());
            contentValues.put(COL_COMPANY, user.getCompany());
            contentValues.put(COL_LOCATION, user.getLocation());
            contentValues.put(COL_CONTACT_NUMBER, user.getContactNumber());
            contentValues.put(COL_WEBSITE, user.getWebsite());
            contentValues.put(COL_FACEBOOK_URL, user.getFacebookUrl());
            contentValues.put(COL_TWITTER_HANDLE, user.getTwitterHandle());
            contentValues.put(COL_TECH_PRIMARY, user.getPrettyMainTechnology());
            contentValues.put(COL_TECH_2, user.getPrettyTechnologyList());
            contentValues.put(COL_TECH_3, user.getPrettyTechnologyList());
            contentValues.put(COL_PHOTO_URL, user.getPhotoUrl());
            database.insert(TABLE_ATTENDEE_FTS, null, contentValues);
        }
    }

    public void update(Attendee attendee) {
        update(helper.getReadableDatabase(), attendee);
    }

    public void update(SQLiteDatabase database, Attendee attendee) {
        ContentValues contentValues = new ContentValues();
        User user = attendee.getUser();
        if (Optional.of(user).isPresent()) {
            contentValues.put(COL_ID, attendee.getId());
            contentValues.put(COL_EMAIL, user.getEmail());
            contentValues.put(COL_FULL_NAME, user.getFullName());
            contentValues.put(COL_POSITION, user.getPosition());
            contentValues.put(COL_COMPANY, user.getCompany());
            contentValues.put(COL_LOCATION, user.getLocation());
            contentValues.put(COL_CONTACT_NUMBER, user.getContactNumber());
            contentValues.put(COL_WEBSITE, user.getWebsite());
            contentValues.put(COL_FACEBOOK_URL, user.getFacebookUrl());
            contentValues.put(COL_TWITTER_HANDLE, user.getTwitterHandle());
            contentValues.put(COL_TECH_PRIMARY, user.getPrettyMainTechnology());
            contentValues.put(COL_TECH_2, user.getPrettyTechnologyList());
            contentValues.put(COL_TECH_3, user.getPrettyTechnologyList());
            contentValues.put(COL_PHOTO_URL, user.getPhotoUrl());
            database.insert(TABLE_ATTENDEE_FTS, null, contentValues);
            database.update(TABLE_ATTENDEE_FTS, contentValues, "_id = " + String.valueOf(attendee.getId()), null);
        }
    }

    public void delete(Attendee attendee) {
        delete(helper.getReadableDatabase(), attendee);
    }

    public void delete(SQLiteDatabase database, Attendee attendee) {
        database.delete(TABLE_ATTENDEE_FTS, "_id = " + String.valueOf(attendee.getId()), null);
    }

    public Cursor search(String query) {
        assert !Util.isNullOrEmpty(query) : "query must not be an empty string!";
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_ATTENDEE_FTS,
                new String[]{
                        COL_ID,
                        COL_FULL_NAME,
                        COL_POSITION,
                        COL_COMPANY,
                        COL_PHOTO_URL,
                        COL_TECH_PRIMARY},
                TABLE_ATTENDEE_FTS + " MATCH ?",
                new String[]{appendWildcard(query)},
                null, null, null);
        return cursor;
    }

    public Cursor queryForAll() {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_ATTENDEE_FTS,
                new String[]{
                        COL_ID,
                        COL_FULL_NAME,
                        COL_POSITION,
                        COL_COMPANY,
                        COL_PHOTO_URL,
                        COL_TECH_PRIMARY},
                COL_ID + "=?", new String[]{"*"},
                null, null, null);
        String countQuery = "SELECT  * FROM " + TABLE_ATTENDEE_FTS;
        cursor = database.rawQuery(countQuery, null);
        return cursor;
    }

    public Cursor queryEmpty() {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_ATTENDEE_FTS,
                new String[]{
                        COL_ID,
                        COL_FULL_NAME,
                        COL_POSITION,
                        COL_COMPANY,
                        COL_PHOTO_URL,
                        COL_TECH_PRIMARY},
                COL_ID + "=?", new String[]{"0"},
                null, null, null);
//        String countQuery = "SELECT  * FROM " + TABLE_ATTENDEE_FTS;
//        cursor = database.rawQuery(countQuery, null);
        return cursor;
    }

    private String appendWildcard(String query) {
        if (Util.isNullOrEmpty(query)) return query;
        final StringBuilder builder = new StringBuilder();
        final String[] splits = Iterables.toArray(Splitter.on(" ").split(query), String.class);
        for (String split : splits)
            builder.append(split).append("*").append(" ");
        return builder.toString().trim();
    }

}
