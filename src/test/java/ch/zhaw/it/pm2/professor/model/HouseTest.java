package ch.zhaw.it.pm2.professor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HouseTest {

    private House house;
    private static final String USERNAME = "Cellestine";

    static final String[] EMPTY_HOUSE =
            {"###############################################################","# Username:%USER________%         Highscore:%HIGHSCORE%       #","###############################################################","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","###############################################################","# Level:%LEVEL% | Time:%TIME%                Score:%SCORE%    #","###############################################################"};
    static final String[] EXPECTED_FULL_HOUSE_NO_USERDATA =
            {"###############################################################","# Username:%USER________%         Highscore:%HIGHSCORE%       #","###############################################################","#                      ################                       #","#                      #              #                       #","#                      #     -        #                       #","#                      #              #                       #","#                      ################                       #","#    ################                    ################     #","#    #              #                    #              #     #","#    #     +        #                    #     *        #     #","#    #              #                    #              #     #","#    ################                    ################     #","#                      ################                       #","#                      #              #                       #","#                      #     /        #                       #","#                      #              #                       #","#                      ################                       #","###############################################################","# Level:%LEVEL% | Time:%TIME%                Score:%SCORE%    #","###############################################################"};
    static final String[] HOUSE_WITH_ROOMS_AND_USERDATA =
            {"###############################################################","# Username:Cellestine             Highscore:%HIGHSCORE%       #","###############################################################","#                      ################                       #","#                      #              #                       #","#                      #     -        #                       #","#                      #              #                       #","#                      ################                       #","#    ################  ################  ################     #","#    #              #  #              #  #              #     #","#    #     +        #  #     --       #  #     *        #     #","#    #              #  #              #  #              #     #","#    ################  ################  ################     #","#                      ################                       #","#                      #              #                       #","#                      #     /        #                       #","#                      #              #                       #","#                      ################                       #","###############################################################","# Level:%LEVEL% | Time:2                     Score:1000       #","###############################################################"};
    static final String[] ENTRANCE =
            {"###############################################################","#                                                             #","#                                                             #","#                                                             #","#                                                             #","#  ,   .     .                     .                          #","#  | . |     |                     |                          #","#  | ) ) ,-. | ,-. ,-. ;-.-. ,-.   |-  ,-.                    #","#  |/|/  |-' | |   | | | | | |-'   |   | |                    #","#  ' '   `-' ' `-' `-' ' ' ' `-'   `-' `-'                    #","#                                                             #","#  .   .   .   .                                           .  #","#  | o |   |   |                    ,-                     |  #","#  | . |-  |-  | ,-.   ;-. ;-. ,-.  |  ,-. ,-. ,-. ,-. ;-. |  #","#  | | |   |   | |-'   | | |   | |  |- |-' `-. `-. | | |      #","#  ' ' `-' `-' ' `-'   |-' '   `-'  |  `-' `-' `-' `-' '   o  #","#                                                             #","#                                                             #","#                                                             #","#                                                             #","###############################################################"};
    @Mock
    private Level levelMock;


    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(levelMock.getRooms()).thenReturn(Room.values());

        house = new House(() -> 2);
        assertNotNull(house);
    }

    @Test
    void testChangeStateToHallway() throws IOException {
        house.changeState(House.State.HALLWAY);
        assertEquals(House.State.HALLWAY, house.getState());
    }

    @Test
    void testChangeStateToEntrance() throws IOException {
        house.changeState(House.State.ENTRANCE);
        assertEquals(House.State.ENTRANCE, house.getState());
    }

    @Test
    void testStateEntrancePrintAsArray() throws IOException {
        house.changeState(House.State.ENTRANCE);
        String[] actualHouse = house.printLevelAsArray(levelMock);
        assertArrayEquals(ENTRANCE, actualHouse);
    }

    @Test
    void testSetUserDataAndAddRoomsInStateHallway() throws IOException {
        house.changeState(House.State.HALLWAY);
        house.setUsername(USERNAME);
        house.setTime(2);
        house.setScore(1000);
        String[] actualHouse = house.printLevelAsArray(levelMock);

        assertArrayEquals(HOUSE_WITH_ROOMS_AND_USERDATA, actualHouse);
    }

    @Test
    void testNullState() throws IOException {
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> house.changeState(null));

    }
}