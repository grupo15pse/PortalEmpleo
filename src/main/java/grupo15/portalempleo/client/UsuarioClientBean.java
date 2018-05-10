/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.entities.Grupo;
import grupo15.portalempleo.entities.Usuario;
import grupo15.portalempleo.json.EmpresaWriter;
import grupo15.portalempleo.json.GrupoWriter;
import grupo15.portalempleo.json.UsuarioWriter;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Admin
 */
@Named(value = "usuarioClientBean")
@RequestScoped
public class UsuarioClientBean {

    Client client;
    WebTarget target;
    /**
     * Creates a new instance of UsuarioClientBean
     */
    public UsuarioClientBean() {
    }
    
    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }
    
    public void addCandidato(Usuario candidato,Grupo grupo) {
        System.out.println("HOALAAA" + candidato.getTelefono());
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/usuario");
        target.register(UsuarioWriter.class).request().post(Entity.entity(candidato, MediaType.APPLICATION_JSON));
        
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/grupo");
        target.register(GrupoWriter.class).request().post(Entity.entity(grupo, MediaType.APPLICATION_JSON));
    }
    
}
