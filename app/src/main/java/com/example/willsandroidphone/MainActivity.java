package com.example.willsandroidphone;

import android.os.Bundle;

import com.appboy.Appboy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mparticle.MPEvent;
import com.mparticle.MParticle;
import com.mparticle.MParticle.ServiceProviders;
import com.mparticle.MParticleOptions;
import com.mparticle.commerce.CommerceEvent;
import com.mparticle.commerce.Product;
import com.mparticle.commerce.TransactionAttributes;
import com.mparticle.identity.IdentityApiRequest;
import com.mparticle.kits.OptimizelyKit;
import com.mparticle.kits.optimizely.*;
import com.optimizely.ab.Optimizely;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        MParticleOptions options = MParticleOptions.builder(this)
                /////replace with MP API Key and Secret
                .credentials("xxxxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .environment(MParticle.Environment.Development)
                .logLevel(MParticle.LogLevel.VERBOSE)
                .identify(
                        IdentityApiRequest.withEmptyUser()
                                /////////////// replace with user email and customer id
                                .email("xxxxxxxxxxxxxxx")
                                .customerId("xxxxxxxx")
                                .build()
                )
                .build();
        MParticle.start(options);


            if (MParticle.getInstance().isKitActive(ServiceProviders.OPTIMIZELY)) {
                System.out.println("This will be printed");
            }





    }





    public void logSearchEvent(View view){
        Map<String, String> eventInfo = new HashMap<String, String>();
        eventInfo.put("category", "Destination Intro");
        eventInfo.put("Search_Test_Key", "Search_Value");

        MPEvent event = new MPEvent.Builder("Search_Test", MParticle.EventType.Search)
                .customAttributes(eventInfo)
                .build();

        MParticle.getInstance().logEvent(event);

    }

    public void logNavEvent(View view){
        Map<String, String> eventInfo = new HashMap<String, String>();
        eventInfo.put("Nav_Test_Key", "Nav_Value");

        MPEvent event = new MPEvent.Builder("Nav_Test", MParticle.EventType.Navigation)
                .customAttributes(eventInfo)
                .build();

        MParticle.getInstance().logEvent(event);

    }

    public void logPurchaseEvent(View view){
        Product product = new Product.Builder("Double Room - Econ Rate", "econ-1", 100.00)
                .quantity(4.0)
                .build();

        TransactionAttributes attributes = new TransactionAttributes("foo-transaction-id")
                .setRevenue(430.00)
                .setTax(30.00);

        CommerceEvent event = new CommerceEvent.Builder(Product.PURCHASE, product)
                .transactionAttributes(attributes)
                .build();

        MParticle.getInstance().logEvent(event);

    }

    public void logout(View view){
        MParticle.getInstance().Identity().logout();

    }

    public void login(View view){
        IdentityApiRequest identityRequest = IdentityApiRequest.withEmptyUser()
                .email("ad@mp.com")
                .customerId("2893498")
                .build();

        MParticle.getInstance().Identity().login(identityRequest);

    }

}
