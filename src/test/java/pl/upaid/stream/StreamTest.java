package pl.upaid.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.stream.Stream;

@Slf4j
public class StreamTest {

    @Test
    public void should_only_concat_two_streams() {
        Stream<String> first = Stream.of("A", "B", "C");
        Stream<String> second = Stream.of("d", "e", "f");

    }


    @Test
    public void should_only_concat_three_streams() {
        Stream<String> first = Stream.of("A", "B", "C");
        Stream<String> second = Stream.of("d", "e", "f");
        Stream<String> third = Stream.of("G", "H", "I");

    }
}