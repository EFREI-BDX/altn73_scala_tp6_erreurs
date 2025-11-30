package config

sealed trait ConfigError

case class InvalidHost(host: String) extends ConfigError
case class InvalidPort(port: Int) extends ConfigError
case class InvalidTimeout(timeout: Int) extends ConfigError


case object EmptyHost extends ConfigError
case object EmptyPort extends ConfigError
case object EmptyTimeout extends ConfigError
