package com.devblack.backend.controller;

import com.devblack.backend.service.PessoaService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(PessoaController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:test.yml")
public class PessoaControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private PessoaService pessoaService;

    private  final String NOME = "Fulano";
    private  final String EMAIL = "fulano@gmail.com";
    private  final String DTNASCIMENTO = "2000-12-15";
    private  final String DTNASCIMENTO_MAIOR = "2150-12-15";
    private  final String CPF = "373.111.220-58";
    private  final String POST_URL = "/pessoa";

    @Test
    public void naoDeveSalvarPessoaSemCPF(){

        try{
            JSONObject pessoaJson = new JSONObject();
            pessoaJson.put("nome",NOME);
            pessoaJson.put("email",EMAIL);
            pessoaJson.put("dtNascimento", DTNASCIMENTO);


            RequestBuilder requestBuilder = MockMvcRequestBuilders.post(POST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(pessoaJson.toString());

            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject mensagem = new JSONObject(response.getContentAsString());
            Assert.assertEquals("cannot save cpf null",mensagem.get("mensagem"));

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarPessoaSemNome() {
        try{
            JSONObject pessoaJson = new JSONObject();
            pessoaJson.put("email",EMAIL);
            pessoaJson.put("dtNascimento", DTNASCIMENTO);
            pessoaJson.put("cpf",CPF);

            RequestBuilder requestBuilder = MockMvcRequestBuilders.post(POST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(pessoaJson.toString());

            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject mensagem = new JSONObject(response.getContentAsString());
            Assert.assertEquals("cannot save nome null",mensagem.get("mensagem"));

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarPessoaSemEmail(){
        try{
            JSONObject pessoaJson = new JSONObject();
            pessoaJson.put("nome",NOME);
            pessoaJson.put("dtNascimento", DTNASCIMENTO);
            pessoaJson.put("cpf",CPF);

            RequestBuilder requestBuilder = MockMvcRequestBuilders.post(POST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(pessoaJson.toString());

            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject mensagem = new JSONObject(response.getContentAsString());
            Assert.assertEquals("cannot save email null",mensagem.get("mensagem"));

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarPessoaSemDataDeNascimento(){
        try{
            JSONObject pessoaJson = new JSONObject();
            pessoaJson.put("nome",NOME);
            pessoaJson.put("email",EMAIL);
            pessoaJson.put("cpf",CPF);


            RequestBuilder requestBuilder = MockMvcRequestBuilders.post(POST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(pessoaJson.toString());

            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject mensagem = new JSONObject(response.getContentAsString());
            Assert.assertEquals("cannot save dtNascimento null",mensagem.get("mensagem"));

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarPessoaComDtDeNascimentoMaiorQueDataAtual(){
        try{
            JSONObject pessoaJson = new JSONObject();
            pessoaJson.put("nome",NOME);
            pessoaJson.put("email",EMAIL);
            pessoaJson.put("dtNascimento", DTNASCIMENTO_MAIOR);
            pessoaJson.put("cpf",CPF);

            RequestBuilder requestBuilder = MockMvcRequestBuilders.post(POST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(pessoaJson.toString());

            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
            JSONObject mensagem = new JSONObject(response.getContentAsString());
            Assert.assertEquals("cannot save dtNascimento greater than the current date",mensagem.get("mensagem"));

        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }
}