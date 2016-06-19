package com.siddhant.routine.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.siddhant.routine.R;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.classes.Topic;
import com.siddhant.routine.viewholders.ModulesChildViewHolder;
import com.siddhant.routine.viewholders.ModulesParentViewHolder;

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
    RecyclerView recyclerView;

    public void addNewModule(UUID uuid) {
        Module m = new Module(uuid);
        m.addTopic();
        moduleList.add(m);
        notifyParentItemInserted(moduleList.size()-1);
    }

    public void deleteModule(Module module, int adapterPosition) {
        int position = moduleList.indexOf(module);
        moduleList.remove(module);
        notifyParentItemRemoved(position);
        notifyItemRangeChanged(adapterPosition, getItemCount());

        SharedPreferences prefs  = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String suggestedId = prefs.getString("suggestedModule", "");
        if(!suggestedId.isEmpty())
            if(module.getModuleId().equals(UUID.fromString(suggestedId))) {
                editor.putString("suggestedModule", "");
                editor.apply();
            }

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

    public void removeTopicChild(UUID moduleId, final int position) {
        if(position != -1) {
            for (final Module m : moduleList) {
                if (m.getModuleId().equals(moduleId)) {
                    m.removeTopic(position);
                    Handler handler = new Handler();

                    final Runnable r = new Runnable() {
                        public void run() {
                            notifyChildItemRemoved(moduleList.indexOf(m), position);
                        }
                    };

                    handler.post(r);
                }
            }
        }
    }

    public ModuleExpandableListAdapter(Context context,
                                       @NonNull List<? extends ParentListItem> parentItemList,
                                       RecyclerView recyclerView) {
        super(parentItemList);
        moduleList = (ArrayList<Module>) parentItemList;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public ModulesParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View moduleView = inflater.inflate(R.layout.expandable_list_item_group, parentViewGroup, false);
        return new ModulesParentViewHolder(this, moduleView);
    }

    @Override
    public ModulesChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View moduleView = inflater.inflate(R.layout.expandable_list_item_child, childViewGroup, false);
        return new ModulesChildViewHolder(this, moduleView, recyclerView);
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

    @Override
    public void onParentListItemExpanded(int position) {
        super.onParentListItemExpanded(position);
        for(Module module : moduleList) {
            ArrayList<Topic> topics = (ArrayList<Topic>) module.getChildItemList();
            if (!TextUtils.isEmpty(topics.get(topics.size() - 1).getTopicName())) {
                addTopicChild(module.getModuleId(), topics.size());
            }
        }
    }
}
