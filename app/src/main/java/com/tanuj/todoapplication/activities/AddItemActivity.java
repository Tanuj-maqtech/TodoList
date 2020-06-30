package com.tanuj.todoapplication.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tanuj.todoapplication.Adapters.TodoListAdapter;
import com.tanuj.todoapplication.Database.DataBaseEntry;
import com.tanuj.todoapplication.Database.FeedReaderDbHelper;
import com.tanuj.todoapplication.Model.TodoData;
import com.tanuj.todoapplication.R;
import com.tanuj.todoapplication.databinding.ActivityAdditemBinding;
import com.tanuj.todoapplication.databinding.ActivityTodolistBinding;

import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity {

    private ActivityAdditemBinding mBinding;

    TodoListAdapter todoListAdapter;
    ArrayList<TodoData> todoDataArrayList;
    ArrayList<TodoData> todoFilterArrayList;
    ArrayList<TodoData> todoDeletedArrayList;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_additem);

        todoDataArrayList =new ArrayList<>();
        setTitle("To Do List");
        context=AddItemActivity.this;
        init();

    }

    private void init() {

mBinding.btnDone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        TodoData todoData=new TodoData();
        todoData.setItemName(mBinding.editTitle.getText().toString());
        todoData.setItemdescription(mBinding.editDescription.getText().toString());
        long isinsert=insertData(todoData);
        if(isinsert>0){
            Toast.makeText(AddItemActivity.this,"Insert Sucessfully",Toast.LENGTH_LONG).show();
        }
        mBinding.editTitle.setText("");
        mBinding.editDescription.setText("");
        finish();

    }
});









    }


    private boolean validate() {



        return true;
    }



    public  long insertData(TodoData todoData){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBaseEntry.title, todoData.getItemName());
        values.put(DataBaseEntry.description, todoData.getItemdescription());

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DataBaseEntry.todoList, null, values);
        return newRowId;
    }




}
