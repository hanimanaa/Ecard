package com.dimatechs.ecard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private Button confirmOrderBtn;
    private String totalAmount="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount=getIntent().getStringExtra("Total Price");

        confirmOrderBtn=(Button)findViewById(R.id.confirm_final_order_btn);
    }
}
