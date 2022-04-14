package com.bignerdranch.android.geoquiz.model;

import com.bignerdranch.android.geoquiz.R;

import java.io.Serializable;

public class QuizModelImpl implements QuizModel, Serializable {
    Question[] mQuestions = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_america, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    @Override
    public int getQuestionResId() {
        return mQuestions[mCurrentIndex].getTextResId();
    }

    @Override
    public int getNextQuestionResId() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
        return mQuestions[mCurrentIndex].getTextResId();
    }

    @Override
    public int getPrevQuestionResId() {
        if (mCurrentIndex == 0) {
            mCurrentIndex = mQuestions.length - 1;
        } else {
            mCurrentIndex = (mCurrentIndex - 1) % mQuestions.length;
        }

        return mQuestions[mCurrentIndex].getTextResId();
    }

    @Override
    public boolean getTrueAnswer() {
        return mQuestions[mCurrentIndex].isAnswerTrue();
    }
}
