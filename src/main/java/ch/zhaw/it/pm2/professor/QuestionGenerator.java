package ch.zhaw.it.pm2.professor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;
import java.util.Random;

public class QuestionGenerator {
    static Question question;
    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine engine = sem.getEngineByName("JavaScript");

    private int getRandomInt(int start, int end) {
        return start + (int) (new Random().nextFloat() * (end - start));
    }

    private Double getRandomDouble(double start, double end) {
        return start + new Random().nextDouble() * (end - start);
    }



}


