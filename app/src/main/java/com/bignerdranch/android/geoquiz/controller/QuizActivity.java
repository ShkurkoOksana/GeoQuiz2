package com.bignerdranch.android.geoquiz.controller;

import static android.widget.Toast.makeText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.bignerdranch.android.geoquiz.R;
import com.bignerdranch.android.geoquiz.databinding.ActivityQuizBinding;
import com.bignerdranch.android.geoquiz.model.QuizModelImpl;

public class QuizActivity extends AppCompatActivity {
    public static final String QUIZ_MODEL = "quizModel";

    private ActivityQuizBinding mQuizBinding;

    private QuizModelImpl mQuizModel = new QuizModelImpl();

    private ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            this::onActivityResult);

    private void onActivityResult(ActivityResult result) {
        if (Activity.RESULT_OK == result.getResultCode()) {
            Intent intent = result.getData();
            if (intent != null) {
                boolean wasCheating = intent.getBooleanExtra(CheatActivity.RESULT_EXTRA_CHEAT, false);

                setCheatingCondition(wasCheating);
            }
        }
    }

    private void setCheatingCondition(boolean wasCheating) {
        if (wasCheating) {
            if (!mQuizModel.isAnswerCheating()) {
                mQuizModel.setCheating(true);
                mQuizModel.decrementCountCheating();

                setCheatingText();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCheatingText() {
        if (mQuizModel.getMaxCountCheating() >= 1) {
            mQuizBinding.countCheating.setText("Remain " + mQuizModel.getMaxCountCheating() + " times of cheating");
        } else {
            mQuizBinding.countCheating.setText("Cheating is over");
            mQuizBinding.cheatButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initQuizView();
        initQuizModel(savedInstanceState);
        updateViews();
    }

    private void initQuizView() {
        mQuizBinding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(mQuizBinding.getRoot());

        mQuizBinding.trueButton.setOnClickListener(this::onClickTrueButton);
        mQuizBinding.falseButton.setOnClickListener(this::onClickFalseButton);
        mQuizBinding.cheatButton.setOnClickListener(this::onClickCheatButton);
        mQuizBinding.prevButton.setOnClickListener(this::onClickPrevButton);
        mQuizBinding.nextButton.setOnClickListener(this::onClickNextButton);
    }

    private void initQuizModel(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mQuizModel = (QuizModelImpl) savedInstanceState.getSerializable(QUIZ_MODEL);

            setCheatingText();
        }
    }

    private void updateViews() {
        updateQuestion(mQuizModel.getQuestionResId());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(QUIZ_MODEL, mQuizModel);
    }

    private void updateQuestion(int questionResId) {
        mQuizBinding.questionTextView.setText(questionResId);
    }

    private void onClickTrueButton(View v) {
        checkAnswer(true);

        mQuizModel.setQuestionAnswered(true);
        stashTrueFalseCheatButton(View.INVISIBLE);

        if (mQuizModel.isAllQuestionAnswered()) {
            showPercentageScore();
        }
    }

    private void onClickFalseButton(View v) {
        checkAnswer(false);

        mQuizModel.setQuestionAnswered(false);
        stashTrueFalseCheatButton(View.INVISIBLE);

        if (mQuizModel.isAllQuestionAnswered()) {
            showPercentageScore();
        }
    }

    private void onClickPrevButton(View v) {
        updateQuestion(mQuizModel.getPrevQuestionResId());

        stashAnsweredQuestion();

        setCheatingText();
    }

    private void onClickNextButton(View v) {
        updateQuestion(mQuizModel.getNextQuestionResId());

        stashAnsweredQuestion();

        setCheatingText();
    }

    private void onClickCheatButton(View v) {
        activityLauncher.launch(
                CheatActivity.getIntent(
                        QuizActivity.this,
                        mQuizModel.getTrueAnswer()
                )
        );
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuizModel.getTrueAnswer();
        int messageResId;

        if (mQuizModel.isAnswerCheating()) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void stashAnsweredQuestion() {
        if (!mQuizModel.isQuestionAnswered()) {
            stashTrueFalseCheatButton(View.VISIBLE);
        } else {
            stashTrueFalseCheatButton(View.INVISIBLE);
        }
    }

    private void stashTrueFalseCheatButton(int invisible) {
        mQuizBinding.trueButton.setVisibility(invisible);
        mQuizBinding.falseButton.setVisibility(invisible);
        mQuizBinding.cheatButton.setVisibility(invisible);
    }

    private void showPercentageScore() {
        String text = "You percentage of correct answer: " + mQuizModel.getPercentageOfRightQuestions();

        Toast toast = makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 500);
        toast.show();
    }
}
