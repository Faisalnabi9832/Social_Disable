package com.example.socialdisable;
import androidx.appcompat.widget.SearchView;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class fragmnet_for_social extends Fragment {

    private List<ModelForSocial> socialAppsList = new ArrayList<>();
    private RecyclerforSocialAdapter socialAdapter;

    public fragmnet_for_social() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmnet_for_social, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get the list of installed social apps
        socialAppsList = getInstalledSocialApps(getContext());

        // Initialize and set up the RecyclerView adapter
        socialAdapter = new RecyclerforSocialAdapter(socialAppsList, getContext(), new RecyclerforSocialAdapter.ToggleButtonCallback() {
            @Override
            public void onToggleButtonClicked(ModelForSocial socialApp, boolean isChecked) {

            }

            @Override
            public void onToggleButtonClicked(int position, boolean isChecked) {
                // Handle the toggle button click event here
                // You can use the 'position' parameter to identify which item's toggle button was clicked
                // and 'isChecked' to determine the new state (checked/unchecked)
                // For example, you can update your data model or perform any other action
            }
        });
        recyclerView.setAdapter(socialAdapter);

        // Initialize SearchView
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                socialAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }

    public static List<ModelForSocial> getInstalledSocialApps(Context context) {
        List<ModelForSocial> socialApps = new ArrayList<>();

        // Define the intent for sharing text
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        // Get the list of ResolveInfo from the package manager
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(shareIntent, 0);

        // Iterate through the ResolveInfo to find social apps
        for (ResolveInfo resolveInfo : resolveInfos) {
            String packageName = resolveInfo.activityInfo.packageName;
            String appName = resolveInfo.loadLabel(pm).toString();
            Drawable appIcon = resolveInfo.loadIcon(pm);
            System.out.println("****************Printing apps");
            System.out.println("AppName :"+appName);
            System.out.println("****************Printing apps***********************");

            // Check if the app is a social app based on your criteria
            if (isSocialApp(packageName)) {
                ModelForSocial appInfo = new ModelForSocial(appName, packageName, appIcon);
                socialApps.add(appInfo);
            }
        }

        return socialApps;
    }

    private static boolean isSocialApp(String packageName) {

        return (packageName.equals("com.facebook.katana") ||        // Facebook
                packageName.equals("com.instagram.android") ||     // Instagram
                packageName.equals("com.youtube.android") || // YouTube
                packageName.equals("com.whatsapp") ||               // WhatsApp
                packageName.equals("com.zhiliaoapp.musically") ||  // TikTok
                packageName.equals("org.telegram.messenger") ||     // Telegram
                packageName.equals("com.snapchat.android") ||// Snapchat
                packageName.contains("com.google.android.youtube&hl=en&gl=US") ||
                packageName.equals("com.twitter.android")||
                packageName.equals("com.whatsapp.w4b&hl=en&gl=US"));  // X
    }
}
