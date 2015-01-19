package com.samples.rohen.simpletodo;

import android.database.sqlite.SQLiteConstraintException;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends ActionBarActivity implements
        EditItemDialog.EditItemDialogListener{
    ArrayList<TodoItem> todoItems;
    ItemArrayAdapter todoItemsAdapter;
    ListView lvItems;
    TodoItemDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new TodoItemDatabase(this);
        lvItems = (ListView) findViewById(R.id.lvItems);
        todoItems = new ArrayList<TodoItem>();
        updateItems();
        todoItemsAdapter = new ItemArrayAdapter(this, todoItems);
        lvItems.setAdapter(todoItemsAdapter);
        setupListViewListener();
    }

    @Override
    public void onFinishEditDialog(int itemId, String itemName, int itemPriority,
                                   String itemDueDate) {
        TodoItem todoItem = (TodoItem) db.getTodoItem(itemId);
        todoItem.setName(itemName);
        todoItem.setPriority(itemPriority);

        if (itemDueDate != null && !itemDueDate.equals("")) {
            try {
                todoItem.setDateDue(itemDueDate);
            } catch (Exception ex) {
                showAlertDialog("Invalid date", "Date should be formatted 'DD/MM/YY'.");
                return;
            }

            Date date = new Date();
            int now = (int) (date.getTime() / 1000L);

            if(todoItem.getTimeDue() <= now && todoItem.getTimeDue() > 0) {
                todoItem.setPriority(0);
            }
        } else {
            todoItem.setTimeDue(-1);
        }

        if(todoItem.getPriority() > 9 || todoItem.getPriority() < 0) {
            showAlertDialog("Invalid priority",
                    "Try using a priority between 0 and 9.");
        } else {
            try {
                db.updateTodoItem(todoItem);
                updateItems();
                todoItemsAdapter.notifyDataSetChanged();
            } catch (SQLiteConstraintException ex) {
                showAlertDialog("Invalid todo name",
                        "Try using a todo name that hasn't been taken.");
            }
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                db.deleteTodoItem(todoItems.get(pos));
                updateItems();
                todoItemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                String itemName = todoItems.get(pos).getName();
                TodoItem todoItem = (TodoItem) db.getTodoItemByName(itemName);

                showEditItemDialog(todoItem);
            }

        });
    }

    private void showEditItemDialog(TodoItem todoItem) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editItemDialog = EditItemDialog.newInstance(todoItem);
        editItemDialog.show(fm, "fragment_edit_item");
    }

    private void showAlertDialog(String titleText, String messageText) {
        FragmentManager fm = getSupportFragmentManager();
        AlertDialog alertDialog = AlertDialog.newInstance(titleText, messageText);
        alertDialog.show(fm, "fragment_alert");
    }

    private void updateItems() {
        List<TodoItem> newTodoItems = db.getAllTodoItems();
        ListIterator itr = newTodoItems.listIterator();
        TodoItem currentItem;

        todoItems.clear();

        Date date = new Date();
        long now = date.getTime() / 1000L;

        while(itr.hasNext()) {
            currentItem = (TodoItem) itr.next();

            if(currentItem.getTimeDue() <= now && currentItem.getTimeDue() > 0) {
                currentItem.setPriority(0);
            }

            db.updateTodoItem(currentItem);
        }

        newTodoItems = db.getAllTodoItems();
        itr = newTodoItems.listIterator();

        while(itr.hasNext()) {
            currentItem = (TodoItem) itr.next();
            todoItems.add(currentItem);
        }
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemName = etNewItem.getText().toString();

        TodoItem todoItem = new TodoItem(itemName, 0, -1);
        try {
            db.addTodoItem(todoItem);
            updateItems();
            todoItemsAdapter.notifyDataSetChanged();
            etNewItem.setText("");
        } catch (SQLiteConstraintException e) {
            showAlertDialog("Invalid todo name", "Try using a todo name that hasn't been taken.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
