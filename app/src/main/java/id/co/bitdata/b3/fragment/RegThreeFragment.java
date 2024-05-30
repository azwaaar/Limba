package id.co.bitdata.b3.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import id.co.bitdata.b3.R;

/**
 * A simple {@link Fragment} subclass.
 * Achmad Azwar Misbah - Bitdata B3 - 29/05/2024
 * create an instance of this fragment.
 */
public class RegThreeFragment extends Fragment implements Step {

    public RegThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg_three, container, false);

        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}