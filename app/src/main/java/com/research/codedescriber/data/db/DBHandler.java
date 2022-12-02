package com.research.codedescriber.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.research.codedescriber.data.entity.SingleEntry;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "innerdb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "cache";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course code column
    private static final String CODE_COL = "code";

    // below variable for our course description column.
    private static final String DESCRIPTION_COL = "description";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CODE_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new entry to our sqlite database.
    public void addNewEntry(SingleEntry entry) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(CODE_COL, entry.getCode());
        values.put(DESCRIPTION_COL, entry.getDescription());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    // this method is use to add new entry to our sqlite database.
    public List<SingleEntry> retrieveEntryByCodeOrDescription(String searchText) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(CODE_COL, searchText);

        // after adding all values we are passing
        // content values to our table.
        String sql = "SELECT * FROM " + TABLE_NAME
                + " WHERE TRIM(" + CODE_COL +") LIKE '%"+searchText.trim()+"%'"
                + " OR TRIM(" + DESCRIPTION_COL +") LIKE '%"+searchText.trim()+"%'";
        Cursor c = db.rawQuery(sql, null);

        List<SingleEntry> result = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                // Passing values
                SingleEntry entry = new SingleEntry();
                entry.setId(c.getInt(c.getColumnIndex(ID_COL)));
                entry.setCode(c.getString(c.getColumnIndex(CODE_COL)));
                entry.setDescription(c.getString(c.getColumnIndex(DESCRIPTION_COL)));
                // Do something Here with values
                result.add(entry);
            } while(c.moveToNext());
        }

        // at last we are closing our
        // database after adding database.
        c.close();
        db.close();

        return result;
    }

    public SingleEntry readSingleRecord(int entryId) {
        SingleEntry objectStudent = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL + " = " + entryId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COL)));
            String code = cursor.getString(cursor.getColumnIndex(CODE_COL));
            String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION_COL));
            objectStudent = new SingleEntry();
            objectStudent.setId(id);
            objectStudent.setCode(code);
            objectStudent.setDescription(description);
        }
        cursor.close();
        db.close();
        return objectStudent;
    }

    public boolean update(SingleEntry entry) {
        ContentValues values = new ContentValues();
        values.put(CODE_COL, entry.getCode());
        values.put(DESCRIPTION_COL, entry.getDescription());
        String where = "id = ?";
        String[] whereArgs = { Integer.toString(entry.getId()) };
        SQLiteDatabase db = this.getWritableDatabase();
        boolean updateSuccessful = db.update(TABLE_NAME, values, where, whereArgs) > 0;
        db.close();
        return updateSuccessful;
    }

    public boolean delete(int entryId) {
        boolean deleteSuccessful;
        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete(TABLE_NAME, ID_COL + " ='" + entryId + "'", null) > 0;
        db.close();
        return deleteSuccessful;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
