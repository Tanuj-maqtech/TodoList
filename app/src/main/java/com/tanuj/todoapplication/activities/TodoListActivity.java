package com.tanuj.todoapplication.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tanuj.todoapplication.Adapters.TodoListAdapter;
import com.tanuj.todoapplication.Database.DataBaseEntry;
import com.tanuj.todoapplication.Database.FeedReaderDbHelper;
import com.tanuj.todoapplication.Model.TodoData;
import com.tanuj.todoapplication.R;
import com.tanuj.todoapplication.databinding.ActivityTodolistBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TodoListActivity extends AppCompatActivity {

    private ActivityTodolistBinding mBinding;

    TodoListAdapter todoListAdapter;
    ArrayList<TodoData> todoDataArrayList;
    ArrayList<TodoData> todoFilterArrayList;
    ArrayList<TodoData> todoDeletedArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_todolist);

        todoDataArrayList =new ArrayList<>();
        todoFilterArrayList=new ArrayList<>();
        setTitle("To Do List");

        init();


    }

    private void init() {



        mBinding.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Intent intent=new Intent(TodoListActivity.this,AddItemActivity.class);
             startActivity(intent);
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TodoListActivity.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.recyclerview.getContext(),
                linearLayoutManager.getOrientation());
        mBinding.recyclerview.setHasFixedSize(true);
        mBinding.recyclerview.setNestedScrollingEnabled(false);
        mBinding.recyclerview.addItemDecoration(dividerItemDecoration);

        mBinding.recyclerview.setLayoutManager(linearLayoutManager);
        todoListAdapter = new TodoListAdapter(TodoListActivity.this, todoFilterArrayList);
        mBinding.recyclerview.setAdapter(todoListAdapter);



        mBinding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterDeviceId(mBinding.editSearch);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //calCulateTotal();
            }
        });
    }


    private boolean validate() {



        return true;
    }





    public void filterDeviceId(EditText deviceID) {
        todoFilterArrayList.clear();
        todoFilterArrayList = (ArrayList<TodoData>) todoDataArrayList.clone();
        todoDeletedArrayList = new ArrayList<>();
        todoDeletedArrayList.clear();
        if (!TextUtils.isEmpty(deviceID.getText().toString())) {
            for (int i = 0; i < todoFilterArrayList.size(); i++) {
                if (todoFilterArrayList.size() > 0) {
                    if (todoFilterArrayList.get(i).getItemName().startsWith(deviceID.getText().toString())) {
                        // studentFilter.add(studentFilter.get(i));
                    } else {
                        todoDeletedArrayList.add(todoFilterArrayList.get(i));
                    }
                }
            }
            for (int i = 0; i < todoDeletedArrayList.size(); i++) {
                for (int j = 0; j < todoFilterArrayList.size(); j++) {
                    if (todoDeletedArrayList.get(i).getItemName().toString().equalsIgnoreCase(todoFilterArrayList.get(j).getItemName().toString())) {
                        todoFilterArrayList.remove(j);
                    }
                }
            }
        }



        todoListAdapter = new TodoListAdapter(TodoListActivity.this, todoFilterArrayList);
        mBinding.recyclerview.setAdapter(todoListAdapter);
        todoListAdapter.notifyDataSetChanged();


    }
private void getAllData(){
    FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(TodoListActivity.this);
    SQLiteDatabase db = dbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
    String[] projection = {
            DataBaseEntry.id,
            DataBaseEntry.title,
            DataBaseEntry.description
    };

// Filter results WHERE "title" = 'My Title'
    String selection = DataBaseEntry.title + " = ?";
    String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
    String sortOrder =
            DataBaseEntry.id + " DESC";

    Cursor cursor = db.query(
            DataBaseEntry.todoList,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
    );
todoDataArrayList = new ArrayList<>();
    while(cursor.moveToNext()) {
        long itemId = cursor.getLong(
                cursor.getColumnIndexOrThrow(DataBaseEntry.id));
        TodoData todoData=new TodoData();
        todoData.setId(itemId);
        todoData.setItemName(cursor.getString(cursor.getColumnIndex(DataBaseEntry.title)));
        todoData.setItemdescription(cursor.getString(cursor.getColumnIndex(DataBaseEntry.description)));
        todoDataArrayList.add(todoData);
        todoFilterArrayList = (ArrayList<TodoData>) todoDataArrayList.clone();

        todoListAdapter = new TodoListAdapter(TodoListActivity.this, todoFilterArrayList);
        mBinding.recyclerview.setAdapter(todoListAdapter);
        todoListAdapter.notifyDataSetChanged();
    }
    cursor.close();
}

    @Override
    protected void onResume() {
        super.onResume();
        getAllData();
    }
}
