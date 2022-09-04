package com.example.pmu_monopol.gameplay;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmu_monopol.MainActivity;

import com.example.pmu_monopol.data.MyData;
import com.example.pmu_monopol.databinding.FragmentGamePlayBinding;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GamePlayFragment extends Fragment {

    private MainActivity mainActivity;
    private int currentId;
    private String currentNames;
    private NavController navController;
    private GamePlayLayout game;



    public GamePlayFragment() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity= (MainActivity) requireActivity();
        currentId= GamePlayFragmentArgs.fromBundle(requireArguments()).getGameId();
        currentNames=GamePlayFragmentArgs.fromBundle(requireArguments()).getGameName();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        game= new GamePlayLayout(getContext(), currentId, currentNames, mainActivity, getViewLifecycleOwner());
        return game;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        game.navController=this.navController;
    }
}