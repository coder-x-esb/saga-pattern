package net.stedin.demo.saga.orchestrator.domain;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Werkorder {
    private Long id;
    private String omschrijving;
}
