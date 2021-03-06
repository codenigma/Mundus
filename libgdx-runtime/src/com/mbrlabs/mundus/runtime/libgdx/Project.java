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

import com.badlogic.gdx.utils.Array;
import com.mbrlabs.mundus.commons.Scene;
import com.mbrlabs.mundus.commons.model.MModel;
import com.mbrlabs.mundus.commons.model.MTexture;
import com.mbrlabs.mundus.commons.terrain.Terrain;

/**
 * @author Marcus Brummer
 * @version 10-02-2016
 */
public class Project {

    private Array<MTexture> textures;
    private Array<Terrain> terrains;
    private Array<MModel> models;

    private Array<Scene> scenes;

    public Project() {
        textures = new Array<MTexture>();
        terrains = new Array<Terrain>();
        scenes = new Array<Scene>();
        models = new Array<MModel>();
    }

    public Array<MTexture> getTextures() {
        return textures;
    }

    public Array<Terrain> getTerrains() {
        return terrains;
    }

    public Array<Scene> getScenes() {
        return scenes;
    }

    public Array<MModel> getModels() {
        return models;
    }
}
