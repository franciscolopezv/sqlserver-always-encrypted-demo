package com.alwaysencrypted.demo.view;

import com.alwaysencrypted.demo.model.CustomerNVarchar;
import com.alwaysencrypted.demo.model.CustomerVarchar;

public final class NVarcharCustomerView implements CustomerView{

    private final CustomerNVarchar customer;

    public NVarcharCustomerView(CustomerNVarchar customer) {
        this.customer = customer;
    }

    @Override
    public Long getId() {
        return customer.getId();
    }

    @Override
    public String getName() {
        return customer.getName();
    }

    @Override
    public String getType() {
        return "NVARCHAR";
    }

    @Override
    public String getSsn() {
        return customer.getSsn();
    }
}
