package main.dtos;

import main.model.User;
import main.model.card.Card;

import java.util.LinkedList;
import java.util.List;

public class UserDeckDTO {
    private User user;
    private List<Card> deck = new LinkedList<>();
}
