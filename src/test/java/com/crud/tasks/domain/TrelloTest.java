package com.crud.tasks.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class TrelloTest {

    private Trello trello;
    int board = 2;
    int card = 17;

    @Test
    void boardAndCardTest() {
        //Given
        trello = new Trello();
        trello.setBoard(board);
        trello.setCard(card);

        Assertions.assertEquals(board,trello.getBoard());
        Assertions.assertEquals(card,trello.getCard());
    }

}