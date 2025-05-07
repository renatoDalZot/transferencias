package com.bancodbworkshop.transferencias.unit;

import com.bancodbworkshop.transferencias.model.Transferencia;
import com.bancodbworkshop.transferencias.repository.TransferenciaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Transactional
public class TrasnferenciaRepositoryTest {

    @Autowired
    private TransferenciaRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void dadoNovoCadastro_quandoSalvoNoRepositorio_entaoOsDadosPersistem() {
        // Cenário
        var transferenciaNova = new Transferencia(1L, 2L, BigDecimal.valueOf(1000.00),
                LocalDateTime.of(2023, 10, 1, 10, 0));

        // Ação
        repository.save(transferenciaNova);
        entityManager.flush();
        entityManager.clear();

        // Verificação
        assertThat(repository.findById(transferenciaNova.getId())).hasValueSatisfying(transferenciaResgatada -> {
            assertThat(transferenciaResgatada.getId()).isEqualTo(transferenciaNova.getId());
            assertThat(transferenciaResgatada.getContaOrigemId()).isEqualTo(1L);
            assertThat(transferenciaResgatada.getContaDestinoId()).isEqualTo(2L);
            assertThat(transferenciaResgatada.getValor().compareTo(BigDecimal.valueOf(1000.00))).isZero();
            assertThat(transferenciaResgatada.getData()).isEqualTo(LocalDateTime.of(2023, 10, 1, 10, 0));
        });
    }
}
