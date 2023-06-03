package com.example.pizza;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroupSize;
    private RadioButton radioButtonSmall, radioButtonMedium, radioButtonLarge;
    private Spinner spinnerFlavors;
    private CheckBox checkBoxBorda, checkBoxRefrigerante;
    private Button buttonAdd, buttonRemove;
    private TextView textviewOrderSummary;

    private List<String> availableFlavors;
    private List<String> selectedFlavors;
    private int pizzaPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupSize = findViewById(R.id.radio_group_size);
        radioButtonSmall = findViewById(R.id.radio_button_small);
        radioButtonMedium = findViewById(R.id.radio_button_medium);
        radioButtonLarge = findViewById(R.id.radio_button_large);
        spinnerFlavors = findViewById(R.id.spinner_flavors);
        checkBoxBorda = findViewById(R.id.checkbox_borda);
        checkBoxRefrigerante = findViewById(R.id.checkbox_refrigerante);
        buttonAdd = findViewById(R.id.button_add);
        buttonRemove = findViewById(R.id.button_remove);
        textviewOrderSummary = findViewById(R.id.textview_order_summary);

        availableFlavors = new ArrayList<>();
        availableFlavors.add("Pepperoni");
        availableFlavors.add("Margherita");
        availableFlavors.add("Quatro Queijos");
        availableFlavors.add("Calabresa");
        availableFlavors.add("Frango com Catupiry");
        availableFlavors.add("Portuguesa");

        selectedFlavors = new ArrayList<>();
        pizzaPrice = 0;

        ArrayAdapter<String> flavorsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, availableFlavors);
        spinnerFlavors.setAdapter(flavorsAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPizza();
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePizza();
            }
        });
    }

    private void addPizza() {
        int selectedSizeId = radioGroupSize.getCheckedRadioButtonId();
        String selectedSize = "";
        int maxFlavors = 0;

        switch (selectedSizeId) {
            case R.id.radio_button_small:
                selectedSize = "Pequena";
                pizzaPrice = 20;
                maxFlavors = 1;
                break;
            case R.id.radio_button_medium:
                selectedSize = "Média";
                pizzaPrice = 30;
                maxFlavors = 2;
                break;
            case R.id.radio_button_large:
                selectedSize = "Grande";
                pizzaPrice = 40;
                maxFlavors = 4;
                break;
        }

        if (selectedSize.equals("")) {
            Toast.makeText(MainActivity.this, "Selecione o tamanho da pizza", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedFlavors.size() >= maxFlavors) {
            Toast.makeText(MainActivity.this, "Você já atingiu o limite de sabores para este tamanho de pizza", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedFlavor = spinnerFlavors.getSelectedItem().toString();
        selectedFlavors.add(selectedFlavor);
        pizzaPrice += 5;

        if (checkBoxBorda.isChecked()) {
            pizzaPrice += 10;
        }

        if (checkBoxRefrigerante.isChecked()) {
            pizzaPrice += 5;
        }

        updateOrderSummary(selectedSize);
    }

    private void removePizza() {
        if (selectedFlavors.isEmpty()) {
            Toast.makeText(MainActivity.this, "Não há nenhuma pizza para remover", Toast.LENGTH_SHORT).show();
            return;
        }

        selectedFlavors.clear();
        pizzaPrice = 0;
        checkBoxBorda.setChecked(false);
        checkBoxRefrigerante.setChecked(false);
        updateOrderSummary("");
        spinnerFlavors.setSelection(0);
    }

    private void updateOrderSummary(String size) {
        StringBuilder orderSummary = new StringBuilder("Seu Pedido:\n");

        if (!size.equals("")) {
            orderSummary.append("Tamanho: ").append(size).append("\n");
        }

        if (selectedFlavors.isEmpty()) {
            orderSummary.append("Nenhum sabor selecionado");
        } else {
            orderSummary.append("Sabores:\n");
            for (String flavor : selectedFlavors) {
                orderSummary.append("- ").append(flavor).append("\n");
            }
        }

        if (checkBoxBorda.isChecked()) {
            orderSummary.append("Borda adicionada\n");
        }

        if (checkBoxRefrigerante.isChecked()) {
            orderSummary.append("Refrigerante adicionado\n");
        }

        orderSummary.append("Preço: R$").append(pizzaPrice).append(".00");

        textviewOrderSummary.setText(orderSummary.toString());
    }
}
