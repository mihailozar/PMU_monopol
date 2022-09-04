package com.example.pmu_monopol.results;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pmu_monopol.MainActivity;
import com.example.pmu_monopol.data.MyData;
import com.example.pmu_monopol.data.MyDataDao;
import com.example.pmu_monopol.databinding.ViewHolderListPlayersBinding;

import java.util.ArrayList;
import java.util.List;
import com.example.pmu_monopol.results.ResultsFragmentDirections;

public class GameDataAdapter extends RecyclerView.Adapter<GameDataAdapter.DataViewHolder> {

    private List<MyData> dataList=new ArrayList<>();
    private MainActivity mainActivity;
    public NavController navController;

    public GameDataAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setData(List<MyData > data){
        this.dataList=data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderListPlayersBinding viewHolder = ViewHolderListPlayersBinding.inflate(
                layoutInflater,
                parent,
                false);
        return new GameDataAdapter.DataViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        public ViewHolderListPlayersBinding binding;

        public DataViewHolder(@NonNull ViewHolderListPlayersBinding binding) {
            super(binding.getRoot());
            this.binding=binding;


            binding.layout.setOnClickListener(view->{
                Toast.makeText(mainActivity.getApplicationContext(), "Implementirati simulaciju sa id:"+binding.idGame.getText().toString(), Toast.LENGTH_SHORT).show();
                ResultsFragmentDirections.ActionResultsFragmentToSimulationFragment action=ResultsFragmentDirections.actionResultsFragmentToSimulationFragment();
                action.setIdGame(Integer.parseInt( binding.idGame.getText().toString()));
                navController.navigate(action);

            });
        }

        public void bind(MyData data){
            binding.idGame.setText (String.valueOf( data.getIdGame()));
            binding.namePlayers.setText(data.getNamePlayers());
            binding.winner.setText(data.getWinner());

        }
    }
}
