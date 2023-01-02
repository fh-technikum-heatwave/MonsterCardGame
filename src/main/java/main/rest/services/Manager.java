package main.rest.services;

import main.dtos.UserDeckDTO;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Manager {

    public final BlockingQueue<UserDeckDTO> waiter = new LinkedBlockingQueue<>();

    public void battle(UserDeckDTO dto) {
            waiter.add(dto);

            while (waiter.size()!=2){
                System.out.println(waiter.size());
            }

    }
}
