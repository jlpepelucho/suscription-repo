package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.library.exceptions.ZurichErrorException;
import ec.com.zurich.suscription.repository.SecuencialLogDbRepository;
import ec.com.zurich.suscription.repository.TipoDocAutorizacionSRIDbRepository;
import ec.com.zurich.suscription.repository.VariablesSistemaDbRepository;
import ec.com.zurich.suscription.resources.entity.SecuencialLog;
import ec.com.zurich.suscription.resources.entity.TipoDocAutorizacionSRI;
import ec.com.zurich.suscription.resources.entity.VariablesSistema;
import ec.com.zurich.suscription.service.VariablesSistemaDbService;
import ec.com.zurich.suscription.util.Valor;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VariablesSistemaDbServiceImpl implements VariablesSistemaDbService {

    private final VariablesSistemaDbRepository variablesSistemaDbRepository;
    private final SecuencialLogDbRepository secuencialLogDbRepository;
    private final TipoDocAutorizacionSRIDbRepository tipoDocAutorizacionSRIDbRepository;
    private final SpSecuenciaDbServiceImpl spSecuenciaDbService;
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional()
    public Integer getSecuencial(String laVariable, String laCompaniaSegurosId,
                                 String laSucursalId) {
        Valor[] valores = new Valor[2];
        if (laCompaniaSegurosId != null && laSucursalId != null) {
            valores = new Valor[4];
            valores[0] = new Valor("esGlobal", "0", Valor.TIPO_NUMERICO);
        } else {
            valores[0] = new Valor("esGlobal", "1", Valor.TIPO_NUMERICO);
        }
        valores[1] = new Valor("nombre", laVariable, Valor.TIPO_CARACTER);
        if (laCompaniaSegurosId != null)
            valores[2] = new Valor("companiaSegurosId", laCompaniaSegurosId, Valor.TIPO_CARACTER);
        if (laSucursalId != null)
            valores[3] = new Valor("sucursalId", laSucursalId, Valor.TIPO_CARACTER);
        return recuperaSecuencia(valores, "getSecuencial:245");
    }

    @Override
    @Transactional()
    public Integer getSecuencial(String laVariable, String laCompaniaSegurosId) {

        Valor[] valores = new Valor[4];
        valores[0] = new Valor("esGlobal", "0", Valor.TIPO_NUMERICO);
        valores[1] = new Valor("nombre", laVariable, Valor.TIPO_CARACTER);
        valores[2] = new Valor("companiaSegurosId", laCompaniaSegurosId,
                Valor.TIPO_CARACTER);
        valores[3] = new Valor("sucursalId", null, Valor.TIPO_NULO);
        return recuperaSecuencia(valores, "getSecuencial:215");

    }

    @Override
    @Transactional()
    public Integer recuperaSecuencia(Valor[] valores, String metodo) {

        // ////////////////////////////////////////////
        String sqlConId = "";
        List<String[]> sqlList = construyeSQLList(valores);
        String sql = construyeSQL(valores);
        // ////////////////////////////////////////////////

        List<Object[]> resultado = variablesSistemaDbRepository.findBySql(sqlList);

        if (resultado.isEmpty())
            throw new RuntimeException(
                    "La variable de sistema localizada: no esta definida en la base. Comuníquese con el administrador del sistema,"
                            + construyeValores(valores));
        if (resultado.size() > 1)
            throw new RuntimeException(
                    "La variable de sistema localizada:, está devolviendo más de un valor secuencial. Comuníquese con el administrador del sistema,"
                            + construyeValores(valores));
        // Map<String, Object> registro = resultado.iterator().next();

        Object[] registro = resultado.iterator().next();
        BigDecimal bigDecimal = null;
        String seq = null;
        if (registro[1] != null) {
            seq = registro[1].toString();
            Session hibernateSession = entityManager.unwrap(Session.class);
            SharedSessionContractImplementor sessionImplementor = hibernateSession
                    .unwrap(SharedSessionContractImplementor.class);

            bigDecimal = new BigDecimal(spSecuenciaDbService.generarSecuencia(sessionImplementor, seq)
                    .toString());

        } else {
            sqlConId = construyeSQLConId(valores);
            List<String[]> sqlList1 = construyeSQLList(valores);
            List<Object> resultadoId = variablesSistemaDbRepository.findBySqlID(sqlList1).orElse(new ArrayList<>());
            VariablesSistema vsId = variablesSistemaDbRepository
                    .getVariablesSistemaById(
                            Objects.requireNonNull(resultadoId.stream().findFirst().orElse(null)).toString())
                    .orElseThrow(() -> new ZurichErrorException("002",
                            "Cannot find the process specified in the billing cancellation"));
            if (vsId != null) {
                bigDecimal = vsId.getValor().add(BigDecimal.ONE);
            }
            variablesSistemaDbRepository.updateVariablesSistemaValorById(
                    Objects.requireNonNull(resultadoId.stream().findFirst().orElse(null)).toString(), bigDecimal);
        }
        saveLogSecuencial(metodo, seq, bigDecimal != null ? bigDecimal.toString() : null, sqlConId, sql, null, null, "",
                "");
        return bigDecimal != null ? bigDecimal.intValue() : 0;

    }


    @Transactional()
    @Override
    public void saveLogSecuencial(String metodo, String secuencia, String secuencial, String scriptUpdate,
                                  String script, Boolean esTransaccional, Boolean esGlobal, String valores, String variable) {
        try {
            SecuencialLog secuencialLog = new SecuencialLog();
            secuencialLog.setServicio("suscription");
            secuencialLog.setMetodo(metodo);
            secuencialLog.setSecuencia(secuencia);
            secuencialLog.setSecuencial(secuencial);
            secuencialLog.setScriptUpdate(scriptUpdate);
            secuencialLog.setScript(script);
            secuencialLog.setEsTransaccional(esTransaccional);
            secuencialLog.setEsGlobal(esGlobal);
            secuencialLog.setFechaActualiza(Date.valueOf(LocalDate.now()));
            secuencialLog.setValores(valores);
            secuencialLog.setVariable(variable);
            secuencialLogDbRepository.save(secuencialLog);
        } catch (Exception ignored) {

        }
    }

    @Override
    @Transactional()
    public String construyeValores(Valor[] valores) {
        StringBuilder bf = new StringBuilder();
        for (Valor v : valores) {
            bf.append(v.getClave()).append("=").append(v.getValor()).append(":");
        }
        return bf.toString();
    }

    @Override
    @Transactional()
    public VariablesSistema getVariable(String laCompaniaSegurosId, String laSucursalId,
                                        String tipoDocumentoId, String puntoEmision) {

        if (puntoEmision == null)
            throw new RuntimeException("No se puede leer el punto de emision del documento");
        VariablesSistema var = new VariablesSistema();

        String activa = null;
        List<Object[]> resultados = new ArrayList<>();
        if (tipoDocumentoId.equals("FACTANT"))
            resultados = variablesSistemaDbRepository
                    .getVariablesSistemaIdActivaNOmbreByPuntoEmisionNombreCompaniaSucursal(puntoEmision,
                            laCompaniaSegurosId, laSucursalId);
        else {
            TipoDocAutorizacionSRI tipoDoc = tipoDocAutorizacionSRIDbRepository
                    .getTipoDocAutorizacionSRIById(tipoDocumentoId).orElseThrow(() -> new ZurichErrorException("002",
                            "Cannot find the process specified in the billing cancellation"));
            if (tipoDoc != null) {
                resultados = variablesSistemaDbRepository
                        .getVariablesSistemaIdActivaNOmbreByPuntoEmisionNombreCompaniaSucursal(puntoEmision,
                                laCompaniaSegurosId, laSucursalId,
                                "NRO" + tipoDoc.getNemonico() + "%");
            }
        }
        long contenido = 0;
        int contador = 0;

        for (Object[] obj : resultados) {
            contador++;
            contenido = Long.parseLong(obj[0].toString());
            activa = obj[1].toString();
        }

        if (contador > 1)
            throw new RuntimeException("La variable de sistema localizada," + " para companiaSegurosId:"
                    + laCompaniaSegurosId
                    + ", está devolviendo más de un valor secuencial. Comuníquese con el administrador del sistema.");
        else {
            if (contador == 0)
                throw new RuntimeException(
                        "La variable de sistema localizada," + " para companiaSegurosId:" + laCompaniaSegurosId
                                + ", No devuelve ningún valor. Comuníquese con el administrador del sistema.");
        }
        if (contador == 1) {
            var = variablesSistemaDbRepository.getVariablesSistemaById(String.valueOf(contenido))
                    .orElseThrow(() -> new ZurichErrorException("002",
                            "Cannot find the process specified in the billing cancellation"));
            if (activa.equals("0") && var != null)
                throw new RuntimeException(
                        "No esta permitido emitir para punto de emision " + var.getPuntoEmision());
        }
        return var;

    }

    @Transactional()
    public String construyeSQL(Valor[] valores) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT VALOR,NOMBRESECUENCIA FROM VARIABLESSISTEMA WHERE 1 = 1 ");
        for (Valor v : valores) {
            switch (v.getTipo()) {
                case Valor.TIPO_CARACTER ->
                        sql.append(" AND ").append(v.getClave()).append("= '").append(v.getValor()).append("'");
                case Valor.TIPO_NUMERICO ->
                        sql.append(" AND ").append(v.getClave()).append("= ").append(v.getValor()).append(" ");
                case Valor.TIPO_NULO -> sql.append(" AND ").append(v.getClave()).append(" is null");
            }
        }
        // System.out.println("*--------------SQL:" + sql.toString());
        return sql.toString();
    }

    @Transactional()
    public List<String[]> construyeSQLList(Valor[] valores) {
        List<String[]> resultado = new ArrayList<>();
        for (Valor v : valores) {
            switch (v.getTipo()) {
                case Valor.TIPO_CARACTER -> resultado.add(new String[]{v.getClave(), "=", "'" + v.getValor() + "'"});
                case Valor.TIPO_NUMERICO -> resultado.add(new String[]{v.getClave(), "=", v.getValor()});
                case Valor.TIPO_NULO -> resultado.add(new String[]{v.getClave(), "IS NULL", null});
            }
        }
        // System.out.println("*--------------SQL:" + sql.toString());
        return resultado;
    }

    @Transactional()
    public String construyeSQLConId(Valor[] valores) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID FROM VARIABLESSISTEMA WHERE 1 = 1 ");
        for (Valor v : valores) {
            switch (v.getTipo()) {
                case Valor.TIPO_CARACTER ->
                        sql.append(" AND ").append(v.getClave()).append("= '").append(v.getValor()).append("'");
                case Valor.TIPO_NUMERICO ->
                        sql.append(" AND ").append(v.getClave()).append("= ").append(v.getValor()).append(" ");
                case Valor.TIPO_NULO -> sql.append(" AND ").append(v.getClave()).append(" is null");
            }
        }
        // System.out.println("*--------------SQL:" + sql.toString());
        return sql.toString();
    }

    @Transactional(readOnly = true)
    public BigDecimal consultarCantidadDeVariablesPorVariableyCompaniaDeSegurosId(String nombreVariable, Boolean esGlobal, String companiaDeSegurosId) {
        List<BigDecimal> variables = variablesSistemaDbRepository.consultarCantidadDeVariablesPorVariableyCompaniaDeSegurosId(
                nombreVariable,
                esGlobal,
                companiaDeSegurosId
        ).stream().toList();
        if (!variables.isEmpty()) {
            if (variables.size() == 1) {
                return variables.get(0);
            } else {
                throw new RuntimeException("The system variable: " + nombreVariable
                        + "for ensurance company with the ID: " + companiaDeSegurosId
                        + "is returning more than one sequential value. Contact system administrator.");
            }
        } else {
            throw new RuntimeException("The system variable: " + nombreVariable
                    + "for ensurance company with the ID: " + companiaDeSegurosId
                    + "is not defined in the database. Contact system administrator.");
        }
    }


}
