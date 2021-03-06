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

package com.mbrlabs.mundus.input;

import com.badlogic.gdx.Input;
import com.mbrlabs.mundus.core.project.ProjectManager;
import com.mbrlabs.mundus.core.registry.Registry;
import com.mbrlabs.mundus.history.CommandHistory;
import com.mbrlabs.mundus.ui.Ui;

/**
 * @author Marcus Brummer
 * @version 07-02-2016
 */
public class ShortcutController extends KeyboardLayoutInputAdapter {

    private boolean isCtrlPressed = false;
    private CommandHistory history;
    private ProjectManager projectManager;

    public ShortcutController(Registry registry, ProjectManager projectManager, CommandHistory history) {
        super(registry);
        this.projectManager = projectManager;
        this.history = history;
    }

    @Override
    public boolean keyDown(int keycode) {
        keycode = convertKeycode(keycode);
        if(keycode == Input.Keys.CONTROL_LEFT) {
            isCtrlPressed = true;
        }

        if(!isCtrlPressed) return false;

        if(keycode == Input.Keys.Z) {
            history.goBack();
            return true;
        } else if(keycode == Input.Keys.Y) {
            history.goForward();
            return true;
        } else if(keycode == Input.Keys.S) {
            projectManager.saveCurrentProject();
            Ui.getInstance().getToaster().success("Project saved");
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keycode = convertKeycode(keycode);
        if(keycode == Input.Keys.CONTROL_LEFT) {
            isCtrlPressed = false;
        }
        return false;
    }

}
