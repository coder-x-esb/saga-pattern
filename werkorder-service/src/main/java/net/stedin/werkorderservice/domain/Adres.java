
package net.stedin.werkorderservice.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Embeddable
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
