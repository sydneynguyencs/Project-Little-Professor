package ch.zhaw.it.pm2.professor.view;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/**
 * Prints the output to the terminal.
 * The class Display could be replaced by a GUI if wanted.
 * <p>
 * All the methods in this class are getting called from another class, so this class only represents the IO.
 */
public class CliDisplay implements Display {
    TextIO textIO;
    TextTerminal<?> terminal;

    /**
     * Constructor of the class DisplayIO. It initializes the Terminal, TextIO and a Config-Object.
     */
    public CliDisplay() {
        textIO = TextIoFactory.getTextIO();
        terminal = textIO.getTextTerminal();
        }
}
