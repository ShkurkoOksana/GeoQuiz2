package com.bignerdranch.android.geoquiz.model;

import com.bignerdranch.android.geoquiz.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private List<QuizStep> mQuizStepList = createQuizSteps();

    private List<QuizStep> createQuizSteps() {
        List<QuizStep> quizStepList = new ArrayList<>();

        for (int i = 0; i < mQuestions.length; i++) {
            quizStepList.add(new QuizStep(mQuestions[i], false, false));
        }

        return quizStepList;
    }

    public boolean isQuestionAnswered() {
        return mQuizStepList.get(mCurrentIndex).isAnswered();
    }

    public void setQuestionAnswered(boolean userAnswer) {
        mQuizStepList.get(mCurrentIndex).setAnswered(true);

        if (userAnswer == mQuizStepList.get(mCurrentIndex).getQuestion().isAnswerTrue()) {
            mQuizStepList.get(mCurrentIndex).setAnswerCorrect(true);
        }
    }

    public boolean isAllQuestionAnswered() {
        int countAnsweredQuestions = 0;

        for (QuizStep quizStep : mQuizStepList) {
            if (quizStep.isAnswered) {
                countAnsweredQuestions++;
            }
        }

        return countAnsweredQuestions == mQuizStepList.size();
    }

    public int getPercentageOfRightQuestions() {
        int userScore = 0;
        int totalScore = 0;

        for (QuizStep quizStep : mQuizStepList) {
            if (quizStep.isAnswerCorrect()) {
                userScore++;
            }

            totalScore++;
        }

        return (userScore * 100) / totalScore;
    }

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
