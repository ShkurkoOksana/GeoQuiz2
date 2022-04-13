package com.bignerdranch.android.geoquiz.model;

public interface QuizModel {
    int getQuestionResId();

    int getNextQuestionResId();

    int getPrevQuestionResId();

    boolean getTrueAnswer();
}
