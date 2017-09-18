package com.example.android.hw2;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;



public class NewActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuSelected = item.getItemId();
        switch (menuSelected) {
            case R.id.Back:
                change();
                break;
        }
        return true;
    }
    public void change()
    {
        Toast.makeText(this, "Back clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(NewActivity.this, MainActivity.class);
        startActivity(intent);
        NewActivity.this.finish();
    }
}

