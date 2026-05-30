package com.example.smartinteligentproject;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartinteligentproject.Model.LoginScreen;
import com.example.smartinteligentproject.R;
import com.google.android.material.navigation.NavigationView;

public class DashboardScreen extends AppCompatActivity {

    NavigationView navMenu;
    ActionBarDrawerToggle toggle;
    DrawerLayout drayerLayout;

    Fragment temp=null;

    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.relative_layout), (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        Toolbar toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        navMenu=findViewById(R.id.navMenu);
        drayerLayout=findViewById(R.id.drawerlayout);

        //    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new Fragment_MainDashboard_cat()).commit();

        tv_result=findViewById(R.id.tv_result);

        toggle=new ActionBarDrawerToggle(this,drayerLayout,toolbar,R.string.app_name,R.string.app_name);
        drayerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {


                    case R.id.menu_Fire:
                        Intent fireIntent=new Intent(DashboardScreen.this,FireSensorScreen.class);
                        startActivity(fireIntent);
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_Rain:
                        Intent smokeIntent=new Intent(DashboardScreen.this,RainSensorScreen.class);
                        startActivity(smokeIntent);
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.menu_Ultra:
                        Intent UltraAlert=new Intent(DashboardScreen.this,UltraSensorScreen.class);
                        startActivity(UltraAlert);
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_Ldr:
                        Intent LDRIntent=new Intent(DashboardScreen.this,LDRScreen.class);
                        startActivity(LDRIntent);
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_dash:
                        Intent dashIntent=new Intent(DashboardScreen.this,SignBoardActivity.class);
                        startActivity(dashIntent);
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_show_all_data:
                        Intent showIntent=new Intent(DashboardScreen.this,FireShowData.class);
                        startActivity(showIntent);
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.menu_logout:

                        Intent logIntent=new Intent(DashboardScreen.this, LoginScreen.class);
                        startActivity(logIntent);
                        finish();

                        Toast.makeText(DashboardScreen.this, "Logout", Toast.LENGTH_SHORT).show();
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                }



                return false;
            }
        });


    }
}