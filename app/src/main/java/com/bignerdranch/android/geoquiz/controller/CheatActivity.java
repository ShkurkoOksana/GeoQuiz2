package com.bignerdranch.android.geoquiz.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.geoquiz.R;
import com.bignerdranch.android.geoquiz.databinding.ActivityCheatBinding;
import com.bignerdranch.android.geoquiz.model.CheatModel;
import com.bignerdranch.android.geoquiz.model.CheatModelImpl;

public class CheatActivity extends AppCompatActivity {
    public static final String EXTRA_ANSWER_IS_TRUE = "extra_answer_is_true";
    public static final String RESULT_EXTRA_CHEAT = "extra_cheat";
    public static final String CHEAT_MODEL = "cheatModel";

    private ActivityCheatBinding mCheatBinding;

    private CheatModel mCheatModel = new CheatModelImpl();

    public static Intent getIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext,
                CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);

        return intent;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCheatBinding = ActivityCheatBinding.inflate(getLayoutInflater());
        setContentView(mCheatBinding.getRoot());

        if (savedInstanceState != null) {
            mCheatModel = (CheatModelImpl) savedInstanceState.getSerializable(CHEAT_MODEL);

            if (mCheatModel.isCheating()) {
                setCheatingAnswer();
            }
        }

        mCheatModel.setAnswerTrue(getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false));

        mCheatBinding.showAnswerButton.setOnClickListener(this::onClickShowButton);

        mCheatBinding.apiLevel.setText("API level " + Build.VERSION.SDK_INT);
    }

    @Override
    public void onBackPressed() {
        defineCheatResult();
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CHEAT_MODEL, mCheatModel);
    }

    private void onClickShowButton(View v) {
        setCheatingAnswer();
        mCheatModel.setCheating(true);
    }

    private void setCheatingAnswer() {
        if (mCheatModel.isAnswerTrue()) {
            mCheatBinding.answerTextView.setText(R.string.true_button);
        } else {
            mCheatBinding.answerTextView.setText(R.string.false_button);
        }
    }

    private void defineCheatResult() {
        Intent intent = new Intent();
        intent.putExtra(RESULT_EXTRA_CHEAT, mCheatModel.isCheating());
        setResult(RESULT_OK, intent);
    }
}