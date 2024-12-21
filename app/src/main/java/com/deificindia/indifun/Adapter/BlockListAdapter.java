package com.deificindia.indifun.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.abs_plugs.BlockList;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.rest.RestWork;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun.rest.RetroCallListener.BLOCKUSER;

public class BlockListAdapter extends RecyclerView.Adapter<BlockListAdapter.Holder> {

    List<BlockList> blockLists = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_block_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) { holder.bind(blockLists.get(position)); }

    @Override
    public int getItemCount() { return blockLists.size(); }

    class Holder extends RecyclerView.ViewHolder{
        CircleImageView avtar;
        TextView name, unblock;


        public Holder(@NonNull View itemView) {
            super(itemView);
            avtar = itemView.findViewById(R.id.avtar);
            unblock = itemView.findViewById(R.id.button);
            name = itemView.findViewById(R.id.name);
        }

        void bind(BlockList bl){
            Picasso.get().load(URLs.UserImageBaseUrl+bl.getProfile_picture()).into(avtar);
            name.setText(bl.getFull_name());

            unblock.setOnClickListener(v->{
                unblock.setEnabled(false);
                if(bl.is_blocked==1){
                    blockUnBlock(bl,4);
                }else blockUnBlock(bl ,1);

            });
        }

        void blockUnBlock(BlockList bl ,int type){
            RetroCalls.instance().callType(BLOCKUSER)
                    .withUID()
                    .stringParam(bl.getId())
                    .intParam(type)
                    .blockuser((a,b)->{
                        if(b!=null && b.status==1){

                            if(type==1){
                                RestWork.changeTextView(unblock, 0);
                                bl.is_blocked=0;
                            }else {
                                RestWork.changeTextView(unblock, 1);
                                bl.is_blocked=1;
                            }
                            unblock.setEnabled(true);
                        }
                    });
        }

    }



    public void updateData(List<BlockList> blockLists){
        this.blockLists = blockLists;
        notifyDataSetChanged();
    }






}
