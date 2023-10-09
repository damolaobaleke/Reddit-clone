package com.tti.wavehealth.views.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tti.wavehealth.R;
import com.tti.wavehealth.models.comment.CommentChildren;

import java.util.List;

public class RecyclerViewAdapterComments extends RecyclerView.Adapter<RecyclerViewAdapterComments.CommentsViewHolder> {
    List<CommentChildren> childrenList;

    public RecyclerViewAdapterComments(List<CommentChildren> childrenList) {
        this.childrenList = childrenList;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_comment_view, parent, false);
        CommentsViewHolder commentsViewHolder = new CommentsViewHolder(view);
        return commentsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        CommentChildren child = childrenList.get(position);

        //body key == comment
        if(child.getData().get("body") != "" || child.getData().get("body") != null) {
            holder.comment.setText(String.valueOf(child.getData().get("body")));
        }
    }

    @Override
    public int getItemCount() {
        return childrenList.size();
    }

    static class CommentsViewHolder extends RecyclerView.ViewHolder{

        TextView comment;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);

            comment= itemView.findViewById(R.id.comment);

        }
    }
}
