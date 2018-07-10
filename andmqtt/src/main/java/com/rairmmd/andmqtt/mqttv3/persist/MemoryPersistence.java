/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *    Dave Locke - initial API and implementation and/or initial documentation
 */
package com.rairmmd.andmqtt.mqttv3.persist;

import com.rairmmd.andmqtt.mqttv3.MqttClientPersistence;
import com.rairmmd.andmqtt.mqttv3.MqttPersistable;
import com.rairmmd.andmqtt.mqttv3.MqttPersistenceException;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Persistence that uses memory
 * <p>
 * In cases where reliability is not required across client or device
 * restarts memory this memory peristence can be used. In cases where
 * reliability is required like when clean session is set to false
 * then a non-volatile form of persistence should be used.
 */
public class MemoryPersistence implements MqttClientPersistence {

    private Hashtable data;

    /**
     * (non-Javadoc)
     *
     * @see MqttClientPersistence#close()
     */
    @Override
    public void close() throws MqttPersistenceException {
        data.clear();
    }

    /**
     * (non-Javadoc)
     *
     * @see MqttClientPersistence#keys()
     */
    @Override
    public Enumeration keys() throws MqttPersistenceException {
        return data.keys();
    }

    /**
     * (non-Javadoc)
     *
     * @see MqttClientPersistence#get(java.lang.String)
     */
    @Override
    public MqttPersistable get(String key) throws MqttPersistenceException {
        return (MqttPersistable) data.get(key);
    }

    /**
     * (non-Javadoc)
     *
     * @see MqttClientPersistence#open(java.lang.String, java.lang.String)
     */
    @Override
    public void open(String clientId, String serverURI) throws MqttPersistenceException {
        this.data = new Hashtable();
    }

    /**
     * (non-Javadoc)
     *
     * @see MqttClientPersistence#put(java.lang.String, MqttPersistable)
     */
    @Override
    public void put(String key, MqttPersistable persistable) throws MqttPersistenceException {
        data.put(key, persistable);
    }

    /**
     * (non-Javadoc)
     *
     * @see MqttClientPersistence#remove(java.lang.String)
     */
    @Override
    public void remove(String key) throws MqttPersistenceException {
        data.remove(key);
    }

    /**
     * (non-Javadoc)
     *
     * @see MqttClientPersistence#clear()
     */
    @Override
    public void clear() throws MqttPersistenceException {
        data.clear();
    }

    /**
     * (non-Javadoc)
     *
     * @see MqttClientPersistence#containsKey(java.lang.String)
     */
    @Override
    public boolean containsKey(String key) throws MqttPersistenceException {
        return data.containsKey(key);
    }
}
