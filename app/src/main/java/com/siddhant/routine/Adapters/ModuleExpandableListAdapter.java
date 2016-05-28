package com.siddhant.routine.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.siddhant.routine.Classes.Module;
import com.siddhant.routine.Classes.Topic;
import com.siddhant.routine.R;
import com.siddhant.routine.ViewHolders.ModulesChildViewHolder;
import com.siddhant.routine.ViewHolders.ModulesParentViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Siddhant on 21-May-16.
 */
public class ModuleExpandableListAdapter extends ExpandableRecyclerAdapter<ModulesParentViewHolder,
        ModulesChildViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Module> moduleList;
    private Context context;

    public void addNewModule(UUID uuid) {
        Module m = new Module(uuid);
        m.addTopic();
        moduleList.add(m);
        notifyParentItemInserted(moduleList.size()-1);
    }

    public void deleteModule(int position) {
        moduleList.remove(position);
        notifyParentItemRemoved(position);
        notifyItemRangeChanged(position, moduleList.size());
    }

    public void addTopicChild(UUID moduleId, int position) {
        for(Module m : moduleList) {
            if(m.getModuleId().equals(moduleId)) {
                m.addTopic();
                notifyChildItemInserted(moduleList.indexOf(m), position);
                break;
            }
        }
    }

    public void removeTopicChild(UUID moduleId, int position) {
        for(Module m : moduleList) {
            if(m.getModuleId().equals(moduleId)) {
                m.removeTopic(position);
                notifyChildItemRemoved(moduleList.indexOf(m), position);
            }
        }
    }

    public ModuleExpandableListAdapter(Context context,
                                       @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        moduleList = (ArrayList<Module>) parentItemList;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ModulesParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View moduleView = inflater.inflate(R.layout.expandable_list_item_group, parentViewGroup, false);
        return new ModulesParentViewHolder(this, moduleView);
    }

    @Override
    public ModulesChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View moduleView = inflater.inflate(R.layout.expandable_list_item_child, childViewGroup, false);
        return new ModulesChildViewHolder(this, moduleView);
    }

    @Override
    public void onBindParentViewHolder(ModulesParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        Module module = (Module) parentListItem;
        parentViewHolder.bind(module, moduleList.size());
    }

    @Override
    public void onBindChildViewHolder(ModulesChildViewHolder childViewHolder, int position, Object childListItem) {
        Topic topic = (Topic) childListItem;
        childViewHolder.bind(topic);
    }
}
