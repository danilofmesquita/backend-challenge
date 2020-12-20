package br.com.me.backendchallenge.domain.validator;

import br.com.me.backendchallenge.enums.Status;
import org.springframework.stereotype.Component;

@Component
public class QuantidadeAprovadaChecker implements AlteracaoStatusChecker {
    @Override
    public void doCheck(AlteracaoStatusContext context) {
        if (context.getIn().getStatus() != Status.APROVADO) {
            return;
        }
        final var total = context.getPedido().getQtdTotalItens();
        final var totalAprovado = context.getIn().getItensAprovados();
        int comparacao = totalAprovado.compareTo(total);
        if (comparacao > 0) {
            context.getOut().addStatus(Status.APROVADO_QTD_A_MAIOR);
        } else if (comparacao < 0) {
            context.getOut().addStatus(Status.APROVADO_QTD_A_MENOR);
        }
    }
}
