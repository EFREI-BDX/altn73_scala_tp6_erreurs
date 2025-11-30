package config

import zio.prelude.Validation

object ValidationValidator:

  // 1. Créer une fonction qui valide le port (entre 1 et 65535)
  def validatePort(port: Int): Validation[ConfigError, Int] =
    if port < 1 || port > 65535 
    then Validation.fail(InvalidPort(port))
    else Validation.succeed(port)

  // 2. Créer une fonction qui valide le host (non vide et non blanc)
  def validateHost(host: String): Validation[ConfigError, String] =
    if host.trim.nonEmpty 
    then Validation.succeed(host.trim) 
    else Validation.fail(InvalidHost(host))

  // 3. Créer une fonction qui valide le timeout (> 0)
  def validateTimeout(timeout: Int): Validation[ConfigError, Int] =
    if timeout < 0 
    then Validation.fail(InvalidTimeout(timeout)) 
    else Validation.succeed(timeout)

  // 4. Créer une fonction qui transforme une RawConfig en ValidatedConfig
  // Toutes les valeurs doivent être présentes et valides
  def validateConfig(config: RawConfig): Validation[ConfigError, ValidatedConfig] =
    // Pour chaque donnée, on doit d'abord séquencer le test "non vide" avec validateXXX
    val vPort = Validation
      .fromOptionWith(EmptyPort)(config.port)
      .flatMap(validatePort)

    val vHost = Validation
      .fromOptionWith(EmptyHost)(config.host)
      .flatMap(validateHost)

    val vTimeout = Validation
      .fromOptionWith(EmptyTimeout)(config.timeout)
      .flatMap(validateTimeout)

    // Les 3 données sont indépendantes, on peut les valider "en parallèle" pour
    // pouvoir accumuler toutes les erreurs
    Validation.validateWith(vPort, vHost, vTimeout)((port, host, timeout) =>
      ValidatedConfig(host = host, port = port, timeout = timeout)
    )
