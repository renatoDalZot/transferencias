package com.bancodbworkshop.transferencias.unit;

import com.bancodbworkshop.transferencias.model.Conta;
import com.bancodbworkshop.transferencias.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ContaRepositoryTest {

    @Autowired
    private ContaRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void dadoNovoCadastro_quandoSalvoNoRepositorio_entaoOsDadosPersistem() {
        // Cenário
        var contaNova = new Conta(123456, "João da Silva", BigDecimal.valueOf(1000.00));

        // Ação
        repository.save(contaNova);
        entityManager.flush();
        entityManager.clear();

        // Verificação
        assertThat(repository.findById(contaNova.getId())).hasValueSatisfying(contaResgatada -> {
            assertThat(contaResgatada.getId()).isEqualTo(contaNova.getId());
            assertThat(contaResgatada.getAgencia()).isEqualTo(123456);
            assertThat(contaResgatada.getTitular()).isEqualTo("João da Silva");
            assertThat(contaResgatada.getSaldo().compareTo(BigDecimal.valueOf(1000.00))).isZero();
        });
    }


}
