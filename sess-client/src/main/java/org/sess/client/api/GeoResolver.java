package org.sess.client.api;

import org.sess.client.pojo.City;

public interface GeoResolver {

    City resolveCity(String langCode, float lat, float lon);
}
