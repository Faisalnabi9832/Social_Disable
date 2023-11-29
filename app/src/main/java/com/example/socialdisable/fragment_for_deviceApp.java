package com.example.socialdisable;

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

public class fragment_for_deviceApp extends Fragment {
    private List<ModelForSocial> DeviceAppsList = new ArrayList<>();
    private RecyclerforSocialAdapter DeviceAdapter;
    View view;

    public fragment_for_deviceApp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_for_device_app, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewfordeviceapp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get the list of installed device apps
        DeviceAppsList = getInstalledDeviceApps(getContext());

        // Initialize and set up the RecyclerView adapter
        DeviceAdapter = new RecyclerforSocialAdapter(DeviceAppsList, getContext(), new RecyclerforSocialAdapter.ToggleButtonCallback() {
            @Override
            public void onToggleButtonClicked(ModelForSocial DeviceApp, boolean isChecked) {
                // Handle the toggle button click event for device apps
                // You can use 'DeviceApp' to access the clicked device app's information
            }

            @Override
            public void onToggleButtonClicked(int position, boolean isChecked) {
                // Handle the toggle button click event here
                // You can use the 'position' parameter to identify which item's toggle button was clicked
                // and 'isChecked' to determine the new state (checked/unchecked)
                // For example, you can update your data model or perform any other action
            }
        });
        recyclerView.setAdapter(DeviceAdapter);

        SearchView searchView = view.findViewById(R.id.searchViewfordevice);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DeviceAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }


    public static List<ModelForSocial> getInstalledDeviceApps(Context context) {
        List<ModelForSocial> deviceApps = new ArrayList<>();

        // Define the intent for sharing text
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        // Get the list of ResolveInfo from the package manager
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(shareIntent, 0);

        // Iterate through the ResolveInfo to find device apps
        for (ResolveInfo resolveInfo : resolveInfos) {
            String packageName = resolveInfo.activityInfo.packageName;
            String appName = resolveInfo.loadLabel(pm).toString();
            Drawable appIcon = resolveInfo.loadIcon(pm);

            // Check if the app is a device app based on your criteria
            if (isDeviceApp(packageName)) {
                ModelForSocial appInfo = new ModelForSocial(appName, packageName, appIcon);
                deviceApps.add(appInfo);
            }
        }

        return deviceApps;
    }

    private static boolean isDeviceApp(String packageName) {
        // Check if the app is a social app based on your criteria
        return !(packageName.equals("com.facebook.katana") ||  // Facebook
                packageName.equals("com.instagram.android") ||  // Instagram
                packageName.equals("com.google.android.youtube") ||  // YouTube
                packageName.equals("com.whatsapp") ||  // WhatsApp
                packageName.equals("com.zhiliaoapp.musically") ||  // TikTok
                packageName.equals("org.telegram.messenger") ||  // Telegram
                packageName.equals("com.snapchat.android") ||  // Snapchat
                packageName.equals("your.package.name.for.X"));  // X
    }
}
