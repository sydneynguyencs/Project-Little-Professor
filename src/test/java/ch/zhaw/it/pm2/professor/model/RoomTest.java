package ch.zhaw.it.pm2.professor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private static final String[] emtyHouse =
            {"###############################################################", "# Username:%USER________%         Highscore:%HIGHSCORE%       #", "###############################################################", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "#                                                             #", "###############################################################", "# Level:%LEVEL% | Time:%TIME%                Score:%SCORE%    #", "###############################################################"};

    private static final String[] expectedFullHouse =
            {"###############################################################", "# Username:%USER________%         Highscore:%HIGHSCORE%       #", "###############################################################", "#                      ################                       #", "#                      #              #                       #", "#                      #     -        #                       #", "#                      #              #                       #", "#                      ################                       #", "#    ################                    ################     #", "#    #              #                    #              #     #", "#    #     +        #                    #     *        #     #", "#    #              #                    #              #     #", "#    ################                    ################     #", "#                      ################                       #", "#                      #              #                       #", "#                      #     /        #                       #", "#                      #              #                       #", "#                      ################                       #","###############################################################", "# Level:%LEVEL% | Time:%TIME%                Score:%SCORE%    #", "###############################################################"};

    @BeforeEach
    void setUp() {

    }


    @Test
    void addToHouse() {
        Room.ROOM_UP.addToHouse(emtyHouse);
        Room.ROOM_DOWN.addToHouse(emtyHouse);
        Room.ROOM_RIGHT.addToHouse(emtyHouse);
        Room.ROOM_LEFT.addToHouse(emtyHouse);
        String[] actualFullHouse = emtyHouse;

        assertArrayEquals(expectedFullHouse, actualFullHouse);
    }
}