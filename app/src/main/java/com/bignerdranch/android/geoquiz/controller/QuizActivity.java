package com.bignerdranch.android.geoquiz.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.geoquiz.R;
import com.bignerdranch.android.geoquiz.model.QuizModelImpl;

public class QuizActivity extends AppCompatActivity {
    public static final String TAG = "QuizActivity";
    public static final String MODEL = "quizModel";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    private QuizModelImpl mQuizModel = new QuizModelImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mQuizModel = (QuizModelImpl) savedInstanceState.getSerializable(MODEL);
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        updateQuestion(mQuizModel.getQuestionResId());

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(v -> {
            checkAnswer(true);

            mQuizModel.setQuestionAnswered(true);
            stashTrueFalseButton(View.INVISIBLE);

            if (mQuizModel.isAllQuestionAnswered()) {
                showPercentageScore();
            }
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(v -> {
            checkAnswer(false);

            mQuizModel.setQuestionAnswered(false);
            stashTrueFalseButton(View.INVISIBLE);

            if (mQuizModel.isAllQuestionAnswered()) {
                showPercentageScore();
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(v -> {
            updateQuestion(mQuizModel.getNextQuestionResId());

            stashAnsweredQuestion();
        });

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(v -> {
            updateQuestion(mQuizModel.getPrevQuestionResId());

            stashAnsweredQuestion();
        });
    }

    private void showPercentageScore() {
        String text = "You percentage of correct answer: " + String.valueOf(mQuizModel.getPercentageOfRightQuestions());

        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 500);
        toast.show();
    }

    private void stashTrueFalseButton(int invisible) {
        mTrueButton.setVisibility(invisible);
        mFalseButton.setVisibility(invisible);
    }

    private void stashAnsweredQuestion() {
        if (!mQuizModel.isQuestionAnswered()) {
            stashTrueFalseButton(View.VISIBLE);
        } else {
            stashTrueFalseButton(View.INVISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState(Bundle) called");
        outState.putSerializable(MODEL, mQuizModel);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroyed() called");
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuizModel.getTrueAnswer();
        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion(int questionResId) {
        mQuestionTextView.setText(questionResId);
    }
}