package com.example.catalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class orders extends AppCompatActivity implements PaymentResultListener {
    private RecyclerView recycle2;
    TextView textview2;
    Button button2;
    Model s1;
    public String usertoken="";
    public ArrayList<items> p=new ArrayList<items>();
    public ArrayList<orderslist> p1=new ArrayList<orderslist>();
    private FirebaseDatabase db=FirebaseDatabase.getInstance();
    private DatabaseReference root=db.getReference().child("Orders");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recycle2=findViewById(R.id.recycle2);
        textview2=findViewById(R.id.textView2);
        button2=findViewById(R.id.button2);

        p = (ArrayList<items>) getIntent().getSerializableExtra("key");
        recycle2.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter1 c=new CustomAdapter1(this,p);
        recycle2.setAdapter(c);
        String cost=getIntent().getStringExtra("key2");
        textview2.setText("TOTAL COST:"+cost);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startPayment(cost);
//                Intent intent=new Intent(orders.this,TrackOrder.class);
//                ArrayList<Model> vt=new ArrayList<>();
//                vt.add(s1);
//                Toast.makeText(orders.this, vt.get(0).customer, Toast.LENGTH_SHORT).show();
//                intent.putExtra("key6",vt);
              //  startActivity(intent);

            }
        });
    }

    public void startPayment(String amt) {

        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_odYsNwFtPWXq1p");
        /**
         * Set your logo here
         */
       // checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */

          int k=Integer.parseInt(amt)*100;
          String l=Integer.toString(k);
        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "SMART RESTAURANT");
            options.put("description", "PAY TO RESTAURANT");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
           // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", l );//pass amount in currency subunits
//            options.put("prefill.email", "gaurav.kumar@example.com");
//            options.put("prefill.contact","9988776655");
//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);

            checkout.open(this, options);

        } catch(Exception e) {
            //Log.e(TAG, "Error in starting Razorpay Checkout", e);
            Toast.makeText(this, "Error in starting Razorpay Checkout", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s)
    {


        setvalues();
        Toast.makeText(this, "PAYMENT SUCESSS", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(orders.this,TrackOrder.class);

        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable)p1);
        intent.putExtra("BUNDLE",args);
        Toast.makeText(this, s1.idg, Toast.LENGTH_SHORT).show();
        intent.putExtra("keykey",s1.idg);

              //  intent.putExtra("key6",p1);
         startActivity(intent);
        //startActivity(new Intent(orders.this,TrackOrder.class));

    }

    private void setvalues()
    {

        HashMap<String,Integer> v=new HashMap<>();
        for(int i=0;i<p.size();i++)
        {
            String str=p.get(i).name;
            if(v.containsKey(str))
            {
                Integer sno=v.get(str);
                v.put(str,sno+1);
            }
            else
            {
                v.put(str,1);
            }
        }

        for (String name :v.keySet())
        {
            Integer sno=v.get(name);
            orderslist s1=new orderslist(name ,sno);
            p1.add(s1);


        }
        s1=new Model("UDAVITY",p1,"");


        getToken();
        Toast.makeText(this,"gettoken-"+s1.token, Toast.LENGTH_SHORT).show();
     //  s1.token=usertoken;

//        s1.idg=root.push().getKey();
//        root.child(s1.idg).setValue(s1).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull  Task<Void> task) {
//                Toast.makeText(orders.this, "ORDER PLACED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });




    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "PAYMENT FAILED", Toast.LENGTH_SHORT).show();

    }


    public void getToken()
    {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return ;
                        }

                        // Get new FCM registration token
                        String token1 = task.getResult();
                        s1.token=token1;
                        s1.idg=root.push().getKey();
                        root.child(s1.idg).setValue(s1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull  Task<Void> task) {
                                Toast.makeText(orders.this, "ORDER PLACED SUCCESSFULLY", Toast.LENGTH_SHORT).show();


                            }
                        });

                        Toast.makeText(orders.this, "s1.token"+s1.token, Toast.LENGTH_SHORT).show();

                    }
                });


    }




}