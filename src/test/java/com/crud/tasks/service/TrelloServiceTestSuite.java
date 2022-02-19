package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void fetchTrelloBoardsTest(){
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1","trelloListDtoName1",false);
        TrelloListDto trelloListDto2 = new TrelloListDto("2","trelloListDtoName2",true);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloListDto1);
        trelloListDtoList.add(trelloListDto2);
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("3","name1",trelloListDtoList);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("4","name2",new ArrayList<>());
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto1);
        trelloBoardDtoList.add(trelloBoardDto2);

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtoList);

        //When
        List<TrelloBoardDto> testList = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(2,testList.size());
        assertEquals("name2",testList.get(1).getName());
        assertEquals(2,testList.get(0).getLists().size());

    }

    @Test
    void createTrelloCardTest(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("trelloCardName","description","pos","listId");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1","name","shortUrl");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto testCard = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals("1",testCard.getId());
        assertEquals("name",testCard.getName());
        assertEquals("shortUrl",testCard.getShortUrl());
    }
}
