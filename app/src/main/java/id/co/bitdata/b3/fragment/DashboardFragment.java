package id.co.bitdata.b3.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.co.bitdata.b3.R;
import id.co.bitdata.b3.adapter.DepositAdapter;
import id.co.bitdata.b3.adapter.MenuAdapter;
import id.co.bitdata.b3.databinding.FragmentDashboardBinding;
import id.co.bitdata.b3.model.Deposit;
import id.co.bitdata.b3.model.Menu;

/**
 * A simple {@link Fragment} subclass.
 * Achmad Azwar Misbah - Bitdata B3 - 29/05/2024
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    private ArrayList<Menu> categoryModelArrayList = new ArrayList<>();
    private ArrayList<Deposit> depositArrayList = new ArrayList<>();
    FragmentDashboardBinding binding;
    private TypedArray image;
    private String[] title;

    private TypedArray imageDeposit;
    private String[] timeDeposit;
    private String[] descriptionDeposit;
    private String[] statusDeposit;
    private String[] priceDeposit;

    MenuAdapter menuAdapter;
    DepositAdapter depositAdapter;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        menuAdapter = new MenuAdapter(getActivity());
        depositAdapter = new DepositAdapter(getActivity());
        binding.recyclerViewMenu.setHasFixedSize(true);
        binding.recyclerViewDeposit.setHasFixedSize(true);

        binding.balanceToggle.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.balanceTV.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                binding.balanceTV.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });

        prepareCategory();
        addItemCategory();
        showCategory();

        prepareDeposit();
        addItemDeposit();
        showDeposit();

        return binding.getRoot();
    }

    private void showDeposit() {
        binding.recyclerViewDeposit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        depositAdapter = new DepositAdapter(getActivity());

        depositAdapter.setCategory(depositArrayList);
        binding.recyclerViewDeposit.setAdapter(depositAdapter);
    }

    private void addItemDeposit() {
        depositArrayList = new ArrayList<>();

        for (int i = 0; i < timeDeposit.length; i++) {
            Deposit designInteriorModel = new Deposit();
            designInteriorModel.setImg(imageDeposit.getResourceId(i, -1));
            designInteriorModel.setTime(timeDeposit[i]);
            designInteriorModel.setDescription(descriptionDeposit[i]);
            designInteriorModel.setStatus(statusDeposit[i]);
            designInteriorModel.setPrice(priceDeposit[i]);
            depositArrayList.add(designInteriorModel);
        }

        menuAdapter.setCategory(categoryModelArrayList);
    }

    private void prepareDeposit() {
        imageDeposit = getResources().obtainTypedArray(R.array.image_deposit);
        timeDeposit = getResources().getStringArray(R.array.time_deposit);
        descriptionDeposit = getResources().getStringArray(R.array.title_deposit);
        statusDeposit = getResources().getStringArray(R.array.status_deposit);
        priceDeposit = getResources().getStringArray(R.array.price_deposit);
    }

    private void showCategory() {
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);
        MenuAdapter menuAdapter = new MenuAdapter(getActivity());
        menuAdapter.setCategory(categoryModelArrayList);
        binding.recyclerViewMenu.setLayoutManager(layoutManager);
        binding.recyclerViewMenu.setAdapter(menuAdapter);
    }

    private void addItemCategory() {
        categoryModelArrayList = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {
            Menu designInteriorModel = new Menu();
            designInteriorModel.setImgMenu(image.getResourceId(i, -1));
            designInteriorModel.setTitleMenu(title[i]);
            categoryModelArrayList.add(designInteriorModel);
        }

        menuAdapter.setCategory(categoryModelArrayList);
    }

    private void prepareCategory() {
        image = getResources().obtainTypedArray(R.array.image_menu);
        title = getResources().getStringArray(R.array.title_menu);
    }
}