package com.siddhant.routine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddhant.routine.R;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.viewholders.ModuleCardViewHolder;

import java.util.ArrayList;

/**
 * Created by Siddhant on 29-May-16.
 */
public class ModuleCardListAdapter extends RecyclerView.Adapter<ModuleCardViewHolder> {

    Context context;
    ArrayList<Module> moduleList;

    public ModuleCardListAdapter(ArrayList<Module> moduleList) {
        this.moduleList = moduleList;
    }

    @Override
    public ModuleCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.card_module_view, parent, false);
        return new ModuleCardViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(ModuleCardViewHolder holder, int position) {
        holder.bind(moduleList.get(position));
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }
}
