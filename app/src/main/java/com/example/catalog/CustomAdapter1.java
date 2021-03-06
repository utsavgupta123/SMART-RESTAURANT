package com.example.catalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter1 extends RecyclerView.Adapter<CustomAdapter1.ViewHolder> {

    ArrayList<items> localDataSet;

    ArrayList<items> orders;

    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final Button button;
        private final ImageView imageview;
        public ViewHolder(View view) {

            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
            button=(Button)view.findViewById(R.id.button);
            imageview=(ImageView)view.findViewById(R.id.imageView2);
            // Define click listener for the ViewHolder's View


        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet   String[] containing the data to populate views to be used
     *                  by RecyclerView.
     *
     */
    public CustomAdapter1(Context context, ArrayList<items> dataSet) {
        localDataSet = dataSet;
        // textview5.setText(Integer.toString(cost));

        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(context)
                .inflate(R.layout.text_row_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
//        context= viewGroup.getContext();

        return vh;
    }

//    @NonNull
//    @org.jetbrains.annotations.NotNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
//        return null;
//    }

    // Replace the contents of a view (invoked by the layout manager)

    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element


        viewHolder.getTextView().setText(localDataSet.get(position).name);
        viewHolder.button.setText(Integer.toString(localDataSet.get(position).price));
        Glide.with(context).load(localDataSet.get(position).imageurl).into(viewHolder.imageview);
        // viewHolder.imageview.getDrawable(R.drawable.ic_launcher_background);
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
