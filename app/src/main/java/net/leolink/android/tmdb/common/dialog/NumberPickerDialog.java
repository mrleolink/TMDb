package net.leolink.android.tmdb.common.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.NumberPicker;

/**
 * @author Leo
 */
public class NumberPickerDialog extends AlertDialog implements DialogInterface.OnClickListener {
    public static class Builder {
        private static final int UNSET = Integer.MIN_VALUE;
        private Context mContext;
        private int mMinValue = UNSET;
        private int mMaxValue = UNSET;
        private int mCurValue = UNSET;
        private OnNumberPickedListener mCallback;

        public Builder(@NonNull Context context) {
            mContext = context;
        }

        public Builder setMinValue(int minValue) {
            mMinValue = minValue;
            return this;
        }

        public Builder setMaxValue(int maxValue) {
            mMaxValue = maxValue;
            return this;
        }

        public Builder setCurValue(int curValue) {
            mCurValue = curValue;
            return this;
        }

        public Builder setCallback(@NonNull OnNumberPickedListener callback) {
            mCallback = callback;
            return this;
        }

        public NumberPickerDialog build() {
            if (mMinValue == UNSET || mMaxValue == UNSET)
                throw new IllegalArgumentException("Both min value and max value have to be set");
            if (mCallback == null)
                throw new IllegalArgumentException("Callback has to be set");
            if (mCurValue < mMinValue || mCurValue > mMaxValue)
                mCurValue = mMinValue;
            return new NumberPickerDialog(mContext, mMinValue, mMaxValue, mCurValue, mCallback);
        }
    }

    private NumberPicker mPicker;
    private OnNumberPickedListener mNumberSelectedListener;

    private NumberPickerDialog() {
        super(null);
    }

    private NumberPickerDialog(Context context, int minValue, int maxValue, int curValue,
                               OnNumberPickedListener callback) {
        super(context);
        mPicker = new NumberPicker(context);
        mPicker.setMinValue(minValue);
        mPicker.setMaxValue(maxValue);
        mPicker.setValue(curValue);
        mNumberSelectedListener = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setButton(BUTTON_NEGATIVE, getContext().getString(android.R.string.cancel), this);
        setButton(BUTTON_POSITIVE, getContext().getString(android.R.string.ok), this);
        setView(mPicker);

        //Install contents
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == BUTTON_POSITIVE && mNumberSelectedListener != null) {
            mNumberSelectedListener.onNumberPicked(mPicker.getValue());
        }
    }

    /** Call back interface **/
    public interface OnNumberPickedListener {
        void onNumberPicked(int value);
    }
}
