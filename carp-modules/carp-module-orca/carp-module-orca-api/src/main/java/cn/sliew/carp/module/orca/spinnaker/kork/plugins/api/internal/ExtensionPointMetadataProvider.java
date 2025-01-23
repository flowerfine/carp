package cn.sliew.carp.module.orca.spinnaker.kork.plugins.api.internal;

import java.lang.reflect.Proxy;

public class ExtensionPointMetadataProvider {

    public static Class<? extends SpinnakerExtensionPoint> getExtensionClass(
            SpinnakerExtensionPoint extensionPoint) {
        if (Proxy.isProxyClass(extensionPoint.getClass())) {
            ExtensionInvocationHandler extensionInvocationHandler =
                    (ExtensionInvocationHandler) Proxy.getInvocationHandler(extensionPoint);
            return extensionInvocationHandler.getTargetClass();
        }
        return extensionPoint.getClass();
    }

    public static String getPluginId(SpinnakerExtensionPoint extensionPoint) {
        if (Proxy.isProxyClass(extensionPoint.getClass())) {
            ExtensionInvocationHandler extensionInvocationHandler =
                    (ExtensionInvocationHandler) Proxy.getInvocationHandler(extensionPoint);
            return extensionInvocationHandler.getPluginId();
        }
        return "default";
    }
}
