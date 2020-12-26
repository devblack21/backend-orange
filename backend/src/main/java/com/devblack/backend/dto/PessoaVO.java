package com.devblack.backend.dto;

import com.devblack.backend.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonPropertyOrder({"id","nome","email","cpf","dtNascimento"})
public class PessoaVO implements Serializable {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("email")
    private String email;
    @JsonProperty("cpf")
    private String cpf;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("dtNascimento")
    private LocalDate dtNascimento;

    public static PessoaVO convert(Pessoa pessoa){
        return new ModelMapper().map(pessoa, PessoaVO.class);
    }
}
