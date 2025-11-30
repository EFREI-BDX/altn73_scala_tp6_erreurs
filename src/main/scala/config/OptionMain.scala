package config

object OptionMain :
  @main
  def testOptionValidator(): Unit =
    val validRaw = RawConfig(Some("api.example.com"), Some(443), Some(5000))
    println(OptionValidator.validateConfig(validRaw))

    val invalidHost = RawConfig(Some(""), Some(443), Some(5000))
    println(OptionValidator.validateConfig(invalidHost))

    val invalidPort = RawConfig(Some("api.example.com"), Some(99999), Some(5000))
    println(OptionValidator.validateConfig(invalidPort))

    val partialRaw = RawConfig(None, None, Some(1000))
    println(OptionValidator.validateConfig(partialRaw))

    val completelyEmpty = RawConfig(None, None, None)
    println(OptionValidator.validateConfig(completelyEmpty))