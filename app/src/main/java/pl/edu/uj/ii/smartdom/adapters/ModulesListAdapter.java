package pl.edu.uj.ii.smartdom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.database.Module;

/**
 * Created by Mohru on 09.09.2017.
 */

public class ModulesListAdapter extends ArrayAdapter<Module> {

    private boolean showRooms;

    public ModulesListAdapter(Context context, List<Module> objects, boolean showRooms) {
        super(context, R.layout.module_row, objects);
        this.showRooms = showRooms;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModuleViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.module_row, parent, false);
            holder = new ModuleViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ModuleViewHolder) convertView.getTag();

        Module currentModule = getItem(position);

        switch (currentModule.getType()) {
            case LIGHT_MODULE:
                holder.moduleIcon.setImageResource(R.drawable.light_icon);
                break;
            case DOOR_MOTOR_MODULE:
                holder.moduleIcon.setImageResource(R.drawable.ic_lock_open);
                break;
            case METEO_MODULE:
                holder.moduleIcon.setImageResource(R.drawable.meteo_icon);
                break;
            case BLIND_MOTOR_MODULE:
                holder.moduleIcon.setImageResource(R.drawable.blind_icon);
                break;
        }
        holder.moduleName.setText(currentModule.getName());
        if (showRooms) {
            if (currentModule.getRoom() != null)
                holder.roomName.setText(currentModule.getRoom().getName());
            else
                holder.roomName.setText(R.string.no_room);
        } else
            holder.roomName.setVisibility(View.GONE);

        return convertView;
    }

    static class ModuleViewHolder {
        @BindView(R.id.module_icon)
        ImageView moduleIcon;
        @BindView(R.id.module_name_textView)
        TextView moduleName;
        @BindView(R.id.room_name_textView)
        TextView roomName;

        public ModuleViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
