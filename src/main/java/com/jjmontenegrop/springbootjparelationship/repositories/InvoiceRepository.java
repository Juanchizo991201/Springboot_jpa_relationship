package com.jjmontenegrop.springbootjparelationship.repositories;

import com.jjmontenegrop.springbootjparelationship.entities.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
