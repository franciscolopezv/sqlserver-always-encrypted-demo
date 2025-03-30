package com.alwaysencrypted.demo.controller;

import com.alwaysencrypted.demo.dto.CreateCustomerRequest;
import com.alwaysencrypted.demo.model.CustomerNVarchar;
import com.alwaysencrypted.demo.model.CustomerVarchar;
import com.alwaysencrypted.demo.repository.CustomerNVarcharRepository;
import com.alwaysencrypted.demo.repository.CustomerVarcharRepository;
import com.alwaysencrypted.demo.view.CustomerView;
import com.alwaysencrypted.demo.view.NVarcharCustomerView;
import com.alwaysencrypted.demo.view.VarcharCustomerView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerNVarcharRepository nVarcharRepository;
    private final CustomerVarcharRepository varcharRepository;

    public CustomerController(CustomerNVarcharRepository nVarcharRepository, CustomerVarcharRepository varcharRepository) {
        this.varcharRepository = varcharRepository;
        this.nVarcharRepository = nVarcharRepository;
    }

    @PostMapping("/nvarchar")
    public CustomerNVarchar createRetail(@RequestBody CustomerNVarchar customer) {
        return nVarcharRepository.save(customer);
    }

    @PostMapping("/varchar")
    public CustomerVarchar createBusiness(@RequestBody CustomerVarchar customer) {
        return varcharRepository.save(customer);
    }

    @PostMapping
    public ResponseEntity<String> createBothCustomers(@RequestBody CreateCustomerRequest request) {
        if (request.getName() == null || request.getSsn() == null) {
            return ResponseEntity.badRequest().body("Name and SSN are required.");
        }

        System.out.println(request.getSsn());

        CustomerVarchar customerVarchar = new CustomerVarchar();
        customerVarchar.setName(request.getName());
        customerVarchar.setSsn(request.getSsn());
        varcharRepository.save(customerVarchar);

        CustomerNVarchar customerNVarchar = new CustomerNVarchar();
        customerNVarchar.setName(request.getName());
        customerNVarchar.setSsn(request.getSsn());
        nVarcharRepository.save(customerNVarchar);

        return ResponseEntity.ok("✅ VARCHAR and NVARCHAR customers saved.");
    }

    @GetMapping("/all")
    public ResponseEntity<?>  getAllUnifiedCustomers() {
        try {
        List<CustomerView> unified = new ArrayList<>();

        varcharRepository.findAll().forEach(r -> unified.add(new VarcharCustomerView(r)));
        nVarcharRepository.findAll().forEach(b -> unified.add(new NVarcharCustomerView(b)));

        return ResponseEntity.ok(unified);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error retrieving customers");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }
    }

    @PatchMapping("/varchar")
    public ResponseEntity<String> updateCustomers(@RequestBody CreateCustomerRequest request) {
        Optional<CustomerVarchar> optionalCustomer = varcharRepository.findById(request.getId());

        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CustomerVarchar customerVarchar = optionalCustomer.get();
        customerVarchar.setName(request.getName());
        varcharRepository.save(customerVarchar);

        return ResponseEntity.ok("✅ VARCHAR Updated.");
    }

}