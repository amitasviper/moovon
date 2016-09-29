package com.appradar.viper.moovon;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appradar.viper.moovon.User.UserProfile;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mutils.CircleTransform;
import mutils.RecognizeMotionService;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient mApiClient;

    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    PieChart moveprogress, drinkprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        //scheduleAlarm();

    }

    private void initViews() {
        // Getting reference to the DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        //Adding toolbar to the activity
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Creating an ArrayAdapter to add items to the listview mDrawerList
        ArrayAdapter<String> adapterL = new ArrayAdapter<String>(
                getBaseContext(),
                R.layout.drawer_list_item,
                getResources().getStringArray(R.array.options)
        );

        // Setting the adapter on mDrawerList
        mDrawerList.setAdapter(adapterL);

        // Enabling Home button
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Disable Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Setting item click listener for the listview mDrawerList NOT_WORKING
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e("setOnItemClickListener", "Position : " + position);

                String option_text = ((TextView) view).getText().toString();
                Toast.makeText(MainActivity.this, option_text, Toast.LENGTH_SHORT).show();

                if (option_text.equalsIgnoreCase("Dashboard")) {
                    Intent intent = new Intent(MainActivity.this, DashBoard.class);
                    startActivity(intent);
                    return;
                }

                if (option_text.equalsIgnoreCase(getString(R.string.addWater))) {
                    Intent intent = new Intent(MainActivity.this, WaterIntakeActivity.class);
                    startActivity(intent);
                    return;
                }

                if (option_text.equalsIgnoreCase(getString(R.string.addDistance))) {
                    Intent intent = new Intent(MainActivity.this, GpsTrackerActivity.class);
                    startActivity(intent);
                    return;
                }

                if (option_text.equalsIgnoreCase(getString(R.string.logout)))
                {
                    LogOutUser();
                    return;
                }

                // Closing the drawer
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(drawerToggle);

        drawerToggle.syncState();
        SetupProfileInfo();
        moveprogress = (PieChart)findViewById(R.id.chartWalkProgress);
        drinkprogress = (PieChart)findViewById(R.id.chartDrinkProgress);

        float target  = 6.0f;//AppSharedPreferences.getInstance().getPropInteger(AppSharedPreferences.SETTING_MOVEMENT_FREQ);
        float done = 2.5f;
        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(target- done, "Achieved"));
        entries.add(new PieEntry(done, "Remaining"));


        PieDataSet set = new PieDataSet(entries, "Movement today");

        int[] colors = {getResources().getColor(R.color.graph1), getResources().getColor(R.color.graph2)};

        set.setColors(colors);

        PieData data = new PieData(set);
        moveprogress.setData(data);
        moveprogress.invalidate();

        target  = 6.0f;//AppSharedPreferences.getInstance().getPropInteger(AppSharedPreferences.SETTING_MOVEMENT_FREQ);
        done = 4.5f;
        entries = new ArrayList<>();

        entries.add(new PieEntry(target- done, "Achieved"));
        entries.add(new PieEntry(done, "Remaining"));


        set = new PieDataSet(entries, "Drinking targets");

        set.setColors(colors);
        data = new PieData(set);
        drinkprogress.setData(data);
        drinkprogress.invalidate();

    }

    private void LogOutUser()
    {
        UserProfile.logout_user(MainActivity.this);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    private void SetupProfileInfo() {
        ImageView iv_profile_image = (ImageView) findViewById(R.id.iv_profile_image);
        TextView iv_profile_name = (TextView) findViewById(R.id.tv_profile_name);
        TextView iv_profile_email = (TextView) findViewById(R.id.tv_profile_email);
        FirebaseUser user = UserProfile.getCurrentUser();

        if (user == null) {
            return;
        }

        Picasso.with(MainActivity.this)
                .load(user.getPhotoUrl())
                .resize(80, 80)
                .transform(new CircleTransform())
                .into(iv_profile_image);

        iv_profile_name.setText(user.getDisplayName());
        iv_profile_email.setText(user.getEmail());
        iv_profile_name.setText(UserProfile.getLoggedOnUserDisplayName());
        iv_profile_email.setText(UserProfile.getLoggedOnUserId());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent(this, RecognizeMotionService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient, 500, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void setupMovemenDetector() {
        mApiClient.connect();

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Toast.makeText(MainActivity.this, "Home button clicked", Toast.LENGTH_SHORT).show();
        }

        if (menuItem.getItemId() == R.id.logout) {
            UserProfile.logout_user(MainActivity.this);
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        if (menuItem.getItemId() == R.id.menu_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
