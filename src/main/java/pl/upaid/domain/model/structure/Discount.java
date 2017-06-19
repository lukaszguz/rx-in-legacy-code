package pl.upaid.domain.model.structure;

import lombok.Value;

@Value(staticConstructor = "of")
public class Discount {
    private Double amount;
}
