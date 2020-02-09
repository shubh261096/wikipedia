package com.enguru.wikipedia.view.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enguru.wikipedia.R;
import com.enguru.wikipedia.service.model.search.PagesItem;
import com.squareup.picasso.Picasso;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    private final List<PagesItem> pagesItemList;
    private OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgViewThumbnail)
        ImageView imgViewThumbnail;
        @BindView(R.id.txtViewTitle)
        TextView txtViewTitle;
        @BindView(R.id.txtViewDescription)
        TextView txtViewDescription;

        ViewHolder(final View itemView, final OnItemClickListener clickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public MainActivityAdapter(List<PagesItem> pagesItemList, OnItemClickListener onItemClickListener) {
        this.pagesItemList = pagesItemList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        PagesItem pagesItem = getItem(position);
        if (!TextUtils.isEmpty(pagesItem.getTitle())) {
            viewHolder.txtViewTitle.setText(pagesItem.getTitle());
        }
        if (pagesItem.getTerms() != null && pagesItem.getTerms().getDescription() != null) {
            if (!TextUtils.isEmpty(pagesItem.getTerms().getDescription().get(0))) {
                viewHolder.txtViewDescription.setText(pagesItem.getTerms().getDescription().get(0));
            } else {
                viewHolder.txtViewDescription.setText("");
            }
        } else {
            viewHolder.txtViewDescription.setText("");
        }

        if (pagesItem.getThumbnail() != null && !TextUtils.isEmpty(pagesItem.getThumbnail().getSource())) {
            Picasso.get()
                    .load(pagesItem.getThumbnail().getSource())
                    .into(viewHolder.imgViewThumbnail);
        }
    }

    private PagesItem getItem(int position) {
        return pagesItemList.get(position);
    }

    @Override
    public int getItemCount() {
        return pagesItemList.size();
    }

    public void clearData() {
        pagesItemList.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}