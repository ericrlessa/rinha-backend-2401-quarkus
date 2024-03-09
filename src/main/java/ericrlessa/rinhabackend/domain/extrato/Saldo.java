package ericrlessa.rinhabackend.domain.extrato;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RegisterForReflection
public record Saldo(Long total, Instant data_extrato, Long limite) {

    public static Saldo of(Long total, Long limite){
        return new Saldo(total, Instant.now().truncatedTo(ChronoUnit.MICROS), limite);
    }
}
