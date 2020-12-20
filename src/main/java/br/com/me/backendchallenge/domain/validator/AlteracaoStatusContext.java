package br.com.me.backendchallenge.domain.validator;

import br.com.me.backendchallenge.domain.Pedido;
import br.com.me.backendchallenge.dto.StatusAlteradoDTO;
import br.com.me.backendchallenge.dto.StatusAlterarDTO;
import lombok.Value;

@Value
public class AlteracaoStatusContext {
    StatusAlterarDTO in;
    StatusAlteradoDTO out;
    Pedido pedido;

    public static AlteracaoStatusContext create(StatusAlterarDTO novoStatus, Pedido pedido) {
        if (pedido != null) {
            return new AlteracaoStatusContext(novoStatus,
                    new StatusAlteradoDTO(pedido.getId()), pedido);
        }
        return new AlteracaoStatusContext(novoStatus, new StatusAlteradoDTO(null), null);
    }
}
