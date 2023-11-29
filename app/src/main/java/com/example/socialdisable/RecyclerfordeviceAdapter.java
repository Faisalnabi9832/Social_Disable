package com.example.socialdisable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerfordeviceAdapter extends RecyclerView.Adapter<RecyclerfordeviceAdapter.ViewHolder>
        implements Filterable {

    private List<ModelForSocial> originalList;
    private List<ModelForSocial> filteredList;
    private Context context;

    public RecyclerfordeviceAdapter(List<ModelForSocial> originalList, Context context, RecyclerforSocialAdapter.ToggleButtonCallback toggleButtonCallback) {
        this.originalList = originalList;
        this.filteredList = new ArrayList<>(originalList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelForSocial deviceApp = filteredList.get(position);
        holder.appNameTextView.setText(deviceApp.getAppName());
        holder.appIconImageView.setImageDrawable(deviceApp.getAppIcon());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView appNameTextView;
        ImageView appIconImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appNameTextView = itemView.findViewById(R.id.txtname);
            appIconImageView = itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase();

                List<ModelForSocial> filtered = new ArrayList<>();

                for (ModelForSocial deviceApp : originalList) {
                    if (deviceApp.getAppName().toLowerCase().contains(query)) {
                        filtered.add(deviceApp);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (List<ModelForSocial>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
