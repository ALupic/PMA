package com.example.news24;



import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.news24.MainActivity;
import com.example.news24.R;


public class NewsAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


            //novo1 za prikaz blokova
            Intent serviceIntent = new Intent(context, NewsWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
            //novo1
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_widget);
            views.setOnClickPendingIntent(R.id.news_widget_button, pendingIntent);
            //n1
            views.setRemoteAdapter(R.id.news_widget_stack_view,serviceIntent);
            views.setEmptyView(R.id.news_widget_stack_view,R.id.news_widget_empty_view);
            //n1
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}