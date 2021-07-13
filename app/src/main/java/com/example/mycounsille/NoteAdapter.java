package com.example.mycounsille;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class NoteAdapter extends FirebaseRecyclerAdapter<Driver, NoteAdapter.NoteHolder> {

    public NoteAdapter(@NonNull FirebaseRecyclerOptions<Driver> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Driver model) {
        holder.textViewTitle.setText(model.getFullName());
        holder.place.setText(model.getAddress());
        holder.plate.setText(model.getDateOfBirth());
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,
                parent, false);
        return new NoteHolder(v);
    }

    public void deleteItem(int position) {
      getSnapshots().getSnapshot(position).getRef().removeValue();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView plate;
        TextView place;
        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.fun);
            place = itemView.findViewById(R.id.userphone);
            plate = itemView.findViewById(R.id.userrole);
        }
    }

}
