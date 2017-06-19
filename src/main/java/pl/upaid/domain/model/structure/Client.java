package pl.upaid.domain.model.structure;

import lombok.Value;

@Value(staticConstructor = "of")
public class Client {
    private String name;
    private Integer age;
}
