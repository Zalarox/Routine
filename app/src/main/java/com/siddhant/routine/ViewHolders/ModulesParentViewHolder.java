package com.siddhant.routine.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.siddhant.routine.Classes.Module;
import com.siddhant.routine.R;

/**
 * Created by Siddhant on 24-May-16.
 */
public class ModulesParentViewHolder extends ParentViewHolder {

    TextView groupTitle;

    public ModulesParentViewHolder(View itemView) {
        super(itemView);
        groupTitle = (TextView) itemView.findViewById(R.id.expandable_list_group_title);
    }

    public void bind(Module module) {
        groupTitle.setText(module.getModuleId().toString());
    }

}
