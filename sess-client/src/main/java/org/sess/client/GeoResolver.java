package org.sess.client;

import org.sess.client.pojo.City;

public interface GeoResolver {

    City resolveCity(String langCode, float lat, float lon);
}
