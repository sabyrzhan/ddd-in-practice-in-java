package kz.sabyr.dddinpractice.core.snackmachine.jpa;

import kz.sabyr.dddinpractice.core.common.jpa.EntityJpa;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "slots")
@Data
public class SlotJpa extends EntityJpa {
    private int quantity;
    private BigDecimal price;
    @Column(name = "snack_id")
    private long snackId;
    @Column(name = "snack_machine_id")
    private long snackMachineId;
    private int position;
}
