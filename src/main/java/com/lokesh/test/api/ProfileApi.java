/**
 * NOTE: This class is used for Swagger API  documentation
 */
package com.api;

import com.entity.Profile;
import com.entity.User;
import com.model.ErrorResponse;
import com.model.SuccessfulResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Api(value = "Profile", description = "the Customer Profile API")
public interface ProfileApi {

    @ApiOperation(value="create user", response = SuccessfulResponse.class, authorizations = {
        @Authorization(value = "profile_auth", scopes = {
            @AuthorizationScope(scope = "write:profile", description = "create user  profile")
            })
    }, tags={ "Profile" })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully created user account", response = SuccessfulResponse.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 409, message = "User account already exists", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Exception occured", response = ErrorResponse.class) })
    @RequestMapping(value = "/users",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SuccessfulResponse> addUser(@ApiParam(value = "", required = true)
                                               @Valid @RequestBody User body);


    @ApiOperation(value = "Delete user", authorizations = {
        @Authorization(value = "profile_auth", scopes = {
            @AuthorizationScope(scope = "write:profile", description = "delete user profile")
            })
    }, tags={ "Profile", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted user account"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "User account not exists", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Exception occured", response = ErrorResponse.class) })
    @RequestMapping(value = "/users",
        produces = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUser(@ApiParam(value = "", required = true)
                                    @PathVariable("id") Long id);


    @ApiOperation(value = "read user profile", response = Profile.class, authorizations = {
        @Authorization(value = "profile_auth", scopes = {
            @AuthorizationScope(scope = "read:profile", description = "read user profile")
            })
    }, tags={ "Profile", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Gets user profile", response = Profile.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 409, message = "User account already exists", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Exception occured", response = ErrorResponse.class) })
    @RequestMapping(value = "/users/{id}/profile",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Profile> getUserProfile(@ApiParam(value = "", required = true)
                                           @PathVariable("id") Long id);


    @ApiOperation(value = "Modify user profile", authorizations = {
        @Authorization(value = "profile_auth", scopes = {
            @AuthorizationScope(scope = "write:profile", description = "modify user profile")
            })
    }, tags={ "Profile" })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Successfully updated profile"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Exception occured", response = ErrorResponse.class) })
    @RequestMapping(value = "/users/{id}/profile",
        produces = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateProfile(@ApiParam(value = "", required = true)
                                       @PathVariable("id") Long id,
                                       @ApiParam(value = "", required = true)
                                       @Valid @RequestBody Profile body);

}
