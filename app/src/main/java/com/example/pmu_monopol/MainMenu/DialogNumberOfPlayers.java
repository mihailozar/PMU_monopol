package com.example.pmu_monopol.MainMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pmu_monopol.MainActivity;
import com.example.pmu_monopol.R;
import com.example.pmu_monopol.databinding.FragmentDialogNumberOfPlayersBinding;
import com.example.pmu_monopol.databinding.FragmentMainMenuBinding;


public class DialogNumberOfPlayers extends DialogFragment {

    private FragmentDialogNumberOfPlayersBinding binding;
    private MainActivity mainActivity;
    private NumberPlayersViewModel viewModel;
    private int selected=0;

    public DialogNumberOfPlayers() {
        // Required empty public constructor
    }


    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    private NoticeDialogListener listener;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        mainActivity = (MainActivity) requireActivity();
        viewModel = new ViewModelProvider(mainActivity).get(NumberPlayersViewModel.class);



        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View layout_spinners = inflater.inflate(R.layout.fragment_dialog_number_of_players,null);
        builder.setView(layout_spinners)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        viewModel.setNumber(selected);
                    }
                })
                .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });


        ArrayAdapter<CharSequence> spinnerAdapter=ArrayAdapter.createFromResource(
                getContext(),
                R.array.spinner_values,
                android.R.layout.simple_spinner_dropdown_item
        );
        Spinner spinner = (Spinner) layout_spinners.findViewById(R.id.spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selected=position+2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return builder.create();
    }


}