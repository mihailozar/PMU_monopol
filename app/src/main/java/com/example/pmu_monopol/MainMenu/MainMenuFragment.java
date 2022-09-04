package com.example.pmu_monopol.MainMenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.pmu_monopol.MainActivity;

import com.example.pmu_monopol.data.MyData;
import com.example.pmu_monopol.databinding.FragmentMainMenuBinding;
import com.example.pmu_monopol.gameplay.DataViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainMenuFragment extends Fragment  {

    private FragmentMainMenuBinding binding;
    private NavController navController;
    private MainActivity mainActivity;
    private volatile String[] playerList=null;
    private FragmentManager fragmentManager;
    private NumberPlayersViewModel viewModel;
    private DataViewModel gameViewModel;

    private ExecutorService executorService;
    private int id;
    private MyData currentData;



    public MainMenuFragment() {
        executorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity=(MainActivity) requireActivity();
        ViewModelProvider modelProvider=new ViewModelProvider(mainActivity);
        viewModel = modelProvider.get(NumberPlayersViewModel.class);
        gameViewModel=modelProvider.get(DataViewModel.class);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        ArrayList<EditText> names=new ArrayList<>();


        binding.play.setOnClickListener(view -> {
            if(playerList==null){

                DialogFragment dialog = new DialogNumberOfPlayers();
                dialog.show(getChildFragmentManager(), "NoticeDialogFragment");

            }else{

                Handler uiThreadHandler = new Handler(Looper.getMainLooper());
                Future<Boolean> future=executorService.submit(()->{
                    String textNames="";
                    for (EditText editText:names) {
                        textNames+=editText.getText().toString()+",";

                    }
                    id=(int)gameViewModel.instertData(new MyData(0,new Date().toString(),textNames,""));
                    currentData=gameViewModel.getGameById(id);

                    return true;
                });

                executorService.submit(()->{
                    try {
                        future.get();
                        MainMenuFragmentDirections.ActionShowGamePlayFragment action=
                                MainMenuFragmentDirections.actionShowGamePlayFragment();
                        action.setGameName(currentData.getNamePlayers());
                        action.setGameId(id);
                        uiThreadHandler.post(()->{
                            navController.navigate(action);
                        });
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                executorService.shutdown();


            }
        });

        viewModel.getNumber().observe(getViewLifecycleOwner(),number->{
            if(number!=0){
                playerList=new String[number];

                for (int index=0; index<playerList.length;index++){
                    playerList[index]="New player"+index;
                    EditText tmp=new EditText(getContext());
                    tmp.setText("New player"+index);
                    names.add(tmp);
                    binding.linearLayout.addView(tmp);
                }
            }

        });


        binding.results.setOnClickListener(view -> {
            NavDirections action=MainMenuFragmentDirections.actionMainMenuFragmentToResultsFragment();
            navController.navigate(action);
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);

    }
}