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

package com.mbrlabs.mundus.ui.modules.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.mbrlabs.mundus.commons.env.lights.BaseLight;
import com.mbrlabs.mundus.core.Inject;
import com.mbrlabs.mundus.core.Mundus;
import com.mbrlabs.mundus.core.project.ProjectContext;
import com.mbrlabs.mundus.core.project.ProjectManager;
import com.mbrlabs.mundus.events.ProjectChangedEvent;
import com.mbrlabs.mundus.events.SceneChangedEvent;
import com.mbrlabs.mundus.ui.widgets.ColorPickerField;

/**
 * @author Marcus Brummer
 * @version 04-03-2016
 */
public class AmbientLightDialog extends BaseDialog implements ProjectChangedEvent.ProjectChangedListener, SceneChangedEvent.SceneChangedListener {

    private VisTextField intensity = new VisTextField("0");

    private ColorPickerField colorPickerField = new ColorPickerField("Color: ");

    @Inject
    private ProjectManager projectManager;

    public AmbientLightDialog() {
        super("Ambient Light");
        Mundus.inject(this);
        Mundus.registerEventListener(this);

        setupUI();
        setupListeners();
    }

    private void setupUI() {
        Table root = new Table();
        root.padTop(6).padRight(6).padBottom(22);
        add(root);

        root.add(new VisLabel("Intensity: ")).left().padBottom(10);
        root.add(intensity).fillX().expandX().padBottom(10).row();
        root.add(colorPickerField).left().fillX().expandX().colspan(2).row();
        resetValues();
    }

    private void setupListeners() {
        final ProjectContext projectContext = projectManager.current();

        // intensity
        intensity.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Float d = convert(intensity.getText());
                if (d != null) {
                    projectContext.currScene.environment.getAmbientLight().intensity = d;
                }
            }
        });

        // color
        colorPickerField.setCallback(new ColorPickerField.ColorSelected() {
            @Override
            public void selected(Color color) {
                projectContext.currScene.environment.getAmbientLight().color.set(color);
            }
        });

    }

    private void resetValues() {
        BaseLight light = projectManager.current().currScene.environment.getAmbientLight();
        intensity.setText(String.valueOf(light.intensity));
        colorPickerField.setColor(light.color);
    }

    private Float convert(String input) {
        try {
            if(input.length() == 0) return null;
            return Float.valueOf(input);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onProjectChanged(ProjectChangedEvent projectChangedEvent) {
        resetValues();
    }

    @Override
    public void onSceneChanged(SceneChangedEvent sceneChangedEvent) {
        resetValues();
    }

}
