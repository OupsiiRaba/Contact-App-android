package com.example.annuairesqliteroom2;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.toolbox.ImageLoader;
import com.example.annuairesqliteroom2.Utils.UniversalImageLoader;
import com.example.annuairesqliteroom2.models.Contact;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements
        ViewContactsFragment.OnContactSelectedListener,
        ContactFragment.OnEditContactListener,
        ViewContactsFragment.OnAddContactListener {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE = 1;

    @Override
    public void onEditcontactSelected(Contact contact) {
        Log.d(TAG, "OnContactSelected: contact selected from "
                + getString(R.string.edit_contact_fragment)
                + " " + contact.getName());

        EditContactFragment fragment = new EditContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.contact), contact);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(getString(R.string.edit_contact_fragment));
        transaction.commit();
    }

    @Override
    public void OnContactSelected(Contact contact) {
        Log.d(TAG, "OnContactSelected: contact selected from "
                + getString(R.string.view_contact_fragment)
                + " " + contact.getName());

        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.contact), contact);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(getString(R.string.view_contact_fragment));
        transaction.commit();
    }


    @Override
    public void onAddContact() {
        Log.d(TAG, "onAddContact: navigating to " + getString(R.string.add_contact_fragment));

        AddContactFragment fragment = new AddContactFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(getString(R.string.add_contact_fragment));
        transaction.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        initImageLoader();

        init();

    }




    /**
     * initialize the first fragment (ViewContactsFragment)
     */
    private void init(){
        ViewContactsFragment fragment = new ViewContactsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // reaplce whatever is in the fragment_container view with this fragment,
        // amd add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    /**
     * Compress a bitmap by the @param "quality"
     * Quality can be anywhere from 1-100 : 100 being the highest quality.
     * @param bitmap
     * @param quality
     * @return
     */
    public Bitmap compressBitmap(Bitmap bitmap, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return bitmap;
    }

    /**
     * Generalized method for asking permission. Can pass any array of permissions
     * @param permissions
     */
    public void verifyPermissions(String[] permissions){
        Log.d(TAG, "verifyPermissions: asking user for permissions.");
        ActivityCompat.requestPermissions(
                MainActivity.this,
                permissions,
                REQUEST_CODE
        );
    }

    /**
     * Checks to see if permission was granted for the passed parameters
     * ONLY ONE PERMISSION MAYT BE CHECKED AT A TIME
     * @param permission
     * @return
     */
    public boolean checkPermission(String[] permission){
        Log.d(TAG, "checkPermission: checking permissions for:" + permission[0]);

        int permissionRequest = ActivityCompat.checkSelfPermission(
                MainActivity.this,
                permission[0]);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermission: \n Permissions was not granted for: " + permission[0]);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: requestCode: " + requestCode);

        switch(requestCode){
            case REQUEST_CODE:
                for(int i = 0; i < permissions.length; i++){
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Log.d(TAG, "onRequestPermissionsResult: User has allowed permission to access: " + permissions[i]);
                    }else{
                        break;
                    }
                }
                break;
        }
    }



}
















