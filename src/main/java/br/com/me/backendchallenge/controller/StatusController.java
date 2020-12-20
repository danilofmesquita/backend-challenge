package br.com.me.backendchallenge.controller;

import br.com.me.backendchallenge.dto.StatusAlteradoDTO;
import br.com.me.backendchallenge.dto.StatusAlterarDTO;
import br.com.me.backendchallenge.service.StatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Status Pedido")
@RestController
@RequestMapping("/status")
@AllArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<StatusAlteradoDTO> alterarStatus(@Valid @RequestBody StatusAlterarDTO statusAlterarDTO) {
        return ResponseEntity.ok(this.statusService.alterarStatus(statusAlterarDTO));
    }

}