package br.com.me.backendchallenge.domain.validator;

import br.com.me.backendchallenge.enums.Status;
import org.springframework.stereotype.Component;

@Component
public class ValorAprovadoChecker implements AlteracaoStatusChecker {
    @Override
    public void doCheck(AlteracaoStatusContext context) {
        if (context.getIn().getStatus() != Status.APROVADO) {
            return;
        }
        final var valor = context.getPedido().getValorTotal();
        final var valorAprovado = context.getIn().getValorAprovado();
        int comparacao = valorAprovado.compareTo(valor);
        if (comparacao > 0) {
            context.getOut().addStatus(Status.APROVADO_VALOR_A_MAIOR);
        } else if (comparacao < 0) {
            context.getOut().addStatus(Status.APROVADO_VALOR_A_MENOR);
        }
    }
}
