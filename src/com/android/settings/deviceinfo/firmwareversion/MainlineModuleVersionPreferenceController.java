/*
 * Copyright (C) 2017 The Android Open Source Project
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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v7.preference.Preference;
import android.text.TextUtils;
import android.util.Log;
import com.android.settings.core.BasePreferenceController;

public class MainlineModuleVersionPreferenceController extends BasePreferenceController {
    static final Intent MODULE_UPDATE_INTENT = new Intent("android.settings.MODULE_UPDATE_SETTINGS");
    private static final String TAG = "MainlineModuleControl";
    private String mModuleVersion;
    private final PackageManager mPackageManager = this.mContext.getPackageManager();

    public MainlineModuleVersionPreferenceController(Context context, String str) {
        super(context, str);
        initModules();
    }

    public int getAvailabilityStatus() {
        return !TextUtils.isEmpty(this.mModuleVersion) ? 0 : 3;
    }

    private void initModules() {
        String string = this.mContext.getString(17039702);
        if (!TextUtils.isEmpty(string)) {
            try {
                this.mModuleVersion = this.mPackageManager.getPackageInfo(string, 0).versionName;
            } catch (NameNotFoundException e) {
                Log.e(TAG, "Failed to get mainline version.", e);
                this.mModuleVersion = null;
            }
        }
    }

    public void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mPackageManager.resolveActivity(MODULE_UPDATE_INTENT, 0) != null) {
            preference.setIntent(MODULE_UPDATE_INTENT);
        } else {
            preference.setIntent(null);
        }
    }

    public CharSequence getSummary() {
        return this.mModuleVersion;
    }
}
