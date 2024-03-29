package com.dimatechs.ecard.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dimatechs.ecard.Interface.ItemClickListner;
import com.dimatechs.ecard.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName,txtProductPrice,txtProductQuantity;
    public ItemClickListner itemClickListner;

    public CartViewHolder(View itemView)
    {
        super(itemView);

        txtProductName = (TextView) itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = (TextView) itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity= (TextView) itemView.findViewById(R.id.cart_product_quantity);

    }

    @Override
    public void onClick(View view)
    {
        itemClickListner.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner listner )
    {
        this.itemClickListner=listner;
    }



}
