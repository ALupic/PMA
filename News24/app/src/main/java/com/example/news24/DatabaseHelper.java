package com.example.news24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="register.db";
    public static final String TABLE_NAME="registeruser";
    //public static final String COL_1="ID";
    public static final String COL_2="username";
    public static final String COL_3="password";
    public static final String COL_4 = "notifications";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, notifications INTEGER)");
        db.execSQL("CREATE TABLE registeruser (username TEXT PRIMARY KEY, password TEXT, type INTEGER, notifications INTEGER)");
        db.execSQL("CREATE TABLE newsarticle (id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT, title TEXT, image TEXT, content TEXT, likes INTEGER, dislikes INTEGER, lat TEXT, long TEXT)");
        db.execSQL("CREATE TABLE comment (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, time DATETIME, likes INTEGER, dislikes INTEGER, user_id TEXT, article_id INTEGER, FOREIGN KEY(user_id) REFERENCES registeruser(username), FOREIGN KEY(article_id) REFERENCES newsarticle(id))");

        //INSERT ADMIN USER
        db.execSQL("INSERT INTO registeruser VALUES ('admin','admin', 1, 0)"); //insert into registeruser values ('admin', 'admin', 1, 0);

        //INSERT NEWS ARTICLES
        db.execSQL("INSERT INTO newsarticle VALUES(1, 'Politics', 'Brexit: Donald Tusk suggests \"flexible\" delay of up to a year', " +
                "'article1', " +
                "'European Council president Donald Tusk says the EU should consider offering the UK a \"flexible\" delay to Brexit of up to a year, with the option of leaving earlier if a deal is ratified.'," +
                "250, 2450,'50.8389', '4.3748299999999745')");

        db.execSQL("INSERT INTO newsarticle VALUES(2, 'Sport', 'Liverpool take control against Porto', " +
                "'article2', " +
                "'Liverpool cruise to victory over Porto after Naby Keïtas flying start. Liverpool controlled much of the quarter-final first leg with Naby Keïta and Roberto Firmino on the scoresheet in a dominant first-half display that threatened a repeat of last seasons 5-0 win in the last-16 stage.'," +
                "240, 201, '41.161758', '41.161758 -8.583933')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", password);
        contentValues.put("type", 0);
        contentValues.put("notifications", 1);
        long res = db.insert("registeruser", null, contentValues);
        db.close();
        return res;
    }

    public boolean checkUser(String username, String password){
        String[] columns = { COL_2 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return true;
        else
            return false;
    }

    public boolean notificationsOn(String username){
        SQLiteDatabase db = getReadableDatabase();
        String select = "Select " + COL_4 + " from " + TABLE_NAME + " where " + COL_2 + "=" + "'" + username + "'"; //Select notifications from registeruser where username = 'value';
        Cursor cursor = db.rawQuery(select, null);

        cursor.moveToFirst();
        boolean result = false;
        if(cursor.getInt(0)!=0){
            result = true;
        }

        cursor.close();
        db.close();

        return result;
    }

    public boolean changeNotificationValue(boolean notifications, String username){
        SQLiteDatabase db = getReadableDatabase();
        int ntf;
        if(notifications) ntf = 1;
        else ntf = 0;

        String update = "Update " + TABLE_NAME + " SET " + COL_4 + "=" + ntf + " where " + COL_2 + "=" + "'" + username + "'"; //Update registeruser set notifications = n_value where username = 'u_value';
        db.execSQL(update);

//        String select = "Select " + COL_4 + " from " + TABLE_NAME + " where " + COL_2 + "=" + "'" + username + "'"; //Select notifications from registeruser where username = 'value';
//        Cursor cursor = db.rawQuery(select, null);
//        cursor.moveToFirst();
//        System.out.println("NOTIFICATIONS ARE NOW::::::::::" + cursor.getInt(0));

//        cursor.close();
        db.close();

        return true;
    }

    public String[] getArticleTitle (){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT title FROM newsarticle ORDER BY id ASC;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        System.out.println("AAAAAAAAAAAAA TESTING AAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(cursor.getString(0));


        return null;
    }
}
