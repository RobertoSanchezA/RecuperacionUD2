package com.example.recuperacionud2.utils;

import android.util.Log;

import com.example.recuperacionud2.entities.PlanetEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class PlanetsJsonUtils {

    public static PlanetEntity[] parseJson(String JsonString) throws JSONException {
        final String PLANET_LIST = "items";
        final String PLANET_NAME = "name";
        final String PLANET_TERRAIN = "terrain";
        final String PLANET_GRAVITY = "gravity";
        Log.e("url", JsonString);
        JSONObject planetsJSON = new JSONObject(JsonString);

        JSONArray planetList = planetsJSON.getJSONArray(PLANET_LIST);

        PlanetEntity[] parseList = new PlanetEntity[planetList.length()];

        for (int i = 0; i < planetList.length(); i++) {
            JSONObject parsedPlanet = planetList.getJSONObject(i);

            String planetName = parsedPlanet.getString(PLANET_NAME);
            String planetTerrain = parsedPlanet.getString(PLANET_TERRAIN);
            String planetGravity = parsedPlanet.getString(PLANET_GRAVITY);

            PlanetEntity planetEntity = new PlanetEntity(planetName, planetTerrain, planetGravity);
            parseList[i] = planetEntity;
        }
        return parseList;
    }
}
