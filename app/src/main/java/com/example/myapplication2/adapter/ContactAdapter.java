package com.example.myapplication2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.R;
import com.example.myapplication2.Utils.DatabaseHandler;
import com.example.myapplication2.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> mContacts;

    public ContactAdapter(List<Contact> mContacts) {
        this.mContacts = mContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(context, contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        TextView tvId = holder.tvId;
        TextView tvName = holder.tvName;
        Button btnMsg = holder.btnDelete;

        tvId.setText(String.valueOf(contact.getId()));
        tvName.setText(contact.getContactName());

        btnMsg.setText("Delete");
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvId;
        public TextView tvName;
        public Button btnDelete;

        private Context context;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.contact_id);
            tvName = itemView.findViewById(R.id.contact_name);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            this.context = context;

            btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {

                DatabaseHandler db = new DatabaseHandler(context);
                db.openDatabase();

                db.deleteContact(Integer.parseInt(tvId.getText().toString().trim()));

                removeAt(pos);
            }
        }

        public void removeAt(int position) {
            mContacts.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mContacts.size());
        }
    }

}
