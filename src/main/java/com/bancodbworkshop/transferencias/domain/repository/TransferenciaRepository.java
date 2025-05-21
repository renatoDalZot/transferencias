package com.bancodbworkshop.transferencias.domain.repository;

import com.bancodbworkshop.transferencias.domain.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
}
