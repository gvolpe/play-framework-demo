package model

import play.api.libs.json.Json

object Model {

  case class BaseProduct(name: String)
  case class Product(id: Long, name: String, price: Double)

  object Implicits {

    implicit val productFormat = Json.format[Product]
    implicit val baseProductFormat = Json.format[BaseProduct]

  }

}
