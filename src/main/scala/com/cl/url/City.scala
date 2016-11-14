package com.cl.url

import java.util.Locale

object City extends Enumeration {
    type City = Value

    val VANCOUVER = Value( "vancouver", Country.CANADA )
    val VICTORIA = Value( "victoria", Country.CANADA )
    val WHISTLER = Value( "whistler", Country.CANADA )

    class CLCity(val name: String, val country: Country.CLCountry) extends Val( nextId, name )

    protected final def Value(name: String, country: Country.CLCountry): CLCity = new CLCity( name, country )

}

object Country extends Enumeration {
    type Country = Value

    val CANADA = Value( "Canada", Locale.ENGLISH )

    class CLCountry(val name: String, val locale: Locale) extends Val( nextId, name )

    protected final def Value(name: String, locale: Locale): CLCountry = new CLCountry( name, locale )
}

object UrlHostName {

    val domain = ".craigslist.org"

    import City._

    def getHostNameForCity(city: CLCity): String = "http://" + city.name + domain

}
