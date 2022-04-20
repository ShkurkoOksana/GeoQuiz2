package com.bignerdranch.android.geoquiz.model;

import java.io.Serializable;

public class CheatModelImpl implements CheatModel {
    private boolean mIsCheating = false;
    private boolean mAnswerTrue;

    @Override
    public boolean isCheating() {
        return mIsCheating;
    }

    @Override
    public void setCheating(boolean cheating) {
        mIsCheating = cheating;
    }

    @Override
    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    @Override
    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
