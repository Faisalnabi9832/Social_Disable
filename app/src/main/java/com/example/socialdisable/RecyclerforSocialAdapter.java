package com.example.socialdisable;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecyclerforSocialAdapter extends RecyclerView.Adapter<RecyclerforSocialAdapter.ViewHolder> implements Filterable {

    private List<ModelForSocial> arrayList;
    private List<ModelForSocial> filteredList;
    private Context context;
    private ToggleButtonCallback toggleButtonCallback;

    public interface ToggleButtonCallback {
        void onToggleButtonClicked(ModelForSocial socialApp, boolean isChecked);

        void onToggleButtonClicked(int position, boolean isChecked);
    }

    public RecyclerforSocialAdapter(List<ModelForSocial> arrayList, Context context, ToggleButtonCallback callback) {
        this.arrayList = arrayList;
        this.filteredList = new ArrayList<>(arrayList);
        this.context = context;
        this.toggleButtonCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelForSocial socialApp = filteredList.get(position);

        holder.appNameTextView.setText(socialApp.getAppName());
        holder.appIconImageView.setImageDrawable(socialApp.getAppIcon());
        holder.toggleButton.setChecked(socialApp.isNotificationEnabled());


        holder.toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            socialApp.setNotificationEnabled(isChecked);
//            if ()
//            // Notify the callback about the toggle button click
//            if (toggleButtonCallback != null) {
//                toggleButtonCallback.onToggleButtonClicked(socialApp, isChecked);
//            }
            if (holder.toggleButton.isChecked())
            {
                openAppNotificationSettings(context, socialApp.getPackageName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView appNameTextView;
        ImageView appIconImageView;
        SwitchCompat toggleButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appNameTextView = itemView.findViewById(R.id.txtname);
            appIconImageView = itemView.findViewById(R.id.imageView);
            toggleButton = itemView.findViewById(R.id.toggleButton);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase();

                List<ModelForSocial> filtered = new ArrayList<>();

                for (ModelForSocial socialApp : arrayList) {
                    if (socialApp.getAppName().toLowerCase().contains(query)) {
                        filtered.add(socialApp);
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
    private void openAppNotificationSettings(Context context, String packageName) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName);

        // If your app is targeting Android 11 (API level 30) or higher, you must
        // use the following extra to ensure the intent opens the specified package
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle the case where the settings activity is not found
            e.printStackTrace();
        }
    }

}
