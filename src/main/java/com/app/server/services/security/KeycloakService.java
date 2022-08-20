package com.app.server.services.security;


import com.app.server.model.user.User;
import com.app.server.property.RealmProperties;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class KeycloakService {

   /* private RealmProperties realmProperties;
    private RealmResource realmResource;

    Logger logger = LoggerFactory.getLogger(KeycloakService.class);

    @Autowired
    public KeycloakService(
            KeycloakSpringBootProperties props,
            RealmProperties realmProperties,
            Keycloak keycloak) {
        this.realmProperties = realmProperties;
        logger.info(props.getRealm());
        realmResource = keycloak.realm(props.getRealm());
    }

    public void addRole(String userID, String role) {

            UsersResource usersResource = realmResource.users();
            UserResource userResource = usersResource.get(userID);
            realmResource.roles().list().stream().forEach((roleRepresentation -> {
                logger.info(roleRepresentation.getName());
            }));

            if(realmResource.roles() != null) {
                RoleRepresentation adminRealmRole = realmResource.roles().get(role).toRepresentation();
                userResource.roles().realmLevel().add(Arrays.asList(adminRealmRole));
            } else {
                throw new NullPointerException("RoleRepresentation is null");
            }
    }
    public boolean hasArtistRole(SimpleKeycloakAccount account) {
        return account.getRoles().contains(realmProperties.getArtistRole());
    }

    public void addArtistRole(String userID) {
        addRole(userID, realmProperties.getArtistRole());
    }

    public List<UserRepresentation> getAllUsers() {
        List<UserRepresentation> userIDList = new ArrayList<>();
        realmResource.users().list().stream().forEach((userRep) -> {
            userIDList.add(userRep);
        });
        return userIDList;
    }

    public String getArtistRole() {
        return this.realmProperties.getArtistRole();
    }

    public List<String> getRealmRoles(String userID) {
        List<String> realmRoles = new ArrayList<>();
        UsersResource usersRessource = realmResource.users();
        UserResource userResource = usersRessource.get(userID);
        RoleScopeResource roleScopeResource = userResource.roles().realmLevel();
        List<RoleRepresentation> roleRepresentations = roleScopeResource.listAll();
        roleRepresentations.stream().forEach(roleRepresentation -> {
            logger.info(roleRepresentation.getName());
            realmRoles.add(roleRepresentation.getName());
        });
        return realmRoles;
    }


    */




}
