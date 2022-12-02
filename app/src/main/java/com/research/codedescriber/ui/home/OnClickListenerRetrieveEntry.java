package com.research.codedescriber.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.research.codedescriber.R;

public class OnClickListenerRetrieveEntry implements View.OnClickListener {
    View root;
    View.OnLongClickListener listener;

    public OnClickListenerRetrieveEntry(View view, View.OnLongClickListener listener) {
        this.root = view;
        this.listener = listener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        Context context = root.getRootView().getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formSearchView = inflater.inflate(R.layout.entry_search_form, null, false);

        new AlertDialog.Builder(context)
                .setTitle("Find Entry")
                .setView(formSearchView)
                .setCancelable(true)
                .setPositiveButton("Search", (dialog, id) -> {
                    // below line is to get data from all edit text fields.
                    EditText searchField = formSearchView.findViewById(R.id.idEdtSearchField);
                    String searchText = searchField.getText().toString();
                    new RenderEntry(context, root).renderEntries(searchText, listener);
                }).show();
    }
}
