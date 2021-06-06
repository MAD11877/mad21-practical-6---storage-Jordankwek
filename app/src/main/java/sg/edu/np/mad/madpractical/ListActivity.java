package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    DatabaseManager dataManager = new DatabaseManager(this,null, null,1);
    static ArrayList<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        userList = dataManager.getUsers();
//        for(int i=0; i<20; i++)
//        {
//            User u = new User();
//            u.id = i;
//            u.name = "Name" + new Random().nextInt();
//            u.description = "Description " + new Random().nextInt();
//            //Coz sqllite got no boolean. Only 1 and 0
//            u.followed = new Random().nextInt()%2 == 0;
//            dataManager.addUser(u);
//        }

        RecyclerView rv = findViewById(R.id.rv);
        ItemsAdapter itemsAdapter = new ItemsAdapter(userList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(itemsAdapter);
    }
}