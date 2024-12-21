package com.deificindia.indifun.Payment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.deificindia.indifun.Activities.RechargeCoins;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.deificpk.modals.MusicInfo;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.LoadInterface;
import com.deificindia.indifun.rest.PaymentPost;
import com.deificindia.indifun.rest.RetroCallListener;
import com.deificindia.indifun.rest.RetroConfig2;
import com.razorpay.Checkout;
import com.razorpay.CheckoutActivity;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment extends AppCompatActivity implements PaymentResultListener {

    private Button startpayment;
    private EditText orderamount,orderemail,ordermobile;
    private TextView uniqid;
    private String TAG =" main";
    float costofcoin=0.75f;
    int totalcostofcoin,totalcoin,paid_id;
    String email,mobile;
    String userid,transaction_id;
    Bitmap sr;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Result result = IndifunApplication.getInstance().getCredential();
        startpayment = (Button) findViewById(R.id.startpayment);
        orderamount = (EditText) findViewById(R.id.orderamount);
        orderemail=findViewById(R.id.edt_email);
        orderemail.setEnabled(false);
        ordermobile=findViewById(R.id.mobile);
        uniqid=findViewById(R.id.uniqid);
        email=result.getEmail();
        mobile=result.getContact();
        userid=result.getId();
          back=findViewById(R.id.back);
          back.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  finishAffinity();
              }
          });
        uniqid.setText(result.getUid());
        orderemail.setText(result.getFullName());
        Intent getintent=getIntent();

        totalcoin=getIntent().getIntExtra("mycoin",0);
        totalcostofcoin=getintent.getIntExtra("mycoin_amount",0);
        paid_id=getintent.getIntExtra("myid",0);


        System.out.println("coincount"+totalcoin);
         if(totalcoin==0){
             ordermobile.setHint("Coins");
         }else{
             ordermobile.setHint(""+totalcoin);
         }
        if(totalcostofcoin==0){
            orderamount.setText("Amounts");
        }else{
            orderamount.setText(""+totalcostofcoin);
        }

      //  orderamount.setText(""+totalcostofcoin);
        orderamount.setEnabled(false);

         ordermobile.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
         try{
             if(!ordermobile.getText().toString().isEmpty()){
                 totalcoin = Integer.parseInt(ordermobile.getText().toString());
                 float amount=(float)(totalcoin)* costofcoin;
                 totalcostofcoin = Math.round(amount);
                 orderamount.setText(""+totalcostofcoin);
             }

             else{

                 orderamount.setText("0");
             }

         }catch(Exception e){

         }

         }

         @Override
         public void afterTextChanged(Editable s) {

         }
     });


        startpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderamount.getText().toString().equals("") && Integer.parseInt(ordermobile.getText().toString())<1)
                {
                    Toast.makeText(Payment.this, "Minimum required 1 INR ", Toast.LENGTH_LONG).show();
                }else {
                    final CharSequence[] choice = {"Razor Payment","Stripe Payment"};

                    final int[] from = new int[1]; //This must be declared as global !

                    AlertDialog.Builder alert = new AlertDialog.Builder(Payment.this);
                    alert.setTitle("Pay Amount");
                    alert.setCancelable(false);
                    alert.setSingleChoiceItems(choice, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (choice[which] == "Razor Payment") {
                                from[0] = 1;
                                startPayment();
                            } else if (choice[which] == "Stripe Payment") {
                                from[0] = 2;
                            }
                        }
                    });
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.show();

                }
            }
        });
    }


    public void payment_method(){

        RetroConfig2.createService(LoadInterface.class,IndifunApplication.getInstance().getCredential().user_token)
                .Coin_purchase(userid,totalcoin,paid_id,transaction_id,totalcostofcoin)
                .enqueue(new Callback<ObjectModal<PaymentPost>>() {
                    @Override
                    public void onResponse(Call<ObjectModal<PaymentPost>> call, Response<ObjectModal<PaymentPost>> response) {
//                        if(onSuccessListenerV2!=null){
//                            onSuccessListenerV2.onSuccessResult(call_type, response.body());
//                        }

                        Toast.makeText(Payment.this, ""+response, Toast.LENGTH_SHORT).show();
                        System.out.println(response);
                      Log.d("respon",String.valueOf(response));
                    }

                    @Override
                    public void onFailure(Call<ObjectModal<PaymentPost>> call, Throwable t) {
//                        if(onFail!=null){
//                            onFail.onError(call_type, 1, t.getMessage());
//                        }

                    }
                });
    }


    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Indifun");
            options.put("description", "App Payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            String payment = orderamount.getText().toString();
            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", mobile);
            co.setKeyID("rzp_test_mCSvwhtwxKprWT");
            options.put("prefill", preFill);

            co.open(activity, options);



        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        // payment successfull pay_DGU19rDsInjcF2
        Log.e(TAG, " payment successfull "+ s.toString());
        transaction_id=s.toString();
//        paid_id=s.toString();
        payment_method();

        AlertDialog.Builder alert = new AlertDialog.Builder(Payment.this);
        alert.setTitle("Payment Success");
        alert.setIcon(R.drawable.checkmark3);
        alert.setCancelable(false);
        alert.setMessage("You have paid for "+totalcoin+" coin,start sending gift ,your transaction id is "+s);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

           dialog.dismiss();
           finish();
            }
        });
        alert.show();







    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e(TAG,  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }

    }

}