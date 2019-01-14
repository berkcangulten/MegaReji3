package com.example.berkcan.megareji;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MainMenuActivity extends Activity {
    private ListView listViewMainMenu;
    TextView textViewPointMainMenu;
    //Menu
    private String[] menu={"Enter Actor","Show Actor","Enter Place","Show Place","Send Notification","Make Plan","Show Plans","Write Scenario","Show Scenarios","Create or Change Request","Create or Change Post Prodution Report","Enter Set List","Show All Request Report","Leader Board","Sign Out","false","false"};


    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;
    boolean[] accessableMenu;
    ArrayList<String> accessableMenu2;
    ArrayAdapter<String> adapter;
  //  UpdatePoint UpdatePoint2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
      //  UpdatePoint2 =new UpdatePoint();
       // UpdatePoint2.ChangeValue(20);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        myRef=firebaseDatabase.getReference();
        accessableMenu=new boolean[16];
        accessableMenu2=new ArrayList<String>();
        String[] array;


        final TextView textViewPointMainMenu=findViewById(R.id.textViewPointMainMenu);

        myRef.addValueEventListener(new ValueEventListener() {

            //Rearrange the array with removing punctuations and spaces
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String accessable =  dataSnapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("accessablemenu").getValue().toString();
                accessable=accessable.replaceAll(",","");
                accessable=accessable.replaceAll("\\[","");
                accessable=accessable.replaceAll("\\]","");
                textViewPointMainMenu.setText(dataSnapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("score").getValue().toString());
                String[] accesableString= accessable.split(" ");

                //Convert Accessable Menu string to boolean
                for (int i = 0; i < accesableString.length; i++) {
                    accessableMenu[i] = Boolean.parseBoolean(accesableString[i]);
                    if(accessableMenu[i]==true){
                        accessableMenu2.add(menu[i]);

                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Lists the menu

        listViewMainMenu=findViewById(R.id.listViewMainMenu);
         adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,accessableMenu2);
        listViewMainMenu.setAdapter(adapter);

        //Listener for clicked item
        listViewMainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the position and check, then direct the clicked page

                if(accessableMenu2.get(position).equals("Enter Actor")){
                    Intent intent=new Intent(getApplicationContext(),EnterActor.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Show Actor")){
                    Intent intent=new Intent(getApplicationContext(),ShowActor.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Enter Place")){
                    Intent intent=new Intent(getApplicationContext(),EnterPlace.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Show Place")){
                    Intent intent=new Intent(getApplicationContext(),ShowPlace.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Send Notification")){
                    Intent intent=new Intent(getApplicationContext(),SendNotification.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Make Plan")){
                    Intent intent=new Intent(getApplicationContext(),EnterPlan.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Show Plans")){
                    Intent intent=new Intent(getApplicationContext(),ShowPlan.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Write Scenario")){
                    Intent intent=new Intent(getApplicationContext(),WriteScenario.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Show Scenarios")){
                    Intent intent=new Intent(getApplicationContext(),ShowScenario.class);
                    startActivity(intent);
                }

                else if(accessableMenu2.get(position).equals("Create or Change Request")){
                    Intent intent=new Intent(getApplicationContext(),CreateorChangeRequestReport.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Leader Board")){
                    Intent intent=new Intent(getApplicationContext(),LeaderBoard.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Create or Change Post Prodution Report")){
                    Intent intent=new Intent(getApplicationContext(),CreateorChangePostProReport.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Enter Set List")){
                    Intent intent=new Intent(getApplicationContext(),EnterSetList.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Sign Out")){
                    mAuth.signOut();
                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }
                else if(accessableMenu2.get(position).equals("Show All Request Report")){

                    Intent intent=new Intent(getApplicationContext(),ShowAllRequestReport.class);
                    startActivity(intent);
                }



            }
        });
    }
    public void getDataFromFirebase() {



    }
}
