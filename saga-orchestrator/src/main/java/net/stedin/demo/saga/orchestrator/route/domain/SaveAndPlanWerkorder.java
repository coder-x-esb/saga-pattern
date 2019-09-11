package net.stedin.demo.saga.orchestrator.route.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveAndPlanWerkorder {
    private Long medewerkerId;
    private LocalDate datum;
    private Werkorder werkorder;
}
