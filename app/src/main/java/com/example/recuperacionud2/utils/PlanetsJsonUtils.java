package com.example.recuperacionud2.utils;

import com.example.recuperacionud2.entities.PlanetEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class PlanetsJsonUtils {

    public static PlanetEntity[] parseJson(String JsonString) throws JSONException {
        final String PLANET_LIST = "items";
        final String PLANET_NAME = "name";
        final String PLANET_DIAMETER = "diameter";
        final String PLANET_FILMS = "films";
        final String PLANET_GRAVITY = "gravity";
        JSONObject planetsJSON = new JSONObject(JsonString);

        JSONArray planetList = planetsJSON.getJSONArray(PLANET_LIST);

        PlanetEntity[] parseList = new PlanetEntity[planetList.length()];

        for (int i = 0; i < planetList.length(); i++) {
            JSONObject parsedRepo = planetList.getJSONObject(i);

            String planetname = parsedRepo.getString(PLANET_NAME);
            String planetdiameter = parsedRepo.getString(PLANET_DIAMETER);
            String planetfilms = parsedRepo.getString(PLANET_FILMS);
            String planetgravity = parsedRepo.getString(PLANET_GRAVITY);
            PlanetEntity planetEntity = new PlanetEntity(planetname, planetdiameter, planetfilms, planetgravity);
            parseList[i] = planetEntity;
        }
        return parseList;
    }
}
