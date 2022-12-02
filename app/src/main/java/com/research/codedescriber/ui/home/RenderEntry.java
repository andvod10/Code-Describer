package com.research.codedescriber.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import com.research.codedescriber.R;
import com.research.codedescriber.data.db.DBHandler;
import com.research.codedescriber.data.entity.SearchTag;
import com.research.codedescriber.data.entity.SingleEntry;

import java.util.Comparator;
import java.util.List;

public class RenderEntry {
    private Context context;
    private DBHandler dbHandler;
    private View root;

    public RenderEntry(Context context, View root) {
        this.dbHandler = new DBHandler(context);
        this.context = context;
        this.root = root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void renderEntries(String searchText, View.OnLongClickListener listener) {
        // validating if the text fields are empty or not.
        if (searchText.isEmpty()) {
            Toast.makeText(context, "Please enter all the data..", Toast.LENGTH_SHORT).show();
            return;
        }
        // on below line we are calling a method to add new
        // course to sqlite data and pass all our values to it.
        List<SingleEntry> matchedEntries = dbHandler.retrieveEntryByCodeOrDescription(searchText);
        matchedEntries.sort(Comparator.comparing(SingleEntry::getCode).reversed());

        LinearLayout linearLayoutRecords = root.findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        if (matchedEntries.size() > 0) {
            for (SingleEntry entry : matchedEntries) {
                int entryId = entry.getId();
                String textViewContents = entry.toString();
                TextView textViewEntryItem= new TextView(context);
                textViewEntryItem.setPadding(0, 10, 0, 10);
                textViewEntryItem.setTextSize(25);
                textViewEntryItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textViewEntryItem.setTextColor(ContextCompat.getColor(context, R.color.purple_500));
                textViewEntryItem.setText(textViewContents);
                textViewEntryItem.setTag(new SearchTag(entryId, searchText));
                textViewEntryItem.setOnLongClickListener(listener);
                linearLayoutRecords.addView(textViewEntryItem);
            }
        }
        else {
            TextView locationItem = new TextView(context);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");
            locationItem.setTextSize(25);
            locationItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            locationItem.setTextColor(ContextCompat.getColor(context, R.color.purple_500));
            linearLayoutRecords.addView(locationItem);
        }
    }
}
