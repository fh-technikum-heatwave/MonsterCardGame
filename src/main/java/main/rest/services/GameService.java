package main.rest.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Battle;
import main.PublishSubscribe.Observer;
import main.Tuple.Tuple;
import main.daos.CardDao;
import main.daos.DeckDao;
import main.daos.GameDao;
import main.daos.UserDao;
import main.dtos.UserDeckDTO;
import main.model.Statistik;
import main.model.User;
import main.model.card.Card;

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


    public Tuple<String, String> battle(String uid) {


        try {
            User user = userDao.getById(uid);
            String deckId = deckDao.getDeckIdByUserId(uid);
            List<Card> deckCards = cardDao.getByDeckid(deckId);


            UserDeckDTO userDTO = new UserDeckDTO(user, deckCards);
            Observer observer = new Observer(userDTO);
            Battle.registerForBattle(observer);

            Tuple<UserDeckDTO, UserDeckDTO> players = null;

            try {
                players = observer.getBlockingQueue().take();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            UserDeckDTO winner = players.getWinner();
            UserDeckDTO looser = players.getLooser();


            if (winner.getUser().getId().equals(uid))
                resetDeckId(winner.getDeck());
            else
                resetDeckId(looser.getDeck());

            if (players.getStatus().equals("draw")) {
//                unentschieden
                return new Tuple<>(null, null, "unentschieden", players.getLog());
            }

            if (winner.getUser().getId().equals(uid)) {

                for (var c : winner.getDeck()) {
                    cardDao.updateUserId(c.getId(), winner.getUser().getId());
                }

                return new Tuple<>(winner.getUser().getUsername(), looser.getUser().getUsername(), "Du hast gewonnen", players.getLog());
            } else {
                return new Tuple<>(winner.getUser().getUsername(), looser.getUser().getUsername(), "Du hast verloren", players.getLog());

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


    private boolean resetDeckId(List<Card> cards) {
        for (var c : cards) {
            try {
                cardDao.updateDeckIdByCardId(c.getId(), null);
            } catch (SQLException throwables) {
                return false;
            }
        }

        return true;
    }

}
