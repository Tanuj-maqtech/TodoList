package com.tanuj.todoapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.tanuj.todoapplication.Model.TodoData;
import com.tanuj.todoapplication.R;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.IMEINumbersVH> {

    private Context mContext;


    private TodoItemClickListener mListener;

    ArrayList<TodoData> todoDataArrayList;

    public TodoListAdapter(Context context, ArrayList<TodoData> todoData) {
        mContext = context;

        todoDataArrayList = todoData;


        try {
            mListener = (TodoItemClickListener) context;
        } catch (Exception e) {

        }


    }

    public class IMEINumbersVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title, tv_description;
        public CheckBox checkBox;
        public RelativeLayout viewBackground, viewForeground;

        public IMEINumbersVH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            checkBox = itemView.findViewById(R.id.checkbox_select);

            tv_description = itemView.findViewById(R.id.tv_description);


        }


        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onCallNoItemClick(getAdapterPosition());
            }
        }
    }


    public void removeData(int postion) {

        if (postion != -1) {
            this.todoDataArrayList.remove(postion);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public IMEINumbersVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.imei_single_item, parent, false);
        return new IMEINumbersVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IMEINumbersVH holder, final int position) {

        holder.title.setText(todoDataArrayList.get(position).getItemName());
        holder.tv_description.setText(todoDataArrayList.get(position).getItemdescription());
        if (todoDataArrayList.get(position).getIsSelect() == 1) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    todoDataArrayList.get(position).setIsSelect(1);
                } else {
                    todoDataArrayList.get(position).setIsSelect(0);
                }
                TodoData newtodo=new TodoData();
                newtodo=todoDataArrayList.get(position);
                todoDataArrayList.remove(position);
                todoDataArrayList.add( (todoDataArrayList.size() == 0 ? 0:todoDataArrayList.size()), newtodo);
                //todoDataArrayList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoDataArrayList.size();
    }


    public interface TodoItemClickListener {

        void onCallNoItemClick(int position);
    }

}
