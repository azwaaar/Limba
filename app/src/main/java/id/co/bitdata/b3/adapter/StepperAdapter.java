package id.co.bitdata.b3.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;

import java.util.List;

import id.co.bitdata.b3.R;
import id.co.bitdata.b3.fragment.RegOneFragment;
import id.co.bitdata.b3.fragment.RegThreeFragment;
import id.co.bitdata.b3.fragment.RegTwoFragment;

public class StepperAdapter extends AbstractFragmentStepAdapter {

    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        switch (position) {
            case 1:
                return new RegTwoFragment();
            case 2:
                return new RegThreeFragment();
            default:
                return new RegOneFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}