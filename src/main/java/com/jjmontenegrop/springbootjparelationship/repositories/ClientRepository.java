package com.jjmontenegrop.springbootjparelationship.repositories;

import com.jjmontenegrop.springbootjparelationship.entities.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("SELECT c FROM Client c left join fetch c.addresses WHERE c.id = ?1")
    Optional<Client> findOne(Long id);
}
