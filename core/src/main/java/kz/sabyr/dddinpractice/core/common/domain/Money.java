package kz.sabyr.dddinpractice.core.common.domain;

import kz.sabyr.dddinpractice.common.ValueObject;
import lombok.Getter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
@Access(AccessType.FIELD)
public class Money extends ValueObject {
    public static final Money NONE =            new Money(0,0,0,0,0,0);
    public static final Money ONE_CENT =        new Money(1,0,0,0,0,0);
    public static final Money TEN_CENT =        new Money(0,1,0,0,0,0);
    public static final Money QUARTER_DOLLAR =  new Money(0,0,1,0,0,0);
    public static final Money ONE_DOLLAR =      new Money(0,0,0,1,0,0);
    public static final Money FIVE_DOLLAR =     new Money(0,0,0,0,1,0);
    public static final Money TWENTY_DOLLAR =   new Money(0,0,0,0,0,1);

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

    public Money() {
        this(0,0,0,0,0,0);
    }

    public Money(int oneCentCount,
                 int tenCentCount,
                 int quarterCount,
                 int oneDollarCount,
                 int fiveDollarCount,
                 int twentyDollarCount) {
        if(oneCentCount < 0 ||
            tenCentCount < 0 ||
            quarterCount < 0 ||
            oneDollarCount < 0 ||
            fiveDollarCount < 0 ||
            twentyDollarCount < 0
        ) {
            throw new IllegalArgumentException();
        }

        this.oneCentCount += oneCentCount;
        this.tenCentCount += tenCentCount;
        this.quarterCount += quarterCount;
        this.oneDollarCount += oneDollarCount;
        this.fiveDollarCount += fiveDollarCount;
        this.twentyDollarCount += twentyDollarCount;
    }

    public BigDecimal getAmount() {
        double amount = oneCentCount * 0.01 +
                tenCentCount * 0.1 +
                quarterCount * 0.25 +
                oneDollarCount +
                fiveDollarCount * 5 +
                twentyDollarCount * 20;

        return new BigDecimal(amount).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static Money add(Money money1, Money money2) {
        Money sum = new Money(
                money1.oneCentCount + money2.oneCentCount,
                money1.tenCentCount + money2.tenCentCount,
                money1.quarterCount + money2.quarterCount,
                money1.oneDollarCount + money2.oneDollarCount,
                money1.fiveDollarCount + money2.fiveDollarCount,
                money1.twentyDollarCount + money2.twentyDollarCount
        );

        return sum;
    }

    public static Money subtract(Money money1, Money money2) {
        Money subtract = new Money(
                money1.oneCentCount - money2.oneCentCount,
                money1.tenCentCount - money2.tenCentCount,
                money1.quarterCount - money2.quarterCount,
                money1.oneDollarCount - money2.oneDollarCount,
                money1.fiveDollarCount - money2.fiveDollarCount,
                money1.twentyDollarCount - money2.twentyDollarCount
        );

        return subtract;
    }

    public static Money multiplyByMultiplier(Money money, int multiplier) {
        Money result = new Money(
                money.getOneCentCount() * multiplier,
                money.getTenCentCount() * multiplier,
                money.getQuarterCount() * multiplier,
                money.getOneDollarCount() * multiplier,
                money.getFiveDollarCount() * multiplier,
                money.getTwentyDollarCount() * multiplier
        );

        return result;
    }

    @Override
    public boolean equals(Object otherObject) {
        Money other = (Money) otherObject;
        return this.oneCentCount == other.oneCentCount &&
                this.tenCentCount == other.tenCentCount &&
                this.quarterCount == other.quarterCount &&
                this.oneDollarCount == other.oneDollarCount &&
                this.fiveDollarCount == other.fiveDollarCount &&
                this.twentyDollarCount == other.twentyDollarCount;

    }

    @Override
    public int hashCode() {
        return Objects.hash(oneCentCount, tenCentCount, quarterCount, oneDollarCount, fiveDollarCount, twentyDollarCount);
    }

    @Override
    public String toString() {
        BigDecimal amount = getAmount();
        if(amount.compareTo(BigDecimal.ONE) < 0) {
            return amount.multiply(new BigDecimal("100")) + " cents";
        } else {
            return amount + " dollars";
        }
    }

    private Money allocateBase(BigDecimal amount) {
        double amountDouble = amount.doubleValue();
        int twentyDollarCount = Math.min((int)(amountDouble / 20), getTwentyDollarCount());
        amountDouble = amountDouble - twentyDollarCount * 20;

        int fiveDollarCount = Math.min((int)(amountDouble / 5), getFiveDollarCount());
        amountDouble = amountDouble - fiveDollarCount * 5;

        int oneDollarCount = Math.min((int) amountDouble, getOneDollarCount());
        amountDouble = amountDouble - oneDollarCount;

        int quarterDollarCount = Math.min((int)(amountDouble / 0.25), getQuarterCount());
        amountDouble = amountDouble - quarterDollarCount * 0.25;

        int tenCentCount = Math.min((int)(amountDouble / 0.1), getTenCentCount());
        amountDouble = amountDouble - tenCentCount * 0.1;

        int oneCentCount = Math.min((int)(amountDouble / 0.01), getOneCentCount());

            return new Money(oneCentCount,tenCentCount, quarterDollarCount, oneDollarCount, fiveDollarCount, twentyDollarCount);
    }

    public Money allocate(BigDecimal amount) {
        if(!canAllocateMoney(amount)) {
            throw new IllegalStateException();
        }

        return allocateBase(amount);
    }

    public boolean canAllocateMoney(BigDecimal money) {
        Money allocatedMoney = allocateBase(money);
        return allocatedMoney.getAmount().compareTo(money) == 0;
    }
}
