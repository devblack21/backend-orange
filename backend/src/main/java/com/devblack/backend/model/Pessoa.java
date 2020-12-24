package com.devblack.backend.model;

import com.devblack.backend.dto.PessoaVO;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "pessoas")
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;
    @Column(name = "dtNascimento", nullable = false)
    private LocalDate dtNascimento;

    public static Pessoa convert(PessoaVO pessoaVO){
        return new ModelMapper().map(pessoaVO, Pessoa.class);
    }
}
