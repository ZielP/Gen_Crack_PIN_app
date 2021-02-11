package com.example.crackthepin;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PinAdapter extends RecyclerView.Adapter<PinAdapter.PinViewHolder> {
    private final Context mContext;
    private Cursor mCursor;

    public PinAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public static class PinViewHolder extends RecyclerView.ViewHolder {
        public TextView outputText;
        public TextView inputText;

        public PinViewHolder(@NonNull View itemView) {
            super(itemView);

            inputText = itemView.findViewById(R.id.textview_input);
            outputText = itemView.findViewById(R.id.textview_output);
        }
    }

    @NonNull
    @Override
    public PinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.pin_item, parent, false);
        return new PinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PinViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String pinInput = mCursor.getString(mCursor.getColumnIndex(PinContract.PinEntry.COLUMN_INPUT));
        String pinOutput = mCursor.getString(mCursor.getColumnIndex(PinContract.PinEntry.COLUMN_OUTPUT));
        long id = mCursor.getLong(mCursor.getColumnIndex(PinContract.PinEntry._ID));

        holder.inputText.setText(pinInput);
        holder.outputText.setText(pinOutput);
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();

        mCursor = newCursor;

        if (newCursor != null) notifyDataSetChanged();
    }
}
