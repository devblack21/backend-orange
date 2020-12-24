package com.devblack.backend.controller;

import com.devblack.backend.dto.PessoaVO;
import com.devblack.backend.exception.ValidationException;
import com.devblack.backend.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Map;
import static java.lang.String.format;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService){
        this.pessoaService = pessoaService;
    }

    private void validarCampos(PessoaVO pessoaVO) throws ValidationException {


        if (pessoaVO.getNome() == null){
            throw new ValidationException("cannot save nome null");
        }
        if (pessoaVO.getEmail() == null){
            throw new ValidationException("cannot save email null");
        }
        if (pessoaVO.getCpf() == null){
            throw new ValidationException("cannot save cpf null");
        }
        if (pessoaVO.getDtNascimento() == null){
            throw new ValidationException("cannot save dtNascimento null");
        }
    }

    @PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
            consumes = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> salvar(@Validated @RequestBody PessoaVO pessoaVO) throws ValidationException {

        validarCampos(pessoaVO);
        var pessoa = this.pessoaService.salvar(pessoaVO);

        return created(URI.create(format("/pessoa/%d", pessoa.getId())))
                .body(Map.of("id",pessoa.getId()));
    }

    @PutMapping(value = "/{id}",
            produces = {"application/json","application/xml","application/x-yaml"},
            consumes = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> alterar(@PathVariable Long id, @Validated @RequestBody PessoaVO pessoaVO){
        this.pessoaService.alterar(pessoaVO);
        return noContent().build();
    }

    @DeleteMapping(value = "/{id}",
            produces = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> remover(@PathVariable Long id){
        this.pessoaService.remover(id);
        return noContent().build();
    }

    @GetMapping(value = "/{id}",
            produces = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        return ok(this.pessoaService.buscarPorId(id));
    }

    @GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> buscarTodos(){
        return ok(this.pessoaService.buscarTodos());
    }

}
