package com.mahmoud.mahmoudapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mahmoud.mahmoudapp.R;
import com.mahmoud.mahmoudapp.View.Activity.MainActivity;
import com.malinskiy.materialicons.widget.IconTextView;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {
    private final MainActivity mainActivity;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    public int selectedItem = 2;
    String[] drawerTitles;
    String[] drawerIcon;

    public DrawerAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        drawerTitles = mainActivity.getResources().getStringArray(R.array.drawer_titles);
        drawerIcon = mainActivity.getResources().getStringArray(R.array.drawer_icon);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemTitle;
        IconTextView itemIcon;
        View background;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            if (ViewType == TYPE_ITEM) {
                itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
                itemIcon = (IconTextView) itemView.findViewById(R.id.ItemIcon);
                background = itemView.findViewById(R.id.background);
            }
        }

        @Override
        public void onClick(View view) {
            mainActivity.selectItem(getAdapterPosition() - 1);
            selectedItem = getAdapterPosition();
            notifyDataSetChanged();
        }
    }

    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.drawer_item, parent, false);

            return new ViewHolder(v, viewType);

        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.drawer_header, parent, false);

            return new ViewHolder(v, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DrawerAdapter.ViewHolder holder, int position) {
        if (!isPositionHeader(position)) {
            holder.itemTitle.setText(drawerTitles[position - 1]);

            holder.itemIcon.setText(drawerIcon[position - 1]);
            if (selectedItem == position) {
                holder.background.setVisibility(View.VISIBLE);
            } else {
                holder.background.setVisibility(View.GONE);
            }
        }
        holder.itemView.setSelected(false);
    }

    @Override
    public int getItemCount() {
        return drawerTitles.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}