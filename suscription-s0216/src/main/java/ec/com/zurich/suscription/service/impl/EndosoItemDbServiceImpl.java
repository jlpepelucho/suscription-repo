package ec.com.zurich.suscription.service.impl;

import ec.com.zurich.suscription.repository.*;
import ec.com.zurich.suscription.resources.entity.Beneficiario;
import ec.com.zurich.suscription.resources.entity.*;
import ec.com.zurich.suscription.resources.mapper.EndosoItemMapper;
import ec.com.zurich.suscription.resources.mapper.EndosoItemProductoMapper;
import ec.com.zurich.suscription.resources.mapper.VehicleMapper;
import ec.com.zurich.suscription.resources.model.BillingResponse;
import ec.com.zurich.suscription.resources.model.EndorsementItemProductData;
import ec.com.zurich.suscription.resources.model.ItemVehicleData;
import ec.com.zurich.suscription.service.EndosoItemDbService;
import ec.com.zurich.suscription.util.*;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EndosoItemDbServiceImpl implements EndosoItemDbService {
    private static final Logger logger = LoggerFactory.getLogger(BillingResponse.class);

    private final EndosoItemDbRepository endosoItemDbRepository;
    private final EndosoItemMapper endosoItemMapper;
    private final EndosoItemProductoMapper endosoItemProductoMapper;
    private final VehicleMapper vehicleMapper;
    private final ColorVehiculoRepository colorVehiculoRepository;
    private final ModeloVehiculoRepository modeloVehiculoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final EndosoItemProductoRepository endosoItemProductoRepository;
    private final PolizaRepository polizaRepository;
    private final ItemRepository itemRepository;
    private final EndosoDbRepository endosoDbRepository;

    private final CoberturasDbServiceImpl coberturasRepository;
    private final EndosoDeducibleTipoRepository endosoDeducibleTipoRepository;
    private final EndosoItemCoberturaDbRepository endosoItemCoberturaDbRepository;
    private final CoberturaRepository coberturaRepository;
    private final BeneficiarioRepository beneficiarioRepository;
    private final EndosoEstadoRepository endosoEstadoRepository;
    private final ReclamoRepository reclamoRepository;
    private final VehiculosBloqueadoRepository vehiculosBloqueadoRepository;
    private final PlantillaCoberturaRepository plantillaCoberturaRepository;
    private final ConjuntoCoberturasRepository conjuntoCoberturasRepository;
    private final DetalleProductoRepository detalleProductoRepository;
    private final DeducibleProductoRepository deducibleProductoRepository;
    private final EndosoDeducibleRepository endosoDeducibleRepository;
    private final DetalleDeducibleRepository detalleDeducibleRepository;
    private final BlanketRepository blanketRepository;
    private final AseguradoRepository aseguradoRepository;
    private final ConductorRepository conductorRepository;
    private final EntidadRepository entidadRepository;
    private final PlantillaDeducibleRepository plantillaDeducibleRepository;
    private final PlantillaDeducibleTipoRepository plantillaDeducibleTipoRepository;
    private final DeducibleDetalleProductoEntityRepository deducibleDetalleProductoEntityRepository;
    private final EndosoItemDetalleRepository endosoItemDetalleRepository;


    @Override
    @Transactional
    public EndosoItem crearItemVehiculoMasivos(ItemVehicleData crearItemVehiculoData) {
        EndosoItem endosoItem = this.crearItemVehiculo(crearItemVehiculoData);
        endosoItem.setValorPrimaNeta(crearItemVehiculoData.getEndorsementItem().getNetPremiumValue().setScale(2, RoundingMode.HALF_UP));
        endosoItemDbRepository.updateEndosoItemById(endosoItem, endosoItem.getId());
        return endosoItem;
    }

    public EndosoItem crearItemVehiculo(ItemVehicleData crearItemVehiculoData) {
        Vehiculo vehiculoRequest = vehicleMapper.vehicleDataToVehiculo(crearItemVehiculoData.getVehicleData());
        EndosoItem endosoItemRequest = endosoItemMapper.toEndosoItem(crearItemVehiculoData.getEndorsementItem());

        // PARA VEHICULOS POR CONTENEDORES O PLANTILLA
        String productoId = crearItemVehiculoData.getProductId(); //configproductoID
        String plantillaId = endosoItemRequest.getPlantillaId();// EndosoItem

        if (plantillaId != null && !plantillaId.equals(UtilService.REGISTRO_NUEVO)
                && productoId != null && !productoId.equals(UtilService.VEHICULO_SIN_PRODUCTO))
            throw new RuntimeException("Solo puede registrar plantillaId o productoId. Asignar -2 para indicar que no se asigna producto al Vehiculo.\nAsignar -1 para indicar que no se asigna plantilla al Vehículo");

        String usuarioActualiza = endosoItemRequest.getUsuarioActualiza();
        String endosoItemId = endosoItemRequest.getId();

        Vehiculo vehiculoEntity = new Vehiculo();

        String vehiculoId = vehiculoRequest.getId();
        Integer anio = vehiculoRequest.getAnio();
        String chasis = vehiculoRequest.getChasis();
        String cilindros = vehiculoRequest.getCilindros();
        String colorVehiculoId = vehiculoRequest.getColorVehiculoId();
        String modeloVehiculoId = vehiculoRequest.getModeloVehiculoId();
        String motor = vehiculoRequest.getMotor();
        String placas = vehiculoRequest.getPlacas();
        String tarjetaAutoasistencia = vehiculoRequest.getTarjeta();
        boolean tieneAutoasistencia = vehiculoRequest.getTieneAutoAsistencia();
        Integer numeroOcupantes = vehiculoRequest.getNumeroOcupantes();
        String tipoUsoId = vehiculoRequest.getTipoUsoId();
        String tipoVehiculoId = vehiculoRequest.getTipoVehiculoId();
        BigDecimal tonelaje ;
        tonelaje = vehiculoRequest.getTonelage().setScale(2, RoundingMode.HALF_UP);

        boolean modificar = false;

        Optional<ColorVehiculo> colorVehiculo = colorVehiculoRepository.getColorVehiculoById(colorVehiculoId);
        Optional<ModeloVehiculo> modeloVehiculo = modeloVehiculoRepository.getModeloVehiculoById(modeloVehiculoId);
        String descripcion;
        MarcaVehiculo marcaVehiculo ;
        if (modeloVehiculo.isPresent()) {
            marcaVehiculo = modeloVehiculo.get().getMarcaVehiculo();
            descripcion = "MARCA: " + marcaVehiculo.getNombre().trim() + " MODELO: " + modeloVehiculo.get().getNombre().trim()
                    + " MOTOR: " + motor.trim() + " CHASIS: " + chasis.trim();
        } else
            throw new RuntimeException("Objeto modeloVehiculo no encontrado: " + modeloVehiculo);

        if (colorVehiculo.isPresent()&&colorVehiculo.get().getId() == null)
            throw new RuntimeException("Nombre de color no encontrado para id: " + colorVehiculoId);
        else if (colorVehiculo.isPresent()&&!colorVehiculo.get().getEstado())
            throw new RuntimeException("Nombre de color no vigente. Id: " + colorVehiculoId);

        String zonaRiesgoId = vehiculoRequest.getItem().getZonaRiesgoId();
        String claseRiesgoId = endosoItemRequest.getClaseRiesgoId();
        String tipoRiesgoId = endosoItemRequest.getTipoRiesgoId();
        double depreciacion = vehiculoRequest.getItem().getDepreciacion().setScale(2, RoundingMode.HALF_UP).doubleValue();// item
        double valorItem = vehiculoRequest.getItem().getValor().setScale(2, RoundingMode.HALF_UP).doubleValue();
        String texto = vehiculoRequest.getItem().getTexto();
        String noOperacion = vehiculoRequest.getItem().getNumeroOperacion();
        int noPanfleto = vehiculoRequest.getItem().getNumero().intValue(); // CONTENEDORES
        String propietario = vehiculoRequest.getPropietario();
        String dispositivoSeguridadId = vehiculoRequest.getDispositivoSeguridadId();
        String tipoItemId = endosoItemRequest.getTipoItemId();
        BigDecimal porcentajeDescuentoDispositivo;
        porcentajeDescuentoDispositivo = vehiculoRequest.getPorcentajeDescuentoDispositivo();

        if (porcentajeDescuentoDispositivo == null)
            porcentajeDescuentoDispositivo = BigDecimal.ZERO;

        BigDecimal valorAsegurado ;
        valorAsegurado = endosoItemRequest.getValorAsegurado();
        //String tipoItemId = endosoItemRequest.getTipoItem().getId();
        String endosoId = endosoItemRequest.getEndoso().getId();


        String nombre = "PLACAS: " + placas + " COLOR: " + (colorVehiculo.isPresent()?colorVehiculo.get().getNombre():"");
        if (nombre.length() > 50)
            nombre = nombre.substring(0, 49);

        // PARA VEHICULO POR CONTENDEDORES
        List<EndosoItemProducto> endItemProdList = new ArrayList<>();
        if (crearItemVehiculoData.getEndorsementItemProductData() != null) {
            List<EndorsementItemProductData> enItemProdData = crearItemVehiculoData.getEndorsementItemProductData();
            for (EndorsementItemProductData endorsementItemProductData : enItemProdData) {
                EndosoItemProducto endosoItemProducto = endosoItemProductoMapper.toEndosoItemProducto(endorsementItemProductData
                );
                endItemProdList.add(endosoItemProducto);
            }
        }
        String polizaPadreId ;
        String productoAntId = "-1";

        if ((endosoItemId != null) && (!endosoItemId.equals(UtilService.REGISTRO_NUEVO))) {
            if ((productoId != null && !productoId.equals("-1") && !productoId.equals("-2")) && !endItemProdList.isEmpty()) {
                for (EndosoItemProducto eip : endItemProdList) {
                    modificar = this.noExisteEndosoItemProducto(endosoItemId, productoId, eip.getConjuntoid(), eip.getPlanid());
                    if (modificar)
                        break;
                }
            }

            if ((vehiculoId != null) && (!vehiculoId.equals("-1"))) {
                // vehiculo.setId(vehiculoId);
                // vehiculo.retrieve();
                Optional<Vehiculo> vehiculo = vehiculoRepository.getVehiculoById(vehiculoId);

                if (vehiculo.isPresent())
                    vehiculoEntity = vehiculo.get();

                // Para marcar si se tiene que generar nuevamente las coberturas F.M. 09/11/2005
                double valorItemA = vehiculoEntity.getItem().getValor().doubleValue();

                if (valorItem != valorItemA)
                    modificar = true;

                vehiculoEntity.setTieneAutoAsistencia(tieneAutoasistencia);
                vehiculoEntity.setAnio(anio);
                vehiculoEntity.setChasis(chasis);
                vehiculoEntity.setCilindros(cilindros);
                if (colorVehiculo.isPresent()){
                    vehiculoEntity.setColor(colorVehiculo.get().getNombre());
                }
                vehiculoEntity.setMotor(motor);
                vehiculoEntity.setPlacas(placas);
                vehiculoEntity.setTarjeta(tarjetaAutoasistencia);
                vehiculoEntity.setTipoVehiculoId(tipoVehiculoId);
                vehiculoEntity.setTipoUsoId(tipoUsoId);
                vehiculoEntity.setTonelage(tonelaje);
                vehiculoEntity.setNumeroOcupantes(numeroOcupantes);
                vehiculoEntity.setModeloVehiculoId(modeloVehiculoId);
                vehiculoEntity.setColorVehiculoId(colorVehiculoId);
                vehiculoEntity.setPropietario(propietario);
                vehiculoEntity.setDispositivoSeguridadId(dispositivoSeguridadId);
                vehiculoEntity.setUsuarioActualiza(usuarioActualiza);
                Item itemVehicle = vehiculoEntity.getItem();
                itemVehicle.setValor(BigDecimal.valueOf(valorItem));
                itemVehicle.setZonaRiesgoId(zonaRiesgoId);
                itemVehicle.setDepreciacion(BigDecimal.valueOf(depreciacion));
                itemVehicle.setTexto(texto);
                itemVehicle.setNumeroOperacion(noOperacion); //COT 2006-120
                // GRABO EL NUMERO DE PANFLETO PARA EL CASO DE CONTENEDORES
                Optional<EndosoItem> endosoItemOp = endosoItemDbRepository.getEndosoItemById(endosoItemId);
                EndosoItem endosoItem ;
                if (endosoItemOp.isPresent())
                    endosoItem = endosoItemOp.get();
                else
                    throw new RuntimeException("vehiculoId !null. Endoso Item no encontrado: " + endosoItemId);
                // System.out.println("endosoItem.getEndoso():
                // "+endosoItem.getEndoso().getPoliza().getPadre() );


                if (endosoItem.getEndoso().getPoliza().getPadreId() != null) {
                    polizaPadreId = endosoItem.getEndoso().getPoliza().getPadreId();


                    Poliza polizaPadre = new Poliza();
                    Optional<Poliza> polizaOp = polizaRepository.getPolizaById(polizaPadreId);


                    if (polizaOp.isPresent()) {
                        polizaPadre = polizaOp.get();
                        productoAntId = polizaPadre.getConfigProductoId();
                    }
                    if (polizaPadre.getTipoContenedor().equals("A"))
                        itemVehicle.setNumero(new BigDecimal(noPanfleto));
                }
                itemRepository.updateItemById(itemVehicle, itemVehicle.getId());
                vehiculoRepository.updateVehiculoById(vehiculoEntity, vehiculoEntity.getId());

            }

            String parametro = vehiculoEntity.getId();

            Optional<EndosoItem> endosoItemOp = endosoItemDbRepository.getEndosoItemById(endosoItemId);
            EndosoItem endosoItem ;
            if (endosoItemOp.isPresent())
                endosoItem = endosoItemOp.get();
            else
                throw new RuntimeException("vehiculoId null. Endoso Item no encontrado: " + endosoItemId);

            BigDecimal valorItemAnterior;
            valorItemAnterior = endosoItem.getValorItem();// recalculaCoberturasEspecificas

            endosoItem.setNombre(nombre);
            endosoItem.setDescripcion(descripcion);
            endosoItem.setValorItem(new BigDecimal(valorItem));
            endosoItem.setValorAsegurado(valorAsegurado);
            endosoItem.setTipoItemId(tipoItemId);
            // if (!endosoItem.getItemId().equals(parametro)) EVALDEZ
            boolean itemVigVehiculos = false;
            if (endosoItem.getItemId().equals(parametro))
                itemVigVehiculos = this.itemVigenteVehiculos(vehiculoEntity.getChasis(),
                        Timestamp.valueOf(endosoItem.getEndoso().getVigenciaDesde()), null);
            if (itemVigVehiculos) {
                String vigencia = this.itemVigenteVigenciaVehiculos(vehiculoEntity.getChasis(),
                        Timestamp.valueOf(endosoItem.getEndoso().getVigenciaDesde()), null);
                if (!vigencia.isEmpty())
                    throw new RuntimeException(endosoItem.getDescripcion() + vigencia);
                else
                    throw new RuntimeException(endosoItem.getDescripcion());
            }

            if (endosoItem.getItemId() != null) {
                this.validarItemReclamoPerdidaTotal(endosoItem.getItemId());
                this.validarItemVehiculosBloqueados(vehiculoEntity.getChasis(), vehiculoEntity.getMotor(),
                        vehiculoEntity.getPlacas());
            }

            endosoItem.setItemId(vehiculoId);

            Endoso endoso = endosoDbRepository.getEndosoById(endosoId).get();
            if (endoso.getId() == null)
                throw new RuntimeException("Endoso no encontrado " + endosoId);

            endosoItem.setEndoso(endoso);

            endosoItem.setClaseRiesgoId(claseRiesgoId);
            endosoItem.setTipoRiesgoId(tipoRiesgoId);

            if ((endosoItem.getPlantillaId() != null)
                    && !plantillaId.equals(UtilService.REGISTRO_NUEVO)) {
                if (!endosoItem.getPlantillaId().equals(plantillaId)) {
                    /************************
                     * OPERACIONES SOBRE ENDOSOITEMCOBERTURA
                     ********************************/
                    coberturasRepository.eliminaCoberturasItem(endosoItem);
                    coberturasRepository.eliminaDeducibleItem(endosoItem);
                    endosoItem.setPlantillaId(plantillaId);
                    grabaPlantilla(endosoItem, usuarioActualiza);
                }
            } else if (endosoItem.getPlantillaId() != null && !endosoItem.getPlantillaId().isEmpty()
                    && plantillaId.equals(UtilService.REGISTRO_NUEVO)) {
                coberturasRepository.eliminaCoberturasItem(endosoItem);
                coberturasRepository.eliminaDeducibleItem(endosoItem);
                // endosoItem.grabaCoberturasGenerales(usuarioActualiza, pm);
                endosoItem.setPlantillaId(null);

                this.recalculaValorPrimaNeta(endosoItem);
                this.recalculaValorAsegurado(endosoItem);

                if (modificar && productoId != null && !productoId.equals("-1") && !productoId.equals("-2")) {
						/*Optional<Producto> prOptional = jpaOperations.lookup(em, productoId, null, Producto.class);
						Producto producto = new Producto();
						if (prOptional.get().getId() != null)
							producto = prOptional.get();
						else
							throw new RuntimeException("El producto no existe: productoId > " + productoId);*/

                    this.eliminaEndosoItemProducto(endosoItemId);
                    if (!endItemProdList.isEmpty()) {
                        for (EndosoItemProducto eip : endItemProdList) {
                            if (!eip.getPlanid().equals("-1")) {// && seleccion.equals("S")
                                this.grabaEndosoItemProducto(endosoItemId, eip, usuarioActualiza);
                                this.grabaCoberturasProducto(endosoItemId, eip, usuarioActualiza);
                            }
                        }
                    }
                    this.grabaDeduciblesEndosoItem(endosoItem, productoId, usuarioActualiza); //CONFIGPRODUCTOID
                } else
                    coberturasRepository.grabaCoberturasGenerales(usuarioActualiza, endosoItem);
            } else {
                // endosoItem:
                if ((endosoItem.getPlantillaId() == null || endosoItem.getPlantillaId().isEmpty())
                        && plantillaId != null && !plantillaId.equals(UtilService.REGISTRO_NUEVO)) {
                    coberturasRepository.eliminaCoberturasItem(endosoItem);
                    coberturasRepository.eliminaDeducibleItem(endosoItem);
                    endosoItem.setPlantillaId(plantillaId);
                    this.eliminaEndosoItemProducto(endosoItemId);
                    this.grabaPlantilla(endosoItem, usuarioActualiza);
                } else {// plantilla=null
                    endosoItem.setPlantillaId(null);
                    // ****** AUMENTADO PARA CUANDO CAMBIAN LOS PLANES EL 03/05/2005 VHBV ( Cambios
                    // F.M. 22/03/2006)*****

                    if (modificar && productoId != null && !productoId.equals("-1") && !productoId.equals("-2")) { //modificar true
                        coberturasRepository.eliminaCoberturasItem(endosoItem);
                        coberturasRepository.eliminaDeducibleItem(endosoItem);
                        this.eliminaEndosoItemProducto(endosoItemId);
                        this.recalculaValorPrimaNeta(endosoItem);
                        this.recalculaValorAsegurado(endosoItem);

                        if (endItemProdList.size() > 0) {
                            for (EndosoItemProducto eip : endItemProdList) {

                                if (!eip.getPlanid().equals(UtilService.REGISTRO_NUEVO)) {// &&
                                    // seleccion.equals("S")
                                    this.grabaEndosoItemProducto(endosoItemId, eip, usuarioActualiza);
                                    this.grabaCoberturasProducto(endosoItemId, eip, usuarioActualiza);
                                }
                            }
                        }
                        this.grabaDeduciblesEndosoItem(endosoItem, productoId, usuarioActualiza);
                    } else {
                        //String productoAntId = "";// request.getParameter("productoAntId");

                        if ((productoAntId != null && !productoAntId.equals("-1")) && (productoId == null || productoId.equals("-1")) && plantillaId.equals("-1")) {
                            coberturasRepository.eliminaCoberturasItem(endosoItem);
                            coberturasRepository.eliminaDeducibleItem(endosoItem);
                            this.eliminaEndosoItemProducto(endosoItemId);
                            this.recalculaValorPrimaNeta(endosoItem);
                            this.recalculaValorAsegurado(endosoItem);
                        }
                    }
                }
            }

            // NUMERO DE PANFLETO
            // CONTENEDORES******************************************************************************
            endosoItem.setEstadoId(EndosoItemEstadoId.NO_REPARTIDO);
            endosoItem.setUsuarioActualiza(usuarioActualiza);
            endosoItem.setFechaActualiza(Util.actualDateTime().toLocalDateTime());
            // GRABO EL NUMERO DE PANFLETO PARA EL CASO DE CONTENEDORES
            if (endosoItem.getEndoso().getPoliza().getPadreId() != null) {
                Poliza polizaPadre = new Poliza();
                Optional<Poliza> polizaOp = polizaRepository.getPolizaById(
                        endosoItem.getEndoso().getPoliza().getPadreId());

                if (polizaOp.isPresent())
                    polizaPadre = polizaOp.get();
                if (polizaPadre.getTipoContenedor().equals("A"))
                    endosoItem.setNumeroItem(noPanfleto);
            }
            // modificar/guardar
            endosoItem = endosoItemDbRepository.saveAndFlush(endosoItem);

//                String endosoItemAutoId = (String) endosoItem.getId();
            if (valorItemAnterior.doubleValue() != endosoItem.getValorItem().doubleValue()) {
                this.recalculaCoberturasEspecificas(endosoItem);
            }

            this.recalculaValores(endosoItem);
            return endosoItem;


        } else  {

            if (endosoId == null || endosoId.equals("")) {
                throw new RuntimeException("Ingrese un id de Endoso valido.");
            }

            Endoso endoso = new Endoso();
            Optional<Endoso> endosoOp = endosoDbRepository.getEndosoById(endosoId);

            if (endosoOp.isPresent())
                endoso = endosoOp.get();
            else
                throw new RuntimeException("Endoso no encontrado.");

            Poliza poliza = new Poliza();
            Optional<Poliza> polizaOp = polizaRepository.getPolizaById(endoso.getPoliza().getId());

            if (polizaOp.isPresent())
                poliza = polizaOp.get();

            BigDecimal numeroItem = BigDecimal.ZERO;
            Integer numeroItemReal = Integer.parseInt(this.valorTotalItems(endoso.getId()));

            if (endoso.getTipoEndoso().getNemotecnico().equals(TipoEndosoNemotecnico.POLIZA)) {
                if (numeroItemReal > 0) {
                    numeroItem = poliza.getNumeroItem().add(new BigDecimal(1));
                } else {
                    numeroItem = new BigDecimal(1);
                }
            } else {
                numeroItem = poliza.getNumeroItem().add(new BigDecimal(1));
            }

            poliza.setNumeroItem(numeroItem);
            polizaRepository.updatePolizaById(poliza, poliza.getId());

            if ((vehiculoId != null) && (!vehiculoId.equals("-1"))) {

                Optional<Vehiculo> vehiculo = vehiculoRepository.getVehiculoById(vehiculoId);

                if (vehiculo.isPresent()) {
                    vehiculoEntity = vehiculo.get();
                    Item itemVehicle = vehiculoEntity.getItem();
                    itemVehicle.setNumero(numeroItem);
                    itemRepository.saveAndFlush(itemVehicle);
                    vehiculoEntity.setItem(itemVehicle);
                    vehiculoEntity = vehiculoRepository.saveAndFlush(vehiculoEntity);
                }
            } else {
                vehiculoEntity.setTieneAutoAsistencia(tieneAutoasistencia);
                vehiculoEntity.setAnio(anio);
                vehiculoEntity.setChasis(chasis);
                vehiculoEntity.setCilindros(cilindros);
                vehiculoEntity.setColor(colorVehiculo.get().getNombre());
                vehiculoEntity.setMotor(motor);
                vehiculoEntity.setPlacas(placas);
                vehiculoEntity.setTarjeta(tarjetaAutoasistencia);
                // campo agregado para insertar no consta en modificar
                vehiculoEntity.setPorcentajeDescuentoDispositivo(porcentajeDescuentoDispositivo);
                vehiculoEntity.setTipoVehiculoId(tipoVehiculoId);
                vehiculoEntity.setTipoUsoId(tipoUsoId);
                vehiculoEntity.setTonelage(tonelaje);
                vehiculoEntity.setNumeroOcupantes(numeroOcupantes);
                vehiculoEntity.setColorVehiculoId(colorVehiculoId);
                vehiculoEntity.setPropietario(propietario);
                vehiculoEntity.setDispositivoSeguridadId(dispositivoSeguridadId);
                vehiculoEntity.setUsuarioActualiza(usuarioActualiza);
                vehiculoEntity.setFechaActualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
                vehiculoEntity.setModeloVehiculoId(modeloVehiculoId);
                Item itemVehiculoEntity = new Item();
                if (vehiculoEntity.getItem() != null) {
                    itemVehiculoEntity = vehiculoEntity.getItem();
                }
                itemVehiculoEntity.setNumero(numeroItem);
                itemVehiculoEntity.setValor(BigDecimal.valueOf(valorItem));
                itemVehiculoEntity.setZonaRiesgoId(zonaRiesgoId);
                itemVehiculoEntity.setDepreciacion(BigDecimal.valueOf(depreciacion));
                itemVehiculoEntity.setTexto(texto);
                itemVehiculoEntity.setNumeroOperacion(noOperacion);// OT 2006-120
                vehiculoEntity.setItem(itemVehiculoEntity);
                this.validarExisteRegistroVehiculo(vehiculoEntity);
                // GRABO EL NUMERO DE PANFLETO PARA EL CASO DE CONTENEDORES
                if (null != poliza.getPadreId()) {
                    Poliza polizaPadre = new Poliza();
                    Optional<Poliza> polizaOp1 = polizaRepository.getPolizaById(poliza.getPadreId());

                    if (polizaOp.isPresent())
                        polizaPadre = polizaOp1.get();

                    if (polizaPadre.getTipoContenedor().equals("A"))
                        itemVehiculoEntity.setNumero(new BigDecimal(noPanfleto));
                }
                vehiculoEntity.setItem(itemVehiculoEntity);
                itemVehiculoEntity = itemRepository.saveAndFlush(itemVehiculoEntity);
                vehiculoEntity = vehiculoRepository.saveAndFlush(vehiculoEntity);

            }

            EndosoItem endosoItem = new EndosoItem();
            endosoItem.setNumeroItem(numeroItem.intValue());
            endosoItem.setItemId(vehiculoEntity.getId());
            endosoItem.setNombre(nombre);
            endosoItem.setDescripcion(descripcion);
            endosoItem.setValorItem(BigDecimal.valueOf(valorItem));
            endosoItem.setTipoItemId(tipoItemId);
//				endosoItem.setEndoso(new Endoso());
//				endosoItem.getEndoso().setId(endosoId);
            endosoItem.setEndoso(endoso);
            endosoItem.setClaseRiesgoId(claseRiesgoId);
            endosoItem.setTipoRiesgoId(tipoRiesgoId);

            if (plantillaId != null && !plantillaId.equals("-1")) {// !sinplantilla
                endosoItem.setPlantillaId(plantillaId);
            }

            // else
            // endosoItem.setPlantillaId(null);
            endosoItem.setUsuarioActualiza(usuarioActualiza);
            endosoItem.setFechaActualiza(Util.actualDateTime().toLocalDateTime());
            endosoItem.setEstadoId(EndosoItemEstadoId.NO_REPARTIDO);
            endosoItem.setValorAsegurado(valorAsegurado);
            // EVALDEZ
            if (endosoItem.getItemId().equals(vehiculoId))
                if (this.itemVigente(vehiculoId, Timestamp.valueOf(endosoItem.getEndoso().getVigenciaDesde()), null)) {
                    String vigencia = this.itemVigenteVigencia(vehiculoId,
                            Timestamp.valueOf(endosoItem.getEndoso().getVigenciaDesde()), null);// (id,
                    // endosoItem.getEndoso().getVigenciaDesde(),null,
                    // pm);
                    if (!vigencia.equals(""))
                        throw new RuntimeException(descripcion + vigencia);
                    else
                        throw new RuntimeException(descripcion);
                }
            this.validarExisteRegistroEndosoItem(endosoItem);

            // valido que el vehiculo no exista en un siniestro con perdida total

            if (endosoItem != null && endosoItem.getItemId() != null) {
                this.validarItemReclamoPerdidaTotal(endosoItem.getItemId());
                this.validarItemVehiculosBloqueados(vehiculoEntity.getChasis(), vehiculoEntity.getMotor(),
                        vehiculoEntity.getPlacas());
            }

            // GRABO EL NUMERO DE PANFLETO PARA EL CASO DE CONTENEDORES
            if (null != endosoItem.getEndoso().getPoliza() &&
                    endosoItem.getEndoso().getPoliza().getPadreId() != null) {
                Poliza polizaPadre = new Poliza();
                Optional<Poliza> polizaOp1 = polizaRepository.getPolizaById(
                        endosoItem.getEndoso().getPoliza().getPadreId());

                if (polizaOp1.isPresent())
                    polizaPadre = polizaOp.get();
                if (polizaPadre.getTipoContenedor().equals("A"))
                    endosoItem.setNumeroItem(noPanfleto);
            }
            // modificar/guardar
            endosoItem = endosoItemDbRepository.saveAndFlush(endosoItem);

            if (plantillaId != null && !plantillaId.equals("-1")) {
                this.grabaPlantilla(endosoItem, usuarioActualiza);
            } else {
                // ****** AUMENTADO PARA CUANDO CAMBIAN LOS PLANES EL 03/05/2005 VHBV (Copia de
                // EndosoItemAPBdD)*****
                // RECUPERO EN ENDOSOITEM PARA GRABAR LAS COBERTURAS
                endosoItemId = endosoItem.getId();
                if ((productoId != null && !productoId.equals("-1") && !productoId.equals("-2") && endItemProdList.size() > 0)) {
                    for (EndosoItemProducto eip : endItemProdList) {
                        if (!eip.getPlanid().equals("-1")) {// && seleccion.equals("S")
                            this.grabaEndosoItemProducto(endosoItemId, eip, usuarioActualiza);
                            this.grabaCoberturasProducto(endosoItemId, eip, usuarioActualiza);
                        }
                    }

                    this.grabaDeduciblesEndosoItem(endosoItem, productoId, usuarioActualiza);
                } else {//no productoid
                    coberturasRepository.grabaCoberturasGenerales(usuarioActualiza, endosoItem);
                }
            }

            String clienteEntidad = endosoItem.getEndoso().getCliente().getEntidad().getId();
            if (!this.tieneConductor(vehiculoEntity))
                this.grabaConductor(clienteEntidad, vehiculoEntity, usuarioActualiza);

            this.grabaBeneficiarioGeneral(clienteEntidad, usuarioActualiza, endosoItem.getId());
            this.grabaAseguradoGeneral(clienteEntidad, usuarioActualiza, endosoItem.getId());

            if (endosoItem != null && endosoItem.getItemId() != null) {
                this.validarItemReclamoPerdidaTotal(endosoItem.getItemId());
                this.validarItemVehiculosBloqueados(vehiculoEntity.getChasis(), vehiculoEntity.getMotor(),
                        vehiculoEntity.getPlacas());
            }

            this.recalculaValores(endosoItem);
            return endosoItem;
        }


    }

    public boolean noExisteEndosoItemProducto(String endosoItemId, String configProductoId, String conjuntoId, String planId) {
        boolean noExisteEIP = true;
        boolean endItProdList = endosoItemProductoRepository.existsEndosoItemProductoByEndosoitemidAndConfigProductoidAndConjuntoidAndPlanid(endosoItemId, configProductoId, conjuntoId, planId);
        if (!endItProdList) {
            noExisteEIP = false;
        }
        return noExisteEIP;
    }

    public boolean itemVigenteVehiculos(String chasis, Timestamp fecha, String ramoId) {

        List<Object[]> result = endosoItemDbRepository.getDistinctByChasisAndFechaVigencia(chasis.trim(), fecha).orElse(new ArrayList<>());

        boolean resultado = false;
        String endosoItemId;
        EndosoItem endosoItem;

        for (Object[] respuesta : result) {
            endosoItemId = (String) respuesta[0];
            Optional<EndosoItem> endosoItemOp = endosoItemDbRepository.getEndosoItemById(endosoItemId);

            if (endosoItemOp.isPresent())
                endosoItem = endosoItemOp.get();
            else
                throw new RuntimeException("itemVigenteVehiculos > endosoItem no encontrado: " + endosoItemId);


            EndosoEstado endosoEstado = endosoEstadoRepository.getEndosoEstadoByEndosoIdAndEsActual(endosoItem.getEndoso().getId(),
                            Boolean.TRUE).orElse(new ArrayList<>())
                    .stream()
                    .findFirst()
                    .orElse(null);

            String tipoEndoso = endosoItem.getEndoso().getTipoEndoso().getNemotecnico();
            String estadoEnd = endosoEstado.getId();//178452671 //ESTADO ENDOSO 11

            if (ramoId == null) {//si siempre
                if (!endosoItem.getEndoso().getPoliza().getRamo().getNombre().equals(UtilService.RAMO_NOMBRE_SOAT)) {// EVALDEZ A\u00D1ADIDO	// ESTADO	// 15	// PARA// FACTURADOS,
                    if (estadoEnd.equals(EndosoEstadoId.EMITIDO) || estadoEnd.equals(EndosoEstadoId.PAGADO)
                            || estadoEnd.equals(EndosoEstadoId.FACTURADO)) {
                        if (tipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)
                                || tipoEndoso.equals(TipoEndosoNemotecnico.CANCELACION_POLIZA)) {
                            resultado = false;
                        } else {
                            if (tipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                                    || tipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION)) {
                                resultado = true;
                            }
                        }
                    }
                }
            } else {
                if (endosoItem.getEndoso().getPoliza().getRamo().getId().equals(ramoId)) {
                    if (estadoEnd.equals(EndosoEstadoId.EMITIDO) || estadoEnd.equals(EndosoEstadoId.PAGADO)
                            || estadoEnd.equals(EndosoEstadoId.FACTURADO)) {
                        if (tipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)
                                || tipoEndoso.equals(TipoEndosoNemotecnico.CANCELACION_POLIZA)) {
                            resultado = false;
                        } else {
                            if (tipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                                    || tipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION)) {
                                resultado = true;
                            }
                        }
                    }
                }
            }
        }
        return resultado;

    }

    public String itemVigenteVigenciaVehiculos(String chasis, Timestamp fecha,
                                               String ramoId) {


        List<Object[]> result = endosoItemDbRepository.getDistinctVigenciasHastaByChasisAndFechaVigencia(chasis.trim(), fecha).orElse(new ArrayList<>());

        String resultado = "";
        String endosoItemId;
        EndosoItem endosoItem;

        for (Object[] respuesta : result) {
            endosoItemId = (String) respuesta[0];
            Optional<EndosoItem> endosoItemOp = endosoItemDbRepository.getEndosoItemById(endosoItemId);
            endosoItem = endosoItemOp.orElse(null);

            EndosoEstado endosoEstado = endosoEstadoRepository.getEndosoEstadoByEndosoIdAndEsActual(endosoItem.getEndoso().getId(),
                            Boolean.TRUE).orElse(new ArrayList<>()).stream()
                    .findFirst().orElse(null);

            String tipoEndoso = endosoItem.getEndoso().getTipoEndoso().getNemotecnico();
            String estadoEnd = endosoEstado.getId();

            if (ramoId == null) {
                if (!endosoItem.getEndoso().getPoliza().getRamo().getNombre().equals(UtilService.RAMO_NOMBRE_SOAT)) {// EVALDEZ
                    // A\u00D1ADIDO
                    // ESTADO
                    // 15
                    // PARA
                    // FACTURADOS,
                    if (estadoEnd.equals(EndosoEstadoId.EMITIDO) || estadoEnd.equals(EndosoEstadoId.PAGADO)
                            || estadoEnd.equals(EndosoEstadoId.FACTURADO)) {
                        if (tipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)
                                || tipoEndoso.equals(TipoEndosoNemotecnico.CANCELACION_POLIZA)) {
                            resultado = "";
                        } else {
                            if (tipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                                    || tipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION)) {
                                resultado = " Desde: " + endosoItem.getEndoso().getVigenciaDesde() + " Hasta: "
                                        + endosoItem.getEndoso().getVigenciaHasta();
                            }
                        }
                    }
                }
            } else {
                if (endosoItem.getEndoso().getPoliza().getRamo().getId().equals(ramoId)) {
                    if (estadoEnd.equals(EndosoEstadoId.EMITIDO) || estadoEnd.equals(EndosoEstadoId.PAGADO)
                            || estadoEnd.equals(EndosoEstadoId.FACTURADO)) {
                        if (tipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)
                                || tipoEndoso.equals(TipoEndosoNemotecnico.CANCELACION_POLIZA)) {
                            resultado = "";
                        } else {
                            if (tipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                                    || tipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION)) {
                                resultado = " Desde: " + endosoItem.getEndoso().getVigenciaDesde() + " Hasta: "
                                        + endosoItem.getEndoso().getVigenciaHasta();
                            }
                        }
                    }
                }
            }

        }
        return resultado;
    }

    public void validarItemReclamoPerdidaTotal(String itemId) {

        Vehiculo v = new Vehiculo();
        Optional<Vehiculo> vehiculoOp = vehiculoRepository.getVehiculoById(itemId);
        if (vehiculoOp.isPresent())
            v = vehiculoOp.get();

        String chasis = v.getChasis();

        StringBuilder reclamos = new StringBuilder();
        List<Object[]> result = reclamoRepository.getDistinctNumeroReclamoByChasis(chasis).orElse(new ArrayList<>());

        for (Object[] respuesta : result) {
            if (respuesta[0] != null)
                reclamos.append((String) respuesta[0]);
        }
        if (!reclamos.toString().isEmpty())
            throw new RuntimeException(
                    "El item se encuentra en un reclamo con perdida total! No se puede emitir. Reclamos: " + reclamos
                            + ". Item Vehiculo Id: " + itemId);

    }

    public void validarItemVehiculosBloqueados(String chasis, String motor, String placa) {


        List<VehiculosBloqueado> result = vehiculosBloqueadoRepository.getVehiculosBloqueadoByChasisAndMotorAndPlaca(chasis.trim(), motor.trim(), placa.trim()).orElse(new ArrayList<>());

        if (!result.isEmpty())
            throw new RuntimeException(
                    "El vehiculo se encuentra bloqueado, por favor contactarse con el departamento de Control Interno");

    }

    public void grabaPlantilla(EndosoItem endosoItem, String usuarioActualiza) {

        grabaDeduciblesPlantilla(endosoItem, usuarioActualiza);
        String plantillaId = endosoItem.getPlantillaId();

        try (Stream<List<PlantillaCobertura>> ignored = plantillaCoberturaRepository.getPlantillaCoberturaByPlantillaid(plantillaId).stream()) {
            List<PlantillaCobertura> result = plantillaCoberturaRepository.getPlantillaCoberturaByPlantillaid(plantillaId).orElse(new ArrayList<>());

            for (PlantillaCobertura plantillaCobertura : result) {

                EndosoItemCobertura endosoItemCobertura = new EndosoItemCobertura();
                endosoItemCobertura.setAfectaGrupo(plantillaCobertura.getAfectagrupo());
                endosoItemCobertura.setAfectaValorAsegurado(plantillaCobertura.getAfectavalorasegurado());
                endosoItemCobertura.setAfectaPrima(Boolean.parseBoolean(plantillaCobertura.getAfectaprima()));
                endosoItemCobertura.setOrden(new BigDecimal(plantillaCobertura.getOrden()));
                endosoItemCobertura.setEsTodoRiesgo(plantillaCobertura.getEstodoriesgo());
                endosoItemCobertura.setSeccion(plantillaCobertura.getSeccion());
                endosoItemCobertura.setTexto(plantillaCobertura.getTexto());
                endosoItemCobertura.setLimite(plantillaCobertura.getLimite());
                endosoItemCobertura.setLimiteCobertura(plantillaCobertura.getLimitecobertura());
                endosoItemCobertura.setEndosoItem(endosoItem);
                endosoItemCobertura.setCoberturaId(plantillaCobertura.getCoberturaid());
                endosoItemCobertura.setTasaCobertura(plantillaCobertura.getTasa());
                endosoItemCobertura.setCoberturaGeneralId(plantillaCobertura.getId());
                endosoItemCobertura.setUsuarioActualiza(usuarioActualiza);
                endosoItemCobertura.setFechaActualiza(Util.actualDateTime().toLocalDateTime());
                endosoItemCobertura.setProveedorServicioId(plantillaCobertura.getProveedorservicioid());
                endosoItemCobertura.setEsPrimaFija(plantillaCobertura.getEsprimafija());
                EndosoAgente agenteLiderId = coberturasRepository
                        .getEndosoAgenteByEndosoIdAndTipoAgente(endosoItem.getEndoso().getId(), TipoAgente.LIDER);

                BigDecimal porcentajeComisionNormal = coberturasRepository
                        .getPorcentajeComisionEIC(agenteLiderId != null ? agenteLiderId.getId() : null,
                                plantillaCobertura.getCoberturaid());

                endosoItemCobertura.setPorcentajeComisionNormal(porcentajeComisionNormal);

                endosoItemCobertura
                        .setMontoCobertura(coberturasRepository.calcularMonto(endosoItemCobertura));
                if (endosoItemCobertura.getAfectaPrima()) {
                    coberturasRepository.calculaPrimaCoberturaNeta(BigDecimal.ZERO, endosoItemCobertura, false, BigDecimal.ZERO.intValue(), null);
                }
                // Valores obligatorios por defecto a setearse
                endosoItemCobertura.setAplicaDeducible(false);
                endosoItemCobertura = endosoItemCoberturaDbRepository.saveAndFlush(endosoItemCobertura);
                coberturasRepository.copiaCoberturaTexto(usuarioActualiza, endosoItemCobertura);
                grabaDeduciblesPlantillaCobertura(plantillaId, endosoItemCobertura);
            }
        }
    }

    public void recalculaValorPrimaNeta(EndosoItem endosoItem2) {
        BigDecimal total = BigDecimal.ZERO;
        // COMENTO POR PETICION DE PATRICIO CASTELLANOS
        // if (getEndoso().getPoliza().getRamo().getNombreNemotecnico().equals("IN")) {
        // total = calcularValorPrimaNetaListaTotal(pm);
        // ajustaValorPrimaAliadas(pm);
        // ajustaValorPrimaBasicas(pm);
        // total = getValorPNSC(pm);
        // } else {
        total = calcularValorPrimaNetaPm(endosoItem2);
        // }
        EndosoItem ei = new EndosoItem();
        ei = endosoItem2;
        ei.setValorPrimaNeta(total.setScale(2, RoundingMode.HALF_UP));
        endosoItemDbRepository.updateEndosoItemById(ei, ei.getId());
    }

    public void recalculaValorAsegurado(EndosoItem endosoItem) {

        BigDecimal total = coberturasRepository.calcularValorAseguradoTotal(endosoItem.getId(), true, null);
        // ACTUALIZO EL VALOR DEL ITEM EN LA TABLA ITEM PARA EL CASO DE MASIVOS
        // 06/05/2005
        String nemoRamo = endosoItem.getEndoso().getPoliza().getRamo().getNombreNemotecnico();
        if (nemoRamo.equals(RamoNemotecnico.ACCIDENTES_PERSONALES)
                || nemoRamo.equals(RamoNemotecnico.ACCIDENTES_PERSONALES_MASIVOS)
                || nemoRamo.equals(RamoNemotecnico.ACCIDENTES_PERSONALES_GUARDIAS)) {
            Item item = itemRepository.getItemById(endosoItem.getItemId()).orElse(null);
            item.setValor(total.setScale(2, RoundingMode.HALF_UP));
            itemRepository.updateItemById(item, item.getId());
        }

    }

    public void eliminaEndosoItemProducto(String endosoItemId) {

        List<EndosoItemProducto> endItProdList = endosoItemProductoRepository.getEndosoItemProductoByEndosoitemid(endosoItemId).orElse(new ArrayList<>());

        if (!endItProdList.isEmpty())
            for (EndosoItemProducto eip : endItProdList)
                try {
                    endosoItemProductoRepository.deleteEndosoItemProductoById(eip.getId());
                } catch (Exception ignored) {

                }

    }

    public void grabaEndosoItemProducto(String endosoItemId, EndosoItemProducto endItProd,
                                        String usuarioActualiza) {

        Optional<ConjuntoCoberturas> conjuntoOp = conjuntoCoberturasRepository.getConjuntoCoberturasById(endItProd.getConjuntoid());
        if (conjuntoOp.isPresent()) {
            ConjuntoCoberturas conjuntoCoberturas = conjuntoOp.get();
            EndosoItemProducto endosoItemProducto = new EndosoItemProducto();
            endosoItemProducto.setConfigProductoid(conjuntoCoberturas.getConfigproductoid().getId());
            endosoItemProducto.setConjuntoid(conjuntoCoberturas.getId());
            endosoItemProducto.setEndosoitemid(endosoItemId);
            endosoItemProducto.setFechaactualiza(Timestamp.valueOf(Util.actualDateTime().toLocalDateTime()));
            endosoItemProducto.setUsuarioactualiza(usuarioActualiza);
            endosoItemProducto.setPlanid(endItProd.getPlanid());
            endosoItemProductoRepository.saveAndFlush(endosoItemProducto);
        } else
            throw new RuntimeException("ConjuntoCoberturas no encontrado.");
    }

    public void grabaCoberturasProducto(String endosoItemId, EndosoItemProducto endItProd,
                                        String usuarioActualiza) {

        Optional<EndosoItem> endItOp = endosoItemDbRepository.getEndosoItemById(endosoItemId);
        EndosoItem endosoItem = new EndosoItem();
        if (endItOp.isPresent())
            endosoItem = endItOp.get();
        else
            throw new RuntimeException("Metodo: grabaCoberturasProducto > EndosoItem no encontrado");

        Optional<Endoso> endOp = endosoDbRepository.getEndosoById(endosoItem.getEndoso().getId());
        Endoso endoso = new Endoso();

        if (endOp.isPresent())
            endoso = endOp.get();
        else
            throw new RuntimeException("Metodo: grabaCoberturasProducto > Endoso no encontrado");

        String ramo = endoso.getPoliza().getRamo().getNombreNemotecnico();
        String planId = endItProd.getPlanid();
        String conjuntoId = endItProd.getConjuntoid();

        List<String> result = detalleProductoRepository.getDetalleProductoByPlanIdAndConjuntoId(planId, conjuntoId).orElse(new ArrayList<>());

        String detalleProductoId = "";
        EndosoItemCobertura endosoItemCobertura = null;
        String coberturaId;
        DetalleProducto detalleProducto = null;
        Cobertura cobertura = null;

        for (String respuesta : result) {
            detalleProductoId = respuesta;
            detalleProducto = new DetalleProducto();
            Optional<DetalleProducto> detProdOp = detalleProductoRepository.getDetalleProductoById(detalleProductoId
            );

            detalleProducto = new DetalleProducto();
            if (detProdOp.isPresent())
                detalleProducto = detProdOp.get();

            coberturaId = detalleProducto.getCoberturasconjunto().getCoberturaid(); //null
            cobertura = new Cobertura();
            cobertura = coberturaRepository.getCoberturaById(coberturaId).orElse(null);
            String cobident = cobertura.getId();


            //System.out.println(cobertura);
            double valorPrima = detalleProducto.getPrima().doubleValue();
            double valorMonto = detalleProducto.getMonto().doubleValue();
            double valorTasa = detalleProducto.getTasa().doubleValue();

            endosoItemCobertura = new EndosoItemCobertura();

            if (cobertura.getEsprincipal()) {
                if (endosoItem.getValorPrimaNeta().doubleValue() > 0)
                    valorPrima = endosoItem.getValorPrimaNeta().doubleValue();
                endosoItemCobertura.setPorcentajeComisionEspecial(detalleProducto.getPorcentajeComision());
                endoso.setPorcentajeComisionAgente(detalleProducto.getPorcentajeComision());
                endosoDbRepository.updateEndoso(endoso.getId(), endoso);
            }

            endosoItemCobertura.setAfectaGrupo(cobertura.getAfectaGrupo());
            endosoItemCobertura.setProveedorServicioId(detalleProducto.getProveedorServicioId());
            endosoItemCobertura.setAfectaValorAsegurado(detalleProducto.getAfectaValorAsegurado());
            endosoItemCobertura.setAfectaPrima(detalleProducto.getAfectaPrima());
            endosoItemCobertura.setTexto(detalleProducto.getTexto());
            endosoItemCobertura.setOrden(cobertura.getOrden());
            endosoItemCobertura.setEsTodoRiesgo(cobertura.getEstodoriesgo());
            endosoItemCobertura.setSeccion(cobertura.getSeccion());
            endosoItemCobertura.setTexto(detalleProducto.getTexto());
            endosoItemCobertura.setLimite(detalleProducto.getMonto());
            endosoItemCobertura.setMontoCobertura(detalleProducto.getMonto());
            endosoItemCobertura.setEndosoItem(endosoItem);
            String polId = endosoItemCobertura.getEndosoItem().getEndoso().getPoliza().getId();
            endosoItemCobertura.setCobertura(cobertura);
            endosoItemCobertura.setTasaCobertura(detalleProducto.getTasa());
            endosoItemCobertura.setCoberturaGeneralId(cobertura.getId());
            endosoItemCobertura.setUsuarioActualiza(usuarioActualiza);
            endosoItemCobertura.setFechaActualiza(Util.actualDateTime().toLocalDateTime());

            // SOLO SI LA COBERTURA TIENE VALOR SE MARCA COMO PRIMA FIJA
            if (valorPrima != 0 && (valorMonto == 0 || valorTasa == 0)) {
                endosoItemCobertura.setEsPrimaFija(true);
            }

            // SETEO EL PORCENTAJE DE COMISION NORMAL DEL AGENTE SEGUN CONTRATO
            EndosoAgente endosoAgente = coberturasRepository
                    .getEndosoAgenteByEndosoIdAndTipoAgente(endoso.getId(), TipoAgente.LIDER);

            if (endosoAgente == null)
                throw new RuntimeException("grabaCoberturasProducto > El endoso con id: " + endoso.getId() + " no consta en ENDOSOAGENTE.");

            Double porcentajeComisionNormal = coberturasRepository
                    .porcentajeComisionEIC(endosoAgente.getAgente().getId(), coberturaId).doubleValue();
            // this.porcentajeComisionEIC(endosoAgente.getAgente().getId(),
            // coberturaId);

            endosoItemCobertura.setPorcentajeComisionNormal(BigDecimal.valueOf(porcentajeComisionNormal));

            endosoItemCobertura = endosoItemCoberturaDbRepository.saveAndFlush(endosoItemCobertura);
            // endosoItemCobertura.save();

            // se aumento para el calculo de la prima F.M. 25/11/2005 ***************

            if (detalleProducto.getMonto() == BigDecimal.ZERO) {
                // endosoItemCobertura.calculaMonto(pm);//pendiente
                BigDecimal montoCobertura = coberturasRepository.calcularMonto(endosoItemCobertura);
                endosoItemCobertura.setMontoCobertura(montoCobertura);
            }

            if (detalleProducto.getAfectaPrima() && !endosoItemCobertura.getEsPrimaFija()) {
                coberturasRepository.calculaPrimaCoberturaNeta(BigDecimal.ZERO, endosoItemCobertura, false, BigDecimal.ZERO.intValue(), null); // endosoItemCobertura.calculaPrima(0,
                // pm);//pendiente
            } else {
                endosoItemCobertura.setPrimaCoberturaNeta(BigDecimal.valueOf(valorPrima));
            }
            if (detalleProducto.getAfectaPrima()
                    && ramo.equals(RamoNemotecnico.INCENDIO_MASIVOS_NUEVOS_NEGOCIOS)) {
                double primaV = endosoItem.getValorPrimaNeta().doubleValue() / 2d;
                endosoItemCobertura.setPrimaCoberturaNeta(BigDecimal.valueOf(primaV));
            }
            if (detalleProducto.getAfectaPrima()
                    && ramo.equals(RamoNemotecnico.ACCIDENTES_PERSONALES_NUEVOS_NEGOCIOS)) {
                endosoItemCobertura.setPrimaCoberturaNeta(endosoItem.getValorPrimaNeta());
            }
            endosoItemCobertura = endosoItemCoberturaDbRepository.saveAndFlush(endosoItemCobertura);
            // endosoItemCobertura.save();
            this.grabaDeduciblesEndosoItemCobertura(endosoItemCobertura, detalleProductoId, usuarioActualiza);

            try {
                coberturasRepository.copiaCoberturaTexto(usuarioActualiza, endosoItemCobertura);
            } catch (Exception e) {
                // To change body of catch statement use File | Settings | File Templates.
            }

        }

    }

    public void grabaDeduciblesEndosoItem(EndosoItem endosoItem, String configProductoId,
                                          String usuarioActualiza) {
        List<DeducibleProducto> deducibles = deducibleProductoRepository.getDeducibleProductoByConfigproductoid(configProductoId).orElse(new ArrayList<>());


        if (deducibles.size() > 0) {
            for (DeducibleProducto deducibleProducto : deducibles) {
                EndosoDeducible endosoDeducible = new EndosoDeducible();
                endosoDeducible.setTexto(deducibleProducto.getDeducible().getTexto());
                endosoDeducible.setClaseid(endosoItem.getId());
                endosoDeducible.setCriterioDeducibleId(CriterioDeducibleId.ESPECIFICO_ITEM);
                endosoDeducible.setEndosoid(endosoItem.getEndoso().getId());
                endosoDeducible.setUsuarioactualiza(usuarioActualiza);
                endosoDeducible.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
                endosoDeducible = endosoDeducibleRepository.saveAndFlush(endosoDeducible);


                List<DetalleDeducible> detallesDeducible = detalleDeducibleRepository.getDetalleDeducibleByDeducibleid(deducibleProducto.getId()).orElse(new ArrayList<>());

                if (!detallesDeducible.isEmpty()) {
                    for (DetalleDeducible detalleDeducible : detallesDeducible) {
                        EndosoDeducibleTipo endosoDeducibleTipo = new EndosoDeducibleTipo();
                        endosoDeducibleTipo.setTexto("Ninguno");
                        endosoDeducibleTipo.setEndosoDeducibleId(endosoDeducible.getId());
                        endosoDeducibleTipo.setValor(detalleDeducible.getValor());
                        endosoDeducibleTipo.setTipoDeducibleId(detalleDeducible.getTipodeducibleid());
                        endosoDeducibleTipo.setUsuarioactualiza(usuarioActualiza);
                        endosoDeducibleTipo.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
                        endosoDeducibleTipoRepository.saveAndFlush(endosoDeducibleTipo);
                    }
                }
            }
        }
    }

    public void recalculaCoberturasEspecificas(EndosoItem endosoItem) {

        BigDecimal totalValorAsegurado = BigDecimal.ZERO;
        BigDecimal totalValorPrimaNeta = BigDecimal.ZERO;
        String endosoItemId = endosoItem.getId();

        try (Stream<List<EndosoItemCobertura>> ignored = endosoItemCoberturaDbRepository.getEndosoItemCoberturaByEndosoItemId(endosoItemId
        ).stream()) {
            List<EndosoItemCobertura> result = endosoItemCoberturaDbRepository.getEndosoItemCoberturaByEndosoItemId(endosoItemId
            ).orElse(new ArrayList<>());
            for (EndosoItemCobertura endosoItemCobertura : result) {
                // CALCULA EL VALOR ASEGURADO PARA CUANDO SE GRABA EL ENDOSOITEM
                endosoItemCobertura
                        .setMontoCobertura(coberturasRepository.calcularMonto(endosoItemCobertura));
//                    String grupoCobertura = endosoItemCobertura.getCobertura().getGrupoCobertura().getNombre();
                // sumo el valor asegurado de la Cobertura para totalizar al Item
                if (endosoItemCobertura.getAfectaPrima()) {
                    // COMENTO POR PETICION DE PATRICIO CASTELLANOS
                    coberturasRepository.calculaPrimaCoberturaNeta(BigDecimal.ZERO, endosoItemCobertura, false, BigDecimal.ZERO.intValue(), null);
                }
                endosoItemCoberturaDbRepository.updateEndosoItemCoberturaById(endosoItemCobertura, endosoItemCobertura.getId());
                // endosoItemCobertura.retrieve();
                if (endosoItemCobertura.getAfectaValorAsegurado()) {
                    totalValorAsegurado = totalValorAsegurado.add(endosoItemCobertura.getMontoCobertura());
                }
                // sumo el valor de Prima de EndosoItem
                if (endosoItemCobertura.getAfectaPrima()) {
                    totalValorPrimaNeta = totalValorPrimaNeta.add(endosoItemCobertura.getPrimaCoberturaNeta());
                }
            }
        }

    }

    public void recalculaValores(EndosoItem endosoItem) {

        endosoItem.setValorAsegurado(
                coberturasRepository.calcularValorAseguradoTotal(endosoItem.getId(), true, null));
        logger.debug("endosoItem.getValorAsegurado(): " + endosoItem.getValorAsegurado());
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal valorTotal;
        BigDecimal valorPrimaVida;
        String nemoRamo = endosoItem.getEndoso().getPoliza().getRamo().getNombreNemotecnico();
        String tipoEndoso = endosoItem.getEndoso().getTipoEndoso().getNemotecnico();

        // COMENTO SOLO PARA VIDA Y ACCIDENTES PERSONALES
        if ((nemoRamo.equals(RamoNemotecnico.ACCIDENTES_PERSONALES)
                || nemoRamo.equals(RamoNemotecnico.ACCIDENTES_PERSONALES_MASIVOS)
                || nemoRamo.equals(RamoNemotecnico.ACCIDENTES_PERSONALES_GUARDIAS)
                || nemoRamo.equals(RamoNemotecnico.VI) || nemoRamo.equals(RamoNemotecnico.VG)
                || nemoRamo.equals(RamoNemotecnico.AM) || nemoRamo.equals(RamoNemotecnico.CV))
                && (tipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                || tipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION))
                && endosoItem.getEndoso().getTipoItem().getEsGrupal()
                && endosoItem.getEndoso().getTipoItem().getNombre().equals(TipoItemNombre.BLANKET)) { // M.A.T.F// Valido// solo// blanket// para// diferente// de// masivos
            valorTotal = calcularValorAseguradoTotalAPVI(endosoItem);
            valorPrimaVida = calcularValorVidaTotalAPVI(endosoItem, "4000");
            Optional<Blanket> blanketOp = blanketRepository.getBlanketById(endosoItem.getItemId());
            Blanket bk = new Blanket();
            if (blanketOp.isPresent()) {
                bk = blanketOp.get();
            }

            bk.setValorUnitario(valorTotal);
            bk.setValorTotal(valorTotal.multiply(new BigDecimal(bk.getCantidad())));
            blanketRepository.updateBlanketById(bk, bk.getId());
            actualizaValoresTrabajadores(endosoItem.getId(), valorTotal, valorPrimaVida);
            endosoItem.setValorItem(
                    coberturasRepository.calcularValorAseguradoTotal(endosoItem.getId(), true, null));

        }
        if ((nemoRamo.equals(RamoNemotecnico.VG) || nemoRamo.equals(RamoNemotecnico.AM))
                && (tipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO)
                || tipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO))) {
            valorTotal = calcularValorAseguradoTotalAPVI(endosoItem);
            valorPrimaVida = calcularValorVidaTotalAPVI(endosoItem, "4000");
            if (tipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO))
                actualizaValoresTrabajadores(endosoItem.getId(), valorTotal, valorPrimaVida);
            endosoItem.setValorUnitario(calcularValorAseguradoTotalAPVI(endosoItem).setScale(2, RoundingMode.HALF_UP));
            endosoItemDbRepository.updateEndosoItemById(endosoItem, endosoItem.getId());
        }

        total = getValorPNSC(endosoItem);
        endosoItem.setValorPrimaNeta(total.setScale(2, RoundingMode.HALF_UP));
        // setValorItem(calcularValorAseguradoTotal(pm));
        endosoItemDbRepository.updateEndosoItemById(endosoItem, endosoItem.getId());

    }


    public String valorTotalItems(String endosoId) {
        String valor;
        try {
            BigDecimal result = endosoItemDbRepository.countByEndosoId(endosoId);
            if (result != null) {
                valor = result.toString();
            } else {
                return BigDecimal.ZERO.toString();
            }
        } catch (NoResultException e) {
            return BigDecimal.ZERO.toString();
        }
        return valor;
    }

    public void validarExisteRegistroVehiculo(Vehiculo vehiculo) {
        //String vehiculoId = vehiculo.getId();
        String chasis = vehiculo.getChasis();
        String motor = vehiculo.getMotor();

        List<Vehiculo> vehiculoList = vehiculoRepository.getVehiculoByChasisAndMotor(chasis, motor).orElse(new ArrayList<>());


        if (!vehiculoList.isEmpty())
            throw new RuntimeException("El chasis o motor del vehiculo ya existe.");

    }

    public boolean itemVigente(String itemId, Timestamp fecha, String ramoId) {

        //    	String fechaF = Util.dateTimeToString(fecha);
//		System.out.println(fecha);


        List<Object[]> result = endosoItemDbRepository.getDistinctByItemIdAndFechaVigencia(itemId.trim(), fecha).orElse(new ArrayList<>());

        boolean resultado = false;
        EndosoItem endosoItem = new EndosoItem();
        String endosoItemId;
        for (Object[] respuesta : result) {
            endosoItemId = (String) respuesta[0];
            Optional<EndosoItem> endosoItemOp = endosoItemDbRepository.getEndosoItemById(endosoItemId);
            if (endosoItemOp.isPresent()) {
                endosoItem = endosoItemOp.get();
            }

            Optional<EndosoEstado> endosoEstado = endosoEstadoRepository.getEndosoEstadoByEndosoIdAndEsActual(endosoItem.getEndoso().getId(),
                            Boolean.TRUE).orElse(new ArrayList<>())
                    .stream()
                    .findFirst();

            String tipoEndoso = endosoItem.getEndoso().getTipoEndoso().getNemotecnico();
            String estadoEnd = "";
            endosoEstado.ifPresent(EndosoEstado::getId);
            if (ramoId == null) {
                if (!endosoItem.getEndoso().getPoliza().getRamo().getNombre().equals(UtilService.RAMO_NOMBRE_SOAT)) {
                    if (estadoEnd.equals(EndosoEstadoId.EMITIDO) || estadoEnd.equals(EndosoEstadoId.PAGADO)
                            || estadoEnd.equals(EndosoEstadoId.FACTURADO)) {
                        if (tipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)
                                || tipoEndoso.equals(TipoEndosoNemotecnico.CANCELACION_POLIZA)) {
                            resultado = false;
                        } else {
                            if (tipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                                    || tipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION)) {
                                resultado = true;
                            }
                        }
                    }
                }
            } else {
                if (endosoItem.getEndoso().getPoliza().getRamo().getId().equals(ramoId)) {
                    if (estadoEnd.equals(EndosoEstadoId.EMITIDO) || estadoEnd.equals(EndosoEstadoId.PAGADO)
                            || estadoEnd.equals(EndosoEstadoId.FACTURADO)) {
                        if (tipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)
                                || tipoEndoso.equals(TipoEndosoNemotecnico.CANCELACION_POLIZA)) {
                            resultado = false;
                        } else {
                            if (tipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                                    || tipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION)) {
                                resultado = true;
                            }
                        }
                    }
                }
            }
        }
        return resultado;
    }

    public String itemVigenteVigencia(String itemId, Timestamp fecha, String ramoId) {

        //    	String fechaF = Util.dateTimeToString(fecha);


        List<Object[]> result = endosoItemDbRepository.getDistinctHastaByItemIdAndFechaVigencia(itemId.trim(), fecha).orElse(new ArrayList<>());
        String resultado = "";
        String endosoItemId;
        EndosoItem endosoItem = new EndosoItem();
        for (Object[] respuesta : result) {
            endosoItemId = (String) respuesta[0];
            Optional<EndosoItem> endosoItemOp = endosoItemDbRepository.getEndosoItemById(endosoItemId);
            if (endosoItemOp.isPresent()) {
                endosoItem = endosoItemOp.get();
            }
            EndosoEstado endosoEstado = new EndosoEstado();
            if (endosoEstadoRepository.getEndosoEstadoByEndosoIdAndEsActual(endosoItem.getEndoso().getId(),
                            Boolean.TRUE)
                    .orElse(new ArrayList<>())
                    .stream()
                    .findFirst().isPresent()) {
                endosoEstado = endosoEstadoRepository.getEndosoEstadoByEndosoIdAndEsActual(endosoItem.getEndoso().getId(),
                                Boolean.TRUE)
                        .orElse(new ArrayList<>())
                        .stream()
                        .findFirst().get();
            }


            String tipoEndoso = endosoItem.getEndoso().getTipoEndoso().getNemotecnico();
            String estadoEnd = endosoEstado.getId();
            // System.out.println("Estado:" + endosoItem.getEndoso().getEstadoId(pm));
            if (ramoId == null) {
                if (!endosoItem.getEndoso().getPoliza().getRamo().getNombre().equals(UtilService.RAMO_NOMBRE_SOAT)) {// EVALDEZ
                    // AÑADIDO
                    // ESTADO
                    // 15
                    // PARA
                    // FACTURADOS,
                    if (estadoEnd.equals(EndosoEstadoId.EMITIDO) || estadoEnd.equals(EndosoEstadoId.PAGADO)
                            || estadoEnd.equals(EndosoEstadoId.FACTURADO)) {
                        if (tipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)
                                || tipoEndoso.equals(TipoEndosoNemotecnico.CANCELACION_POLIZA)) {
                            resultado = "";
                        } else {
                            if (tipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                                    || tipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION)) {
                                resultado = " Desde: " + endosoItem.getEndoso().getVigenciaDesde() + " Hasta: "
                                        + endosoItem.getEndoso().getVigenciaHasta();
                            }
                        }
                    }
                }
            } else {
                if (endosoItem.getEndoso().getPoliza().getRamo().getId().equals(ramoId)) {
                    if (estadoEnd.equals(EndosoEstadoId.EMITIDO) || estadoEnd.equals(EndosoEstadoId.PAGADO)
                            || estadoEnd.equals(EndosoEstadoId.FACTURADO)) {
                        if (tipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)
                                || tipoEndoso.equals(TipoEndosoNemotecnico.CANCELACION_POLIZA)) {
                            resultado = "";
                        } else {
                            if (tipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                                    || tipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION)) {
                                resultado = " Desde: " + endosoItem.getEndoso().getVigenciaDesde() + " Hasta: "
                                        + endosoItem.getEndoso().getVigenciaHasta();
                            }
                        }
                    }
                }
            }
        }
        return resultado;
    }

    public void validarExisteRegistroEndosoItem(EndosoItem endosoItem) {


        List<EndosoItem> endosoItemList = endosoItemDbRepository.getEndosoItemByIdNotContainingAndEndosoIdAndItemId(endosoItem.getId(), endosoItem.getEndoso().getId(), endosoItem.getItemId()).orElse(new ArrayList<>());
        if (!endosoItemList.isEmpty()) {
            throw new RuntimeException("El registro endoso item está duplicado");
        }
    }

    public boolean tieneConductor(Vehiculo vehiculo) {
        String vehiculoId = vehiculo.getId();
        List<Conductor> conductorList = conductorRepository.getConductorByVehiculoid(vehiculoId).orElse(new ArrayList<>());
        return !conductorList.isEmpty();
    }

    public void grabaConductor(String clienteEntidadId, Vehiculo vehiculo, String usuarioActualiza) {

        Optional<Entidad> enOptional = entidadRepository.getEntidadById(clienteEntidadId);
        Entidad entidad = new Entidad();
        if (enOptional.isPresent())
            entidad = enOptional.get();

        if (entidad.getTipoid().equals("c")) {
            Conductor conductor = new Conductor();
            conductor.setVehiculoid(vehiculo.getId());
            conductor.setEntidadid(clienteEntidadId);
            conductor.setUsuarioactualiza(usuarioActualiza);
            conductor.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
            conductorRepository.saveAndFlush(conductor);
        }
    }

    public void grabaBeneficiarioGeneral(String clienteEntidadId, String usuarioActualiza,
                                         String endosoItemId) {

        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setEndosoitemid(endosoItemId);
        beneficiario.setEntidadid(clienteEntidadId);
        beneficiario.setParentescoold(ParentescoId.TITULAR);
        beneficiario.setProporcion(new BigDecimal(UtilService.PROPORCION_BENEFICIARIO_100));
        beneficiario.setEsactivo(true);
        beneficiario.setUsuarioactualiza(usuarioActualiza);
        beneficiario.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
        beneficiario.setEsacreedor(false);
        beneficiario.setParentescoold(ec.com.zurich.suscription.util.Beneficiario.PARENTESCO_OLD);
        beneficiarioRepository.saveAndFlush(beneficiario);

    }

    public void grabaAseguradoGeneral(String clienteEntidadId, String usuarioActualiza,
                                      String endosoItemId) {

        Asegurado asegurado = new Asegurado();
        asegurado.setEndosoitemid(endosoItemId);
        asegurado.setEntidadid(clienteEntidadId);
        asegurado.setUsuarioactualiza(usuarioActualiza);
        asegurado.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
        aseguradoRepository.saveAndFlush(asegurado);


    }


    public void grabaDeduciblesPlantilla(EndosoItem endosoItem, String usuarioActualiza) {


        try (Stream<List<PlantillaDeducible>> ignored1 = plantillaDeducibleRepository.getPlantillaDeducibleByPlantillaidAndCriteriodeducibleid(endosoItem.getPlantillaId(),
                CriterioDeducibleId.GENERAL_PLANTILLA).stream()) {
            // Ordena por Id
            List<PlantillaDeducible> lista = plantillaDeducibleRepository.getPlantillaDeducibleByPlantillaidAndCriteriodeducibleid(endosoItem.getPlantillaId(),
                    CriterioDeducibleId.GENERAL_PLANTILLA).orElse(new ArrayList<>());
            List<PlantillaDeducible> resultDeducible = lista.stream()
                    .sorted(Comparator.comparing(PlantillaDeducible::getId))
                    .toList();
            for (PlantillaDeducible plantillaDeducible : resultDeducible) {
                EndosoDeducible endosoDeducible = new EndosoDeducible();
                endosoDeducible.setTexto(plantillaDeducible.getTexto());
                endosoDeducible.setClaseid(endosoItem.getId());
                endosoDeducible.setCriterioDeducibleId(CriterioDeducibleId.ESPECIFICO_ITEM);
                endosoDeducible.setEndosoid(endosoItem.getEndoso().getId());
                endosoDeducible.setUsuarioactualiza(usuarioActualiza);
                endosoDeducible.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
                endosoDeducible = endosoDeducibleRepository.saveAndFlush(endosoDeducible);
                String plantillaDeducibleId = plantillaDeducible.getId();

                try (Stream<List<PlantillaDeducibleTipo>> ignored = plantillaDeducibleTipoRepository.getPlantillaDeducibleTipoByPlantillaDeducibleId(plantillaDeducibleId).stream()) {
                    List<PlantillaDeducibleTipo> resultDeducibleTipo = plantillaDeducibleTipoRepository.getPlantillaDeducibleTipoByPlantillaDeducibleId(plantillaDeducibleId).orElse(new ArrayList<>());

                    for (PlantillaDeducibleTipo plantillaDeducibleTipo : resultDeducibleTipo) {
                        EndosoDeducibleTipo endosoDeducibleTipo = new EndosoDeducibleTipo();
                        endosoDeducibleTipo.setTexto(plantillaDeducibleTipo.getTexto());
                        endosoDeducibleTipo.setEndosoDeducibleId(endosoDeducible.getId());
                        endosoDeducibleTipo.setValor(plantillaDeducibleTipo.getValor());
                        endosoDeducibleTipo.setTipoDeducibleId(plantillaDeducibleTipo.getTipoDeducibleId());
                        endosoDeducibleTipo.setUsuarioactualiza(usuarioActualiza);
                        endosoDeducibleTipo.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
                        endosoDeducibleTipoRepository.saveAndFlush(endosoDeducibleTipo);
                    }
                }
            }
        }
    }

    public void grabaDeduciblesPlantillaCobertura(String plantillaId,
                                                  EndosoItemCobertura endosoItemCobertura) {
        Optional<Cobertura> coberturaOp = coberturaRepository.getCoberturaById(endosoItemCobertura.getCobertura().getId());
        Cobertura cobertura = coberturaOp.orElseGet(Cobertura::new);

        try (Stream<List<PlantillaDeducible>> ignored = plantillaDeducibleRepository.getPlantillaDeducibleByPlantillaidAndCriteriodeducibleid(plantillaId,
                CriterioDeducibleId.COBERTURA_PLANTILLA).stream()) {
            List<PlantillaDeducible> resultDeducible = plantillaDeducibleRepository.getPlantillaDeducibleByPlantillaidAndCriteriodeducibleid(plantillaId,
                    CriterioDeducibleId.COBERTURA_PLANTILLA).orElse(new ArrayList<>());

            String nombreCobertura ;
            for (PlantillaDeducible plantillaDeducible : resultDeducible) {
                nombreCobertura = getNombreCobertura(plantillaDeducible.getClaseid());
                if (nombreCobertura.equals(cobertura.getNombre())) {
                    EndosoDeducible endosoDeducible = new EndosoDeducible();
                    endosoDeducible.setTexto(plantillaDeducible.getTexto());
                    endosoDeducible.setClaseid(endosoItemCobertura.getId());
                    endosoDeducible.setCriterioDeducibleId(CriterioDeducibleId.COBERTURA_ESPECIFICA);
                    endosoDeducible.setEndosoid(endosoItemCobertura.getEndosoItem().getEndoso().getId());
                    endosoDeducible.setFechaactualiza(endosoItemCobertura.getEndosoItem().getFechaActualiza().toLocalDate());
                    endosoDeducible.setUsuarioactualiza(endosoItemCobertura.getEndosoItem().getUsuarioActualiza());
                    endosoDeducible = endosoDeducibleRepository.saveAndFlush(endosoDeducible);

                    try (Stream<List<PlantillaDeducibleTipo>> ignored1 = plantillaDeducibleTipoRepository.getPlantillaDeducibleTipoByPlantillaDeducibleId(
                            plantillaDeducible.getId()).stream()) {
                        List<PlantillaDeducibleTipo> resultDeducibleTipo = plantillaDeducibleTipoRepository.getPlantillaDeducibleTipoByPlantillaDeducibleId(
                                plantillaDeducible.getId()).orElse(new ArrayList<>());

                        for (PlantillaDeducibleTipo plantillaDeducibleTipo : resultDeducibleTipo) {
                            EndosoDeducibleTipo endosoDeducibleTipo = new EndosoDeducibleTipo();
                            endosoDeducibleTipo.setTexto(plantillaDeducibleTipo.getTexto());
                            endosoDeducibleTipo.setEndosoDeducibleId(endosoDeducible.getId());
                            endosoDeducibleTipo.setValor(plantillaDeducibleTipo.getValor());
                            endosoDeducibleTipo.setTipoDeducibleId(plantillaDeducibleTipo.getTipoDeducibleId());
                            endosoDeducibleTipo.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
                            endosoDeducibleTipo.setUsuarioactualiza(plantillaDeducibleTipo.getUsuarioActualiza());
                             endosoDeducibleTipoRepository.saveAndFlush(endosoDeducibleTipo);
                        }
                    }
                }
            }
        }
    }

    public BigDecimal calcularValorPrimaNetaPm(EndosoItem endosoItem2) {

        BigDecimal total = BigDecimal.ZERO;
        String endosoItemId = endosoItem2.getId();
        List<EndosoItemCobertura> eics = endosoItemCoberturaDbRepository.getEndosoItemCoberturaByEndosoItemIdAndAfectaPrima(endosoItemId, Boolean.TRUE).orElse(new ArrayList<>());

        for (EndosoItemCobertura obj : eics) {
            // sumo el valor Asegurado
            if (obj.getAfectaPrima()) {
                // COMENTO POR PETICION DE PATRICIO CASTELLANOS
                // String ramo =
                // endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico();
                // if (ramo.equals("IN")) {
                // String grupoCobertura =
                // endosoItemCobertura.getCobertura().getGrupoCobertura().getNombre();
                // if (grupoCobertura.equals("INCENDIO")) {
                // float porcentajeIncendio =
                // (endosoItemCobertura.getEndosoItem().getItem().getZonaRiesgo().getPorcentaje("INCENDIO"));
                // endosoItemCobertura.calculaPrima(porcentajeIncendio, pm);
                // } else if (grupoCobertura.equals("TERREMOTO")) {
                // float porcentajeTerremoto =
                // (endosoItemCobertura.getEndosoItem().getItem().getZonaRiesgo().getPorcentaje("TERREMOTO"));
                // endosoItemCobertura.calculaPrima(porcentajeTerremoto, pm);
                // } else {
                // int numeroAliadas = getNumeroAliadas((String) getId(), "EndosoItemCobertura",
                // pm);
                // float porcentajeAliadas =
                // ((endosoItemCobertura.getEndosoItem().getItem().getZonaRiesgo().getPorcentaje("LINEAS
                // ALIADAS")) / numeroAliadas);
                // endosoItemCobertura.calculaPrima(porcentajeAliadas, pm);
                // }
                // endosoItemCobertura.save();
                // endosoItemCobertura.retrieve();
                // } else {
                coberturasRepository.calculaPrimaCoberturaNeta(new BigDecimal(0), obj, false, BigDecimal.ZERO.intValue(), null);
                endosoItemCoberturaDbRepository.updateEndosoItemCoberturaById(obj, obj.getId());
                // }
                total = total.add(obj.getPrimaCoberturaNeta());
            }
        }

        return total;

    }

    public void grabaDeduciblesEndosoItemCobertura(EndosoItemCobertura endosoItemCobertura,
                                                   String detalleProductoId, String usuarioActualiza) {

        Optional<Cobertura> coberturaOp = coberturaRepository.getCoberturaById(endosoItemCobertura.getCobertura().getId()
        );
        Cobertura cobertura ;

        if (coberturaOp.isPresent())
            cobertura = coberturaOp.get();
        else
            throw new RuntimeException("EndosoItemRepository > grabaDeduciblesEndosoItemCobertura > Cobertura no encontrada");

        List<DeducibleDetalleProductoEntity> detProdDeducibleList = deducibleDetalleProductoEntityRepository.getDeducibleDetalleProductoEntityByDetproductoid(detalleProductoId).orElse(new ArrayList<>());


        String nombreCobertura;
        if (!detProdDeducibleList.isEmpty())
            for (DeducibleDetalleProductoEntity detProdDeducible : detProdDeducibleList) {
                Optional<DetalleProducto> dProdOptional = detalleProductoRepository.getDetalleProductoById(detProdDeducible.getDetproductoid()
                );

                DetalleProducto detProducto ;
                if (dProdOptional.isPresent())
                    detProducto = dProdOptional.get();
                else
                    throw new RuntimeException("EndosoItemRepository > grabaDeduciblesEndosoItemCobertura > detProducto no encontrado");

                nombreCobertura = detProducto.getCoberturasconjunto().getCobertura().getNombre();

                if (nombreCobertura.equals(cobertura.getNombre())) {
                    EndosoDeducible endosoDeducible = new EndosoDeducible();
                    endosoDeducible.setTexto(detProdDeducible.getDeducible().getTexto());
                    endosoDeducible.setClaseid(endosoItemCobertura.getId());
                    endosoDeducible.setCriterioDeducibleId(CriterioDeducibleId.COBERTURA_ESPECIFICA);// setCriterioDeducibleId("4");
                    endosoDeducible.setEndosoid(endosoItemCobertura.getEndosoItem().getEndoso().getId());
                    endosoDeducible.setUsuarioactualiza(usuarioActualiza);
                    endosoDeducible.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
                    endosoDeducible = endosoDeducibleRepository.saveAndFlush(endosoDeducible);// endosoDeducible.save();
                    // endosoDeducible.retrieve();
                    String endosoDeducibleId = endosoDeducible.getId();


                    List<DetalleDeducible> detallesDeducibleList = detalleDeducibleRepository.getDetalleDeducibleByDeducibleid(detProdDeducible.getId()).orElse(new ArrayList<>());


                    if (!detallesDeducibleList.isEmpty()) {
                        for (DetalleDeducible detalleDeducible : detallesDeducibleList) {
                            EndosoDeducibleTipo endosoDeducibleTipo = new EndosoDeducibleTipo();
                            endosoDeducibleTipo.setTexto("Ninguno");
                            endosoDeducibleTipo.setEndosoDeducibleId(endosoDeducibleId);
                            endosoDeducibleTipo.setValor(detalleDeducible.getValor());
                            endosoDeducibleTipo.setTipoDeducibleId(detalleDeducible.getTipodeducibleid());
                            endosoDeducibleTipo.setUsuarioactualiza(usuarioActualiza);
                            endosoDeducibleTipo.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
                            endosoDeducibleTipoRepository.saveAndFlush(endosoDeducibleTipo);
                            // endosoDeducibleTipo.save();
                        }

                    }
                }

            }
    }

    public BigDecimal calcularValorAseguradoTotalAPVI(EndosoItem endosoItem) {

        BigDecimal total = BigDecimal.ZERO;
        String endosoItemId = endosoItem.getId();
        List<EndosoItemCobertura> result = endosoItemCoberturaDbRepository.getEndosoItemCoberturaByEndosoItemIdAndAfectaValorAsegurado(endosoItemId,
                true).orElse(new ArrayList<>());

        for (EndosoItemCobertura endosoItemCobertura : result) {
            /*
             * sumo el valor Asegurado
             */
            if (endosoItemCobertura.getAfectaValorAsegurado()) {
                total = total.add(endosoItemCobertura.getLimiteCobertura());
            }
        }
        return total;

    }

    public BigDecimal calcularValorVidaTotalAPVI(EndosoItem endosoItem, String coberturaId) {

            double total = 0;
            String endosoItemId = endosoItem.getId();
            // criteria.getWhereCondition().addAndCriteria(criteria.getNotEqualToCriteria("coberturaId"));
            // parametros.add("4000");

            List<EndosoItemCobertura> result = endosoItemCoberturaDbRepository.getEndosoItemCoberturaByEndosoItemIdAndAfectaPrimaAndCoberturaId(endosoItemId, true,coberturaId).orElse(new ArrayList<>());


            for (EndosoItemCobertura endosoItemCobertura : result) {
                total = total + endosoItemCobertura.getPrimaCoberturaNeta().doubleValue();
            }
            Optional<Blanket> blanketOp = blanketRepository.getBlanketById(endosoItem.getItemId());
            Blanket bk = new Blanket();
            if (blanketOp.isPresent()) {
                bk = blanketOp.get();
            }
            if (bk.getCantidad() > 0) {
                total = new BigDecimal(total / bk.getCantidad()).setScale(4, RoundingMode.HALF_UP).doubleValue();
            } else {
                throw new RuntimeException(
                        "Por Favor ingrese la cantidad en el grupo " + "para poder grabar las coberturas ");
            }
            return new BigDecimal(total);

    }

    public void actualizaValoresTrabajadores(String elEndosoItemId, BigDecimal valorAsegurado,
                                             BigDecimal valorPrima) {
        Collection<String> estados = new ArrayList<>();
        estados.add("A");
        estados.add("I");
        List<EndosoItemDetalle> endosoItemDetalleList = endosoItemDetalleRepository.getEndosoItemDetallesByEndosoitemidAndEstadoIn(elEndosoItemId, estados).orElse(new ArrayList<>());
        for (EndosoItemDetalle endosoItemDetalle : endosoItemDetalleList) {
            endosoItemDetalle.setValor(valorAsegurado);
            endosoItemDetalle.setValorprimavida(valorPrima);
            endosoItemDetalleRepository.updateEndosoItemDetalleById(endosoItemDetalle, endosoItemDetalle.getId());
        }
    }


    public BigDecimal getValorPNSC(EndosoItem endosoItem) {


        String endosoItemId = endosoItem.getId();
        BigDecimal total = BigDecimal.ZERO;
        List<EndosoItemCobertura> result = endosoItemCoberturaDbRepository.getEndosoItemCoberturaByEndosoItemIdAndAfectaPrima(endosoItemId, true).orElse(new ArrayList<>());


        for (EndosoItemCobertura endosoItemCobertura : result) {
            BigDecimal valorEA = endosoItemCobertura.getPrimaCoberturaNeta();
            total = total.add(valorEA);
        }
        return total;

    }

    public String getNombreCobertura(String coberturaId) {

        String nombre = null;
        Optional<PlantillaCobertura> plantillaCoberturaOp = plantillaCoberturaRepository.getPlantillaCoberturaById(coberturaId
        );

        if (plantillaCoberturaOp.isPresent()) {
            PlantillaCobertura plantillaCobertura = plantillaCoberturaOp.get();
            nombre = plantillaCobertura.getCobertura().getNombre();
        }
        return nombre;
    }


}
