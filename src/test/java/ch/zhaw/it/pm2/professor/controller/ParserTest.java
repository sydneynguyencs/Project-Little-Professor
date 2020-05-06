package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.model.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static ch.zhaw.it.pm2.professor.model.Config.Command.*;
import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    List<Config.Command> acceptedCommands;
    Parser parser;
    private static final String VALID_INPUT_DOWN_TEST_TRIM = "  down     ";
    private static final String VALID_INPUT_GO = "go";
    private static final String VALID_INPUT_HELP = "help";
    private static final String VALID_INPUT_LEFT_TEST_TRIM = "   left               ";
    private static final String VALID_INPUT_QUIT = "quit";
    private static final String VALID_INPUT_RIGHT = "right";
    private static final String VALID_INPUT_UNKNOWN = "?";
    private static final String VALID_INPUT_UP_TEST_TRIM = "     up  ";
    private static final String INVALID_INPUT_ONE = "SomethingSeemsToBeWrong";
    private static final String INVALID_INPUT_TWO = "d0wn";
    private static final String EMPTY_STRING = "";

    private static final String VALID_USERNAME = "Cellestine";
    private static final String VALID_USERNAME_TEST_TRIM_15_CHARS = " Cellestine    ";
    private static final String VALID_USERNAME_FOUR_CHARS = "Otto";
    private static final String INVALID_USERNAME = "ThisUsernameHasToManyCharsAndIsNotAccepted";

    @BeforeEach
    void setUp() {
        parser = new Parser();
        acceptedCommands = new ArrayList<>();
        acceptedCommands.add(DOWN);
        acceptedCommands.add(GO);
        acceptedCommands.add(HELP);
        acceptedCommands.add(LEFT);
        acceptedCommands.add(QUIT);
        acceptedCommands.add(RIGHT);
        acceptedCommands.add(UNKNOWN);
        acceptedCommands.add(UP);
        assertNotNull(acceptedCommands);
    }

    @Test
    void parseValidDownInputWithSpaces() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands, VALID_INPUT_DOWN_TEST_TRIM);
        assertEquals(DOWN,actualCommand);
    }

    @Test
    void parseValidInputLeftWithSpaces() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands, VALID_INPUT_LEFT_TEST_TRIM);
        assertEquals(LEFT,actualCommand);
    }

    @Test
    void parseValidInputHelp() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands,VALID_INPUT_HELP);
        assertEquals(HELP,actualCommand);
    }

    @Test
    void parseValidInputQuit() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands,VALID_INPUT_QUIT);
        assertEquals(QUIT,actualCommand);
    }

    @Test
    void parseValidInputRight() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands,VALID_INPUT_RIGHT);
        assertEquals(RIGHT,actualCommand);
    }

    @Test
    void parseValidInputUnknown() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands,VALID_INPUT_UNKNOWN);
        assertEquals(UNKNOWN,actualCommand);
    }

    @Test
    void parseValidInputUpWithSpaces() throws InvalidInputException {
        Config.Command actualCommand = parser.parseInput(acceptedCommands, VALID_INPUT_UP_TEST_TRIM);
        assertEquals(UP,actualCommand);
    }

    @Test
    void parseInvalidInput() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseInput(acceptedCommands, INVALID_INPUT_ONE));
    }

    @Test
    void parseAnotherInvalidInput() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseInput(acceptedCommands, INVALID_INPUT_TWO));
    }

    @Test
    void parseEmptyString() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseInput(acceptedCommands, EMPTY_STRING));
    }

    @Test
    void parseNullInput() throws NullPointerException {
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> parser.parseInput(acceptedCommands, null));
    }

    @Test
    void parseValidName() throws InvalidInputException {
        String actualUsername = parser.parseName(VALID_USERNAME);
        assertEquals("Cellestine", actualUsername);
    }

    @Test
    void parseValidNameFourChars() throws InvalidInputException {
        String actualUsername = parser.parseName(VALID_USERNAME_FOUR_CHARS);
        assertEquals("Otto", actualUsername);
    }

    @Test
    void parseValidNameTrimAccidentallyGivenSpaces() throws InvalidInputException {
        String actualUsername = parser.parseName(VALID_USERNAME_TEST_TRIM_15_CHARS);
        assertEquals("Cellestine", actualUsername);
    }

    @Test
    void parseInvalidName() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseName(INVALID_USERNAME));
    }

    @Test
    void parseInvalidNameEmptyString() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseName(EMPTY_STRING));
    }

    @Test
    void parseInvalidNameThreeChars() throws InvalidInputException {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> parser.parseName("abc"));
    }

    @Test
    void parseInvalidNameNullObject() throws InvalidInputException {
        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> parser.parseName(null));
    }
}