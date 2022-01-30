package com.example.recuperacionud2.recyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recuperacionud2.R;
import com.example.recuperacionud2.entities.PlanetEntity;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    private static final String TAG = RepoAdapter.class.getSimpleName();
    private final ListItemClickListener onClickListener;

    public PlanetEntity[] repoData;

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public RepoAdapter(ListItemClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediatly = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediatly);

        RepoViewHolder viewHolder = new RepoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        PlanetEntity repo = repoData[position];
        holder.bind(repo);
    }
    @Override
    public int getItemCount() {
        if(repoData == null) return 0;
        return repoData.length;
    }

    public void setRepoData(PlanetEntity[] repositories){
        repoData = repositories;
        notifyDataSetChanged();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        TextView gravity;
        TextView terrain;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.planetname);
            gravity = (TextView) itemView.findViewById(R.id.planet_gravity);
            terrain = (TextView) itemView.findViewById(R.id.planet_terrain);
            itemView.setOnClickListener(this);
        }

        void bind(PlanetEntity planet) {

            name.setText(planet.name);
            gravity.setText((planet.gravity));
            terrain.setText(planet.terrain);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }
}
