package kz.sabyr.dddinpractice.core.snackmachine.domain;

import kz.sabyr.dddinpractice.common.AggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Snack extends AggregateRoot {
    public static final Snack NONE = new Snack(0, "None");
    public static final Snack CHOCOLATE = new Snack(1, "Chocolate");
    public static final Snack SODA = new Snack(2, "Soda");
    public static final Snack GUM = new Snack(3, "Gum");

    private String name;

    private Snack() {
    }

    private Snack(long id, String name) {
        this.setId(id);
        this.name = name;
    }

    public static class SnackBuilder {
        private long id;
        private String name;

        public SnackBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Snack build() {
            return new Snack(id, name);
        }
    }
}