package com.customergroup.application.controller;

import com.customergroup.application.serivce.CustomerValidatorService;
import com.customergroup.domain.Contact;
import com.customergroup.domain.Customer;
import com.customergroup.application.serivce.ContactService;
import com.customergroup.application.serivce.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/")
// API Layer
public class CustomerController {

    private final CustomerService customerService;
    private final ContactService contactService;
    private final CustomerValidatorService customerValidatorService;

    @Autowired
    public CustomerController(CustomerService customerService,
                              ContactService contactService,
                              CustomerValidatorService customerValidatorService){
        this.customerService = customerService;
        this.contactService = contactService;
        this.customerValidatorService = customerValidatorService;
    }
    /*
     ############ Get Mappings ############
     */
    @GetMapping(path = "/customer")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping(path = "/customer/{customerId}")
    public Customer getCustomer(@PathVariable("customerId") long Id){
        return customerService.getCustomerById(Id);
    }

    @GetMapping(path = "/customer/companyName={customerCompanyName}")
    public Customer getCustomer(@PathVariable("customerCompanyName") String customerCompanyName){
        return customerService.getCustomer(customerCompanyName);
    }

    @GetMapping(path = "/customer/phone={customerPhone}")
    public Map<String, String> getCustomerByPhone(@PathVariable("customerPhone")
                                            String customerPhone){
        return customerService.getCustomerByPhone(customerPhone);
    }

    @GetMapping(path = "/contact")
    public List<Contact> getContacts(){
        return contactService.getContacts();
    }

    @GetMapping(path = "/contact/{contactId}")
    public Contact getContact(@PathVariable("contactId") Long contactId){
        return contactService.getContact(contactId);
    }

    @GetMapping(path = "/customer/validate={customerId}")
    public Map<String, String> validateCustomer(@PathVariable("customerId") long customerId){
        return customerValidatorService.validateCustomer(customerId);
    }

    @GetMapping(path = "/contact/customer/{customerId}")
    public Contact getContactByCustomerId(@PathVariable("customerId")
                                          long customerId){
        return customerService.getContactByCustomerId(customerId);
    }

    /*
     ############ POST MAPPING ############
     */
    @PostMapping(path = "/customer")
    public void registerNewCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
    }

    @PostMapping(path = "/contact")
    public void registerNewCustomer(@RequestBody Contact contact){
        contactService.addContact(contact);
    }

    /*
    ############ PUT MAPPING ############
    */
    @PutMapping(path = "/customer/{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") long customerId,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String country){
        customerService.updateCustomer(customerId, companyName, address, country);
    }

    @PutMapping(path = "/customer/putRaw/{customerId}")
    public void updateCustomerByRaw(@RequestBody Customer customer){
        customerService.updateCustomerByRaw(customer);
    }

    @PutMapping(path = "/contact/{customerId}")
    public void updateContact(
            @PathVariable("customerId") long customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String position){
        contactService.updateContact(customerId, name, phone, email, position);
    }

    @PutMapping(path = "/contact/putRaw/{customerId}")
    public void updateContact(@RequestBody Contact contact){
        contactService.updateContactByRaw(contact);
    }

    // PUT - Update the Customer's Contact Details
    @PutMapping(path = "/customer/{id}/contact/{contactId}")
    public void updateCustomerContactDetails(@PathVariable("id") Long id,
                                             @PathVariable("contactId") Long contactId){
        customerService.updateCustomerContactDetails(id, contactId);
    }

    /*
    ############ DELETE MAPPING ############
     */

    @DeleteMapping(path = "/contact/{contactId}")
    public void deleteContact(@PathVariable("contactId") Long contactId){
        contactService.deleteContact(contactId);
    }

    @DeleteMapping(path = "/customer/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
    }

    // DELETE - removes the customer's contact details
    @DeleteMapping(path = "/customer/{customerId}/removeContactDetail")
    public void removeContactDetails(@PathVariable("customerId") long customerId){
        customerService.removeCustomerContactDetails(customerId);
    }

}

