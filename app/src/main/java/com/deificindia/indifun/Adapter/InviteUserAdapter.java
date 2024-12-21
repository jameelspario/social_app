package com.deificindia.indifun.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.Model.ModalInviteUser;
import com.deificindia.indifun.R;

import java.util.List;

public class InviteUserAdapter extends RecyclerView.Adapter<InviteUserAdapter.Holderclass> {

    private List<ModalInviteUser> userLisrt;
    private OnInviteClicked _listener;

    public InviteUserAdapter(List<ModalInviteUser> userLisrt) {
        this.userLisrt = userLisrt;
    }

    @NonNull
    @Override
    public Holderclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invited_user, parent, false);
        return new Holderclass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holderclass holder, int position) {
        holder.bind(userLisrt.get(position));
    }

    @Override
    public int getItemCount() {
        return userLisrt.size();
    }

    class Holderclass extends RecyclerView.ViewHolder{

        TextView tvUsrName;
        Button btnInvite;

        public Holderclass(@NonNull View itemView) {
            super(itemView);
            tvUsrName = itemView.findViewById(R.id.tv_user);
            btnInvite = itemView.findViewById(R.id.btn_invite);
            btnInvite.setOnClickListener(v ->{
                if(_listener!=null) _listener.onInvite(userLisrt.get(getAdapterPosition()));
            });
        }

        void bind(ModalInviteUser data){
            tvUsrName.setText(data.getFull_name());
        }
    }

    public void updateUser(List<ModalInviteUser> list){
        this.userLisrt = list;
        notifyDataSetChanged();
    }

    public interface OnInviteClicked{
        void onInvite(ModalInviteUser data);
    }

    public void setOnInviteClicked(OnInviteClicked listener){
        this._listener = listener;
    }

}
