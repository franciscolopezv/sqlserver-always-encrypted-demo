package com.alwaysencrypted.demo.view;

import com.alwaysencrypted.demo.model.CustomerVarchar;

public final class VarcharCustomerView implements CustomerView{

    private final CustomerVarchar customer;

    public VarcharCustomerView(CustomerVarchar customer) {
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
        return "VARCHAR";
    }

    @Override
    public String getSsn() {
        return customer.getSsn();
    }
}
