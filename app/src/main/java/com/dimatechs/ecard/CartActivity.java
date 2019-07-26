package com.dimatechs.ecard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dimatechs.ecard.Model.Cart;
import com.dimatechs.ecard.Model.Products;
import com.dimatechs.ecard.Prevalent.Prevalent;
import com.dimatechs.ecard.ViewHolder.CartViewHolder;
import com.dimatechs.ecard.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity
{
    private  RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextprocessBtn;
    private TextView txtTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView=(RecyclerView)findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextprocessBtn=(Button)findViewById(R.id.next_process_btn);
        txtTotalAmount=(TextView)findViewById(R.id.total_price);
    }

    @Override
    protected void onStart()
    {
            super.onStart();
            final DatabaseReference CartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        Log.d("cart", "onStart");
        FirebaseRecyclerOptions<Cart> options=
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(CartListRef.child("User View")
                        .child(Prevalent.currentOnlineUser.getPhone())
                        .child("Products"),Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model)
                    {
                        Log.d("cart", "onBindViewHolder");
                        holder.txtProductName.setText(model.getName());
                        holder.txtProductPrice.setText( model.getPrice());
                        holder.txtProductQuantity.setText(" כמות : " + model.getQuantity());
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        Log.d("cart", "onCreateViewHolder");

                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
