package com.example.pmu_monopol.results;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.sax.TextElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmu_monopol.MainActivity;

import com.example.pmu_monopol.data.MyData;
import com.example.pmu_monopol.databinding.FragmentResultsBinding;
import com.example.pmu_monopol.gameplay.DataViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResultsFragment extends Fragment {

    private FragmentResultsBinding binding;
    private DataViewModel viewModel;
    private NavController navController;
    private MainActivity mainActivity;
    private ExecutorService executorService;
    private List<MyData> tmp=null;
    private LiveData<List<MyData>> data;
    GameDataAdapter adapter;


    public ResultsFragment( ) {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity=(MainActivity) requireActivity();
        viewModel=new ViewModelProvider(mainActivity).get(DataViewModel.class);

        data=viewModel.getDataList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentResultsBinding.inflate(inflater,container,false);
        adapter=new GameDataAdapter(mainActivity);

        data.observe(
                getViewLifecycleOwner(),
                adapter::setData
        );
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        adapter.navController=navController;

    }


}