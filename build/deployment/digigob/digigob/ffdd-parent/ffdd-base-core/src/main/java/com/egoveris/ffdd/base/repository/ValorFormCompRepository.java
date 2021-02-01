package com.egoveris.ffdd.base.repository;

import com.egoveris.ffdd.base.model.ValorFormComp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ValorFormCompRepository extends JpaRepository<ValorFormComp, Integer>, QueryDslPredicateExecutor<ValorFormComp> {

}
