package com.egoveris.te.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.te.base.model.ExpedientTransactionDTO;
import com.egoveris.te.base.model.RegistryTransaction;

public interface RegistryTransactionRepository extends JpaRepository<RegistryTransaction, Long>{


}
