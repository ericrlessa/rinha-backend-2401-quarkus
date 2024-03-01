package org.acme.extrato;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;

@RegisterForReflection
public record Saldo(Long total, LocalDateTime data_extrato, Long limite) {
}
