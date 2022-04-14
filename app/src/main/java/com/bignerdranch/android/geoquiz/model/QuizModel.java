package com.bignerdranch.android.geoquiz.model;

import java.io.Serializable;

public interface QuizModel {
    int getQuestionResId();

    int getNextQuestionResId();

    int getPrevQuestionResId();

    boolean getTrueAnswer();
}
