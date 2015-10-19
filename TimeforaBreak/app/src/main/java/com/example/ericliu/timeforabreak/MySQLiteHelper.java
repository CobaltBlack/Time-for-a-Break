package com.example.ericliu.timeforabreak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by Josh on 10/17/2015.
 * Database class for the program to gather stretch information from a SQLite DB
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "StretchDB";

    // Stretch table name
    private static final String TABLE_STRETCH = "stretch";

    // Stretch Table Column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE_PATH = "image_path";

    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_IMAGE_PATH};


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * @param db Creates the database for the app
     *           Includes on table with the following columns:
     *           id [int]
     *           name [text]
     *           description (in the strings.xml) [text]
     *           image_path [text]
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create stretch table
        String CREATE_STRETCH_TABLE = "CREATE TABLE stretch ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, description TEXT, image_path TEXT )";

        // create stretchs table
        db.execSQL(CREATE_STRETCH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older stretches table if existed
        db.execSQL("DROP TABLE IF EXISTS stretch");

        // create fresh stretches table
        this.onCreate(db);
    }

    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) stretch + get all stretches + delete all stretches
     */

    public void addStretch(Stretch stretch) {
        //for logging
        Log.d("addStretch", stretch.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, stretch.getName());
        values.put(KEY_DESCRIPTION, stretch.getDescription());
        values.put(KEY_IMAGE_PATH, stretch.getImage_path());

        // 3. insert
        db.insert(TABLE_STRETCH, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Stretch getstretch(int id) {

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_STRETCH, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[]{String.valueOf(id)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build stretch object
        Stretch stretch = new Stretch();
        stretch.setId(Integer.parseInt(cursor.getString(0)));
        stretch.setName(cursor.getString(1));
        stretch.setDescription(cursor.getString(2));
        stretch.setImage_path(cursor.getString(3));

        Log.d("getstretch(" + id + ")", stretch.toString());

        // 5. return stretch
        return stretch;
    }

    // Get All stretches
    public List<Stretch> getAllStretches() {
        List<Stretch> stretches = new LinkedList<Stretch>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_STRETCH;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build stretch and add it to list
        Stretch stretch = null;
        if (cursor.moveToFirst()) {
            do {
                stretch = new Stretch();
                stretch.setId(Integer.parseInt(cursor.getString(0)));
                stretch.setName(cursor.getString(1));
                stretch.setDescription(cursor.getString(2));
                stretch.setImage_path(cursor.getString(3));

                // Add stretch to stretches
                stretches.add(stretch);
            } while (cursor.moveToNext());
        }

        Log.d("getAllStretches()", stretches.toString());

        // return stretches
        return stretches;
    }

    // Updating single stretch
    public int updatestretch(Stretch stretch) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", stretch.getName());
        values.put("description", stretch.getDescription());

        // 3. updating row
        int i = db.update(TABLE_STRETCH, //table
                values, // column/value
                KEY_ID + " = ?", // selections
                new String[]{String.valueOf(stretch.getId())}); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single stretch
    public void deletestretch(Stretch stretch) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_STRETCH,
                KEY_ID + " = ?",
                new String[]{String.valueOf(stretch.getId())});

        // 3. close
        db.close();

        Log.d("deleteStretch", stretch.toString());

    }
}
