package com.example.count_up_cout_down;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Integer> number;
    private MutableLiveData<ArrayList<String>> numberList;

    public LiveData<Integer> getNumber() {
        if (number == null) {
            number = new MutableLiveData<>();
            number.setValue(0);
        }
        return number;
    }

    public LiveData<ArrayList<String>> getNumberList() {
        if (numberList == null) {
            numberList = new MutableLiveData<>();
            numberList.setValue(new ArrayList<>());
        }
        return numberList;
    }

    public void increaseNumber() {
        number.setValue(number.getValue() + 1);
        ArrayList<String> newNumberList = numberList.getValue();
        newNumberList.add(number.getValue().toString());
        numberList.setValue(newNumberList);
    }

    public void decreaseNumber() {
        number.setValue(number.getValue() - 1);
        ArrayList<String> newNumberList = numberList.getValue();
        newNumberList.add(number.getValue().toString());
        numberList.setValue(newNumberList);
    }
}
