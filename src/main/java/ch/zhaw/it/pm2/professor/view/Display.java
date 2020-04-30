package ch.zhaw.it.pm2.professor.view;

/**
 * We use an Interface for our prototype.
 * At later stages it would be easier to replace the DisplayIO, which in the prototype is with berxyTextIO,
 * to a JavaFX-View (or another View).
 */
public interface Display {
    public void messageUserForInput();

    public void welcomeMessage();

    public void requestUsername();

    public void seeHouse();

    public void seeTheHighscores();

    public void displayHighscores();

    public void navigate();

    public void timeIsUp();

    public void levelComplete();

    public String getNextUserInput();


}
