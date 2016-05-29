package com.siddhant.routine.ViewHolders;

import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.siddhant.routine.Adapters.ModuleExpandableListAdapter;
import com.siddhant.routine.Classes.Module;
import com.siddhant.routine.R;

import java.util.ArrayList;

/**
 * Created by Siddhant on 24-May-16.
 */
public class ModulesParentViewHolder extends ParentViewHolder {
    Context context;
    TextView groupTitle;
    ImageButton deleteButton;
    ArrayList<Module> moduleList;
    private long lastClickTime = 0;

    public ModulesParentViewHolder(final ModuleExpandableListAdapter adapter, View itemView) {
        super(itemView);
        context = itemView.getContext();
        groupTitle = (TextView) itemView.findViewById(R.id.expandable_list_group_title);
        deleteButton = (ImageButton) itemView.findViewById(R.id.expandable_list_module_delete);
        moduleList = (ArrayList<Module>) adapter.getParentItemList();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Module")
                        .setMessage("Are you sure you want to delete this module?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.deleteModule(getAdapterPosition());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // no implementation required
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void bind(Module module) {
        int position = moduleList.indexOf(module)+1;
        groupTitle.setText(context.getString(R.string.module_list_title, position));
    }
}
