package com.egoveris.te.base.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.internal.service.TransactionESBService;
import com.egoveris.te.base.model.TransactionESB;
import com.egoveris.te.base.repository.TransactionESBRepository;

@Service
@Transactional
public class TransctionESBServiceImpl implements TransactionESBService{

	@Autowired
	TransactionESBRepository transactionRepository;
	
	private final static Logger logger = LoggerFactory.getLogger(TransctionESBServiceImpl.class);
	
	@Override
	public String getNewTransaction() throws ServiceException {
		try {
			TransactionESB entity = new TransactionESB();
			entity.setDateCreation(new Date());
			transactionRepository.save(entity);
			entity.setTransaction("TRS-" + entity.getId());
			transactionRepository.save(entity);
			return entity.getTransaction(); 
		} catch (Exception e) {
			logger.error("", e);
			throw new ServiceException(e);
		}
		
	}

}
