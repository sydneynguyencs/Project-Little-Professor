package ch.zhaw.it.pm2.professor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HouseTest {

    House house;

    private static final String USERNAME = "Cellestine";
    private static final String ENTRANCE_STRING =
                            "###############################################################\n" +
                            "#                                                             #\n" +
                            "#                                                             #\n" +
                            "#                                                             #\n" +
                            "#                                                             #\n" +
                            "#  ,   .     .                     .                          #\n" +
                            "#  | . |     |                     |                          #\n" +
                            "#  | ) ) ,-. | ,-. ,-. ;-.-. ,-.   |-  ,-.                    #\n" +
                            "#  |/|/  |-' | |   | | | | | |-'   |   | |                    #\n" +
                            "#  ' '   `-' ' `-' `-' ' ' ' `-'   `-' `-'                    #\n" +
                            "#                                                             #\n" +
                            "#  .   .   .   .                                           .  #\n" +
                            "#  | o |   |   |                    ,-                     |  #\n" +
                            "#  | . |-  |-  | ,-.   ;-. ;-. ,-.  |  ,-. ,-. ,-. ,-. ;-. |  #\n" +
                            "#  | | |   |   | |-'   | | |   | |  |- |-' `-. `-. | | |      #\n" +
                            "#  ' ' `-' `-' ' `-'   |-' '   `-'  |  `-' `-' `-' `-' '   o  #\n" +
                            "#                                                             #\n" +
                            "#                                                             #\n" +
                            "#                                                             #\n" +
                            "#                                                             #\n" +
                            "###############################################################\n";

    private static final String HOUSE_WITH_ROOMS_AND_USERDATA =
                            "###############################################################\n" +
                            "# Username:Cellestine             Highscore:%HIGHSCORE%       #\n" +
                            "###############################################################\n" +
                            "#                      ################                       #\n" +
                            "#                      #              #                       #\n" +
                            "#                      #     -        #                       #\n" +
                            "#                      #              #                       #\n" +
                            "#                      ################                       #\n" +
                            "#    ################  ################  ################     #\n" +
                            "#    #              #  #              #  #              #     #\n" +
                            "#    #     +        #  #     --       #  #     *        #     #\n" +
                            "#    #              #  #              #  #              #     #\n" +
                            "#    ################  ################  ################     #\n" +
                            "#                      ################                       #\n" +
                            "#                      #              #                       #\n" +
                            "#                      #     /        #                       #\n" +
                            "#                      #              #                       #\n" +
                            "#                      ################                       #\n" +
                            "###############################################################\n" +
                            "# Level:%LEVEL% | Time:2                     Score:1000       #\n" +
                            "###############################################################\n";


    @BeforeEach
    void setUp() throws IOException {
        house = new House();
        assertNotNull(house);
    }

    @Test
    void changeStateToHallway() throws IOException {
        house.changeState(House.State.HALLWAY);
        assertEquals(House.State.HALLWAY, house.getState());
    }

    @Test
    void changeStateToEntrance() throws IOException {
        house.changeState(House.State.ENTRANCE);
        assertEquals(House.State.ENTRANCE, house.getState());
    }

    @Test
    void testEntranceToString() throws IOException {
        house.changeState(House.State.ENTRANCE);
        String actualHouse = house.toString();

        assertEquals(ENTRANCE_STRING, actualHouse);
    }

    @Test
    void setUserDataAndAddRoomsInStateHallway() throws IOException {
        house.changeState(House.State.HALLWAY);
        house.setUsername(USERNAME);
        house.setTime(2);
        house.setScore(1000);
        String actualHouse = house.toString();

        assertEquals(HOUSE_WITH_ROOMS_AND_USERDATA, actualHouse);
    }

    @Test
    void testNullState() throws IOException {
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> house.changeState(null));

    }
}