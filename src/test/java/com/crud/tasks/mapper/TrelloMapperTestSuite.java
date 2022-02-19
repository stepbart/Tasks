package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    TrelloList trelloList1 = new TrelloList("45","trelloListTest1",false);
    TrelloList trelloList2 = new TrelloList("46","trelloListTest2",true);
    TrelloList trelloList3 = new TrelloList("47","trelloListTest3",false);

    TrelloListDto trelloListDto1 = new TrelloListDto("45","trelloListTest1",false);
    TrelloListDto trelloListDto2 = new TrelloListDto("46","trelloListTest2",true);
    TrelloListDto trelloListDto3 = new TrelloListDto("47","trelloListTest3",false);

    List<TrelloList> trelloLists1 = new ArrayList<>();
    List<TrelloList> trelloLists2 = new ArrayList<>();

    List<TrelloListDto> trelloListsDto1 = new ArrayList<>();
    List<TrelloListDto> trelloListsDto2 = new ArrayList<>();

    @Test
    public void mapToBoardsTest(){
        //Given
        trelloListsDto1.add(trelloListDto1);
        trelloListsDto2.add(trelloListDto2);
        trelloListsDto2.add(trelloListDto3);
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("48","trelloBoard1",trelloListsDto1);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("49","trelloBoard2",trelloListsDto2);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto1);
        trelloBoardDtos.add(trelloBoardDto2);

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);

        //Then
        assertEquals(2,trelloBoards.size());
        assertEquals(2,trelloBoards.get(1).getLists().size());
        assertEquals("48",trelloBoards.get(0).getId());
    }

    @Test
    public void mapToBoardsDtoTest(){
        //Given
        trelloLists1.add(trelloList1);
        trelloLists2.add(trelloList2);
        trelloLists2.add(trelloList3);
        TrelloBoard trelloBoard1 = new TrelloBoard("50","trelloBoard1",trelloLists1);
        TrelloBoard trelloBoard2 = new TrelloBoard("51","trelloBoard2",trelloLists2);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(2,trelloBoardsDto.size());
        assertEquals(2,trelloBoardsDto.get(1).getLists().size());
        assertEquals("50",trelloBoardsDto.get(0).getId());
    }

    @Test
    public void mapToListTest(){
        //Given
        trelloListsDto1.add(trelloListDto1);
        trelloListsDto2.add(trelloListDto2);
        trelloListsDto2.add(trelloListDto3);

        //When
        List<TrelloList> trelloLists1 = trelloMapper.mapToList(trelloListsDto1);
        List<TrelloList> trelloLists2 = trelloMapper.mapToList(trelloListsDto2);

        //Then
        assertEquals(1,trelloLists1.size());
        assertEquals(2,trelloLists2.size());
        assertEquals("46",trelloLists2.get(0).getId());
    }

    @Test
    public void mapToListDtoTest(){
        //Given
        trelloLists1.add(trelloList1);
        trelloLists2.add(trelloList2);
        trelloLists2.add(trelloList3);

        //When
        List<TrelloListDto> trelloListsDto1 = trelloMapper.mapToListDto(trelloLists1);
        List<TrelloListDto> trelloListsDto2 = trelloMapper.mapToListDto(trelloLists2);

        //Then
        assertEquals(1,trelloListsDto1.size());
        assertEquals(2,trelloListsDto2.size());
        assertEquals("47",trelloListsDto2.get(1).getId());
    }

    @Test
    public void mapToCardDtoTest(){
        //Given
        TrelloCard trelloCard = new TrelloCard("test111","description111","pos111","55");
        TrelloCardDto trelloCardDto = new TrelloCardDto("test111","description111","pos111","55");

        //When
        TrelloCardDto testTrelloCartDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals(trelloCardDto.getName(),testTrelloCartDto.getName());
        assertEquals(trelloCardDto.getDescription(),testTrelloCartDto.getDescription());
        assertEquals(trelloCardDto.getPos(),testTrelloCartDto.getPos());
        assertEquals(trelloCardDto.getListId(),testTrelloCartDto.getListId());
    }

    @Test
    public void mapToCardTest(){
        //Given
        TrelloCard trelloCard = new TrelloCard("test111","description111","pos111","55");
        TrelloCardDto trelloCardDto = new TrelloCardDto("test111","description111","pos111","55");

        //When
        TrelloCard testTrelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals(trelloCard.getName(),testTrelloCard.getName());
        assertEquals(trelloCard.getDescription(),testTrelloCard.getDescription());
        assertEquals(trelloCard.getPos(),testTrelloCard.getPos());
        assertEquals(trelloCard.getListId(),testTrelloCard.getListId());
    }
}
