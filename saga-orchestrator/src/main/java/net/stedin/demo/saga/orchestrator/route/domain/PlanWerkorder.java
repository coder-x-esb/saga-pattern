package net.stedin.demo.saga.orchestrator.route.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@Builder
public class PlanWerkorder {
    private Long medewerkerId;
    private LocalDate datum;
    private Long werkorderId;
}
