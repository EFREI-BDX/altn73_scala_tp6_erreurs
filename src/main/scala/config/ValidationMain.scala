package config

object ValidationMain:
  @main
  def testValidationValidator(): Unit =
    val validRaw = RawConfig(Some("api.example.com"), Some(443), Some(5000))
    println(ValidationValidator.validateConfig(validRaw))

    val invalidHost = RawConfig(Some(""), Some(443), Some(5000))
    println(ValidationValidator.validateConfig(invalidHost))

    val invalidPort = RawConfig(Some("api.example.com"), Some(99999), Some(5000))
    println(ValidationValidator.validateConfig(invalidPort))

    val partialRaw = RawConfig(None, None, Some(1000))
    println(ValidationValidator.validateConfig(partialRaw))

    val completelyEmpty = RawConfig(None, None, None)
    println(ValidationValidator.validateConfig(completelyEmpty))
