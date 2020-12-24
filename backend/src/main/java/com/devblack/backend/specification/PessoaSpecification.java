package com.devblack.backend.specification;

import com.devblack.backend.model.Pessoa;
import org.springframework.data.jpa.domain.Specification;

public final class PessoaSpecification {

    private PessoaSpecification(){

    }

    public static Specification<Pessoa> cpfEquals(String cpf){
        return ((root, query, cb) -> cb.equal(root.get("cpf"), cpf));
    }

    public static Specification<Pessoa> emailEquals(String email){
        return ((root, query, cb) -> cb.equal(root.get("email"), email));
    }

}

