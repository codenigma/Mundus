/*
 * Copyright (c) 2016. See AUTHORS file.
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

package com.mbrlabs.mundus.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.util.ToastManager;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.toast.Toast;

/**
 * Displays Android-like toasts at top right corner of the screen.
 *
 * @author Marcus Brummer
 * @version 07-06-2016
 */
public class Toaster {

    /** Toast type determines background color of toast. */
    enum ToastType {
        SUCCESS, INFO, ERROR
    }

    private ToastManager toastManager;

    public Toaster(Stage stage) {
        this.toastManager = new ToastManager(stage);
    }

    /**
     * Amber background.
     * @param msg
     */
    public void info(String msg) {
        final VisTable table = newTable(msg);
        final Toast toast = new Toast(ToastType.INFO.name().toLowerCase(), table);
        toastManager.show(toast, 3);
    }

    /**
     * Red background
     * @param msg
     */
    public void error(String msg) {
        final VisTable table = newTable(msg);
        final Toast toast = new Toast(ToastType.ERROR.name().toLowerCase(), table);
        toastManager.show(toast, 5);
    }

    /**
     * Teal background.
     * @param msg
     */
    public void success(String msg) {
        final VisTable table = newTable(msg);
        final Toast toast = new Toast(ToastType.SUCCESS.name().toLowerCase(), table);
        toastManager.show(toast, 3);
    }

    /**
     * Does not close itself. User has to
     *
     * @param type
     * @param msg
     */
    public void sticky(ToastType type, String msg) {
        VisTable table = newTable(msg);
        Toast toast = null;
        if(type == ToastType.SUCCESS) {
            toast = new Toast(ToastType.SUCCESS.name().toLowerCase(), table);
        } else if(type == ToastType.INFO) {
            toast = new Toast(ToastType.INFO.name().toLowerCase(), table);
        } else if(type == ToastType.ERROR) {
            toast = new Toast(ToastType.ERROR.name().toLowerCase(), table);
        }

        toastManager.show(toast);
    }

    private VisTable newTable(String text) {
        final VisTable table = new VisTable();
        table.add(new VisLabel(text));
        table.pad(5);
        return table;
    }

}
