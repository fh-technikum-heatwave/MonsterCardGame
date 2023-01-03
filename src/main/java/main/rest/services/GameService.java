package main.rest.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Battle;
import main.PublishSubscribe.Listener;
import main.PublishSubscribe.Observer;
import main.Tuple.Tuple;
import main.daos.CardDao;
import main.daos.DeckDao;
import main.daos.GameDao;
import main.daos.UserDao;
import main.dtos.UserDeckDTO;
import main.model.Deck;
import main.model.Statistik;
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
public class GameService {

    private UserDao userDao;
    private CardDao cardDao;
    private DeckDao deckDao;
    private GameDao gameDao;

    private static Queue<String> battleWaiter = new LinkedList<>();


    public GameService(UserDao userDao, CardDao cardDao, DeckDao deckDao, GameDao gameDao) {
        this.userDao = userDao;
        this.cardDao = cardDao;
        this.deckDao = deckDao;
        this.gameDao = gameDao;
    }


    public Tuple<UserDeckDTO, UserDeckDTO> battle(String uid) {


        try {
            User user = userDao.getById(uid);
            String deckId = deckDao.getDeckIdByUserId(uid);
            List<Card> deckCards = cardDao.getByDeckid(deckId);
            UserDeckDTO userDTO = new UserDeckDTO(user, deckCards);


            Observer observer = new Observer(userDTO);
            Battle.registerForBattle(observer);

            System.out.println("before while loop");


            while (true && !observer.isFinish()) {
                if (observer.list.size() == 2) {
                    break;
                }
            }


            String output = "winner " + observer.getList().get(0) + "\n looser: " + observer.getList().get(1);

            UserDeckDTO winner = observer.getList().get(0);
            UserDeckDTO looser = observer.getList().get(1);

            if (winner == null && looser == null) {
//                unentschieden

                return new Tuple<>(null, null, "unentschieden");
            }


            if (winner.getUser().getId().equals(uid)) {
                return new Tuple<>(winner, looser, "Du hast gewonnen");
            } else {
                return new Tuple<>(winner, looser, "Du hast verloren" );
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Statistik getStats(String userId) {
        try {
            return gameDao.getByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Statistik> getScores() {
        try {
            return gameDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
