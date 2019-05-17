package com.example.news24;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class NewsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NewsWidgetItemFactory(getApplicationContext(), intent);
    }

    class NewsWidgetItemFactory implements  RemoteViewsFactory{

        private Context context;
        private int appWidgetId;

        //Proslediti podatke iz baze podataka
        private String[] exampleData= {"one", "two", "three"};


        NewsWidgetItemFactory(Context context,Intent intent){
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
        //connect with data source
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
        //close data connection source
        }

        @Override
        public int getCount() {
            return exampleData.length;// array list size ili sta vec imamo
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.news_widget_item);
            views.setTextViewText(R.id.titleWidgetTextView,exampleData[position]);// ubaciti iz baze
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
            return 0; // ako zelis posle identifikovati svaki objekat u zavisnosti od pozicije ovo koristi
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
