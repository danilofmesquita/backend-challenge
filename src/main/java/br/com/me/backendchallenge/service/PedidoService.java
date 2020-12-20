package br.com.me.backendchallenge.service;

import br.com.me.backendchallenge.domain.Pedido;
import br.com.me.backendchallenge.domain.validator.AlteracaoStatusChecker;
import br.com.me.backendchallenge.domain.validator.AlteracaoStatusContext;
import br.com.me.backendchallenge.dto.ItemDTO;
import br.com.me.backendchallenge.dto.PedidoDTO;
import br.com.me.backendchallenge.dto.StatusAlteradoDTO;
import br.com.me.backendchallenge.dto.StatusAlterarDTO;
import br.com.me.backendchallenge.enums.Status;
import br.com.me.backendchallenge.repository.ItemRepository;
import br.com.me.backendchallenge.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ItemRepository itemRepository;
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

    public String add(PedidoDTO dto) {
        var pedido = new Pedido();
        pedido.setCriadoEm(new Date());
        dto.getItens().forEach(pedido::addItem);
        return pedidoRepository.save(pedido).getId();
    }

    public Optional<Pedido> findById(String id) {
        return pedidoRepository.findById(id);
    }

    public void deleteById(String pedidoId) {
        pedidoRepository.deleteById(pedidoId);
    }

    public void update(String id, PedidoDTO dto) {
        final var pedidoOpt = pedidoRepository.findById(id);
        if (pedidoOpt.isPresent()) {
            final var pedido = pedidoOpt.get();
            pedido.getItens().clear();
            for (ItemDTO item : dto.getItens()) {
                if (item.getId() != null) {
                    final var found = itemRepository.findById(item.getId());
                    if (found.isPresent()) {
                        pedido.addItem(found.get(), item);
                        continue;
                    }
                }
                pedido.addItem(item);
            }
            pedidoRepository.save(pedido);
        } else {
            throw new EntityNotFoundException("Não foi possível encontrar o Pedido de id " + id);
        }
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }
}