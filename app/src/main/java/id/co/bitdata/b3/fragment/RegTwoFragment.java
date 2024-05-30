package id.co.bitdata.b3.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import id.co.bitdata.b3.R;
import id.co.bitdata.b3.activity.LocationActivity;
import id.co.bitdata.b3.databinding.FragmentRegOneBinding;
import id.co.bitdata.b3.databinding.FragmentRegTwoBinding;

/**
 * A simple {@link Fragment} subclass.
 * Achmad Azwar Misbah - Bitdata B3 - 29/05/2024
 * create an instance of this fragment.
 */
public class RegTwoFragment extends Fragment implements Step {

    FragmentRegTwoBinding binding;


    public RegTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegTwoBinding.inflate(inflater, container, false);

        binding.selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LocationActivity.class));
            }
        });

        return binding.getRoot();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}