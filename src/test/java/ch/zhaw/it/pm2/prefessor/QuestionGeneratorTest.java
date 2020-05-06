package ch.zhaw.it.pm2.prefessor;
import ch.zhaw.it.pm2.professor.controller.QuestionGenerator;
import ch.zhaw.it.pm2.professor.model.Question;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuestionGeneratorTest {
    QuestionGenerator questionGenerator;
    Question controlQuestion;



    @BeforeEach
    void setUp() throws Exception {
        questionGenerator = new QuestionGenerator();
        controlQuestion = new Question();
    }

    @Test
    void getQuestionIntTest(){
        //Todo
    }

    @Test
    void getQuestionDoubleTest(){
        //Todo
    }
}
