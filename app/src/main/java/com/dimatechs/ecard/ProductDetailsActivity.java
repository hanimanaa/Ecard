package com.dimatechs.ecard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.dimatechs.ecard.Model.Products;
import com.dimatechs.ecard.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

   // private FloatingActionButton addToCartBtn;
    private Button addToCartBtn;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription,productName;
    private String productID="";
    private String PPrice="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID=getIntent().getStringExtra("pid");

        addToCartBtn=(Button)findViewById(R.id.pd_add_to_cart_btn);
        numberButton=(ElegantNumberButton)findViewById(R.id.number_btn);
        productImage=(ImageView)findViewById(R.id.product_image_details);
        productName=(TextView) findViewById(R.id.product_name_details);
        productDescription=(TextView)findViewById(R.id.product_description_details);
        productPrice=(TextView)findViewById(R.id.product_price_details);

        getProductDetails(productID);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                addingToCartList();

            }
        });

    }

    private void addingToCartList()
    {
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        final DatabaseReference carListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String,Object> cartMap =  new HashMap<>();
        cartMap.put("pid",productID);
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("name",productName.getText().toString());
        cartMap.put("price",PPrice);
        cartMap.put("quantity",numberButton.getNumber());

         carListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                 .child("Products").child(productID)
                 .updateChildren(cartMap)
                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task)
                     {
                            if(task.isSuccessful())
                            {
                                carListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                        .child("Products").child(productID)
                                        .updateChildren(cartMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(ProductDetailsActivity.this, "הוסף לסל - המשך בקניה", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            }

                                        });
                            }
                     }
                 });


    }

    private void getProductDetails(String productID)
    {
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Products products=dataSnapshot.getValue(Products.class);

                    productName.setText(products.getName());
                    productPrice.setText(" מחיר : " +products.getPrice()+ " ש\"ח ");
                    PPrice=products.getPrice();
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
