package cn.sliew.carp.module.orca.spinnaker.api.resolver;

import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;

import javax.annotation.Nonnull;
import java.util.Collection;

import static java.lang.String.format;

/**
 * {@code StageResolver} allows for {@code StageDefinitionBuilder} retrieval via bean name or alias.
 */
public interface StageResolver {

  /**
   * Fetch a {@code StageDefinitionBuilder} by {@code type}.
   *
   * @param type StageDefinitionBuilder type
   * @return the StageDefinitionBuilder matching {@code type} or {@code typeAlias}
   * @throws DefaultStageResolver.NoSuchStageDefinitionBuilderException if StageDefinitionBuilder
   *     does not exist
   */
  @Nonnull
  default StageDefinitionBuilder getStageDefinitionBuilder(@Nonnull String type) {
    return getStageDefinitionBuilder(type, null);
  }

  /**
   * Fetch a {@code StageDefinitionBuilder} by {@code type} or {@code typeAlias}.
   *
   * @param type StageDefinitionBuilder type
   * @param typeAlias StageDefinitionBuilder alias (optional)
   * @return the StageDefinitionBuilder matching {@code type} or {@code typeAlias}
   * @throws DefaultStageResolver.NoSuchStageDefinitionBuilderException if StageDefinitionBuilder
   *     does not exist
   */
  @Nonnull
  StageDefinitionBuilder getStageDefinitionBuilder(@Nonnull String type, String typeAlias);

  class DuplicateStageAliasException extends IllegalStateException {
    DuplicateStageAliasException(String message) {
      super(message);
    }
  }

  class NoSuchStageDefinitionBuilderException extends IllegalArgumentException {
    NoSuchStageDefinitionBuilderException(
        String type, String alias, Collection<String> knownTypes) {
      super(
          format(
              "No StageDefinitionBuilder implementation for %s(alias: %s) found (knownTypes: %s)",
              type, alias, String.join(",", knownTypes)));
    }
  }
}
