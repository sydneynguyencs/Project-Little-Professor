package ch.zhaw.it.pm2.professor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private static final String[] EMPTY_HOUSE =
            {"###############################################################", "# Username:%USER________%         Highscore:%HIGHSCORE%       #", "###############################################################", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "###############################################################", "# Level:%LEVEL% | Time:%TIME%                Score:%SCORE%    #", "###############################################################"};

    private static final String[] EXPECTED_FULL_HOUSE_NO_HALLWAY =
            {"###############################################################", "# Username:%USER________%         Highscore:%HIGHSCORE%       #", "###############################################################", "#                      ################                       #", "#                      #              #                       #", "#                      #     -        #                       #", "#                      #              #                       #", "#                      ################                       #", "#    ################                    ################     #", "#    #              #                    #              #     #", "#    #     +        #                    #     *        #     #", "#    #              #                    #              #     #", "#    ################                    ################     #", "#                      ################                       #", "#                      #              #                       #", "#                      #     /        #                       #", "#                      #              #                       #", "#                      ################                       #","###############################################################", "# Level:%LEVEL% | Time:%TIME%                Score:%SCORE%    #", "###############################################################"};


    @BeforeEach
    void setUp() {

    }


    @Test
    void addToHouseNoHallway() {
        Room.ROOM_UP.addToHouse(EMPTY_HOUSE);
        Room.ROOM_DOWN.addToHouse(EMPTY_HOUSE);
        Room.ROOM_RIGHT.addToHouse(EMPTY_HOUSE);
        Room.ROOM_LEFT.addToHouse(EMPTY_HOUSE);
        String[] actualFullHouse = EMPTY_HOUSE;

        assertArrayEquals(EXPECTED_FULL_HOUSE_NO_HALLWAY, actualFullHouse);
    }
}