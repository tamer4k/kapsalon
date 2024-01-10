package kapsalon.nl.services;

import kapsalon.nl.models.dto.CustomerDTO;
import kapsalon.nl.models.entity.Customer;
import kapsalon.nl.repo.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    public List<CustomerDTO> getAllCustomers() {

        List<Customer> entityList = customerRepository.findAll();
        List<CustomerDTO> dtoList = new ArrayList<>();
        for (Customer entity : entityList) {
            dtoList.add(fromEntityToDto(entity));
        }
        return dtoList;
    }

    @Override
    public CustomerDTO getCustomertById(Long id) {
        Optional<Customer> entity = customerRepository.findById(id);
        CustomerDTO dto;
        if (entity.isPresent()) {
            dto = fromEntityToDto(entity.get());

            return dto;
        }
        return  null;
    }


    @Override
    public CustomerDTO createCustomer(CustomerDTO dto) {
        Customer entity = customerRepository.save(fromDtoToEntity(dto));
        return fromEntityToDto(entity);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
        Optional<Customer> entityId = customerRepository.findById(id);
        if (entityId.isPresent()) {
            Customer entity = entityId.get();
            entity.setFirstName(dto.getFirstName());
            entity.setSecondName(dto.getSecondName());
            entity.setEmail(dto.getEmail());
            entity.setPhoneNumber(dto.getPhoneNumber());

            Customer updatedEntity = customerRepository.save(entity);
            return fromEntityToDto(updatedEntity);

        }
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public static CustomerDTO fromEntityToDto(Customer entity){
        CustomerDTO dto = new CustomerDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setSecondName(entity.getSecondName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        return  dto;
    }

    public static Customer fromDtoToEntity(CustomerDTO dto) {
        Customer entity = new Customer();

        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setSecondName(dto.getSecondName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }
}
