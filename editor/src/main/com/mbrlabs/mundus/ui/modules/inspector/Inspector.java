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

package com.mbrlabs.mundus.ui.modules.inspector;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.mbrlabs.mundus.commons.scene3d.GameObject;
import com.mbrlabs.mundus.commons.scene3d.components.Component;
import com.mbrlabs.mundus.core.Inject;
import com.mbrlabs.mundus.core.Mundus;
import com.mbrlabs.mundus.core.project.ProjectContext;
import com.mbrlabs.mundus.core.project.ProjectManager;
import com.mbrlabs.mundus.events.GameObjectModifiedEvent;
import com.mbrlabs.mundus.events.GameObjectSelectedEvent;
import com.mbrlabs.mundus.scene3d.components.ModelComponent;
import com.mbrlabs.mundus.scene3d.components.TerrainComponent;
import com.mbrlabs.mundus.shader.Shaders;
import com.mbrlabs.mundus.ui.Ui;
import com.mbrlabs.mundus.ui.modules.inspector.terrain.TerrainComponentWidget;
import com.mbrlabs.mundus.utils.TerrainUtils;

/**
 * @author Marcus Brummer
 * @version 19-01-2016
 */
public class Inspector extends VisTable implements GameObjectSelectedEvent.GameObjectSelectedListener,
        GameObjectModifiedEvent.GameObjectModifiedListener {

    private VisTable root;
    private ScrollPane scrollPane;

    private IdentifierWidget identifierWidget;
    private TransformWidget transformWidget;
    private Array<ComponentWidget> componentWidgets;

    private VisTextButton addComponentBtn;

    private VisTable componentTable;

    @Inject
    private ProjectManager projectManager;

    public Inspector() {
        super();
        Mundus.inject(this);
        Mundus.registerEventListener(this);
        identifierWidget = new IdentifierWidget();
        transformWidget = new TransformWidget();
        componentWidgets = new Array<>();
        addComponentBtn = new VisTextButton("Add Component");
        componentTable = new VisTable();

        init();
        setupUi();
        setupListeners();
    }

    public void init() {
        setBackground("window-bg");
        add(new VisLabel("Inspector")).expandX().fillX().pad(3).row();
        addSeparator().row();
        root = new VisTable();
        root.align(Align.top);
        scrollPane = new VisScrollPane(root);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFlickScroll(false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Ui.getInstance().setScrollFocus(scrollPane);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Ui.getInstance().setScrollFocus(null);
            }
        });

        add(scrollPane).expand().fill().top();
    }

    public void setupUi() {
        root.add(identifierWidget).expandX().fillX().pad(7).row();
        root.add(transformWidget).expandX().fillX().pad(7).row();
        for(BaseInspectorWidget cw : componentWidgets) {
            componentTable.add(cw).row();
        }
        root.add(componentTable).fillX().expandX().pad(7).row();
        root.add(addComponentBtn).expandX().fill().top().center().pad(10).row();
    }

    private void buildComponentWidgets() {
        final ProjectContext projectContext = projectManager.current();
        componentWidgets.clear();
        if(projectContext.currScene.currentSelection != null) {
            for(Component component : projectContext.currScene.currentSelection.getComponents()) {

                // model component widget
                if(component.getType() == Component.Type.MODEL) {
                    componentWidgets.add(new ModelComponentWidget((ModelComponent)component));
                // terrain component widget
                } else if(component.getType() == Component.Type.TERRAIN) {
                    componentWidgets.add(new TerrainComponentWidget((TerrainComponent)component));
                }

            }
        }
    }

    public void setupListeners() {

    }

    private void setValues(GameObject go) {
        final ProjectContext projectContext = projectManager.current();
        identifierWidget.setValues(go);
        transformWidget.setValues(go);

        buildComponentWidgets();
        componentTable.clearChildren();

        for(ComponentWidget cw : componentWidgets) {
            componentTable.add(cw).expand().fill().row();
            cw.setValues(projectContext.currScene.currentSelection);
        }
    }

    @Override
    public void onGameObjectSelected(GameObjectSelectedEvent gameObjectSelectedEvent) {
        setValues(projectManager.current().currScene.currentSelection);
    }

    @Override
    public void onGameObjectModified(GameObjectModifiedEvent gameObjectModifiedEvent) {
        final ProjectContext projectContext = projectManager.current();
        identifierWidget.setValues(projectContext.currScene.currentSelection);
        transformWidget.setValues(projectContext.currScene.currentSelection);
    }

}
