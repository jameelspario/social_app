package com.deificindia.indifun.vm.modal;

public class LoginVmModal {
    public int TYPE;
    public String data;

    public LoginVmModal(int TYPE) {
        this.TYPE = TYPE;
    }

    public LoginVmModal(int TYPE, String data) {
        this.TYPE = TYPE;
        this.data = data;
    }
}
