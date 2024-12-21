package com.deificindia.indifun.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deificindia.indifun.vm.modal.LoginVmModal;

public class LoginVm extends ViewModel {
    private final MutableLiveData<LoginVmModal> selectedItem = new MutableLiveData<LoginVmModal>();

    public void sendData(LoginVmModal item) {
        selectedItem.setValue(item);
    }

    public LiveData<LoginVmModal> livelistener() {
        return selectedItem;
    }

}
