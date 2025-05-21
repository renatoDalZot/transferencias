package com.bancodbworkshop.transferencias.domain.repository;


import com.bancodbworkshop.transferencias.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
}
