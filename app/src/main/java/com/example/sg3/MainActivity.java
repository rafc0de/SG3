package com.example.sg3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private FloatingActionButton fabAdd;
    private MenuAdapter adapter;
    private List<MenuModel> listMenu = new ArrayList<>();

    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(getApplicationContext());
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("menu_makanan");

        rvList = findViewById(R.id.rv_list);
        fabAdd = findViewById(R.id.fab_add);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddMenuActivity.class));
            }
        });

//        MenuModel m1 = new MenuModel("Menu 1",15000,"Ayam Geprek Mercon");
//        MenuModel m2 = new MenuModel("Menu 2",15000,"Ayam Geprek Mercon");
//        MenuModel m3 = new MenuModel("Menu 3",15000,"Ayam Geprek Mercon");
//        MenuModel m4 = new MenuModel("Menu 4",15000,"Ayam Geprek Mercon");
//        MenuModel m5 = new MenuModel("Menu 5",15000,"Ayam Geprek Mercon");
//
//        listMenu.add(0,m1);
//        listMenu.add(1,m2);
//        listMenu.add(2,m3);
//        listMenu.add(3,m4);
//        listMenu.add(4,m5);

        adapter = new MenuAdapter(this);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(adapter);

        getMenu();

    }

    private void getMenu() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMenu.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    MenuModel menuModel = ds.getValue(MenuModel.class);
                    listMenu.add(menuModel);
                }
                adapter.setData(listMenu);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
