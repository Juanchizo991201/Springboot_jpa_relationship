package com.jjmontenegrop.springbootjparelationship;

import com.jjmontenegrop.springbootjparelationship.entities.Address;
import com.jjmontenegrop.springbootjparelationship.entities.Client;
import com.jjmontenegrop.springbootjparelationship.entities.Invoice;
import com.jjmontenegrop.springbootjparelationship.repositories.ClientRepository;
import com.jjmontenegrop.springbootjparelationship.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

    ClientRepository clientRepository;
    InvoiceRepository invoiceRepository;

    @Autowired
    public SpringbootJpaRelationshipApplication(ClientRepository clientRepository, InvoiceRepository invoiceRepository) {
        this.clientRepository = clientRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        manyToOne();
        oneToManyInvoiceBidirectional();
    }

    @Transactional
    public void oneToManyInvoiceBidirectional(){
        Client client = new Client("John", "Doe");
        clientRepository.save(client);

        Invoice invoice1 = new Invoice("Expenses", 700L);
        Invoice invoice2 = new Invoice("Cleaning", 100L);

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        client.setInvoices(invoices);

        invoice1.setClient(client);
        invoice2.setClient(client);

        clientRepository.save(client);

        System.out.println("Client: "+client);
    }

    @Transactional
    public void createClients() {
        Client client = new Client("John", "Doe");
        clientRepository.save(client);
    }

    @Transactional
    public void oneToManyFindOne() {
        Optional<Client> optionalClient = clientRepository.findOne(1L);
        optionalClient.ifPresent(client -> {
            Address address1 = new Address("Main Street", 123);
            Address address2 = new Address("Second Street", 456);

            client.setAddresses(Arrays.asList(address1, address2));

            clientRepository.save(client);
        });
    }

    @Transactional
    public void manyToOne() {
        Client client = new Client("Juan", "Pérez");

        Invoice invoice = new Invoice("MacBook Pro", 2000L);
        client.setInvoices(Arrays.asList(invoice));
        clientRepository.save(client);

        invoice.setClient(client);
        invoiceRepository.save(invoice);

        System.out.println(client);
    }

    @Transactional
    public void findOneClient() {

        Optional<Client> optionalClient = clientRepository.findOne(1L);

        if (optionalClient.isEmpty()) {
            System.out.println("Client not found");
        }

        Invoice invoice = new Invoice("MacBook Pro", 2000L);
        invoice.setClient(optionalClient.get());

        invoiceRepository.save(invoice);
        System.out.println("Invoice: "+ invoice+ " saved!");
    }

    @Transactional
    public void removeAddress() {
        Client client = new Client("Jeremy", "Smith");

        Address address1 = new Address("Main Street", 123);
        Address address2 = new Address("Silvestre Dangond's Street", 789);

        client.setAddresses(Arrays.asList(address1, address2));

        clientRepository.save(client);

        System.out.println(client);

        Optional<Client> optionalClient = clientRepository.findOne(client.getId());
        optionalClient.ifPresent(c -> {
            c.getAddresses().remove(address1);
            clientRepository.save(c);
            System.out.println(c);
        });
    }

    @Transactional
    public void removeAddressedOne() {

        System.out.println("Add two addresses to client with id 1 and remove Address2 from client");

        Optional<Client> optionalClient = clientRepository.findOne(1L);
        optionalClient.ifPresent(client -> {
            Address address1 = new Address("Boyaca Street", 012);
            Address address2 = new Address("Cali Street", 345);

            client.setAddresses(Arrays.asList(address1, address2));

            clientRepository.save(client);

            System.out.println("Client before delete: "+client);

            Optional<Client> optionalClient1 = clientRepository.findOne(client.getId());
            optionalClient1.ifPresent(c -> {
                c.getAddresses().remove(address2);
                clientRepository.save(c);
                System.out.println(c);
            });
        });
    }
}
