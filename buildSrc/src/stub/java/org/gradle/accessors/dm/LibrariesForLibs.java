package org.gradle.accessors.dm;

import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import org.gradle.api.internal.attributes.AttributesFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;

public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    public LibrariesForLibs(
        DefaultVersionCatalog config,
        ProviderFactory providers,
        ObjectFactory objects,
        AttributesFactory attributesFactory,
        CapabilityNotationParser capabilityNotationParser
    ) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }
}
