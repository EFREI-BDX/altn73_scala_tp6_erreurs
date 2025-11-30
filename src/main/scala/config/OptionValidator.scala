package config

object OptionValidator:

  // 1. Créer une fonction qui valide le port (entre 1 et 65535)
  def validatePort(port: Int): Option[Int] =
    if port < 1 || port > 65535
    then None
    else Some(port)

  // 2. Créer une fonction qui valide le host (non vide et non blanc)
  def validateHost(host: String): Option[String] =
    if host.trim.nonEmpty
    then Some(host.trim)
    else None

  // 3. Créer une fonction qui valide le timeout (> 0)
  def validateTimeout(timeout: Int): Option[Int] =
    if timeout < 0
    then None
    else Some(timeout)

  // 4. Créer une fonction qui transforme une RawConfig en ValidatedConfig
  // Toutes les valeurs doivent être présentes et valides
  def validateConfig(config: RawConfig): Option[ValidatedConfig] =
    for {
      port <- config.port
      validPort <- validatePort(port)
      host <- config.host
      validHost <- validateHost(host)
      timeout <- config.timeout
      validTimeout <- validateTimeout(timeout)
    } yield ValidatedConfig(host = validHost, port = validPort, timeout = validTimeout)

  // 5. Créer une fonction qui construit une URL depuis une ValidatedConfig
  // Format: "http://host:port"
  def buildUrl(config: ValidatedConfig): String =
    s"http://${config.host}/${config.port}"

  // 6. BONUS : Créer une fonction qui valide avec des valeurs par défaut
  // Défauts : host = "localhost", port = 8080, timeout = 3000
  def validateConfigWithDefaults(config: RawConfig): ValidatedConfig =
    // Si un des attributs est vide ou invalide, on aura la valeur par défaut
    val port = config.port.flatMap(validatePort)
    val host = config.host.flatMap(validateHost)
    val timeout = config.timeout.flatMap(validateTimeout)

    ValidatedConfig(
      host = host.getOrElse("localhost"),
      port = port.getOrElse(8080),
      timeout = timeout.getOrElse(3000)
    )
