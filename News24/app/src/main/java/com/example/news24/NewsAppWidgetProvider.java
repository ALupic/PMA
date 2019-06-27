package com.example.news24;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class NewsAppWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_TOAST = "actionToast";
    public static final String EXTRA_ITEM_POSITION = "extraItemPosition";
    DatabaseHelper db;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent buttonIntent = new Intent(context, MainActivity.class);
            PendingIntent buttonPendingIntent = PendingIntent.getActivity(context, 0, buttonIntent, 0);


            //novo1 za prikaz blokova
            Intent serviceIntent = new Intent(context, NewsWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
            //novo1

            //n3za listenner na clanak
            Intent clickIntent = new Intent(context, NewsAppWidgetProvider.class);
            clickIntent.setAction(ACTION_TOAST);
            PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,0,clickIntent,0);


            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_widget);
            views.setOnClickPendingIntent(R.id.news_widget_button, buttonPendingIntent);
            //n1
            views.setRemoteAdapter(R.id.news_widget_stack_view,serviceIntent);
            views.setEmptyView(R.id.news_widget_stack_view,R.id.news_widget_empty_view);

            //n3
            views.setPendingIntentTemplate(R.id.news_widget_stack_view,clickPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent){
        if(ACTION_TOAST.equals(intent.getAction())){
            db = new DatabaseHelper(context);
            int clickedPosition = intent.getIntExtra(EXTRA_ITEM_POSITION,0);
            int articleId = intent.getIntExtra("articleId",0);


            Intent showArticleActivity = new Intent(context, ArticleActivity.class);
            NewsArticle newsArticle = db.findNewsArticleById(articleId);
            showArticleActivity.putExtra("newsArticle", newsArticle);
            showArticleActivity.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(showArticleActivity);




           // Toast.makeText(context, "positoin " + clickedPosition, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context,intent);

    }
}