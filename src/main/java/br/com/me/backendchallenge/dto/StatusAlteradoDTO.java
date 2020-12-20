package br.com.me.backendchallenge.dto;

import br.com.me.backendchallenge.enums.Status;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class StatusAlteradoDTO {
    String pedido;
    List<Status> status = new ArrayList<>();

    public void addStatus(Status status) {
        this.status.add(status);
    }
}
