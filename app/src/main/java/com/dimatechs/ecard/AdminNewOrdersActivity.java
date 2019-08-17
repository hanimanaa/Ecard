package com.dimatechs.ecard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dimatechs.ecard.Interface.ItemClickListner;
import com.dimatechs.ecard.Model.AdminOrders;
import com.dimatechs.ecard.Model.Products;
import com.dimatechs.ecard.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminNewOrdersActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);


        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        ordersList=findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("aaaa", "onstart");

        FirebaseRecyclerOptions<AdminOrders> options=
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef,AdminOrders.class)
                        .build();
        Log.d("aaaa","op");

        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter=
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model)
                    {
                        Log.d("aaaa","holder");
                        holder.userName.setText(model.getName());
                        holder.userPhoneNumber.setText(model.getPhone());
                        holder.userTotalPrice.setText(" מחיר : " +model.getTotalAmount() + " ש\"ח ");
                        holder.userDateTime.setText(model.getDate()+" "+model.getTime());
                        holder.userAdress.setText("חיפה");

                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String uID = getRef(position).getKey();
                                Intent intent = new Intent(AdminNewOrdersActivity.this,AdminUserProductsActivity.class);
                                intent.putExtra("uid",uID);
                                startActivity(intent);
                            }
                        });



                    }
                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
                    {
                        Log.d("aaaa","onCreateViewHolder");

                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                        AdminOrdersViewHolder holder = new AdminOrdersViewHolder(view);
                        return holder;
                    }
                };
        ordersList.setAdapter(adapter);
        adapter.startListening();


    }


    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName,userPhoneNumber,userTotalPrice,userDateTime,userAdress;
        public Button showOrdersBtn;

        public AdminOrdersViewHolder(View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = (TextView) itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = (TextView) itemView.findViewById(R.id.order_total_price);
            userDateTime = (TextView) itemView.findViewById(R.id.order_date_time);
            userAdress = (TextView) itemView.findViewById(R.id.order_address_city);
            showOrdersBtn = (Button) itemView.findViewById(R.id.show_all_products_btn);




        }
    }
}
