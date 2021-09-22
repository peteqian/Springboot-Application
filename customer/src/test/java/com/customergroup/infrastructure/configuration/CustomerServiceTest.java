package com.customergroup.infrastructure.configuration;

import com.customergroup.application.CustomerService;
import com.customergroup.infrastructure.repository.CustomerRespository;
import com.customergroup.domain.Customer;
import com.customergroup.infrastructure.repository.ContactRespository;
import com.customergroup.exception.BadRequestException;
import com.customergroup.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRespository customerRespository;
    private ContactRespository contactRespository;
    private CustomerService underTest;

    @BeforeEach
    void setUp(){
        underTest = new CustomerService(customerRespository, contactRespository);
    }

    @Test
    void getAllCustomers(){
        underTest.getCustomers();
        verify(customerRespository).findAll();
    }

    @Test
    void can_add_customer(){
        Customer customerOne = new Customer(
            "companyName",
            "address",
            "country"
        );

        underTest.addCustomer(customerOne);
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerRespository).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer).isEqualTo(customerOne);
    }

    @Test
    void will_throw_when_company_already_exists(){
        Customer apple = new Customer(
                "Apple",
                "Somewhere in FreedomLand",
                "FreedomLand"
        );

        given(customerRespository.selectExistingCompany(apple.getCompanyName()))
                .willReturn(true);
        assertThatThrownBy(()-> underTest.addCustomer(apple))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Company Name: " + apple.getCompanyName() + " already exists!");
    }

    @Test
    void can_delete_customer(){

        // Given input
        long temp_id = 5;
        Customer tempCustomer = new Customer(
                temp_id,
                "TemporaryName",
                "Temp Address",
                "Temp Country"
        );

        underTest.addCustomer(tempCustomer);
        given(customerRespository.existsById(temp_id))
                .willReturn(true);

        // Try
        underTest.deleteCustomer(temp_id);

        // Result
        verify(customerRespository).deleteById(temp_id);

    }

    @Test
    void will_throw_when_delete_customer_not_found(){
        // Given input
        long temp_id = 5;

        given(customerRespository.existsById(temp_id))
                .willReturn(false);

        // Try
        assertThatThrownBy(() -> underTest.deleteCustomer(temp_id))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Company with id " + temp_id + " does not exist!");

        verify(customerRespository, never()).deleteById(any());
    }

}