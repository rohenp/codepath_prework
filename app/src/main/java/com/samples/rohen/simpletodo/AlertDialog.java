package com.samples.rohen.simpletodo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AlertDialog extends DialogFragment {

    public AlertDialog() {
        // Empty constructor required for DialogFragment
    }

    public static AlertDialog newInstance(String titleText, String messageText) {
        AlertDialog frag = new AlertDialog();
        Bundle args = new Bundle();
        args.putString("message_text", messageText);
        args.putString("title_text", titleText);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert, container);
        TextView messageField = (TextView) view.findViewById(R.id.messageTextField);

        String messageText = getArguments().getString("message_text");
        String titleText = getArguments().getString("title_text");

        getDialog().setTitle(titleText);
        messageField.setText(messageText);

        Button okButton = (Button) view.findViewById(R.id.okButton);
        okButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );

        return view;
    }
}
