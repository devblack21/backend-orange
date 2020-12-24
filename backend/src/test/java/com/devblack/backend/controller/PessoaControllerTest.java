package com.devblack.backend.controller;

import com.devblack.backend.dto.PessoaVO;
import com.devblack.backend.exception.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;


public class PessoaControllerTest {


    @InjectMocks
    private PessoaController pessoaController;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarPessoaSemCPF(){

        try{
            PessoaVO pessoa = new PessoaVO();
            pessoa.setNome("Fulano");
            pessoa.setEmail("Fulano@gmail.com");
            pessoa.setDtNascimento(LocalDate.of(2000,12,25));

            pessoaController.salvar(pessoa);
            Assert.fail("não era para salvar");
        }catch (Exception e){
            Assert.assertEquals("cannot save cpf null", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarPessoaSemNome(){
        try{
            PessoaVO pessoa = new PessoaVO();

            pessoa.setEmail("Fulano@gmail.com");
            //cpf gerado automaticamente em (https://www.4devs.com.br/gerador_de_cpf)
            pessoa.setCpf("373.111.220-58");
            pessoa.setDtNascimento(LocalDate.of(2000,12,25));

            pessoaController.salvar(pessoa);
            Assert.fail("não era para salvar");
        }catch (ValidationException e){
            Assert.assertEquals("cannot save nome null", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarPessoaSemEmail(){
        try{
            PessoaVO pessoa = new PessoaVO();
            pessoa.setNome("Fulano");
            pessoa.setCpf("373.111.220-58");
            pessoa.setDtNascimento(LocalDate.of(2000,12,25));

            pessoaController.salvar(pessoa);
            Assert.fail("não era para salvar");
        }catch (Exception e){
            Assert.assertEquals("cannot save email null", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarPessoaSemDataDeNascimento(){
        try{
            PessoaVO pessoa = new PessoaVO();
            pessoa.setNome("Fulano");
            pessoa.setEmail("Fulano@gmail.com");
            pessoa.setCpf("373.111.220-58");

            pessoaController.salvar(pessoa);
            Assert.fail("não era para salvar");
        }catch (ValidationException e){
            Assert.assertEquals("cannot save dtNascimento null", e.getMessage());
        }
    }



    @Test
    public void naoDeveSalvarPessoaComCPFDuplicado()
    {
        try{
            PessoaVO pessoa = new PessoaVO();
            pessoa.setNome("Fulano");
            pessoa.setEmail("Fulano@gmail.com");
            pessoa.setCpf("373.111.220-58");
            pessoa.setDtNascimento(LocalDate.of(2000,12,25));
            pessoaController.salvar(pessoa);
            pessoaController.salvar(pessoa);
            Assert.fail("não era para salvar");
        }catch (ValidationException e){
            Assert.assertEquals("cannot save duplicate cpf", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarPessoaComEmailDuplicado()
    {
        try{
            PessoaVO pessoa = new PessoaVO();
            pessoa.setNome("Fulano");
            pessoa.setEmail("Fulano@gmail.com");
            pessoa.setCpf("373.111.220-58");
            pessoa.setDtNascimento(LocalDate.of(2000,12,25));
            pessoaController.salvar(pessoa);
            pessoaController.salvar(pessoa);
            Assert.fail("não era para salvar");
        }catch (ValidationException e){
            Assert.assertEquals("cannot save duplicate email", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarPessoaComDtDeNascimentoMaiorQueDataAtual(){
        try{
            PessoaVO pessoa = new PessoaVO();
            pessoa.setNome("Fulano");
            pessoa.setEmail("Fulano@gmail.com");
            pessoa.setCpf("373.111.220-58");
            pessoa.setDtNascimento(LocalDate.of(2090,12,25));
            pessoaController.salvar(pessoa);
            pessoaController.salvar(pessoa);
            Assert.fail("não era para salvar");
        }catch (ValidationException e){
            Assert.assertEquals("cannot save duplicate email", e.getMessage());
        }
    }
}