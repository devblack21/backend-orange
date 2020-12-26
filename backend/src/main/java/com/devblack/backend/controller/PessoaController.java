package com.devblack.backend.controller;

import com.devblack.backend.dto.PessoaVO;
import com.devblack.backend.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.time.LocalDate;
import java.util.Map;
import static com.devblack.backend.exception.ControllerException.conflict;
import static com.devblack.backend.exception.ControllerException.notFound;
import static java.lang.String.format;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService){
        this.pessoaService = pessoaService;
    }

    private void validarCampos(PessoaVO pessoaVO)  {

        if (pessoaVO.getNome() == null || pessoaVO.getNome().isEmpty()){
            throw conflict("cannot save nome null");
        }
        if (pessoaVO.getEmail() == null || pessoaVO.getEmail().isEmpty()){
            throw conflict("cannot save email null");
        }
        if (pessoaVO.getCpf() == null || pessoaVO.getCpf().isEmpty()){
            throw conflict("cannot save cpf null");
        }
        if (pessoaVO.getDtNascimento() == null){
            throw conflict("cannot save dtNascimento null");
        }
        if(pessoaVO.getDtNascimento().isAfter(LocalDate.now())){
            throw conflict("cannot save dtNascimento greater than the current date");
        }
    }

    @PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
            consumes = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> salvar(@Validated @RequestBody PessoaVO pessoaVO) throws Exception {
        ResponseEntity responseEntity = null;
        validarCampos(pessoaVO);
        try
        {
            var pessoa = this.pessoaService.salvar(pessoaVO);
            responseEntity = created(URI.create(format("/pessoa/%d", pessoa.getId())))
                    .body(Map.of("id",pessoa.getId()));

        }catch (ConstraintViolationException e){

            throw conflict(e.getMessage().
                    substring(
                            e.getMessage()
                                    .indexOf("'"),e.getMessage()
                                    .indexOf("',"))
                    .replaceAll("'",""));
        }

        return responseEntity;
    }

    @PutMapping(value = "/{id}",
            produces = {"application/json","application/xml","application/x-yaml"},
            consumes = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> alterar(@PathVariable Long id, @Validated @RequestBody PessoaVO pessoaVO) {
        validarCampos(pessoaVO);
        try {
            this.pessoaService.alterar(pessoaVO);

        }catch (Exception e){
            e.printStackTrace();
        }

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

        var pessoaVoRetorno = this.pessoaService.buscarPorId(id);
        if(pessoaVoRetorno == null){
           throw notFound("Nenhum dados encontrado com o ID informado.");
        }

        return ok(pessoaVoRetorno);
    }

    @GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> buscarTodos(){
        return ok(this.pessoaService.buscarTodos());
    }

}
