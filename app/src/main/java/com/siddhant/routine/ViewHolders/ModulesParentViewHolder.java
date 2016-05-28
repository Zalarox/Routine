package com.siddhant.routine.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.siddhant.routine.Adapters.ModuleExpandableListAdapter;
import com.siddhant.routine.Classes.Module;
import com.siddhant.routine.R;

/**
 * Created by Siddhant on 24-May-16.
 */
public class ModulesParentViewHolder extends ParentViewHolder {

    TextView groupTitle;
    ImageButton deleteButton;

    public ModulesParentViewHolder(final ModuleExpandableListAdapter adapter, View itemView) {
        super(itemView);
        groupTitle = (TextView) itemView.findViewById(R.id.expandable_list_group_title);
        deleteButton = (ImageButton) itemView.findViewById(R.id.expandable_list_module_delete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteModule(getAdapterPosition());
            }
        });
    }

    public void bind(Module module, int position) {
        groupTitle.setText("Module " + (position+1));
    }
}
