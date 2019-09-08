package kz.sabyr.dddinpractice.core.snackmachine.jpa;

import kz.sabyr.dddinpractice.core.common.jpa.EntityJpa;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "snack_machines")
@Data
public class SnackMachineJpa extends EntityJpa {
    @Column(name = "one_cent_count")
    private int oneCentCount;
    @Column(name = "ten_cent_count")
    private int tenCentCount;
    @Column(name = "quarter_count")
    private int quarterCount;
    @Column(name = "one_dollar_count")
    private int oneDollarCount;
    @Column(name = "five_dollar_count")
    private int fiveDollarCount;
    @Column(name = "twenty_dollar_count")
    private int twentyDollarCount;
}
