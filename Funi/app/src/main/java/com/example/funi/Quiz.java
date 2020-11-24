package com.example.funi;
import java.util.ArrayList;
import java.util.Iterator;

public class Quiz implements QuizInterface {
    public ArrayList<Question> questions = new ArrayList<>();
    Iterator<Question> it;
    private Question q;
    public boolean hasEnded = false;

    @Override
    public void addQuestion(String answer, ArrayList<String> answerChoices, String question) {
        Question quest = new Question();
        quest.setAnswer(answer);
        quest.setAnswerChoices(answerChoices);
        quest.setQuestion(question);
        questions.add(quest);
    }

    @Override
    public Question getCurrentQuestion() {
        it = questions.iterator();
        if(it.hasNext()) {
            q = it.next();
        }
        return q;
    }

    @Override
    public QuizInterface getQuiz() {
        return new Quiz();
    }

    @Override
    public Question getNextQuestion() {
        if(it.hasNext()) {
            q = it.next();
        } else {
            hasEnded = true;
        }
        return q;
    }

    @Override
    public Boolean getHasEnded() {
        return hasEnded;
    }

    @Override
    public Question checkAnswer(String chosenAnswer) {
        if(!hasEnded) {
            if (chosenAnswer == q.getAnswer()) {
                //notifyObserverCorrect
            } else {
                //notifyObserverIncorrect
            }
            q = getNextQuestion();
        }
        return q;
    }
}
