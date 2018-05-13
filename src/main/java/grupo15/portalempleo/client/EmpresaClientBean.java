/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.entities.Grupo;
import grupo15.portalempleo.entities.Usuario;
import grupo15.portalempleo.json.EmpresaReader;
import grupo15.portalempleo.json.EmpresaWriter;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

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
        
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    public void addEmpresa() {
        Usuario empresa = new Usuario();
        empresa.setTipo("Empresa");
        empresa.setNombre(ebb.getNombre());
        empresa.setEmail(ebb.getEmail());
        empresa.setPass(ebb.getPass());
        
        Grupo grupo = new Grupo();
        grupo.setEmail(ebb.getEmail());
        grupo.setNombreGrupo("empresa");
        
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/usuario");
        target.register(EmpresaWriter.class).request().post(Entity.entity(empresa, MediaType.APPLICATION_JSON));
        
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/grupo");
        target.register(EmpresaWriter.class).request().post(Entity.entity(grupo, MediaType.APPLICATION_JSON));

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Éxito", "La empresa " + empresa.getNombre() + " ha sido enviada."));
    }

    public Usuario[] getEmpresas() {
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/usuario");
        
        Usuario[] array = target.register(EmpresaReader.class)
                .path("findEmpresas")
                .request(MediaType.APPLICATION_JSON)
                .get(Usuario[].class);       
        return array;
    }

}
