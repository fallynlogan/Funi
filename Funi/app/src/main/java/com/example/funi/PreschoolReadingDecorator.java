package com.example.funi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PreschoolReadingDecorator extends QuizDecorator{
    public PreschoolReadingDecorator(QuizInterface decoratedQuiz) {
        super(decoratedQuiz);
        setQuestions(decoratedQuiz);
    }

    private QuizInterface setQuestions(QuizInterface decoratedQuiz) {
        decoratedQuiz.addQuestion("A", new ArrayList<>(Arrays.asList("A", "B", "C", "D")), "A.png");
        decoratedQuiz.addQuestion("E", new ArrayList<>(Arrays.asList("E", "F", "G", "H")), "E.png");
        decoratedQuiz.addQuestion("I", new ArrayList<>(Arrays.asList("I", "J", "K", "L")), "Choose I");
        return decoratedQuiz;
    }


}
