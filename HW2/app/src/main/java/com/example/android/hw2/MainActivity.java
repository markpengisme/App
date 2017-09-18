package com.example.android.hw2;
import com.example.android.hw2.NewActivity;
import android.content.Intent;
import android.view.Menu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuSelected = item.getItemId();
        switch (menuSelected) {
            case R.id.NewActivity:
                change();
                break;
        }
        return true;
    }
    public void change()
    {
        Toast.makeText(this, "NewActivity clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, NewActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

}

