package com.example.funi;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Quiz {
    public ArrayList<Question> questions;
    private Question q;

    public Quiz() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion(String answer, ArrayList<String> answerChoices, String question) {
        Question quest = new Question();
        quest.setAnswer(answer);
        quest.setAnswerChoices(answerChoices);
        quest.setQuestion(question);
        questions.add(quest);
    }

    public Question getQuiz() {
        Iterator<Question> it = questions.iterator();
        if(it.hasNext()) {
            q = it.next();
        }
        return q;
    }

    public void checkAnswer(String chosenAnswer) {

    }
}
