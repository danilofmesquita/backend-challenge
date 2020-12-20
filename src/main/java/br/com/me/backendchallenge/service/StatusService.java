package br.com.me.backendchallenge.service;

import br.com.me.backendchallenge.domain.validator.AlteracaoStatusChecker;
import br.com.me.backendchallenge.domain.validator.AlteracaoStatusContext;
import br.com.me.backendchallenge.dto.StatusAlteradoDTO;
import br.com.me.backendchallenge.dto.StatusAlterarDTO;
import br.com.me.backendchallenge.enums.Status;
import br.com.me.backendchallenge.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusService {
    private final PedidoRepository pedidoRepository;
    private final List<AlteracaoStatusChecker> alteracaoStatusCheckers;

    public StatusAlteradoDTO alterarStatus(StatusAlterarDTO novoStatus) {
        final var pedidoOpt = this.pedidoRepository.findById(novoStatus.getPedido());
        final var context = AlteracaoStatusContext.create(novoStatus, pedidoOpt.orElse(null));
        if (pedidoOpt.isPresent()) {
            final var pedido = pedidoOpt.get();
            pedido.alterarStatus(novoStatus);
            alteracaoStatusCheckers.forEach(i -> i.doCheck(context));
            if (context.getOut().getStatus().isEmpty()) {
                context.getOut().addStatus(novoStatus.getStatus());
            }
            this.pedidoRepository.save(pedido);
        } else {
            context.getOut().addStatus(Status.CODIGO_PEDIDO_INVALIDO);
        }
        return context.getOut();
    }
}
