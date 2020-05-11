package ch.zhaw.it.pm2.professor.model;

import org.junit.jupiter.api.Test;

import static ch.zhaw.it.pm2.professor.model.HouseTest.*;
import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void testAddToHouseNoHallway() {
        Room.ROOM_UP.addToHouse(EMPTY_HOUSE);
        Room.ROOM_DOWN.addToHouse(EMPTY_HOUSE);
        Room.ROOM_RIGHT.addToHouse(EMPTY_HOUSE);
        Room.ROOM_LEFT.addToHouse(EMPTY_HOUSE);
        String[] actualFullHouse = EMPTY_HOUSE;
        assertArrayEquals(EXPECTED_FULL_HOUSE_NO_USERDATA, actualFullHouse);
    }

    @Test
    void testAddToNull() {
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> Room.ROOM_UP.addToHouse(null));
    }
}