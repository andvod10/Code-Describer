package com.research.codedescriber.ui.modify;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ModifyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ModifyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is modify fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}