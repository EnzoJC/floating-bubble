package com.abex.floating_bubble.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abex.floating_bubble.FloatingBubbleConfig;
import com.abex.floating_bubble.R;
import com.abex.floating_bubble.dto.ItemDTO;

import java.util.List;

import lombok.Getter;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    public static final String TAG = CustomAdapter.class.getSimpleName();
    private List<ItemDTO> listaItemDTO;
    private FloatingBubbleConfig config;

    public CustomAdapter(List<ItemDTO> listaItemDTO, FloatingBubbleConfig config) {
        this.listaItemDTO = listaItemDTO;
        this.config = config;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.floating_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(listaItemDTO.get(position).getName());
        final int pos = position;
        viewHolder.getImageView().setImageDrawable(config.getListaItemDTO().get(pos).getIcon());
        viewHolder.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaItemDTO.get(pos).getBubbleCommand().execute();
            }
        });
        viewHolder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaItemDTO.get(pos).getBubbleCommand().execute();
            }
        });
    }

    private void execute(int pos) {
        Log.i(TAG, listaItemDTO.get(pos).getName());
    }

    @Override
    public int getItemCount() {
        return listaItemDTO.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final @Getter TextView textView;
        private final @Getter LinearLayout linearLayout;
        private final @Getter ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.tvItemText);
            linearLayout = view.findViewById(R.id.item);
            imageView = view.findViewById(R.id.itemIcon);
        }
    }
}
