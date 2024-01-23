package com.github.darkrymit.demo.postgresarraydemo.hibernate;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;


/**
 * This class is needed to register custom operators for PostgreSQL in Hibernate.
 *
 * <p> Note that this class is not part of Spring Context, so it is not scanned by Spring.
 *
 * <p> This class is registered in
 * src/main/resources/META-INF/services/org.hibernate.boot.model.FunctionContributor and
 * ServiceLoader will pick it up and register it.
 *
 * @see org.hibernate.boot.model.FunctionContributor for more information.
 * @see org.hibernate.dialect.PostgreSQLDialect for the list of operators that are registered by
 * default.
 */
public class PostgreSQLOperatorsAdditionalFunctionsContributor implements FunctionContributor {

  @Override
  public void contributeFunctions(FunctionContributions functionContributions) {
    var functionRegistry = functionContributions.getFunctionRegistry();
    functionRegistry.patternDescriptorBuilder("@>", " (?1) @> (?2) ").setExactArgumentCount(2)
        .register();
    functionRegistry.patternDescriptorBuilder("<@", " (?1) <@ (?2) ").setExactArgumentCount(2)
        .register();
    functionRegistry.patternDescriptorBuilder("&&", " (?1) && (?2) ").setExactArgumentCount(2)
        .register();
    functionRegistry.patternDescriptorBuilder("||", " (?1) || (?2) ").setExactArgumentCount(2)
        .register();
  }

}
