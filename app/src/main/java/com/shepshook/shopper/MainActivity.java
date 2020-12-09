package com.shepshook.shopper;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shepshook.shopper.viewmodel.CategoriesViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private NavController navController;
    private CategoriesViewModel viewModel;
    private WorkManager workManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.action_CategoriesFragment_to_cartActivity));

        viewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        workManager = WorkManager.getInstance(getApplicationContext());
        scheduleNotifications();

        mediaPlayer = MediaPlayer.create(this, R.raw.boop);
    }

    @Override
    public boolean onNavigateUp() {
        return navController.navigateUp() || super.onNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRefreshButton:
                mediaPlayer.start();
                viewModel.reloadDataFromApi();
                break;
            case R.id.menuAboutButton:
                mediaPlayer.start();
                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.action_CategoriesFragment_to_aboutFragment);
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void scheduleNotifications() {
        WorkRequest request = new PeriodicWorkRequest.Builder(NotificationWorker.class, 1, TimeUnit.MINUTES)
                .setInitialDelay(20, TimeUnit.SECONDS)
                .addTag("Notification Worker")
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build();
        workManager.enqueue(request);
    }
}