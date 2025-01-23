package cn.sliew.carp.module.orca.spinnaker.kork.plugins.api.internal;

import java.lang.reflect.InvocationHandler;

/**
 * When proxying an extension class, implement this interface to provide a mechanism to obtain the
 * underlying proxied class.
 */
public interface ExtensionInvocationHandler extends InvocationHandler {

    /**
     * Get the proxy target class.
     */
    Class<? extends SpinnakerExtensionPoint> getTargetClass();

    /**
     * Get the plugin ID of the proxied extension point.
     */
    String getPluginId();
}
