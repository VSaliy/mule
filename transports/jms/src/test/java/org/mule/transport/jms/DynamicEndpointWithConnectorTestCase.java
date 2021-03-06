/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.jms;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.client.LocalMuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DynamicEndpointWithConnectorTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "dynamic-endpoint-with-connector-config.xml";
    }

    @Test
    public void testDynamicEndpointAcceptsConnectorRef() throws Exception
    {
        LocalMuleClient client = muleContext.getClient();

        String testMessage = "TEST";
        DefaultMuleMessage message = new DefaultMuleMessage(testMessage, muleContext);
        message.setOutboundProperty("queueName", "test.out");

        MuleMessage test = client.send("vm://input", message, null);
        assertNotNull(test);

        MuleMessage response = client.request("jms://test.out", 5000);
        assertEquals(testMessage, response.getPayload());
    }
}
