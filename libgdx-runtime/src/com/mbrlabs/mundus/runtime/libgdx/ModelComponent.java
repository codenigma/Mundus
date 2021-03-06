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

package com.mbrlabs.mundus.runtime.libgdx;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.mbrlabs.mundus.commons.scene3d.GameObject;
import com.mbrlabs.mundus.commons.scene3d.components.AbstractComponent;
import com.mbrlabs.mundus.commons.scene3d.components.Component;

/**
 *
 * @author Marcus Brummer
 * @version 16-01-2016
 */
public class ModelComponent extends AbstractComponent {

    private ModelInstance modelInstance;
    private Shader shader;

    public ModelComponent(GameObject go) {
        super(go);
        type = Type.MODEL;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public void setModelInstance(ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
    }

    @Override
    public void render(float delta) {
        gameObject.sceneGraph.batch.render(modelInstance,
                gameObject.sceneGraph.scene.environment, shader);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public Component clone(GameObject go) {
        ModelComponent mc = new ModelComponent(go);
        mc.shader = this.shader;
        mc.modelInstance = this.modelInstance;
        return mc;
    }

}
