package com.alwaysencrypted.demo.view;

public sealed interface CustomerView
        permits VarcharCustomerView, NVarcharCustomerView {

    Long getId();
    String getName();
    String getType();
    String getSsn();
}