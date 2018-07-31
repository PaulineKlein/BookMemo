package com.pklein.bookmemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;

import com.pklein.bookmemo.data.Book;
import com.pklein.bookmemo.data.BookContract;
import com.pklein.bookmemo.tools.BookDbTool;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BookWidget extends AppWidgetProvider {

    private static final String TAG = BookWidget.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Log.i(TAG, "BEGIN updateAppWidget : ");

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.book_widget);
        views.setOnClickPendingIntent(R.id.content_widget, pendingIntent);

        //Get the Data :
        BookDbTool bookDbTool = new BookDbTool();
        String subquery = BookContract.BookDb.COLUMN_FAVORITE + "= 1";
        List<Book> listOfBooks = bookDbTool.getSelectedBookfromDatabase(subquery, context.getContentResolver());
        String content = "";
        for(int i = 0; i < listOfBooks.size(); i++){
            content = content+"<BR/>"+listOfBooks.get(i).getTitle();
            content = content+"<BR/> T"+listOfBooks.get(i).getTome()+" , chap."+listOfBooks.get(i).getChapter()+" , ep."+listOfBooks.get(i).getEpisode();
            content = content+"<BR/>";
        }
        views.setTextViewText(R.id.content_widget, Html.fromHtml(content));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

