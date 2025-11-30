package config

object EitherMain:
  @main
  def testEitherValidator(): Unit =
    val validRaw = RawConfig(Some("api.example.com"), Some(443), Some(5000))
    println(EitherValidator.validateConfig(validRaw))

    val invalidHost = RawConfig(Some(""), Some(443), Some(5000))
    println(EitherValidator.validateConfig(invalidHost))

    val invalidPort = RawConfig(Some("api.example.com"), Some(99999), Some(5000))
    println(EitherValidator.validateConfig(invalidPort))

    val partialRaw = RawConfig(None, None, Some(1000))
    println(EitherValidator.validateConfig(partialRaw))

    val completelyEmpty = RawConfig(None, None, None)
    println(EitherValidator.validateConfig(completelyEmpty))
