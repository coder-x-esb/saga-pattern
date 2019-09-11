
package net.stedin.demo.saga.orchestrator.route.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adres {

    @NotEmpty
    @Pattern(regexp = "[0-9]{4}\\s?[aA-zZ]{2}")
    public String postcode;

    @NotEmpty
    public String huisNr;

    @NotEmpty
    public String straat;

    @NotEmpty
    public String woonplaats;
}
