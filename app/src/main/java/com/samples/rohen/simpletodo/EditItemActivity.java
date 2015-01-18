package com.samples.rohen.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {
    EditText newNameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String itemName = getIntent().getStringExtra("item_name");
        final int itemPos = getIntent().getIntExtra("item_pos", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        newNameField = (EditText) findViewById(R.id.editText);
        newNameField.setText(itemName);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newName = newNameField.getText().toString();

                    Intent data = new Intent();
                    data.putExtra("item_name", newName);
                    data.putExtra("item_pos", itemPos);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
