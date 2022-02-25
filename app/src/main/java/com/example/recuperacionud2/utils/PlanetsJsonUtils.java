package com.example.recuperacionud2.utils;

import android.util.Log;

import com.example.recuperacionud2.entities.PlanetEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class PlanetsJsonUtils {

    public static PlanetEntity[] parseJson(String JsonString) throws JSONException {
        final String PLANET_LIST = "results";
        final String PLANET_NAME = "name";
        final String PLANET_TERRAIN = "terrain";
        final String PLANET_GRAVITY = "gravity";

        JSONObject planetsJSON = new JSONObject(JsonString);
        JSONArray planetList = null; //planetsJSON.getJSONArray(PLANET_LIST);
        PlanetEntity[] parseList;

        if(JsonString.startsWith("{\"count\":")){

            planetList = planetsJSON.getJSONArray(PLANET_LIST);
            parseList = new PlanetEntity[planetList.length()];

            for (int i = 0; i < planetList.length(); i++) {
                JSONObject parsedPlanet = planetList.getJSONObject(i);

                String planetName = parsedPlanet.getString(PLANET_NAME);
                String planetTerrain = parsedPlanet.getString(PLANET_TERRAIN);
                String planetGravity = parsedPlanet.getString(PLANET_GRAVITY);

                PlanetEntity planetEntity = new PlanetEntity(planetName, planetTerrain, planetGravity);

                parseList[i] = planetEntity;
                Log.i("planet entity", parseList.toString());
            }
        } else {
            JSONObject parsedPlanet = new JSONObject(JsonString);
            parseList = new PlanetEntity[1];

            String planetName = parsedPlanet.getString(PLANET_NAME);
            String planetTerrain = parsedPlanet.getString(PLANET_TERRAIN);
            String planetGravity = parsedPlanet.getString(PLANET_GRAVITY);

            PlanetEntity planetEntity = new PlanetEntity(planetName, planetTerrain, planetGravity);
            parseList[0] = planetEntity;
        }
        return parseList;
    }
}
