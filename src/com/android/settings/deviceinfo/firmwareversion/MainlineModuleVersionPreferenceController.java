/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.preference.Preference;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.core.BasePreferenceController;

public class MainlineModuleVersionPreferenceController extends BasePreferenceController {

    private static final String TAG = "MainlineModuleControl";

    private final PackageManager mPackageManager;

    private String mModuleVersion;

    public MainlineModuleVersionPreferenceController(Context context, String key) {
        super(context, key);
        mPackageManager = mContext.getPackageManager();
        initModules();
    }

    @Override
    public int getAvailabilityStatus() {
        return !TextUtils.isEmpty(mModuleVersion) ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    private void initModules() {
        String string = this.mContext.getString(17039702);
        if (!TextUtils.isEmpty(string)) {
            try {
                this.mModuleVersion = this.mPackageManager.getPackageInfo(string, 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Failed to get mainline version.", e);
                mModuleVersion = null;
            }
        }
    }

    @Override
    public CharSequence getSummary() {
        return mModuleVersion;
    }
}
