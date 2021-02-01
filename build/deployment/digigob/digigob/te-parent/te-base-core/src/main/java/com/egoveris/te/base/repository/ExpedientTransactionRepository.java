package com.egoveris.te.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.ExpedientTransaction;

public interface ExpedientTransactionRepository extends JpaRepository<ExpedientTransaction, Long>{

	@Query("FROM ExpedientTransaction WHERE idTransaction = :idTransaction and status = 1")
	public ExpedientTransaction findActivTransaction(@Param("idTransaction") String idTransaction);
	
	@Query("FROM ExpedientTransaction WHERE idTransaction = :idTransaction")
	public ExpedientTransaction findTransaction(@Param("idTransaction") String idTransaction);
	
	public ExpedientTransaction getByIdTransaction(String idTransaction);
	
	@Query("FROM ExpedientTransaction WHERE  status = 1")
	public List<ExpedientTransaction> getAllActivesTransactions();
	
	public ExpedientTransaction getByIdExpedient(Long idExpedient);
	
	public ExpedientTransaction getByIdExpedientAndCode(Long idExpedient, String code);
	
}
