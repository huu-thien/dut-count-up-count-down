package com.example.count_up_cout_down;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.count_up_cout_down.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyViewModel model;

    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        model = new ViewModelProvider(this).get(MyViewModel.class);

        model.getNumber().observe(this, number -> {
            binding.tvCount.setText(number.toString());
        });

        model.getNumberList().observe(this, numberList -> {
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numberList);
            binding.lvCount.setAdapter(arrayAdapter);
        });

        binding.lvCount.setAdapter(arrayAdapter);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.increaseNumber();
                arrayAdapter.notifyDataSetChanged();
            }
        });

        binding.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.decreaseNumber();
                arrayAdapter.notifyDataSetChanged();
            }
        });

        binding.lvCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("value", ((TextView) view).getText());
                intent.putExtra("index", i);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String newValue = data.getStringExtra("value");
                int index = data.getIntExtra("index", 0);
                model.getNumberList().observe(this, numberList -> {
                    numberList.set(index, newValue);
                    arrayAdapter.notifyDataSetChanged();
                });
            }
        }
    }
}