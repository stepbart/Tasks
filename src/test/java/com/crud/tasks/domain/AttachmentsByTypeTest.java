package com.crud.tasks.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class AttachmentsByTypeTest {

    @Mock
    private Trello trello;

    @Test
    void getterAndSetterTest() {
        //Given
        AttachmentsByType attachments = new AttachmentsByType();
        trello = new Trello();
        trello.setBoard(1);
        trello.setCard(2);

        //When
        attachments.setTrello(trello);

        //Then
        assertEquals(2,attachments.getTrello().getCard());
    }
}