package id.co.bitdata.b3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import id.co.bitdata.b3.R;
import id.co.bitdata.b3.databinding.ActivityOtpactivityBinding;

public class OTPActivity extends AppCompatActivity {

    ActivityOtpactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otpactivity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.otp.setText(Html.fromHtml(getResources().getString(R.string.otp)));
        binding.generateOtp.setOnClickListener(view -> {
            if(binding.mobileNumber.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(),"Masukkan nomor telepon",Toast.LENGTH_SHORT).show();
            else if(binding.mobileNumber.getText().length()<10)
                Toast.makeText(getApplicationContext(),"Nomor telepon kurang dari 10 digit",Toast.LENGTH_SHORT).show();
            else{
                Intent intent = new Intent(getApplicationContext(), CodeOTPActivity.class);
                startActivity(intent);
            }

        });

    }
}