package com.dimatechs.ecard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dimatechs.ecard.Model.Products;
import com.dimatechs.ecard.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener  {

    private Button RegisterAccountBtn;
    private FloatingActionButton btn;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

     //   RegisterAccountBtn = (Button) findViewById(R.id.button1);
      //  RegisterAccountBtn.setOnClickListener(this);


        btn = (FloatingActionButton) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onClick(View view) {
        if(view==RegisterAccountBtn) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }
        else if (view==btn){
            Toast.makeText(HomeActivity.this, "סל קניות", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ShopCardActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerOptions<Products> options=
                new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef,Products.class)
                .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtProductName.setText(model.getName());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText(" מחיר : " +model.getPrice() + " ש\"ח ");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Intent intent = new Intent(HomeActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                                //test
                            }
                        });
                    }

                    @Override
                    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
                    {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Register) {
            Toast.makeText(this,"you selected הרשמה",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_Update) {
            Toast.makeText(this, "you selected עדכון", Toast.LENGTH_LONG).show();
            return true;
        }

        else if (id == R.id.action_Admin) {
                Toast.makeText(this,"you selected מנהל",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),NewProductActivity.class);
                startActivity(intent);
                return true;
        }

        else if (id == R.id.action_Exit) {
            Toast.makeText(this,"you selected יציאה",Toast.LENGTH_LONG).show();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return true;
        }
        return true;
    }
}


