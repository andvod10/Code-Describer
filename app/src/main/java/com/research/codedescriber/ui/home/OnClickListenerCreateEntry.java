package com.research.codedescriber.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.research.codedescriber.R;
import com.research.codedescriber.data.db.DBHandler;
import com.research.codedescriber.data.entity.SingleEntry;

public class OnClickListenerCreateEntry implements View.OnClickListener {
    private EditText codeEdt, descriptionEdt;
    private DBHandler dbHandler;
    private final View root;

    public OnClickListenerCreateEntry(View root) {
        this.root = root;
    }

    @Override
    public void onClick(View view) {
        Context context = root.getRootView().getContext();
        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.entry_input_form, null, false);
        new AlertDialog.Builder(context)
                .setTitle("Create Entry")
                .setView(formElementsView)
                .setCancelable(true)
                .setPositiveButton("Add", (dialog, id) -> {
                    // initializing all our variables.
                    codeEdt = formElementsView.findViewById(R.id.idEdtCode);
                    descriptionEdt = formElementsView.findViewById(R.id.idEdtDescription);

                    // below line is to get data from all edit text fields.
                    String code = codeEdt.getText().toString();
                    String description = descriptionEdt.getText().toString();

                    // validating if the text fields are empty or not.
                    if (code.isEmpty() || description.isEmpty()) {
                        Toast.makeText(context, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SingleEntry entry = new SingleEntry(code, description);

                    // on below line we are calling a method to add new
                    // course to sqlite data and pass all our values to it.
                    dbHandler.addNewEntry(entry);

                    LinearLayout linearLayoutRecords = root.findViewById(R.id.linearLayoutRecords);
                    linearLayoutRecords.removeAllViews();

                    // after adding the data we are displaying a toast message.
                    Toast.makeText(context, "Course has been added.", Toast.LENGTH_SHORT).show();
                    codeEdt.setText("");
                    descriptionEdt.setText("");
                }).show();
    }
}
