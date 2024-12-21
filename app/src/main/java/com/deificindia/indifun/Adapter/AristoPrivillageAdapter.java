package com.deificindia.indifun.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deificindia.indifun.R;
import com.deificindia.indifun.bindingmodals.aristocracycenterprivilage.AristoPrivileges;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.deificindia.indifun.Utility.URLs.PRIVILEGES;

public class AristoPrivillageAdapter extends RecyclerView.Adapter<AristoPrivillageAdapter.AristoHolder> {

    List<AristoPrivileges> aristoPrivileges;

    public AristoPrivillageAdapter(List<AristoPrivileges> aristoPrivileges) {
        this.aristoPrivileges = aristoPrivileges;
    }

    @NonNull
    @Override
    public AristoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AristoHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_aristo_privillage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AristoHolder holder, int position) {
        holder.bind(aristoPrivileges.get(position));
    }

    @Override
    public int getItemCount() {
        return aristoPrivileges.size();
    }

    class AristoHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tv;

        public AristoHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tv = itemView.findViewById(R.id.text1);
        }

        void bind(AristoPrivileges res){
            Picasso.get().load(PRIVILEGES+ res.getCover())
                    .into(imageView);
            tv.setText(res.getTitle());
        }
    }
}
