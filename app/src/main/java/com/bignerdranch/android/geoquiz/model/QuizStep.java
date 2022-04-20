package com.bignerdranch.android.geoquiz.model;

import java.io.Serializable;

public class QuizStep implements Serializable {
    private Question mQuestion;
    private boolean isAnswered;
    private boolean isAnswerCorrect;
    private boolean isAnswerCheating;

    public QuizStep(Question question, boolean isAnswered, boolean isAnswerCorrect) {
        mQuestion = question;
        this.isAnswered = isAnswered;
        this.isAnswerCorrect = isAnswerCorrect;
    }

    public Question getQuestion() {
        return mQuestion;
    }

    public void setQuestion(Question question) {
        mQuestion = question;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isAnswerCorrect() {
        return isAnswerCorrect;
    }

    public void setAnswerCorrect(boolean answerCorrect) {
        isAnswerCorrect = answerCorrect;
    }

    public boolean isAnswerCheating() {
        return isAnswerCheating;
    }

    public void setAnswerCheating(boolean answerCheating) {
        isAnswerCheating = answerCheating;
    }
}
