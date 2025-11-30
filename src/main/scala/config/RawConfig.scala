package config

// Configuration brute issue d'un fichier ou de variables d'environnement
case class RawConfig(
    host: Option[String],
    port: Option[Int],
    timeout: Option[Int]
)

// Configuration validée avec des valeurs garanties correctes
case class ValidatedConfig(
    host: String,
    port: Int,
    timeout: Int
)
