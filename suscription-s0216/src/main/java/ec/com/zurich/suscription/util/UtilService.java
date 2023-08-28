package ec.com.zurich.suscription.util;

public final class UtilService {

    private UtilService(){}

    public static final String PROPORCION_BENEFICIARIO_100 = "100.00";
    public static final String REGISTRO_NUEVO = "-1";
    public static final String VEHICULO_SIN_PRODUCTO = "-2";

    // Ramos - Nombres
    public static final String RAMO_NOMBRE_SOAT = "SOAT";

    public static final String NCPROVISIONCOMISIONAGENTE = "3";
    public static final String NCPROVPRO = "134"; //NOTA DE CREDITO DE PROVISION DE PROVEEDOR
    public static final String CLASE_ENDOSO_PROV = "ENDOSO PROV";
    public static final String CLASE_ENDOSO_PROV_NEG = "ENDOSO PROV NEG";
    public static final String NUMERO_SEQ_NOTA_CREDITO_AGENTE = "NRONCPROVAG";
    public static final String NUMERO_SEQ_NOTA_CREDITO_PROVEEDORES = "NRONCPROV";
    public static final String NUMERO_SEQ_NOTA_DEBITO_PROVEEDORES = "NRONDPROV";
    public static final String COMISIONISTA = "7938799828993";

    public final static String ASIENTO_NUMEROASIENTO = "ASIENTO_NUMEROASIENTO";

    /**
     * ASIENTO
     */
    public final static String ASIENTO_JORNALIZADO = "300";
    public final static String ASIENTO_MAYORIZADO = "301";
    public final static String ASIENTO_BORRADOR = "302";
    public final static String ASIENTO_ANULADO = "303";
    public final static String ASIENTO_ANULADO_AUTOMATICO = "305";

    /**
     * EJERCICIO PERIODO
     */
    public final static String EJERCICIO_PERIODO_ABIERTO = "310";
    public final static String EJERCICIO_PERIODO_CERRADO_CON_BALANCE = "312";

    public final static String POLIZA_EMITIDA = "6";
    public final static String INDEMNIZACION_EMITIDA = "120";
    public final static String TRANSACCION_ADMINISTRATIVA_EMITIDA = "174";
    public final static String ENDOSO_EMITIDO = "12";
    public final static String ENDOSO_DIFERIDO_EMITIDO = "401";
    public final static String DETALLE_MOVIMIENTO_BANCARIO_EMITIDO = "180";

    public final static String DOCUMENTO_EMITIDO = "160";
    public final static String DOCUMENTO_CONTABILIZADO = "163";

    /**
     * DETALLES XCASOCONTABLE WHERE CASOCONTABLEID = 4
     */
    public final static String COMISIONES = "COMISIONES";
    public final static String COMISIONES_PAGADAS = "COMPAG";
    public final static String COMISIONES_DIFERIDAS = "COMDIF";
    public final static String COMISIONES_ANTICIPADAS = "COMANT";


    /**
     * DOCUMENTOS
     */
    public final static String NOTA_DEBITO_CREDITO_PROVISION_COMISION_AGENTE = "NotaDebitoCreditoProvisionComisionAgente";
    public final static String NOTA_DEBITO_CREDITO_PROVISION_COMISION_SERVICIOS = "NotaDebitoCreditoProvisionComisionServicios";
    public final static String NOTA_DEBITO_CREDITO_CESION_REASEGURO = "NotaDebitoCreditoCesionReaseguro";
    public final static String CRUCE_DOCUMENTOS = "CruceDocumentos";
    public final static String NOTA_CREDITO_COMISION_CEDIDO = "NotaCreditoComisionCedido";

    public final static String FACTURA_CLIENTE = "FacturaCliente";
    public final static String FACTURA_COMISION = "FacturaComision";


    public final static String ENDOSO = "ENDOSO";


    public final static String ERROR_ELIMINAR = "Error al eliminar ";
    public final static String DATO_NO_ENCONTRADO = "No existe registro ";
    public final static String ERROR_RESULTADO_MULTIPLE = "Error existe mas de un resultado ";

    public static final String IVA = "IVA";
    public static final String COMCOA = "COMCOA";
    public static final String COMISIONESD = "COMISIONESD";
    public static final String COMISIONESFD = "COMISIONESFD";
    public static final String SUBTOTAL = "SUBTOTAL";
    public static final String SUBEXC = "SUBEXC";

    public final static String MES = "mes";
    public final static String RAMO = "ramo";
    public final static String BROKER_REASEGURO = "brokerReaseguro";
    public final static String TIPO_ENTIDAD = "tipoEntidad";
    public final static String LOCALIDAD = "localidad";
    public final static String TIPO_RAMO = "tipoRamo";
    public final static String AGENTE = "agente";

    public final static String PROVEEDOR_SERVICIOS = "proveedor";
    public final static String TIPO_ENTIDAD_SERVICIOS = "tipoProveedor";
    public final static String TIPO_CONTRATO_REASEGURO = "tipoContrato";
    public final static String CUENTA_BANCARIA = "cuentaBancaria";
    public final static String ENTIDADRECLAMO = "entReclamo";
    public final static String TIPO_RAMO_RES = "tipoRamoRes";
    public final static String TIPOACTIVO = "tipoActivo";

    /**
     * FACTURACION CLIENTE
     */
    public static final String CLASE_PLANTILLA_TRATADO = "PlantillaReaseguradorContrato";
    public final static String COMPANIA_SEGUROS = "28770304";
    public static final String CLASE_TRATADO = "ReaseguradorContrato";
    public static final String MOVRCARICNR = "MOVRCARICNR";
    public static final String MOVRCARICR = "MOVRCARICR";
    public static final String RESERVA_DE_SINIESTROS = "RESSIN";
    public static final String RECUPERACION_SINIESTROS = "RECSIN";
    public static final String SINIESTROS_POR_LIQUIDAR = "SINPORLIQ";
    public static final String SINIESTROS_POR_LIQUIDAR_SINREPARTO = "SINPORLIQSR";
    public static final String RESERVA = "Reserva";
    public static final String CUOTA_INICIAL = "PAGO DE CONTADO";

    /**
     * SECUENCIAS
     */
    public static final String SEQ_FACTURA = "NROFAC";
    public static final String SEQ_CUOTA = "NROCUOTA";

    /**
     * FORMATOS FECHA
     */
    public static final String FORMATO_DDMMYYYY = "dd/MM/yyyy";
    public static final String FORMATO_HHMMSS = "HH:mm:ss";


    /**
     * CONSTANTES CREACION DE ENDOSO
     */
    public final static String ESTADO_ENDOSO = "11";


    public static final String FECHA_COBRA_IVA_PUBLICOS = "25/10/2009";

    /**
     * Constantes texto
     */
    public final static String EXEQUIAS = "EXEQUIAS";
    public final static String QBE_CARGO = "QBE_CARGO";
    public final static String USUARIO_ACTUALIZA_ID = "718060795640";
    public final static String SIN_DIRECCION = "SIN_DIRECCION";
    public final static String SIN_TELEFONO = "SIN TELEFONO";
    public final static String ENDOSO_NEGATIVO = "ENDOSO NEGATIVO";
    public final static String TRANSPORTE_ABIERTO = "TRANSPORTE ABIERTO";
    public final static String TRANSPORTE_ESPECIFICO = "TRANSPORTE ESPECIFICO";
    public final static String TRANSPORTE_GENERAL = "TRANSPORTE GENERAL";
    public final static String OBJETO_ASEGURADO = "OBJETO ASEGURADO";
    public final static String MSG_EXITO = "EXITO";


    public static String mostrarMensaje(){
        return "Hola, como estas ";
    }
}

