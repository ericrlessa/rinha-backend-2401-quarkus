package ericrlessa.rinhabackend.domain;

import jakarta.ws.rs.NotFoundException;

public abstract class GerenciadorAbstract {

    public void validarClienteExistente(Integer id) {
        if(Cliente.count("id", id) == 0){
            throw new NotFoundException("Cliente não existe!");
        }
    }

}
