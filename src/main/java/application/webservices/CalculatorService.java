package application.webservices;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import application.models.CalculatorEntity;

@Stateless
@Path("/")
@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
public class CalculatorService {
	@PersistenceContext
    private EntityManager EM;
	
	  @POST
	  @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	  @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	  @Path("/calc")
	public Object calculateAnswer(CalculatorEntity Cal) {
			
		  Object Result = null;

			if (Cal.getOperation().contentEquals("+")) {
				Result = Cal.getNumber1() + Cal.getNumber2();
			} 
			else if (Cal.getOperation().contentEquals("*")) {
				Result = Cal.getNumber1() * Cal.getNumber2();
			} 
			else if (Cal.getOperation().contentEquals("-")) {
				Result = Cal.getNumber1() - Cal.getNumber2();
			} 
			else if (Cal.getOperation().contentEquals("/")) {
				if (Cal.getNumber2() == 0) {
					throw new IllegalArgumentException("Can't divide by zero !");
				}
				Result = Cal.getNumber1() / Cal.getNumber2();
			} 
			else {
				throw new IllegalArgumentException("Opearation is not valid !!");
			}

			EM.persist(Cal);
			return Result;
		}
	  
	  
	  	@Path("calculations")
	    @GET
	    public List<CalculatorEntity> getAllCalculations() {
		  
	        return EM.createQuery("select Cal from CalculatorEntity Cal", CalculatorEntity.class).getResultList();
 
	    }
	

}
