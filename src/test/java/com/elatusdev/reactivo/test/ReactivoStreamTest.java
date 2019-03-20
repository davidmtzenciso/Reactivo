 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.test;

import com.elatusdev.reactivo.stream.StreamReactivo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.elatusdev.reactivo.streamImpl.StreamReactivoImpl;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 *
 * @author root
 */
@RunWith(JUnit4.class)
public class ReactivoStreamTest {
    
    private static StreamReactivo reactivo;
    private int i;
    
    @BeforeClass
    public static void init(){
        reactivo = new StreamReactivoImpl();

    }   
    
    @Test
    public void test(){}

    /*@Test
    public void tesTSubscriptionURL()throws InterruptedException{
        i=0;
        reactivo.subscribirse(Usuario.class,URL.crearURLlogin("admin", "admin"))
               .onResponse(obj->{
                   i++;
               })
                .onError(msg->{LOG.log(Level.SEVERE, msg); Assert.fail();})
                .onComplete();
        Thread.sleep(2000);
        Assert.assertEquals(1, i);
    }
    
    @Test
    public void testSubscriptionInCache() throws InterruptedException{
        i = 0;
        reactivo.subscribirse(Usuario.class, URL.crearURLlogin("admin", "admin"))
               .onResponse(obj->{})
                .onError(msg->{LOG.log(Level.SEVERE, msg);})
                .onComplete();
        Thread.sleep(1000);
        
        reactivo.subscribirse(Usuario.class)
               .onResponse(obj->{
                   i++;
                   Assert.assertTrue(Usuario.class.isInstance(obj));})
                .onError(msg->{LOG.log(Level.SEVERE, msg); Assert.fail();})
                .onComplete();
        Assert.assertEquals(1, i);
    }
    
    //@Test
    public void testSubscriptionInCacheWithFilter() throws InterruptedException{
        i = 0;
        reactivo.subscribirse(Material.class,URL.GET_MATERIALES)
               .onResponse(obj->{})
                .onError(msg->{LOG.log(Level.SEVERE, msg);})
                .onComplete();
        Thread.sleep(1000);
        
        reactivo.subscribirse(Material.class)
               .onResponse(obj->{
                   i++;
                   Assert.assertTrue(Material.class.isInstance(obj));})
                .onError(msg->{LOG.log(Level.SEVERE, msg); Assert.fail();})
                .filterBy(obj->((Material)obj).getNombre().equals("AAAAA"))
                .onComplete();
        Assert.assertEquals(1, i);
    }

    public void testSubscriptionPOST(){
        Material material;
        
        for(int i=0; i < 100; i++){
            material = new MaterialImpl();
            material.setId(null);
            material.setNombre("aaaa"+i);
            material.setMedicion("AAAA"+i);
            reactivo.subscribePOST(URL.POST_MATERIAL, material, 
                    obj->LOG.log(Level.INFO, obj));
    }   }
        
    */
    private static final Logger LOG = 
            Logger.getLogger(StreamReactivo.class.getName());
}
