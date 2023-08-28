package ec.com.zurich.suscription.resources.model;

public record DatosContactoData(

    String paisId,
    String provinciaId,
    String ciudadId,
    String telefono,
    String direccion,
    String email,
    String tipoDireccion

) {}
