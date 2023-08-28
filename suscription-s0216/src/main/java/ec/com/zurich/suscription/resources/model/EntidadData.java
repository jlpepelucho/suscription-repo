package ec.com.zurich.suscription.resources.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EntidadData(

    String actividadEconomicaId,
    String apellido,
    ClienteData cliente,
    DatosContactoData datosContacto,
    String emailOpcional,
    String emailPrincipal,
    String entidadId,
    Boolean esEmpresa,
    LocalDateTime fechaNacimiento,
    LocalDateTime fechaConstitucion,
    String genero,
    String identificacion,
    BigDecimal limiteCredito,
    String lugarNacimiento,
    String nacionalidad,
    String nombre,
    String nombresRepresentante,
    String nombreCorto,
    String nombreCompleto,
    Boolean peps,
    String tipoObjeto,
    String tipoEntidad,
    String tipoIdentificacion,
    String titulo,
    String website,
    LocalDateTime fechaActualiza

) {}
