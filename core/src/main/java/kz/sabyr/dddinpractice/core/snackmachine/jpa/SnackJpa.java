package kz.sabyr.dddinpractice.core.snackmachine.jpa;

import kz.sabyr.dddinpractice.core.common.jpa.EntityJpa;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "snacks")
@Data
public class SnackJpa extends EntityJpa {
    private String name;
}
