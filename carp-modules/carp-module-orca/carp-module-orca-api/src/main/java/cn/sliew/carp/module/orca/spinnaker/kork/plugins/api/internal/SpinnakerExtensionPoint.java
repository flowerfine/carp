package cn.sliew.carp.module.orca.spinnaker.kork.plugins.api.internal;

import org.pf4j.ExtensionPoint;

/**
 * Designates a Spinnaker interface or abstract class as a PF4J {@link ExtensionPoint}.
 */
public interface SpinnakerExtensionPoint extends ExtensionPoint {

    /**
     * Return the plugin ID this extension point implementation is associated with. Returns "default"
     * if extension point is not associated with a plugin.
     */
    default String getPluginId() {
        return ExtensionPointMetadataProvider.getPluginId(this);
    }

    /**
     * Spinnaker extension points are typically proxied to provide some extension invocation
     * instrumentation (logging, metrics, etc). To get the extension class type, use this method
     * instead of {@link #getClass()}.
     */
    default Class<? extends SpinnakerExtensionPoint> getExtensionClass() {
        return ExtensionPointMetadataProvider.getExtensionClass(this);
    }
}
