package com.bignerdranch.android.geoquiz.model;

import java.io.Serializable;

public interface CheatModel extends Serializable {
    boolean isCheating();

    void setCheating(boolean cheating);

    boolean isAnswerTrue();

    void setAnswerTrue(boolean answerTrue);
}
