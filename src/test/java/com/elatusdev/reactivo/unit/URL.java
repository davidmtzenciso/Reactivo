/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.unit;

/**
 *
 * @author root
 */
public final class URL {
    
    private static final String SERVIDOR = "http://192.168.0.28:8080/";
    private static final String MODULO_CATALOGO = "catalogo/";
    private static final String MODULO_RH = "recursos-humanos/";
    private static final String MODULO_CONTABILIDAD = "contabilidad/";
    private static final String MODULO_VENTAS = "ventas/";
    private static final String MODULO_SEGURIDAD = "seguridad/";
    
    private static final String ENTIDAD_CONTENEDOR = "contenedor";
    private static final String ENTIDAD_EQUIPO_DE_TRABAJO = "equipo-de-trabajo";
    private static final String ENTIDAD_GASTO = "gasto";
    private static final String ENTIDAD_INSUMO = "insumo";
    private static final String ENTIDAD_MAQUINARIA = "maquinaria";
    private static final String ENTIDAD_MATERIAL = "material";
    private static final String ENTIDAD_VEHICULO = "vehiculo";
    
    private static final String ENTIDAD_CHOFER = "chofer";
    private static final String ENTIDAD_ADMINISTRATIVO = "administrativo";
    private static final String ENTIDAD_OPERATIVO = "operativo";
    
    private static final String ENTIDAD_CLIENTE = "cliente";
    private static final String ENTIDAD_CUENTA = "cuenta";
    
    private static final String ENTIDAD_BOLETA_BASCULA = "boleta-bascula";
    private static final String ENTIDAD_ORDEN_DE_TRABAJO = "orden-de-trabajo";
    private static final String DELETE = "/";
    private static final String PLURAL_ES = "es";
    private static final String PLURAL_S = "s";
    
    /*
    ***********************************************************************
    */
    
    private static final String CATALOGO = 
            new StringBuilder().append(SERVIDOR).append(MODULO_CATALOGO).toString();
    
    private static final String CONTENEDOR =
            new StringBuilder().append(CATALOGO).append(ENTIDAD_CONTENEDOR).toString(); 
    private static final String CONTENEDOR_ID =
            new StringBuilder().append(CONTENEDOR).append(DELETE).toString();
    private static final String CONTENEDORES = 
            new StringBuilder().append(CATALOGO).append(ENTIDAD_CONTENEDOR)
                               .append(PLURAL_ES).toString();
    
    public static final String POST_CONTENEDOR = CONTENEDOR;
    public static final String POST_CONTENEDORES = CONTENEDORES;
    public static final String UPDATE_CONTENENDOR = CONTENEDOR;
    public static final String DELETE_CONTENEDOR = CONTENEDOR_ID;
    public static final String GET_CONTENEDORES = CONTENEDORES;
    public static final String GET_CONTENEDOR_BY_ID = CONTENEDOR_ID;
    
    private static final String EQUIPO_DE_TRABAJO =
            new StringBuilder().append(CATALOGO).append(ENTIDAD_EQUIPO_DE_TRABAJO).toString(); 
    private static final String EQUIPO_DE_TRABAJO_ID =
            new StringBuilder().append(EQUIPO_DE_TRABAJO).append(DELETE).toString();
    private static final String EQUIPOS_DE_TRABAJO = 
            new StringBuilder().append(CATALOGO).append("equipos-de-trabajo").toString();
    
    public static final String POST_EQUIPO_DE_TRABAJO = EQUIPO_DE_TRABAJO;
    public static final String POST_EQUIPOS_DE_TRABAJO = EQUIPOS_DE_TRABAJO;
    public static final String UPDATE_EQUIPO_DE_TRABAJO = EQUIPO_DE_TRABAJO;
    public static final String DELETE_EQUIPO_DE_TRABAJO = EQUIPO_DE_TRABAJO_ID;
    public static final String GET_EQUIPOS_DE_TRABAJO = EQUIPOS_DE_TRABAJO;
    public static final String GET_EQUIPO_DE_TRABAJO_BY_ID = EQUIPO_DE_TRABAJO_ID;
    
    private static final String GASTO =
            new StringBuilder().append(CATALOGO).append(ENTIDAD_GASTO).toString(); 
    private static final String GASTO_ID =
            new StringBuilder().append(GASTO).append(DELETE).toString();
    private static final String GASTOS = 
            new StringBuilder().append(CATALOGO).append(ENTIDAD_GASTO)
                                .append(PLURAL_S).toString();
    
    public static final String POST_GASTO = GASTO;
    public static final String POST_GASTOS = GASTOS;
    public static final String UPDATE_GASTO = GASTO;
    public static final String DELETE_GASTO = GASTO_ID;
    public static final String GET_GASTOS = GASTOS;
    public static final String GET_GASTO_BY_ID = GASTO_ID;
    
    private static final String INSUMO =
            new StringBuilder().append(CATALOGO).append(ENTIDAD_INSUMO).toString(); 
    private static final String INSUMO_ID =
            new StringBuilder().append(INSUMO).append(DELETE).toString();
    private static final String INSUMOS = 
            new StringBuilder().append(CATALOGO).append(ENTIDAD_INSUMO)
                                .append(PLURAL_S).toString();
    
    public static final String POST_INSUMO = INSUMO;
    public static final String POST_INSUMOS = INSUMOS;
    public static final String UPDATE_INSUMO = INSUMO;
    public static final String DELETE_INSUMO = INSUMO_ID;
    public static final String GET_INSUMOS = INSUMOS;
    public static final String GET_INSUMO_BY_ID = INSUMO_ID;
    
    private static final String MAQUINARIA =
            new StringBuilder().append(CATALOGO).append(ENTIDAD_MAQUINARIA).toString(); 
    private static final String MAQUINARIA_ID =
            new StringBuilder().append(INSUMO).append(DELETE).toString();
    private static final String MAQUINARIAS = 
            new StringBuilder().append(CATALOGO).append(ENTIDAD_MAQUINARIA)
                    .append(PLURAL_S).toString();
    
    public static final String POST_MAQUINARIA = MAQUINARIA;
    public static final String POST_MAQUINARIAS = MAQUINARIAS;
    public static final String UPDATE_MAQUINARIA = MAQUINARIA;
    public static final String DELETE_MAQUINARIA = MAQUINARIA_ID;
    public static final String GET_MAQUINARIAS = MAQUINARIAS;
    public static final String GET_MAQUINARIA_BY_ID = MAQUINARIA_ID;
    
    private static final String MATERIAL =
            new StringBuilder().append(CATALOGO).append(ENTIDAD_MATERIAL).toString(); 
    private static final String MATERIAL_ID =
            new StringBuilder().append(MATERIAL).append(DELETE).toString();
    private static final String MATERIALES = 
            new StringBuilder().append(CATALOGO).append(ENTIDAD_MATERIAL)
                    .append(PLURAL_ES).toString();
    
    public static final String POST_MATERIAL = MATERIAL;
    public static final String POST_MATERIALES = MATERIALES;
    public static final String UPDATE_MATERIAL = MATERIAL;
    public static final String DELETE_MATERIAL = MATERIAL_ID;
    public static final String GET_MATERIALES = MATERIALES;
    public static final String GET_MATERIAL_BY_ID = MATERIAL_ID;
    
    private static final String VEHICULO =
            new StringBuilder().append(CATALOGO).append(ENTIDAD_VEHICULO).toString(); 
    private static final String VEHICULO_ID =
            new StringBuilder().append(VEHICULO).append(DELETE).toString();
    private static final String VEHICULOS = 
            new StringBuilder().append(CATALOGO).append(ENTIDAD_VEHICULO)
                               .append(PLURAL_S).toString();
    
    public static final String POST_VEHICULO = VEHICULO;
    public static final String POST_VEHICULOS = VEHICULOS;
    public static final String UPDATE_VEHICULO = VEHICULO;
    public static final String DELETE_VEHICULO = VEHICULO_ID;
    public static final String GET_VEHICULOS = VEHICULOS;
    public static final String GET_VEHICULO_BY_ID = VEHICULO_ID;

    /*
    *********************************************************************
    */
    private static final String RH = 
            new StringBuilder().append(SERVIDOR).append(MODULO_RH).toString();
    
    private static final String CHOFER = 
            new StringBuilder().append(RH).append(ENTIDAD_CHOFER).toString();
    private static final String CHOFER_ID = 
            new StringBuilder().append(CHOFER).append(DELETE).toString();
    private static final String CHOFERES = 
            new StringBuilder().append(RH).append(ENTIDAD_CHOFER)
                               .append(PLURAL_ES).toString();
    
    public static final String POST_CHOFER = CHOFER;
    public static final String POST_CHOFERES = CHOFERES;
    public static final String UPDATE_CHOFER = CHOFER;
    public static final String DELETE_CHOFER = CHOFER_ID;
    public static final String GET_CHOFERES = CHOFERES;
    public static final String GET_CHOFER_BY_ID = CHOFER_ID;
    
    private static final String ADMINISTRATIVO = 
            new StringBuilder().append(RH).append(ENTIDAD_ADMINISTRATIVO).toString();
    private static final String ADMINISTRATIVO_ID = 
            new StringBuilder().append(ADMINISTRATIVO).append(DELETE).toString();
    private static final String ADMINISTRATIVOS = 
            new StringBuilder().append(RH).append(ENTIDAD_ADMINISTRATIVO)
                               .append(PLURAL_S).toString();
    
    public static final String POST_ADMINISTRATIVO = ADMINISTRATIVO;
    public static final String POST_ADMINISTRATIVOS = ADMINISTRATIVOS;
    public static final String UPDATE_ADMINISTRATIVO = ADMINISTRATIVO;
    public static final String DELETE_ADMINISTRATIVO = ADMINISTRATIVO_ID;
    public static final String GET_ADMINISTRATIVOS = ADMINISTRATIVOS;
    public static final String GET_ADMINISTRATIVO_BY_ID = ADMINISTRATIVO_ID;
    public static final String GET_ADMINISTRATIVO_BY_USUARIO = ADMINISTRATIVO_ID;
    
    private static final String OPERATIVO = 
            new StringBuilder().append(RH).append(ENTIDAD_OPERATIVO).toString();
    private static final String OPERATIVO_ID =
            new StringBuilder().append(OPERATIVO).append(DELETE).toString();
    private static final String OPERATIVOS = 
            new StringBuilder().append(RH).append(ENTIDAD_OPERATIVO)
                               .append(PLURAL_S).toString();
    
    public static final String POST_OPERATIVO = OPERATIVO;
    public static final String POST_OPERATIVOS = OPERATIVOS;
    public static final String UPDATE_OPERATIVO = OPERATIVO;
    public static final String DELETE_OPERATIVO = OPERATIVO_ID;
    public static final String GET_OPERATIVOS = OPERATIVOS;
    public static final String GET_OPERATIVO_BY_ID = OPERATIVO_ID;
    
                                
    /*
    ********************************************************************
    */
    
    private static final String CONTABILIDAD = 
            new StringBuilder().append(SERVIDOR).append(MODULO_CONTABILIDAD).toString();
    
    private static final String CLIENTE =
            new StringBuilder().append(CONTABILIDAD).append(ENTIDAD_CLIENTE).toString();
    private static final String CLIENTE_ID = 
            new StringBuilder().append(CONTABILIDAD).append(ENTIDAD_CLIENTE)    
                               .append(DELETE).toString();
    private static final String CLIENTES = 
            new StringBuilder().append(CONTABILIDAD).append(ENTIDAD_CLIENTE)
                               .append(PLURAL_S).toString();
    
    public static final String POST_CLIENTE = CLIENTE;
    public static final String POST_CLIENTES = CLIENTES;
    public static final String UPDATE_CLIENTE = CLIENTE;
    public static final String DELETE_CLIENTE = CLIENTE_ID;
    public static final String GET_CLIENTES = CLIENTES;
    public static final String GET_CLIENTE_BY_ID = CLIENTE_ID;
    
    private static final String CUENTA =
            new StringBuilder().append(CONTABILIDAD).append(ENTIDAD_CUENTA).toString();
    private static final String CUENTA_ID = 
            new StringBuilder().append(CONTABILIDAD).append(ENTIDAD_CUENTA)    
                               .append(DELETE).toString();
    private static final String CUENTAS = 
            new StringBuilder().append(CONTABILIDAD).append(ENTIDAD_CUENTA)
                               .append(PLURAL_S).toString();
    
    public static final String POST_CUENTA = CUENTA;
    public static final String POST_CUENTAS = CUENTAS;
    public static final String UPDATE_CUENTA = CUENTA;
    public static final String DELETE_CUENTA = CUENTA_ID;
    public static final String GET_CUENTAS = CUENTAS;
    public static final String GET_CUENTA_BY_ID = CUENTA_ID;
    
    /*
    ************************************************************************
    */
    private static final String VENTAS = 
            new StringBuilder().append(SERVIDOR).append(MODULO_VENTAS).toString();
    
    private static final String BOLETA_BASCULA = 
            new StringBuilder().append(VENTAS).append(ENTIDAD_BOLETA_BASCULA).toString();
    private static final String BOLETA_BASCULA_ID = 
            new StringBuilder().append(BOLETA_BASCULA).append(DELETE).toString();
    private static final String BOLETAS_BASCULA =
            new StringBuilder().append(VENTAS).append("boletas-bascula").toString();

    public static final String POST_BOLETA_BASCULA = BOLETA_BASCULA;
    public static final String POST_BOLETAS_BASCULA = BOLETAS_BASCULA;
    public static final String UPDATE_BOLETA_BASCULA = BOLETA_BASCULA;
    public static final String DELETE_BOLETA_BASCULA = BOLETA_BASCULA_ID;
    public static final String GET_BOLETAS_BASCULA = BOLETAS_BASCULA;
    public static final String GET_BOLETA_BASCULA_BY_ID = BOLETA_BASCULA_ID;
    
    private static final String ORDEN_DE_TRABAJO = 
            new StringBuilder().append(VENTAS).append(ENTIDAD_ORDEN_DE_TRABAJO).toString();
    private static final String ORDEN_DE_TRABAJO_ID =
            new StringBuilder().append(ORDEN_DE_TRABAJO).append(DELETE).toString();
    private static final String ORDENES_DE_TRABAJO = 
            new StringBuilder().append(VENTAS).append("ordenes-de-trabajo").toString();
    
    public static final String POST_ORDEN_DE_TRABAJO = ORDEN_DE_TRABAJO;
    public static final String POST_ORDENES_DE_TRABAJO = ORDENES_DE_TRABAJO;
    public static final String UPDATE_ORDEN_DE_TRABAJO = ORDEN_DE_TRABAJO;
    public static final String DELETE_ORDEN_DE_TRABAJO = ORDEN_DE_TRABAJO_ID;
    public static final String GET_ORDENES_DE_TRABAJO = ORDENES_DE_TRABAJO;
    public static final String GET_ORDEN_DE_TRABAJO_BY_ID = ORDEN_DE_TRABAJO_ID;
    
    /*
    ************************************************************************
    */
    private static final String SEGURIDAD = 
            new StringBuilder().append(SERVIDOR).append(MODULO_SEGURIDAD).toString();
            
    public static String crearURLlogin(String usuario, String pw){
        return new StringBuilder().append(SEGURIDAD)
                    .append("verificar/usuario/").append(usuario)
                    .append("/pw/").append(pw).toString();
    }
    public static String construirURL(String URL, String arg){
        return new StringBuilder().append(URL).append(arg).toString();
    }
    
    /*
    ***********************************************************************
    */
    
    public static String getPostURL(Class<?> cls, boolean esColeccion){
        switch(cls.getSimpleName().toLowerCase()){
            case ENTIDAD_CONTENEDOR: return esColeccion ? POST_CHOFERES : POST_CHOFER;
            case "equipodetrabajo": return esColeccion ? POST_EQUIPOS_DE_TRABAJO : 
                                                         POST_EQUIPO_DE_TRABAJO;
            case ENTIDAD_GASTO: return esColeccion ? POST_GASTOS : POST_GASTO;
            case ENTIDAD_INSUMO: return esColeccion ? POST_INSUMOS : POST_INSUMO;
            case ENTIDAD_MAQUINARIA: return esColeccion ? POST_MAQUINARIAS : POST_MAQUINARIA;
            case ENTIDAD_MATERIAL: return esColeccion ? POST_MATERIALES : POST_MATERIAL;
            case ENTIDAD_VEHICULO: return esColeccion ? POST_VEHICULOS : POST_VEHICULO;
            case ENTIDAD_CHOFER: return esColeccion ? POST_CHOFERES : POST_CHOFER;
            case ENTIDAD_ADMINISTRATIVO: return esColeccion ? POST_ADMINISTRATIVOS :
                                                         POST_ADMINISTRATIVO;
            case ENTIDAD_OPERATIVO: return esColeccion ? POST_OPERATIVOS : POST_OPERATIVO;
            case "boletabascula": return esColeccion ? POST_BOLETAS_BASCULA : 
                                                       POST_BOLETA_BASCULA;
            case "ordendetrabajo": return esColeccion ? POST_ORDENES_DE_TRABAJO :
                                                         POST_ORDEN_DE_TRABAJO;
            default: return null;
        }
    }

}
