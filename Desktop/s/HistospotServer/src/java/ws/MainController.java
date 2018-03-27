package ws;

import db.AccountDB;
import db.ProfileDB;
import ejb.AccountDBFacade;
import ejb.ProfileDBFacade;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.xml.security.utils.Base64;
import org.json.simple.JSONObject;
import static sun.security.krb5.Confounder.bytes;

/**
 * REST Web Service
 *
 * @author alex
 */
@Path("api")
public class MainController {

    @Context
    private UriInfo context;
    @EJB
    private AccountDBFacade accountDBFacade;
    @EJB
    private ProfileDBFacade profileDBFacade;

    /**
     * Creates a new instance of ApiResource
     */
    public MainController() {
    }

    /**
     * Retrieves representation of an instance of ws.ApiResource
     *
     * @return an instance of javax.json.JSONObject
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ApiResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(JSONObject content) {
    }

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
        JSONObject res = new JSONObject();
        res.put("msg", "MERGE!!!");
        return Response.ok().entity(res).build();
    }

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @HeaderParam("googleId") String googleId,
            @HeaderParam("email") String email,
            @HeaderParam("firstName") String firstName,
            @HeaderParam("lastName") String lastName,
            @HeaderParam("photoUrl") String photoUrl) {
        try {
            if (googleId == null || googleId.isEmpty()) {
                JSONObject error = new JSONObject();
                error.put("error", "Please provide googleId!");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }
            if (email == null || email.isEmpty()) {
                JSONObject error = new JSONObject();
                error.put("error", "Please provide email!");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }
            if (firstName == null || firstName.isEmpty()) {
                JSONObject error = new JSONObject();
                error.put("error", "Please provide firstName!");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }
            if (lastName == null || lastName.isEmpty()) {
                JSONObject error = new JSONObject();
                error.put("error", "Please provide lastName!");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            AccountDB account = this.accountDBFacade.findByGoogleId(googleId);
            if (account != null) {
                JSONObject res = new JSONObject();
                res.put("id", account.getId());
                return Response.ok().entity(res).build();
            }

            byte[] photo = null;
            if (photoUrl != null && !photoUrl.isEmpty()) {
                BufferedImage img = ImageIO.read(new URL(photoUrl));
                BufferedImage scaled = new BufferedImage(200, 200, img.getType());

                Graphics2D bGr = scaled.createGraphics();
                bGr.drawImage(img, 0, 0, 200, 200, null);
                bGr.dispose();

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                ImageIO.write((RenderedImage) scaled, "jpg", bytes);
                photo = bytes.toByteArray();
            }

            account = new AccountDB(googleId, 0, 1);
            this.accountDBFacade.create(account);
            this.accountDBFacade.updateEm();
            ProfileDB profile = new ProfileDB(account.getId(), email, firstName, lastName);
            profile.setPicture(photo);
            this.profileDBFacade.create(profile);

            JSONObject res = new JSONObject();
            res.put("id", account.getId());
            return Response.ok().entity(res).build();
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @GET
    @Path("profile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response profile(
            @HeaderParam("id") String idCode) {
        try {
            if (idCode == null || idCode.isEmpty()) {
                JSONObject error = new JSONObject();
                error.put("error", "Please provide id!");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }
            Integer id;
            try {
                id = Integer.parseInt(idCode);
            } catch (NumberFormatException e) {
                JSONObject error = new JSONObject();
                error.put("error", "Invalid id!");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            ProfileDB profile = this.profileDBFacade.find(id);
            if (profile == null) {
                JSONObject error = new JSONObject();
                error.put("error", "Invalid id!");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            AccountDB account = this.accountDBFacade.find(profile.getAccount());

            JSONObject res = new JSONObject();
            res.put("firstName", profile.getFirstName());
            res.put("lastName", profile.getLastName());
            res.put("points", account.getPoints());
            if (profile.getPicture() != null) {
                res.put("photo", Base64.encode(profile.getPicture()));
            }
            res.put("level", account.getLevel());

            return Response.ok().entity(res).build();
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}
