/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.entities.Usuario;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author Admin
 */
@Named(value = "empresaClientBean")
@RequestScoped
public class EmpresaClientBean {

    Client client;
    WebTarget target;
    
    @Inject
    private EmpresaBackingBean ebb;
    
    public EmpresaClientBean() {
    }
    
    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/usuario");
    }
    
    public void addEmpresa() {
        Usuario empresa = new Usuario();
        empresa.setTipo("Empresa");
        empresa.setNombre(ebb.getNombre());
        empresa.setEmail(ebb.getEmail());
        empresa.setPass(ebb.getPass());
        empresa.setFechaNacimiento(null);
        empresa.setTarjeta(null);
        empresa.setTelefono(null);
        try{
            
        }catch (Exception e){
            System.out.println("ERRRRRRRROR");
        }
        
        FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage("Éxito",  "La empresa " + empresa.getNombre() + " ha sido enviada." ));
    }

    
}
