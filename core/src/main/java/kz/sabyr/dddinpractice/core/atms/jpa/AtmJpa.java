package kz.sabyr.dddinpractice.core.atms.jpa;

import kz.sabyr.dddinpractice.core.common.jpa.EntityJpa;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "atms")
@Getter
@Setter
public class AtmJpa extends EntityJpa {
    @Column(name = "money_charged")
    private BigDecimal moneyCharged;

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