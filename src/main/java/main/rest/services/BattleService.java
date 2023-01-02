package main.rest.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Battle;
import main.PublishSubscribe.Listener;
import main.PublishSubscribe.Observer;
import main.daos.CardDao;
import main.daos.DeckDao;
import main.daos.UserDao;
import main.dtos.UserDeckDTO;
import main.model.Deck;
import main.model.User;
import main.model.card.Card;
import main.rest.http.ContentType;
import main.rest.http.HttpStatus;
import main.rest.server.Response;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Getter
@Setter(AccessLevel.PRIVATE)
public class BattleService {

    private UserDao userDao;
    private CardDao cardDao;
    private DeckDao deckDao;

    private static Queue<String> battleWaiter = new LinkedList<>();


    public BattleService(UserDao userDao, CardDao cardDao, DeckDao deckDao) {
        this.userDao = userDao;
        this.cardDao = cardDao;
        this.deckDao = deckDao;
    }


    public String battle(String uid) {


        try {
            User user = userDao.getById(uid);
            String deckId = deckDao.getDeckIdByUserId(uid);
            List<Card> deckCards = cardDao.getByDeckid(deckId);
            UserDeckDTO userDTO = new UserDeckDTO(user, deckCards);


            System.out.println(Thread.currentThread().getName());

            Observer observer = new Observer(userDTO);

            Battle.registerForBattle(observer);

            System.out.println("before while loop");





            String output = "winner " + observer.winner + "\n looser: " + observer.looser;


            return output;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
