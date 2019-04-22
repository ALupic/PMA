package com.example.news24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
        db.execSQL("CREATE TABLE newsarticle (id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT, title TEXT, image TEXT, content TEXT, likes INTEGER, dislikes INTEGER, lat FLOAT(4,8), long FLOAT(4,8))");
        db.execSQL("CREATE TABLE comment (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, time DATETIME, likes INTEGER, dislikes INTEGER, user_id TEXT, article_id INTEGER, FOREIGN KEY(user_id) REFERENCES registeruser(username), FOREIGN KEY(article_id) REFERENCES newsarticle(id))");

        //INSERT ADMIN USER
        db.execSQL("INSERT INTO registeruser VALUES ('admin','admin', 1, 0)");

        //INSERT OTHER USERS
        db.execSQL("INSERT INTO registeruser VALUES ('marko', 'marko', 0, 1)");
        db.execSQL("INSERT INTO registeruser VALUES ('stefan', 'stefan', 0, 1)");
        db.execSQL("INSERT INTO registeruser VALUES ('leksa', 'leksa', 0, 1)");
        db.execSQL("INSERT INTO registeruser VALUES ('isidora', 'isidora', 0, 1)");

        //INSERT NEWS ARTICLES
        db.execSQL("INSERT INTO newsarticle VALUES(1, 'Politics', 'Brexit: Donald Tusk suggests \"flexible\" delay of up to a year', " +
                "'article1', " +
                "'European Council president Donald Tusk says the EU should consider offering the UK a \"flexible\" delay to Brexit of up to a year, with the option of leaving earlier if a deal is ratified.'," +
                "250, 2450,-34, 151)");

        db.execSQL("INSERT INTO newsarticle VALUES(2, 'Sport', 'Liverpool take control against Porto', " +
                "'article2', " +
                "'Liverpool cruise to victory over Porto after Naby Keïtas flying start. Liverpool controlled much of the quarter-final first leg with Naby Keïta and Roberto Firmino on the scoresheet in a dominant first-half display that threatened a repeat of last seasons 5-0 win in the last-16 stage.'," +
                "240, 201, 34, 151)");

        //INSERT COMMENTS
        db.execSQL("INSERT INTO comment VALUES (1, 'People feel betrayed!!!', '15/4/2019 17:04', 139, 52, 'marko', 1)");
        db.execSQL("INSERT INTO comment VALUES (2, 'I voted to leave! Why are politicians not fulfilling the will of the people!?', '15/4/2019 15:24', 112, 32, 'stefan', 1)");
        db.execSQL("INSERT INTO comment VALUES (3, 'I voted to remain, but this is just ridiculous. We live in a democratic society.', '15/4/2019 16:02', 187, 112, 'leksa', 1)");
        db.execSQL("INSERT INTO comment VALUES (4, 'Ha! Get rekt Leavers! Were never getting out and  thats a good thing!', '15/4/2019 16:32', 97, 318, 'isidora', 1)");

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

    public ArrayList<NewsArticle> getNewsArticles(){

        ArrayList<NewsArticle> articles = new ArrayList<NewsArticle>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM newsarticle ORDER BY id ASC;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        for(int i=0;i<cursor.getCount(); i++){
            NewsArticle newsArticle = new NewsArticle(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getFloat(7), cursor.getFloat(8));

            articles.add(newsArticle);
            System.out.println("TRENUTNI NEWS ARTICLE JE:::::" + newsArticle.getTitle());
            cursor.moveToNext();
        }
//        System.out.println(cursor.getString(0));
//        cursor.moveToNext();
//        System.out.println(cursor.getString(0));

        cursor.close();
        db.close();


        return articles;
    }

    public NewsArticle findNewsArticleById(int id){

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM newsarticle WHERE id = " + id + " ;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        NewsArticle newsArticle = new NewsArticle(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getFloat(7), cursor.getFloat(8));

        cursor.close();
        db.close();

        return newsArticle;
    }

    public ArrayList<Comment> findCommentsByArticleId(int id){
        ArrayList<Comment> comments = new ArrayList<Comment>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM comment WHERE article_id = " + id + " ORDER BY time DESC";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        for(int i=0; i<cursor.getCount(); i++){
            Comment comment = new Comment(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                    cursor.getInt(4), cursor.getString(5), cursor.getInt(6));
            comments.add(comment);
            //System.out.println("DATE JE :::::::::::::: " + cursor.getString(2));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return comments;
    }

    public RegisteredUser findUserByUsername(String username){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM registeruser WHERE username = " + username;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        RegisteredUser user = new RegisteredUser(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        return  user;
    }

    public void addComment(Comment comment){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", comment.getContent());
        contentValues.put("time", comment.getTime());
        contentValues.put("likes", comment.getLikes());
        contentValues.put("dislikes", comment.getDislikes());
        contentValues.put("user_id", comment.getUser_id());
        contentValues.put("article_id", comment.getArticle_id());

        long res = db.insert("comment", null, contentValues);

        db.close();
        //id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, time DATETIME, likes INTEGER, dislikes INTEGER, user_id TEXT, article_id INTEGER
    }

    public NewsArticle likeNewsArticleById(int id, int nbrLike){

        SQLiteDatabase db = getReadableDatabase();

        String update = "Update newsarticle SET likes =" + nbrLike + " WHERE id = " + id + " ;"; //Update registeruser set notifications = n_value where username = 'u_value';
        db.execSQL(update);

        String query = "SELECT * FROM newsarticle WHERE id = " + id + " ;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        NewsArticle newsArticle = new NewsArticle(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getFloat(7), cursor.getFloat(8));

        cursor.close();
        db.close();
        return newsArticle;
    }


    public NewsArticle dislikeNewsArticleById(int id, int nbrDisike){

        SQLiteDatabase db = getReadableDatabase();

        String update = "Update newsarticle SET dislikes =" + nbrDisike + " WHERE id = " + id + " ;"; //Update registeruser set notifications = n_value where username = 'u_value';
        db.execSQL(update);

        String query = "SELECT * FROM newsarticle WHERE id = " + id + " ;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        NewsArticle newsArticle = new NewsArticle(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getFloat(7), cursor.getFloat(8));

        cursor.close();
        db.close();

        return newsArticle;
    }


    public Comment findCommentById(int id){

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM comment WHERE id = " + id + " ;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        Comment comment = new Comment(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                cursor.getInt(4), cursor.getString(5), cursor.getInt(6));
        cursor.close();
        db.close();

        return comment;
    }

    public Comment likeCommentById(int id, int nbrLike){

        SQLiteDatabase db = getReadableDatabase();

        String update = "Update comment SET likes =" + nbrLike + " WHERE id = " + id + " ;"; //Update registeruser set notifications = n_value where username = 'u_value';
        db.execSQL(update);

        String query = "SELECT * FROM comment WHERE id = " + id + " ;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        Comment comment = new Comment(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                cursor.getInt(4), cursor.getString(5), cursor.getInt(6));

        cursor.close();
        db.close();
        return comment;
    }


    public Comment dislikeCommentById(int id, int nbrDisike){

        SQLiteDatabase db = getReadableDatabase();

        String update = "Update comment SET dislikes =" + nbrDisike + " WHERE id = " + id + " ;"; //Update registeruser set notifications = n_value where username = 'u_value';
        db.execSQL(update);

        String query = "SELECT * FROM comment WHERE id = " + id + " ;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        Comment comment = new Comment(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                cursor.getInt(4), cursor.getString(5), cursor.getInt(6));

        cursor.close();
        db.close();

        return comment;
    }


}
