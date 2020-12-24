package com.devblack.backend.service;

import com.devblack.backend.dto.PessoaVO;
import com.devblack.backend.model.Pessoa;
import com.devblack.backend.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import static org.springframework.data.jpa.domain.Specification.where;
import static com.devblack.backend.specification.PessoaSpecification.*;
import static com.devblack.backend.exception.ControllerException.conflict;
import static com.devblack.backend.exception.ControllerException.notFound;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository){
        this.pessoaRepository = pessoaRepository;
    }

    private PessoaVO convertPessoaToPessoaVO(Pessoa pessoa){
        return PessoaVO.convert(pessoa);
    }

    private boolean isCpfExists(String cpf){
        AtomicBoolean isExists = new AtomicBoolean(false);
        this.pessoaRepository.findOne(where(cpfEquals(cpf)))
                .ifPresent(it ->
                {
                    isExists.set(true);
                });

        return isExists.get();
    }

    private boolean isEmailExists(String email){
        AtomicBoolean isExists = new AtomicBoolean(false);
        this.pessoaRepository.findOne(where(emailEquals(email)))
                .ifPresent(it ->
                {
                    isExists.set(true);
                });

        return isExists.get();
    }

    private void validaDuplicidade(PessoaVO pessoaVO){
        if(isCpfExists(pessoaVO.getCpf())){
            throw conflict("Já existe uma pessoa cadastrada com o CPF informado");
        }
        if(isEmailExists(pessoaVO.getEmail())){
            throw conflict("Já existe uma pessoa cadastrada com o Email informado");
        }
    }

    public PessoaVO salvar(PessoaVO pessoaVO){
        validaDuplicidade(pessoaVO);;
        return PessoaVO.convert(this.pessoaRepository.save(Pessoa.convert(pessoaVO)));
    }

    public PessoaVO alterar(PessoaVO pessoaVO){
        validaDuplicidade(pessoaVO);
        return PessoaVO.convert(this.pessoaRepository.save(Pessoa.convert(pessoaVO)));
    }

    public void remover(Long id){
        PessoaVO pessoaAserRemovidaVO = buscarPorId(id);
        if(pessoaAserRemovidaVO == null){
            throw notFound("Não existe uma pessoa com o ID informado.");
        }
        this.pessoaRepository.delete(Pessoa.convert(pessoaAserRemovidaVO));
    }

    public PessoaVO buscarPorId(Long id){
        if (id < 0){
            throw conflict("O ID informado é invalido.");
        }

        return PessoaVO.convert(this.pessoaRepository.findById(id).get());
    }

    public List<PessoaVO> buscarTodos(){
        return this.pessoaRepository.findAll().stream().map(this::convertPessoaToPessoaVO).collect(Collectors.toList());
    }


}
