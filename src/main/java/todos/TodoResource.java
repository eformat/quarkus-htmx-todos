package todos;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.CheckedTemplate;
import java.util.UUID;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jboss.resteasy.annotations.Form;

@Path("/todos")
public class TodoResource {

  @CheckedTemplate
  public static class Templates {

    public static native TemplateInstance list();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance list() {
    return Templates.list().data("todos", Todo.listAll());
  }

  @POST
  @Transactional
  public Response add(@Form Todo todo) {
    Todo.persist(todo);
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }

  @POST
  @Path("{id}/delete")
  @Transactional
  public Response delete(@PathParam("id") UUID id) {
    Todo.deleteById(id);
    return Response.status(Status.FOUND).header("Location", "/todos").build();
  }
}
