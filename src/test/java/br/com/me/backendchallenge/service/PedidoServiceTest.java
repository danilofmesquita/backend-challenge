package br.com.me.backendchallenge.service;

import br.com.me.backendchallenge.domain.Pedido;
import br.com.me.backendchallenge.dto.StatusAlterarDTO;
import br.com.me.backendchallenge.enums.Status;
import br.com.me.backendchallenge.repository.PedidoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {
    @Mock
    PedidoRepository pedidoRepository;

    @InjectMocks
    PedidoService pedidoService;

    @Test
    void deveRetornarStatusCodigoPedidoInvalido() {
        var pedido = "1";
        Mockito.when(pedidoRepository.findById(pedido)).thenReturn(Optional.empty());

        var statusAlterarDTO = new StatusAlterarDTO();
        statusAlterarDTO.setPedido(pedido);
        statusAlterarDTO.setStatus(Status.APROVADO);
        statusAlterarDTO.setItensAprovados(1L);
        statusAlterarDTO.setValorAprovado(BigDecimal.ONE);

        final var statusAlterado = pedidoService.alterarStatus(statusAlterarDTO);

        Assertions.assertThat(statusAlterado.getStatus())
                .hasSize(1)
                .contains(Status.CODIGO_PEDIDO_INVALIDO);
    }
}