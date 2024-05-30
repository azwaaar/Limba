package id.co.bitdata.b3.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.Objects;

import id.co.bitdata.b3.R;
import id.co.bitdata.b3.databinding.FragmentRegOneBinding;

/**
 * A simple {@link Fragment} subclass.
 * Achmad Azwar Misbah - Bitdata B3 - 29/05/2024
 * create an instance of this fragment.
 */
public class RegOneFragment extends Fragment implements Step {

    FragmentRegOneBinding binding;

    public RegOneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegOneBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        String username = binding.usernameET.getText().toString();
        String email = binding.emailET.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (username.isEmpty()) {
            binding.usernameET.setError("Nama Lengkap Tidak Boleh Kosong");
            binding.usernameET.setFocusable(true);
        } else if (email.isEmpty()) {
            binding.emailET.setError("Email Tidak Boleh Kosong");
            binding.emailET.setFocusable(true);
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(getContext(), "Format Email Tidak Valid", Toast.LENGTH_SHORT).show();
        } else {
            return null;
        }
        return new VerificationError("We encountered an error");
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}