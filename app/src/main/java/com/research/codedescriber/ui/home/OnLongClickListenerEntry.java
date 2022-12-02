package com.research.codedescriber.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.research.codedescriber.R;
import com.research.codedescriber.data.db.DBHandler;
import com.research.codedescriber.data.entity.SearchTag;
import com.research.codedescriber.data.entity.SingleEntry;

public class OnLongClickListenerEntry implements View.OnLongClickListener {
    private EditText codeEdt, descriptionEdt;
    private final View root;

    public OnLongClickListenerEntry(View root) {
        this.root = root;
    }

    @Override
    public boolean onLongClick(View view) {
        Context context = root.getRootView().getContext();
        SearchTag searchEntryTag = (SearchTag)view.getTag();
        String entryId = Integer.toString(searchEntryTag.getIdOfSearchedEntry());
        String searchData = searchEntryTag.getSearchData();
        final CharSequence[] items = { "Edit", "Delete" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Modify Entry");
        builder.setItems(items, (dialog, item) -> {
            if (item == 0) {
                editRecord(context, Integer.parseInt(entryId), searchData);
            }
            else if (item == 1) {
                deleteRecord(context, Integer.parseInt(entryId), searchData);
            }
        }).show();
        return false;
    }

    private void deleteRecord(Context context, int entryId, String searchData) {
        final DBHandler dbHandler = new DBHandler(context);
        boolean deleteSuccessful = dbHandler.delete(entryId);
        if (deleteSuccessful) {
            Toast.makeText(context, "Record was deleted.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Unable to delete record.", Toast.LENGTH_SHORT).show();
        }
        renderAfterModify(context, searchData);
    }

    private void editRecord(Context context, final int entryId, String searchData) {
        // creating a new dbhandler class
        // and passing our context to it.
        final DBHandler dbHandler = new DBHandler(context);
        SingleEntry singleRecord = dbHandler.readSingleRecord(entryId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.entry_input_form, null, false);

        codeEdt = formElementsView.findViewById(R.id.idEdtCode);
        descriptionEdt = formElementsView.findViewById(R.id.idEdtDescription);

        codeEdt.setText(singleRecord.getCode());
        descriptionEdt.setText(singleRecord.getDescription());

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        (dialog, id) -> {
                            SingleEntry entry = new SingleEntry();
                            entry.setId(entryId);
                            entry.setCode(codeEdt.getText().toString());
                            entry.setDescription(descriptionEdt.getText().toString());

                            boolean updateSuccessful = dbHandler.update(entry);
                            if(updateSuccessful){
                                Toast.makeText(context, "Record was updated.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to update record.", Toast.LENGTH_SHORT).show();
                            }
                            renderAfterModify(context, searchData);
                        }).show();
    }

    private void renderAfterModify(Context context, String searchData) {
        new RenderEntry(context, root).renderEntries(searchData, this);
    }
}
