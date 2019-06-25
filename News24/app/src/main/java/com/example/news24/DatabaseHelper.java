package com.example.news24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        db.execSQL("CREATE TABLE favorites(id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT, article_id INTEGER, FOREIGN KEY(user_id) REFERENCES registeruser(username), FOREIGN KEY(article_id) REFERENCES newsarticle(id))");
        //db.execSQL("CREATE TABLE category (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, article_id INTEGER, FOREIGN KEY(article_id) REFERENCES newsarticle(id))");
        db.execSQL("CREATE TABLE category (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, selected INTEGER)");


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
                "250, 2450, 50.8143, 4.4122)");

        db.execSQL("INSERT INTO newsarticle VALUES(2, 'Sport', 'Liverpool take control against Porto', " +
                "'article2', " +
                "'Liverpool cruise to victory over Porto after Naby Keïtas flying start. Liverpool controlled much of the quarter-final first leg with Naby Keïta and Roberto Firmino on the scoresheet in a dominant first-half display that threatened a repeat of last seasons 5-0 win in the last-16 stage.'," +
                "240, 201, 41.16173, -8.58360)");

        db.execSQL("INSERT INTO newsarticle VALUES(3, 'Travel', 'We went troll hunting in Iceland', " +
                "'article3', " +
                "'The majority of Icelanders believe in, or at least refuse to the deny the existence of elves, trolls, and other hidden beings. Cut off from the rest of the world for centuries, Icelanders developed a rich storytelling tradition and stories about elves and hidden people are still part of their heritage today.'," +
                "90, 17, 64.9631, -19.0208)");

        db.execSQL("INSERT INTO newsarticle VALUES(4, 'Politics', 'Donald Trump''s state visit to the UK set for 3 June', " +
                "'article4', " +
                "'US President Donald Trump will make a three-day state visit to the UK from 3 to 5 June, Buckingham Palace has announced.',"+
                "540, 874, 38.8977, -77.0365)");

        db.execSQL("INSERT INTO newsarticle VALUES(5, 'Entertainment', 'Britney Spears tells fans ''all is well'' after #FreeBritney campaign', " +
                "'article5', " +
                "'Britney Spears has reassured fans over her wellbeing following internet speculation about her safety.\n" +
                "\n" +
                "The pop star put her career on hold in January and reportedly sought mental health treatment last month.'," +
                "418, 373, 34.07362, -118.40036)");

        db.execSQL("INSERT INTO newsarticle VALUES(6, 'Technology', 'What happens when AI meets robotics?', " +
                "'article6', " +
                "'Researchers in Texas are developing robots that have minds of their own.\n" +
                "\n" +
                "The scientists are creating systems that can learn for themselves and be able to operate in the home, the workplace and even on the sports field.'," +
                "378, 67, 30.2849, -97.7341)");

        db.execSQL("INSERT INTO newsarticle VALUES(7, 'Politics', 'Angela Merkel to quit as CDU party leader', " +
                "'article7', " +
                "'German Chancellor Angela Merkel has confirmed she will not run for re-election as its chairwoman in December.\n" +
                "\n" +
                "Her decision comes after her party suffered heavy losses in regional elections that threatened the stability of the governing coalition.'," +
                "420, 467, 52.5202, 13.3730)");

        db.execSQL("INSERT INTO newsarticle VALUES(8, 'Travel', 'The temples that had to be moved', " +
                "'article8', " +
                "'If Abu Simbel had not been saved, places like Vienna''s Historic Centre, Cambodia''s Angkor Wat and other Unesco World Heritage sites might only live on in history books.'," +
                "140, 7, 22.3372, 31.6258)");

        db.execSQL("INSERT INTO newsarticle VALUES(9, 'Business', 'US Treasury Secretary calls big US banks after huge stock market falls', " +
                "'article9', " +
                "'US Treasury Secretary Steven Mnuchin has made calls to the heads of the country''s six largest banks, in an unusual move aimed at reassuring investors after big falls in US stocks.'," +
                "276, 67, 38.5354, -77.2300)");

        db.execSQL("INSERT INTO newsarticle VALUES(10, 'Sport', 'Novak Djokovic''s seven Melbourne titles in pictures', " +
                "'article10', " +
                "'After Novak Djokovic claims a historic seventh title at the Australian Open, News takes a look at his successes over the years.\n" +
                "\n" +
                "The Serb''s 6-3 6-2 6-3 victory over great rival Rafael Nadal in Sunday''s Melbourne showpiece took him clear of six-time men''s winners Roy Emerson and Roger Federer.'," +
                "674, 197, -37.8227, 144.9801)");

        db.execSQL("INSERT INTO newsarticle VALUES(11, 'Sport', 'Irish Open: Ryder Cup star Ian Poulter to compete at Lahinch', " +
                "'article11', " +
                "'Ian Poulter is the latest high-profile European Tour player to commit to taking part in the 2019 Irish Open.\n" +
                "\n" +
                "Poulter, 43, will join major winners Louis Oosthuizen, Danny Willett and Padraig Harrington at Lahinch in July.'," +
                "72, 16, 55.2784, -7.3942)");

        db.execSQL("INSERT INTO newsarticle VALUES(12, 'Technology', 'Data on 540 million Facebook users exposed', " +
                "'article12', " +
                "'Detailed information about more than 540 million Facebook users was left publicly viewable for months, a security firm has found.'," +
                "157, 244, 37.4848, -122.1484)");

        db.execSQL("INSERT INTO newsarticle VALUES(13, 'Travel', 'Can this ''lost'' tribe save the world?', " +
                "'article13', " +
                "'The hidden people of the Sierra Nevada de Santa Marta mountains have emerged from centuries of isolation to help save the world from climate change.'," +
                "174, 21, 10.8292, -73.6923)");

        db.execSQL("INSERT INTO newsarticle VALUES(14, 'Entertainment', 'Oscars organisers decide against rule changes to restrict streaming films', " +
                "'article14', " +
                "'Film streaming services could see continued awards success after the body behind the Oscars voted down calls to tighten its submission process.\n" +
                "\n" +
                "Figures including Steven Spielberg have said films that are given only brief cinema runs shouldn''t be nominated.'," +
                "271, 47, 34.1027, -118.3404)");

        db.execSQL("INSERT INTO newsarticle VALUES(15, 'Technology', 'What happens when AI meets robotics?', " +
                "'article15', " +
                "'Researchers in Texas are developing robots that have minds of their own.\n" +
                "\n" +
                "The scientists are creating systems that can learn for themselves and be able to operate in the home, the workplace and even on the sports field.'," +
                "378, 67, 31.96860, -99.90181)");

        db.execSQL("INSERT INTO newsarticle VALUES(16, 'Business', 'Germany''s economy: Should we be worried?', " +
                "'article16', " +
                "'There''s no question that the German economy has hit a difficult patch. The widest measure of economic activity, gross domestic product (GDP), declined in the third quarter of last year by 0.2% and failed to grow in the following three months.'," +
                "140, 29, 52.52001, 13.40495)");

        //INSERT COMMENTS
        db.execSQL("INSERT INTO comment VALUES (1, 'People feel betrayed!!!', '15/4/2019 17:04', 139, 52, 'marko', 1)");
        db.execSQL("INSERT INTO comment VALUES (2, 'I voted to leave! Why are politicians not fulfilling the will of the people!?', '15/4/2019 15:24', 112, 32, 'stefan', 1)");
        db.execSQL("INSERT INTO comment VALUES (3, 'I voted to remain, but this is just ridiculous. We live in a democratic society.', '15/4/2019 16:02', 187, 112, 'leksa', 1)");
        db.execSQL("INSERT INTO comment VALUES (4, 'Ha! Get rekt Leavers! Were never getting out and  thats a good thing!', '15/4/2019 16:32', 97, 318, 'isidora', 1)");

        //INSERT FAVORITES
        db.execSQL("INSERT INTO favorites VALUES (1,'marko', 1 )");
        db.execSQL("INSERT INTO favorites VALUES (3,'leksa', 1 )");
        db.execSQL("INSERT INTO favorites VALUES (4,'leksa', 2 )");
        db.execSQL("INSERT INTO favorites VALUES (5,'isidora', 2 )");


        //INSERT CATEGORY za sada se ne koristi
        db.execSQL("INSERT INTO category VALUES (1,'Home', 1)");
        db.execSQL("INSERT INTO category VALUES (2,'Sport', 0)");
        db.execSQL("INSERT INTO category VALUES (3,'Politics', 0)");
        db.execSQL("INSERT INTO category VALUES (4,'Travel', 0)");
        db.execSQL("INSERT INTO category VALUES (5,'Technology', 0 )");
        db.execSQL("INSERT INTO category VALUES (6,'Entertainment', 0 )");
        db.execSQL("INSERT INTO category VALUES (7,'Business', 0 )");





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

    public ArrayList<Favorites> getAllFavorites(){

        ArrayList<Favorites> favorites = new ArrayList<Favorites>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM favorites ORDER BY id ASC;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        for(int i=0;i<cursor.getCount(); i++){
            Favorites favorite = new Favorites(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            favorites.add(favorite);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return favorites;
    }

    public void addFavorites(int articleId, String username ){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("user_id", username);
        contentValues.put("article_id", articleId);

        long res = db.insert("favorites", null, contentValues);

        db.close();
        //id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, time DATETIME, likes INTEGER, dislikes INTEGER, user_id TEXT, article_id INTEGER
    }
    public void deleteFavorites(int articleId, String username ){

        SQLiteDatabase db = getReadableDatabase();
        db.delete("favorites","user_id=? and article_id=?",new String[]{username,""+articleId+""});
        db.close();
    }

//    public void deleteFavorites(int articleId, String username ){
//
//        SQLiteDatabase db = getReadableDatabase();
//        String query = "DELETE FROM favorites WHERE article_id = " + articleId + " AND user_id = " + username +"  ;";
//        Cursor cursor = db.rawQuery(query, null);
//       // cursor.moveToFirst();
//
//        //Favorites favorites = new Favorites(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
//
//        cursor.close();
//        db.close();
//   }



    public ArrayList<Category> getCategories(){

        ArrayList<Category> categories = new ArrayList<Category>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM category ORDER BY id ASC;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        for(int i=0;i<cursor.getCount(); i++){
            Category category = new Category(cursor.getInt(0), cursor.getString(1),cursor.getInt(2));

            categories.add(category);
            //System.out.println("Categorty ->" + category.getTitle());
            cursor.moveToNext();
        }
//        System.out.println(cursor.getString(0));
//        cursor.moveToNext();
//        System.out.println(cursor.getString(0));

        cursor.close();
        db.close();


        return categories;
    }

    public Category findCategoryById(int id){

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM category WHERE id = " + id + " ;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        Category category = new Category(cursor.getInt(0), cursor.getString(1),cursor.getInt(2));

        cursor.close();
        db.close();

        return category;
    }

    public void unselectAll(){
        SQLiteDatabase db = getReadableDatabase();
        String update = "Update category SET selected =" + 0 + " ;";
        db.execSQL(update);

        db.close();
    }

    public Category selectCategory(int id){
        unselectAll();
        SQLiteDatabase db = getReadableDatabase();

        String update = "Update category SET selected =" + 1 + " WHERE id = " + id + " ;";
        db.execSQL(update);

        String query = "SELECT * FROM category WHERE id = " + id + " ;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        Category category = new Category(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));

        cursor.close();
        db.close();
        return category;
    }

    public Category getSelectedCategory(){

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM category WHERE selected = " + 1 + " ;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        Category category = new Category(cursor.getInt(0), cursor.getString(1),cursor.getInt(2));

        cursor.close();
        db.close();

        return category;
    }


    public ArrayList<NewsArticle> getNewsArticlesByCategory(String cat){

        ArrayList<NewsArticle> articles = new ArrayList<NewsArticle>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM newsarticle WHERE category = '" + cat + "' ORDER BY id ASC ;";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        for(int i=0;i<cursor.getCount(); i++){
            NewsArticle newsArticle = new NewsArticle(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getFloat(7), cursor.getFloat(8));

            articles.add(newsArticle);
            System.out.println("Odabrani po kategoriji ->" + newsArticle.getTitle());
            cursor.moveToNext();
        }

        cursor.close();
        db.close();


        return articles;
    }

}
