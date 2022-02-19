package com.crud.tasks.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TrelloValidatorTestSuite {

    @Mock
    private TrelloValidator mockedValidator;

    @Autowired
    private TrelloValidator realValidator;

    @Test
    void validateCardTest(){
        //Given
        TrelloCard trelloCard = new TrelloCard("name","description","pos","listId");
        doNothing().when(mockedValidator).validateCard(trelloCard);

        //When
        mockedValidator.validateCard(trelloCard);

        //Then
        verify(mockedValidator,times(1)).validateCard(trelloCard);
    }

    @Test
    void validateTrelloBoardsTest(){
        //Given
        TrelloBoard trelloBoard1 = new TrelloBoard("1","name1",new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("1","name2",new ArrayList<>());
        TrelloBoard trelloBoard3 = new TrelloBoard("1","test",new ArrayList<>());
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);
        trelloBoards.add(trelloBoard3);

        //When
        List<TrelloBoard> testList = realValidator.validateTrelloBoards(trelloBoards);

        //Then
        Assertions.assertEquals(2,testList.size());
    }
}
