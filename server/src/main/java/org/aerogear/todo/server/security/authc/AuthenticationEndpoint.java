/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aerogear.todo.server.security.authc;

import org.aerogear.todo.server.security.service.UserManager;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>JAX-RS Endpoint to authenticate users.</p>
 *
 * @author <a href="mailto:psilva@redhat.com">Pedro Silva</a>
 */
@Stateless
@Path("/auth")
public class AuthenticationEndpoint {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationEndpoint.class);

    @Inject
    private UserManager manager;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AuthenticationResponse register(final AuthenticationRequest authcRequest) {

        LOGGER.debug("My pretty registered user: " + authcRequest.getFirstName());

        manager.registerUser(authcRequest);

        return manager.createResponse(authcRequest.getUserId());
    }

    /**
     * <p>Performs the authentication using the informations provided by the {@link AuthenticationRequest}</p>
     *
     * @param authcRequest
     * @return
     */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthenticationResponse login(@HeaderParam("Auth-Credential") String username,
                                        @HeaderParam("Auth-Password") String password) {

        LOGGER.debug("Logged in!");

        manager.userLogin(username, password);

        return manager.createResponse(username);
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void logout() {
        manager.logout();
    }

}