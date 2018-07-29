package org.somnath.javabrains.messenger.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.somnath.javabrains.messenger.model.Profile;
import org.somnath.javabrains.messenger.service.ProfileService;
                         /*........Class containing HTTP Methods................*/
@Path("/profiles")
@Produces(MediaType.APPLICATION_JSON)

public class ProfileResource {

	/*.................Creating an instance of ProfileService Class..........*/
	
	private ProfileService profileService = new ProfileService();

	@GET
	public List<Profile> getProfiles() {
		return profileService.getAllProfiles();
	}

	@POST
	public Profile addProfile(Profile profileName) {
		return profileService.addProfile(profileName);
	}

	@PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam("profileName") String profileName, Profile profile) {
		profile.setProfileName(profileName);
		return profileService.updateProfile(profile);
	}

	@DELETE
	@Path("/{profileName}")
	public void deleteProfile(@PathParam("profileName") String profileName) {
		profileService.removeProfile(profileName);
	}

}
