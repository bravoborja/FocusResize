/*
 * Copyright (C) 2016 Borja Bravo Álvarez
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

package com.example.library;

import android.app.Activity;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;

public final class Utils {

    public static int getScreenHeight(Activity activity) {
        try {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.y;
        } catch (Exception e) {
            Log.e(activity.getClass().getName(), e.getMessage());
        }
        return 0;
    }

    public static int getFooterHeight(Activity activity, int height) {
        return getScreenHeight(activity) - height;
    }
}
