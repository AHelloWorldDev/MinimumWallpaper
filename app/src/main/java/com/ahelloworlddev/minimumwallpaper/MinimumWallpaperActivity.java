package com.ahelloworlddev.minimumwallpaper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ahelloworlddev.minimumwallpaper.helpers.MinimumWallpaperHelper;
import com.ahelloworlddev.minimumwallpaper.services.MinimumWallpaperService;
import com.ahelloworlddev.minimumwallpaper.utils.ColorUtils;
import com.ahelloworlddev.minimumwallpaper.utils.SystemUtils;
import com.ahelloworlddev.minimumwallpaper.utils.Utils;


public class MinimumWallpaperActivity extends Activity {

//region Fields

    private static final String TAG = "MinimumWallpaper";
    private static final int DEFAULT_MINIMUM_SIZE = 1;

    private static boolean mExplainNextMinimumWidth = true;
    private static boolean mExplainNextMinimumHeight = true;

    private int mNextMinimumWidth = DEFAULT_MINIMUM_SIZE;
    private int mNextMinimumHeight = DEFAULT_MINIMUM_SIZE;

    /**
     * Main views
     */
    private TextView mIntroTextView;
    private EditText mWidthEditText;
    private EditText mHeightEditText;
    private EditText mColorEditText;
    private View mColorSampleView;
    private CheckBox mWallpaperSystemCheckBox;
    private CheckBox mWallpaperLockCheckBox;
    private RadioGroup mRunRadioGroup;

//endregion

//region Activity Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minimum_wallpaper);

        // Find main views
        mIntroTextView = (TextView) findViewById(R.id.text_intro);
        mWidthEditText = (EditText) findViewById(R.id.edit_width);
        mHeightEditText = (EditText) findViewById(R.id.edit_height);
        mColorEditText = (EditText) findViewById(R.id.edit_color);
        mColorSampleView = findViewById(R.id.color_sample_view);
        mWallpaperSystemCheckBox = (CheckBox) findViewById(R.id.check_wallpaper_system);
        mWallpaperLockCheckBox = (CheckBox) findViewById(R.id.check_wallpaper_lock);
        mRunRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_run);

        mColorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkColor();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // The Set Wallpaper custom Action Bar button
        Button setWallpaperActionButton = (Button) findViewById(R.id.action_set_wallpaper);
        setWallpaperActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSetWallpaper();
            }
        });

        // Load app preferences
        loadPreferences();

        // Always show the overflow menu even if the phone has a menu button
        SystemUtils.setOverflowMenuAlwaysOn(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    /**
     * Inflate the menu items for use in the action bar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_minimum_wallpaper, menu);
        return super.onCreateOptionsMenu(menu);
    }

//endregion

//region App Functionality

    /**
     * Go set the wallpaper.
     */
    private void doSetWallpaper() {

        int width = Utils.parseSize(mWidthEditText.getText().toString());
        if (width == 0) {
            mWidthEditText.setError(getString(R.string.invalid_size));
            return;
        }

        int height = Utils.parseSize(mHeightEditText.getText().toString());
        if (height == 0) {
            mHeightEditText.setError(getString(R.string.invalid_size));
            return;
        }

        int color = checkColor();
        if (color == 0) return;

        boolean whichSystem = mWallpaperSystemCheckBox.isChecked();
        boolean whichLock = mWallpaperLockCheckBox.isChecked();

        // Update next minimum untested width and height
        if (mNextMinimumWidth == width) mNextMinimumWidth++;
        if (mNextMinimumHeight == height) mNextMinimumHeight++;

        // Are we running in background or foreground?
        boolean backgroundRun = mRunRadioGroup.getCheckedRadioButtonId() == R.id.radio_background;

        // Log beginning setting the wallpaper
        Log.i(TAG, getString(R.string.log_begin, width, height, ColorUtils.getColorCode(color),
                getString(backgroundRun ? R.string.radio_background : R.string.radio_foreground)));

        if (backgroundRun) {
            // Start the dummy wallpaper service
            MinimumWallpaperService.set(this, color, width, height, whichSystem, whichLock);
        } else {
            // Set the dummy wallpaper directly, on the UI thread
            MinimumWallpaperHelper.set(this, color, width, height, whichSystem, whichLock);
        }
    }

    /**
     * Check the Color field, and return the parsed color value.
     */
    private int checkColor() {
        int color = Utils.parseColor(mColorEditText.getText().toString());
        if (color == 0) {
            mColorSampleView.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC);
            //mColorSampleView.setBackgroundColor(Color.TRANSPARENT);
            mColorEditText.setError(getString(R.string.invalid_color));
        } else {
            //mColorSampleView.setBackgroundColor(color);
            mColorSampleView.getBackground().setColorFilter(color, PorterDuff.Mode.SRC);
            mIntroTextView.setBackgroundColor(color);
            mIntroTextView.setTextColor(ColorUtils.getContrastColor(color));
        }

        return color;
    }

    /**
     * Set a random color in the color edit text field.
     */
    private void setRandomColor() {
        mColorEditText.setText(ColorUtils.getColorCode(ColorUtils.getRandomColor()));
        mColorEditText.setError(null);
    }

//endregion

//region View Click Events

    /**
     * Width and Height Text Views -> Click
     * Copy the value from one field to the other.
     */
    public void onWidthHeightTextViewsClick(View view) {
        switch (view.getId()) {
            case R.id.text_width:
                mHeightEditText.setText(mWidthEditText.getText());
                mHeightEditText.setError(null);
                break;
            case R.id.text_height:
                mWidthEditText.setText(mHeightEditText.getText());
                mWidthEditText.setError(null);
                break;
        }
    }

    /**
     * Next Image Buttons -> Click
     * Fill the width and height fields with the next minimum untested values, and inform the user.
     */
    public void onNextSizeClick(View view) {
        String toastText = "";
        switch (view.getId()) {
            case R.id.button_next_width:
                mWidthEditText.setText(String.valueOf(mNextMinimumWidth));
                mWidthEditText.setError(null);
                if (mExplainNextMinimumWidth) {
                    toastText = getString(R.string.toast_next_size, getString(R.string.label_width));
                    mExplainNextMinimumWidth = false;
                }
                break;
            case R.id.button_next_height:
                mHeightEditText.setText(String.valueOf(mNextMinimumHeight));
                mHeightEditText.setError(null);
                if (mExplainNextMinimumHeight) {
                    toastText = getString(R.string.toast_next_size, getString(R.string.label_height));
                    mExplainNextMinimumHeight = false;
                }
                break;
        }

        if (!TextUtils.isEmpty(toastText)) Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

    /**
     * Color Sample View -> Click
     * Set a random color in the color edit text field.
     */
    @SuppressWarnings("UnusedParameters")
    public void onColorSampleClick(View view) {
        setRandomColor();
    }

//endregion

//region Action Bar Click Events

    /**
     * Action Bar -> Sizes -> Use minimum size
     * Fill the width and height fields with the minimum.
     */
    @SuppressWarnings("UnusedParameters")
    public void actionMinimumSize(MenuItem item) {
        mWidthEditText.setText(R.string.minimum_width);
        mHeightEditText.setText(R.string.minimum_height);
    }

    /**
     * Action Bar -> Sizes -> Use desired size
     * Fill the width and height fields with the desired minimum values by the Wallpaper manager.
     */
    @SuppressWarnings("UnusedParameters")
    public void actionDesiredSize(MenuItem item) {
        Point size = SystemUtils.getWallpaperDesiredMinimumSize(this);
        mWidthEditText.setText(String.valueOf(size.x));
        mHeightEditText.setText(String.valueOf(size.y));
    }

    /**
     * Action Bar -> Sizes -> Use display size
     * Fill the width and height fields with the size of the default display.
     */
    @SuppressWarnings("UnusedParameters")
    public void actionDisplaySize(MenuItem item) {
        Point size = SystemUtils.getDefaultDisplaySize(this);
        mWidthEditText.setText(String.valueOf(size.x));
        mHeightEditText.setText(String.valueOf(size.y));
    }

    /**
     * Action Bar -> Random Color
     * Set a random color in the color edit text field.
     */
    @SuppressWarnings("UnusedParameters")
    public void actionColorRandom(MenuItem item) {
        setRandomColor();
    }

    /**
     * Action Bar -> Reset to Black
     * Set the Black color code in the color edit text field.
     */
    @SuppressWarnings("UnusedParameters")
    public void actionColorReset(MenuItem item) {
        mColorEditText.setText(ColorUtils.getColorCode(Color.BLACK));
        mColorEditText.setError(null);
    }

    /**
     * Action Bar -> Send Feedback
     * Open the app Issues page in the default browser.
     */
    @SuppressWarnings("UnusedParameters")
    public void actionFeedback(MenuItem item) {
        SystemUtils.viewUrl(this, R.string.action_feedback_url, R.string.toast_error_url);
    }

    /**
     * Action Bar -> Help
     * Open the Help page in the default browser.
     */
    @SuppressWarnings("UnusedParameters")
    public void actionHelp(MenuItem item) {
        SystemUtils.viewUrl(this, R.string.action_help_url, R.string.toast_error_url);
    }

//endregion

//region Preferences

    /**
     * Load and apply the app preferences.
     */
    private void loadPreferences() {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        mNextMinimumWidth = pref.getInt(getString(R.string.pref_next_minimum_width_key), DEFAULT_MINIMUM_SIZE);
        mNextMinimumHeight = pref.getInt(getString(R.string.pref_next_minimum_height_key), DEFAULT_MINIMUM_SIZE);
        mWidthEditText.setText(pref.getString(getString(R.string.pref_width_key), getString(R.string.minimum_width)));
        mHeightEditText.setText(pref.getString(getString(R.string.pref_height_key), getString(R.string.minimum_height)));
        mColorEditText.setText(pref.getString(getString(R.string.pref_color_key), getString(R.string.default_color)));
        mWallpaperSystemCheckBox.setChecked(pref.getBoolean(getString(R.string.pref_wallpaper_system_key), true));
        mWallpaperLockCheckBox.setChecked(pref.getBoolean(getString(R.string.pref_wallpaper_lock_key), true));
        boolean backgroundRun = pref.getBoolean(getString(R.string.pref_background_run_key), true);
        mRunRadioGroup.check(backgroundRun ? R.id.radio_background : R.id.radio_foreground);
    }

    /**
     * Save app preferences.
     */
    private void savePreferences() {
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt(getString(R.string.pref_next_minimum_width_key), mNextMinimumWidth)
                .putInt(getString(R.string.pref_next_minimum_height_key), mNextMinimumHeight)
                .putString(getString(R.string.pref_width_key), mWidthEditText.getText().toString())
                .putString(getString(R.string.pref_height_key), mHeightEditText.getText().toString())
                .putString(getString(R.string.pref_color_key), mColorEditText.getText().toString())
                .putBoolean(getString(R.string.pref_wallpaper_system_key), mWallpaperSystemCheckBox.isChecked())
                .putBoolean(getString(R.string.pref_wallpaper_lock_key), mWallpaperLockCheckBox.isChecked())
                .putBoolean(getString(R.string.pref_background_run_key),
                        mRunRadioGroup.getCheckedRadioButtonId() == R.id.radio_background)
                .apply();
    }

//endregion
}