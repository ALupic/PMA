package com.example.news24;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NewsWidgetItemFactory(getApplicationContext(), intent);
    }

    class NewsWidgetItemFactory implements  RemoteViewsFactory{


        private Context context;
        private int appWidgetId;
        DatabaseHelper db;
        private ArrayList<NewsArticle> newsArticles = new ArrayList<NewsArticle>();
        Resources resources;
        int[] imageIds;


        //Proslediti podatke iz baze podataka
        private String[] exampleData= {"one", "two", "three"};


        NewsWidgetItemFactory(Context context,Intent intent){
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
        //connect to data source
          //  SystemClock.sleep(3000);

            db = new DatabaseHelper(context);

        }

        @Override
        public void onDataSetChanged() {

//            // refresh data
//            Date date = new Date();
//            String timeFormatted = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
//            exampleData = new String[]{"one\n" + timeFormatted, "two\n" + timeFormatted};
        }

        @Override
        public void onDestroy() {
        //close data connection source
            db.close();
        }

        @Override
        public int getCount() {
            newsArticles =  db.getNewsArticles();
            return newsArticles.size();// array list size ili sta vec imamo
        }

        @Override
        public RemoteViews getViewAt(int position) {//load data from data source

            newsArticles =  db.getNewsArticles();

            String[] articles = new String[newsArticles.size()];
            String[] categories = new String[newsArticles.size()];
            String[] images = new String[newsArticles.size()];

            for(int i = 0; i < newsArticles.size(); i++){
                articles[i] = newsArticles.get(i).getTitle();
                categories[i] = newsArticles.get(i).getCategory();
                images[i] = newsArticles.get(i).getImage();
            }

            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.news_widget_item);
            views.setTextViewText(R.id.titleWidgetTextView,articles[position]);// ubaciti iz baze
            views.setTextViewText(R.id.categoryWidgetTextView,categories[position]);

            imageIds = new int[images.length];
            resources = context.getResources();
            for(int i=0; i<images.length; i++){
                int resourceId = resources.getIdentifier(images[i], "drawable", context.getPackageName());
                imageIds[i] = resourceId;
            }
            views.setImageViewResource(R.id.articleWidgetImageView,imageIds[position]);
            Intent fillIntent = new Intent();
            fillIntent.putExtra(NewsAppWidgetProvider.EXTRA_ITEM_POSITION, position);
            //ovde radimo custom ostalih data kada ubacimo ceo clanak
            views.setOnClickFillInIntent(R.id.widgetRelativeLayout, fillIntent);



            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position; // ako zelis posle identifikovati svaki objekat u zavisnosti od pozicije ovo koristi
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
