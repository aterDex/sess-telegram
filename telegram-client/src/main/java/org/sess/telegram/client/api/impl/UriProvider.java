package org.sess.telegram.client.api.impl;

import java.net.URI;

public interface UriProvider {

    URI createUri(String method);
}
