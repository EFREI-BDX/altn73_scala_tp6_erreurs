package config

object EitherValidator:

  // 1. Créer une fonction qui valide le port (entre 1 et 65535)
  def validatePort(port: Int): Either[ConfigError, Int] =
    if port < 1 || port > 65535 then Left(InvalidPort(port)) else Right(port)

  // 2. Créer une fonction qui valide le host (non vide et non blanc)
  def validateHost(host: String): Either[ConfigError, String] =
    if host.trim.nonEmpty then Right(host.trim) else Left(InvalidHost(host)) 

  // 3. Créer une fonction qui valide le timeout (> 0)
  def validateTimeout(timeout: Int): Either[ConfigError, Int] =
    if timeout < 0 then Left(InvalidTimeout(timeout)) else Right(timeout)

  // 4. Créer une fonction qui transforme une RawConfig en ValidatedConfig
  // Toutes les valeurs doivent être présentes et valides
  def validateConfig(config: RawConfig): Either[ConfigError, ValidatedConfig] =
    for {
      port <- config.port.toRight(EmptyPort)
      validPort <- validatePort(port)
      host <- config.host.toRight(EmptyHost)
      validHost <- validateHost(host)
      timeout <- config.timeout.toRight(EmptyTimeout)
      validTimeout <- validateTimeout(timeout)
    } yield ValidatedConfig(
      host = validHost,
      port = validPort,
      timeout = validTimeout)

  // 5. Créer une fonction qui construit une URL depuis une ValidatedConfig
  // Format: "http://host:port"
  def buildUrl(config: ValidatedConfig): String =
    s"http://${config.host}/${config.port}"




