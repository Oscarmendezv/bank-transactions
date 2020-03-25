package com.oscarmendezv.banktransactions.repository;

import com.oscarmendezv.banktransactions.repository.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<AccountEntity, Long> {

  Optional<AccountEntity> findByIban(String iban);
}
