package id.co.bitdata.b3.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import id.co.bitdata.b3.R;
import id.co.bitdata.b3.databinding.ActivityDashboardBinding;
import id.co.bitdata.b3.fragment.BillingFragment;
import id.co.bitdata.b3.fragment.DashboardFragment;
import id.co.bitdata.b3.fragment.HistoryFragment;
import id.co.bitdata.b3.fragment.ProfileFragment;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.chipNavigationBar.setItemSelected(R.id.home_bottom, true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,
                        new DashboardFragment()).commit();
        bottomMenu();

    }

    private void bottomMenu() {
        binding.chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                if (i == R.id.home_bottom) {
                    fragment = new DashboardFragment();
                } else if (i == R.id.history_bottom) {
                    fragment = new HistoryFragment();
                } else if (i == R.id.billing_bottom) {
                    fragment = new BillingFragment();
                } else if (i == R.id.profile_bottom) {
                    fragment = new ProfileFragment();
                } else {
                    fragment = new DashboardFragment();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content,
                                fragment).commit();
            }
        });
    }
}