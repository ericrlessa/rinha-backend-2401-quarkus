package ericrlessa.rinhabackend.domain.extrato;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.Instant;

@RegisterForReflection
public record Saldo(Long total, Instant data_extrato, Long limite) {
}
