package main.Tuple;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Tuple<T1, T2> {


    private String status;
    private T1 winner;
    private T2 looser;


    public Tuple(T1 winner, T2 looser, String status) {
        setWinner(winner);
        setLooser(looser);
        setStatus(status);
    }
}
