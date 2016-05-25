package com.siddhant.routine.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.siddhant.routine.Classes.Module;
import com.siddhant.routine.R;
import com.siddhant.routine.Classes.Topic;
import com.siddhant.routine.ViewHolders.ModulesChildViewHolder;
import com.siddhant.routine.ViewHolders.ModulesParentViewHolder;

import java.util.List;

/**
 * Created by Siddhant on 21-May-16.
 */
public class ModuleExpandableListAdapter extends ExpandableRecyclerAdapter<ModulesParentViewHolder,
        ModulesChildViewHolder> {

    private LayoutInflater inflater;

    public ModuleExpandableListAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ModulesParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View moduleView = inflater.inflate(R.layout.expandable_list_item_group, parentViewGroup, false);
        return new ModulesParentViewHolder(moduleView);
    }

    @Override
    public ModulesChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View moduleView = inflater.inflate(R.layout.expandable_list_item_child, childViewGroup, false);
        return new ModulesChildViewHolder(moduleView);
    }

    @Override
    public void onBindParentViewHolder(ModulesParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        Module module = (Module) parentListItem;
        parentViewHolder.bind(module);
    }

    @Override
    public void onBindChildViewHolder(ModulesChildViewHolder childViewHolder, int position, Object childListItem) {
        Topic topic = (Topic) childListItem;
        childViewHolder.bind(topic);
    }
}
