package ericrlessa.rinhabackend.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Cliente extends PanacheEntityBase {

    @Id
    public Long id;

    public Long limite;
    public Long saldo;

    Cliente(){}

    public Cliente(Long id, Long limite, Long saldo){
        this.id = id;
        this.limite = limite;
        this.saldo = saldo;
    }

    public static Cliente of(Long id, Long limite, Long saldoInicial){
        return new Cliente(id, limite, saldoInicial);
    }

    public static Cliente of(Long id, Long limite){
        return new Cliente(id, limite, 0l);
    }
}
