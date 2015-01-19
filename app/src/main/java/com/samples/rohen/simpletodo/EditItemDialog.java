package com.samples.rohen.simpletodo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class EditItemDialog extends DialogFragment {
    private EditText itemNameField;
    private EditText itemPriorityField;
    private EditText itemDueDateField;
    private int itemId;

    public EditItemDialog() {
        // Empty constructor required for DialogFragment
    }

    public interface EditItemDialogListener {
        void onFinishEditDialog(int itemId, String itemName, int itemPriority, String itemDueDate);
    }

    public static EditItemDialog newInstance(TodoItem todoItem) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("item_name", todoItem.getName());
        args.putInt("item_id", todoItem.getId());
        args.putInt("item_priority", todoItem.getPriority());
        args.putString("item_due_date", todoItem.getDateDue());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container);
        itemNameField = (EditText) view.findViewById(R.id.itemNameField);
        itemPriorityField = (EditText) view.findViewById(R.id.itemPriorityField);
        itemDueDateField = (EditText) view.findViewById(R.id.itemDueDateField);
        getDialog().setTitle("Edit ToDo Item");

        // Show soft keyboard automatically
        itemNameField.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        String itemName = getArguments().getString("item_name");
        int itemPriority = getArguments().getInt("item_priority");
        String itemDueDate = getArguments().getString("item_due_date");
        itemId = getArguments().getInt("item_id", 0);
        itemNameField.setText(itemName);
        itemPriorityField.setText(String.valueOf(itemPriority));
        itemDueDateField.setText(itemDueDate);

        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int itemPriority = Integer.valueOf(itemPriorityField.getText().toString());
                        String itemDueDate = itemDueDateField.getText().toString();
                        String itemName = itemNameField.getText().toString();
                        EditItemDialogListener listener = (EditItemDialogListener) getActivity();
                        listener.onFinishEditDialog(itemId, itemName, itemPriority, itemDueDate);
                        dismiss();
                    }
                }
        );
        return view;
    }
}