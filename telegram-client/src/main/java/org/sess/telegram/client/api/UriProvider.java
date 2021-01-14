package org.sess.telegram.client.api;

import java.net.URI;

public interface UriProvider {

    URI createUri(String method);
}
