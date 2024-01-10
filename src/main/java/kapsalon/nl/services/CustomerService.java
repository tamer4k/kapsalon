package kapsalon.nl.services;

import kapsalon.nl.models.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    public List<CustomerDTO> getAllCustomers();
    public CustomerDTO getCustomertById(Long id);
    public CustomerDTO createCustomer(CustomerDTO customerDTO);
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
    public void deleteCustomer(Long id);
}
