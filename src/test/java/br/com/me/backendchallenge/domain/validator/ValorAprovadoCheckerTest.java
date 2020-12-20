package br.com.me.backendchallenge.domain.validator;

import br.com.me.backendchallenge.domain.Pedido;
import br.com.me.backendchallenge.domain.StatusPedido;
import br.com.me.backendchallenge.dto.ItemDTO;
import br.com.me.backendchallenge.dto.StatusAlterarDTO;
import br.com.me.backendchallenge.enums.Status;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

class ValorAprovadoCheckerTest {

    Pedido pedido;
    ValorAprovadoChecker checker;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.addItem(new ItemDTO(null, null, BigDecimal.valueOf(2L), 5L));
        pedido.addItem(new ItemDTO(null, null, BigDecimal.valueOf(2L), 5L));
        checker = new ValorAprovadoChecker();
    }

    AlteracaoStatusContext novaAlteracao(Status status, Long qtd, BigDecimal valor) {
        return AlteracaoStatusContext.create(new StatusAlterarDTO(status, qtd, valor, null), pedido);
    }

    @Test
    void deveRetornarAprovadoMaior() {
        final var statusPedidoMock = Mockito.mock(StatusPedido.class);
        this.pedido.setStatusAtual(statusPedidoMock);
        final var context = novaAlteracao(Status.APROVADO, 10L, new BigDecimal("25.0"));
        checker.doCheck(context);
        Assertions.assertThat(context.getOut().getStatus()).hasSize(1).contains(Status.APROVADO_VALOR_A_MAIOR);
    }

    @Test
    void deveRetornarAprovadoMenor() {
        final var statusPedidoMock = Mockito.mock(StatusPedido.class);
        this.pedido.setStatusAtual(statusPedidoMock);
        final var context = novaAlteracao(Status.APROVADO, 10L, new BigDecimal("10.0"));
        checker.doCheck(context);
        Assertions.assertThat(context.getOut().getStatus()).hasSize(1).contains(Status.APROVADO_VALOR_A_MENOR);
    }

}